/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import static config.Config.*;
import static game.Universe.VlcChanges.*;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import logging.Log;
import models.Figure;
import ui.UI;

/**
 * Clase que modela o universo onde viven as células.
 * 
 * @author Iago Oitavén Fraga e Samuel Novoa Comesaña
 */
public class Universe {
    private UI uiPanel;

    private boolean isRunning;
    private boolean isPaused;

    private Figure figureToSpawn;

    private int diff;
    private int vlc;

    private long generation;

    private final Cell[][] cells;
    private final Corner[] corners;

    /**
     * Estrutura de datos para almacenar o canto dunha selección.
     */
    private class Corner {
        private final byte col;
        private final byte row;

        /**
         * Constructor do canto.
         * 
         * @param row Fila seleccionada
         * @param col Columna seleccionada
         */
        public Corner(byte row, byte col) {
            this.col = col;
            this.row = row;
        }
    }

    /**
     * Enumerando de tipos de cambio de velocidade.
     */
    public enum VlcChanges {
        /** 
         * Aumento de velocidad (Duplicarla)
         */
        VLC_INCREASE,
        
        /**
         * Decremento de volicad (Dividirla por dos) 
         */
        VLC_DECREASE, 
        
        /**
         * Automático (Incrementa, pero si ha llegado al máximo, vuelve al inicio) 
         */
        VLC_AUTO
    }

    /**
     * Construtor do universo.
     */
    public Universe() {
        uiPanel = new UI(this);

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

    /**
     * Método para obter a interface de usuario.
     * 
     * @return A interface de usuario
     */
    public UI getUI() {
        return uiPanel;
    }

    /**
     * Método para iniciar o xogo.
     * 
     * @throws InterruptedException Excepción producida si se interrumpe el sleep
     */
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

    /**
     * Método para actualizar as células. Chámase en cada cambio de xeración.
     */
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
        uiPanel.setGeneration(generation);
    }

    /**
     * Método para contar cántas células vivas hai ó redor dunha posición dada.
     * 
     * @param row Fila a comprobar
     * @param col Columna a comprobar
     * @return O número de células vivas ó redor da posición
     */
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

    /**
     * Método para resetear o universo (Big Crunch ?).
     */
    public void reset() {
        pause(true);

        generation = 0;
        uiPanel.setGeneration(0);

        for (Cell[] row : cells) {
            for (Cell cell : row) {
                cell.reset();
            }
        }

        highlightSelected(false);
        corners[0] = null;
        corners[1] = null;
    }

    /**
     * Método para intercambiar a pausa do xogo.
     */
    public void pause() {
        pause(!isPaused);
    }

    /**
     * Método para pausar o xogo.
     * 
     * @param pause Se o xogo se debe pausar ou non
     */
    public void pause(boolean pause) {
        isPaused = pause;
        uiPanel.getBtns().setPause(isPaused);
    }

    /**
     * Método para cambiar a velocidade.
     * 
     * @param change Tipo de cambio realizado
     */
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
        uiPanel.getBtns().setVlc(vlc);
    }

    /**
     * Método para gardar unha figura.
     */
    public void saveFigure() {
        String name;

        name = uiPanel.showStringDialog("Introduce un nombre para la figura: ");
        if (name == null)
            return;

        Figure fig = getSelected(name);
        if (fig == null) {
            uiPanel.showDialog("¡Antes debes seleccionar una figura con click derecho!");
            return;
        }

        fig.save();
    }
    
    /**
     * Método para copiar ó portapapeis a cadea hexadecimal que define a figura seleccionada.
     */
    public void copyFigure() {
        Figure fig = getSelected("");
        if (fig == null)
            return;
        
        String hexa = fig.getHexa();
        
        StringSelection stringSelection = new StringSelection(hexa);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    /**
     * Método para establecer unha figura como seleccionada para posteriormente
     * ser spawneada.
     * 
     * @param figure Figura a spawnear
     */
    public void setFigureToSpawn(Figure figure) {
        this.figureToSpawn = figure;
    }
    
    /**
     * Método para pegar a figura representada por unha cadea hexadecimal
     * gardada no portapapeis.
     */
    public void pasteFigure() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        
        String hexa = "";
        if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor))
            try {
                hexa = clipboard.getData(DataFlavor.stringFlavor).toString();
        } catch (UnsupportedFlavorException ex) {
            Log.writeErr(ex.getMessage());
        } catch (IOException ex) {
            Log.writeErr(ex.getMessage());
        }

        setFigureToSpawn(Figure.getFromHexa(hexa));
    }

    /**
     * Método chamado cando se pulsa unha tecla.
     * 
     * @param e Datos da tecla pulsada
     */
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
            case KeyEvent.VK_C:
                if ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)
                    copyFigure();
                break;
            case KeyEvent.VK_V:
                if ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)
                    pasteFigure();
                break;
            default:
                break;
        }
    }

    /**
     * Método chamado cando se solta unha tecla.
     * 
     * @param e Datos da tecla soltada
     */
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_F:
                pause(false);
                break;
            default:
                break;
        }
    }

    /**
     * Método que se chama cando se fai click esquerdo.
     * 
     * @param row Fila pulsada
     * @param col Columna pulsada
     */
    public void mouseLeftClicked(int row, int col) {
        if (figureToSpawn == null) {
            changeCell(row, col);
        } else {
            spawnFigure(row, col);
            figureToSpawn = null;
        }
    }

    /**
     * Método que se chama cando se fai click dereito.
     * 
     * @param row Fila pulsada
     * @param col Columna pulsada
     */
    public void mouseRightClicked(byte row, byte col) {
        selectCorner(row, col);
    }

    /**
     * Método para resaltar as células seleccionadas.
     * 
     * @param highlight Se hay que resaltar ou eliminar o resalte
     */
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

    /**
     * Método para obter a figura seleccionada.
     * 
     * @param name Nome da figura que estamos a seleccionar
     * @return A figura seleccionada
     */
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
        
        highlightSelected(false);

        corners[0] = null;
        corners[1] = null;

        return new Figure(uiPanel.getProfile(), name, outStream.toByteArray());
    }

    /**
     * Método para intercambiar o estado da célula nunha posición dada.
     * 
     * @param row Fila da célula
     * @param col Columna da célula
     */
    private void changeCell(int row, int col) {
        cells[row][col].changeCell();
    }

    /**
     * Método para establecer un punto como canto de selección.
     * 
     * @param row Fila seleccionada
     * @param col Columna seleccionada
     */
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

    /**
     * Método para facer aparecer unha figura.
     * 
     * @param row Fila na que debe aparecer
     * @param col Columna na que debe aparecer
     */
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
