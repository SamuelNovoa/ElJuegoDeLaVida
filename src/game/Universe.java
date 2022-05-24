/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import static config.Config.*;
import static game.Universe.VlcChanges.*;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import logging.Log;
import models.Figure;
import ui.UI;

/**
 *
 * @author samue
 */
public class Universe {

    private UI ui;

    private boolean isRunning;
    private boolean isPaused;

    private Figure figureToSpawn;

    private int diff;
    private int vlc;

    private long generation;

    private final Cell[][] cells;
    private final Corner[] corners;

    private class Corner {

        private final byte col;
        private final byte row;

        public Corner(byte row, byte col) {
            this.col = col;
            this.row = row;
        }

        public byte getCol() {
            return col;
        }

        public byte getRow() {
            return row;
        }
    }

    public enum VlcChanges {
        VLC_INCREASE,
        VLC_DECREASE,
        VLC_AUTO
    }

    public Universe() {
        ui = new UI(this);

        isRunning = true;
        isPaused = true;

        figureToSpawn = null;

        diff = DEFAULT_DIFF;
        vlc = 1;

        generation = 0L;

        cells = new Cell[TP_HEIGHT][TP_WIDTH];
        corners = new Corner[2];

        for (int i = 0; i < TP_HEIGHT; i++) {
            for (int j = 0; j < TP_WIDTH; j++) {
                Cell cell = new Cell(this, i, j);
                cells[i][j] = cell;
            }
        }
    }

    public UI getUI() {
        return ui;
    }

    public void run() throws InterruptedException {
        isRunning = true;

        while (isRunning) {
            if (isPaused) {
                Thread.sleep(20);
                continue;
            }

            update();
            Thread.sleep(diff);
        }
    }

    public void update() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                cell.update();
            }
        }

        for (Cell[] row : cells) {
            for (Cell cell : row) {
                cell.saveState();
            }
        }

        generation++;
        ui.setGeneration(generation);
    }

    public int checkAlives(int row, int col) {
        int count = 0;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                int _row = row + i;
                int _col = col + j;

                if (_row < 0) {
                    _row = TP_HEIGHT - 1;
                }
                if (_row >= TP_HEIGHT) {
                    _row = 0;
                }

                if (_col < 0) {
                    _col = TP_WIDTH - 1;
                }
                if (_col >= TP_WIDTH) {
                    _col = 0;
                }

                if (cells[_row][_col].isAlive()) {
                    count++;
                }
            }
        }

        return count;
    }

    public void reset() {
        pause(true);

        generation = 0;
        ui.setGeneration(0);

        for (Cell[] row : cells) {
            for (Cell cell : row) {
                cell.reset();
            }
        }

        highlightSelected(false);
        corners[0] = null;
        corners[1] = null;
    }

    public void pause() {
        pause(!isPaused);
    }

    public void pause(boolean pause) {
        isPaused = pause;
        ui.getBtns().setPause(isPaused);
    }

    public void changeVelocity(VlcChanges change) {
        switch (change) {
            case VLC_AUTO:
                vlc = vlc < MAX_VLC ? vlc * 2 : 1;
                break;
            case VLC_INCREASE:
                if (vlc < MAX_VLC) {
                    vlc *= 2;
                }
                break;
            case VLC_DECREASE:
                if (vlc > 1) {
                    vlc /= 2;
                }
                break;
            default:
                break;
        }

        diff = DEFAULT_DIFF / vlc;
        ui.getBtns().setVlc(vlc);
    }

    public void saveFigure() {
        String name;

        name = ui.showStringDialog("Introduce un nombre para la figura: ");
        if (name == null) {
            return;
        }

        Figure fig = getSelected(name);
        if (fig == null) {
            ui.showDialog("¡Antes debes seleccionar una figura con click derecho!");
            return;
        }

        fig.save();
    }

    public void setFigureToSpawn(Figure figure) {
        this.figureToSpawn = figure;
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                pause();
                break;
            case KeyEvent.VK_COMMA:
                changeVelocity(VLC_DECREASE);
                break;
            case KeyEvent.VK_PERIOD:
                changeVelocity(VLC_INCREASE);
                break;
            case KeyEvent.VK_F:
                pause(true);
                break;
            case KeyEvent.VK_R:
                reset();
                break;
            case KeyEvent.VK_S:
                saveFigure();
                break;
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_F:
                pause(false);
                break;
            default:
                break;
        }
    }

    public void mouseLeftClicked(int row, int col) {
        if (figureToSpawn == null) {
            changeCell(row, col);
        } else {
            spawnFigure(row, col);
            figureToSpawn = null;
        }
    }

    public void mouseRightClicked(byte row, byte col) {
        selectCorner(row, col);
    }

    private void highlightSelected(boolean highlight) {
        if (corners[0] == null && corners[1] == null)
            return;

        if (corners[0] != null && corners[1] == null) {
            cells[corners[0].row][corners[0].col].setSelected(highlight);
        } else if (corners[0] == null && corners[1] != null) {
            cells[corners[1].row][corners[1].col].setSelected(highlight);
        } else {
            byte minY = corners[0].row < corners[1].row ? corners[0].row : corners[1].row;
            byte maxY = corners[0].row > corners[1].row ? corners[0].row : corners[1].row;

            byte minX = corners[0].col < corners[1].col ? corners[0].col : corners[1].col;
            byte maxX = corners[0].col > corners[1].col ? corners[0].col : corners[1].col;

            for (byte i = minY; i <= maxY; i++)
                for (byte j = minX; j <= maxX; j++)
                    cells[i][j].setSelected(highlight);
        }
    }

    private Figure getSelected(String name) {
        if (corners[0] == null || corners[1] == null)
            return null;

        byte minY = corners[0].row < corners[1].row ? corners[0].row : corners[1].row;
        byte maxY = corners[0].row > corners[1].row ? corners[0].row : corners[1].row;

        byte minX = corners[0].col < corners[1].col ? corners[0].col : corners[1].col;
        byte maxX = corners[0].col > corners[1].col ? corners[0].col : corners[1].col;

        byte sizeY = (byte) (maxY - minY + 1);
        byte sizeX = (byte) (maxX - minX + 1);

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(outStream);

        try {
            byte b = 0;
            int remain = 8;

            data.writeByte(sizeX);
            data.writeByte(sizeY);
            for (int i = minY; i <= maxY; i++) {
                for (int j = minX; j <= maxX; j++) {
                    if (remain == 0) {
                        data.writeByte(b);
                        b = 0;

                        remain = 8;
                    }

                    remain--;

                    b = (byte) (b << 1);
                    if (cells[i][j].isAlive()) {
                        b |= 1;
                    }
                }
            }

            b = (byte) (b << remain);
            data.writeByte(b);

        } catch (IOException ex) {
            Log.writeErr(ex.getMessage());
        }

        return new Figure(ui.getProfile(), name, outStream.toByteArray());
    }

    private void changeCell(int row, int col) {
        cells[row][col].changeCell();
    }

    private void selectCorner(byte row, byte col) {
        if (corners[0] == null && corners[1] == null) {
            corners[0] = new Corner(row, col);

            highlightSelected(true);
        } else if (corners[0] != null && corners[1] == null) {
            corners[1] = new Corner(row, col);

            highlightSelected(true);
        } else {
            highlightSelected(false);

            corners[0] = null;
            corners[1] = null;
        }
    }

    private void spawnFigure(int row, int col) {
        byte cols = figureToSpawn.data[0];
        byte rows = figureToSpawn.data[1];

        int cornerCol = col - (cols / 2);
        int cornerRow = row - (rows / 2);

        int indexCol = 0;
        int indexRow = 0;

        for (int i = 2; i < figureToSpawn.data.length; i++) {
            byte b = figureToSpawn.data[i];

            for (int j = 0x80; j > 0x0; j = j >> 1) {
                int destRow = cornerRow + indexRow;
                int destCol = cornerCol + indexCol;

                if (destRow >= TP_HEIGHT) {
                    destRow -= TP_HEIGHT;
                } else if (destRow < 0) {
                    destRow += TP_HEIGHT;
                }

                if (destCol >= TP_WIDTH) {
                    destCol -= TP_WIDTH;
                } else if (destCol < 0) {
                    destCol += TP_WIDTH;
                }

                cells[destRow][destCol].changeCell((b & j) != 0);

                indexCol++;
                if (indexCol >= cols) {
                    if (indexRow >= rows) {
                        break;
                    }

                    indexCol = 0;
                    indexRow++;
                }
            }
        }
    }
}
