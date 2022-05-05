/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import static config.Config.*;
import static game.Universe.VlcChanges.*;

import java.awt.event.KeyEvent;
import ui.UI;

/**
 *
 * @author samue
 */
public class Universe {
    private UI ui;
    
    private int diff;
    private int vlc;
    
    private Cell[][] cells;
    
    private boolean isRunning;
    private boolean isPaused;
    
    private long generation;
    
    private Corner[] corners;
    
    private class Corner {
        private final int x;
        private final int y;
        
        public Corner(int y, int x) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
    
    public enum VlcChanges {
        VLC_INCREASE,
        VLC_DECREASE,
        VLC_AUTO
    }
    
    public Universe() {
        ui = new UI(this);
        
        diff = DEFAULT_DIFF;
        vlc = 1;
        
        cells = new Cell[TP_HEIGHT][TP_WIDTH];
        isRunning = true;
        isPaused = true;
        
        generation = 0L;
        
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
        for (Cell[] row : cells)
            for (Cell cell : row)
                cell.update();
        
        for (Cell[] row : cells)
            for (Cell cell : row)
                cell.saveState();
        
        generation++;
        ui.setGeneration(generation);
    }
    
    public int checkAlives(int row, int col) {
        int count = 0;
        
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0)
                    continue;
                
                int _row = row + i;
                int _col = col + j;
                
                if (_row < 0)
                    _row = TP_HEIGHT - 1;
                if (_row >= TP_HEIGHT)
                    _row = 0;
                
                if (_col < 0)
                    _col = TP_WIDTH - 1;
                if (_col >= TP_WIDTH)
                    _col = 0;
                
                if (cells[_row][_col].isAlive())
                    count++;
            }
        }

        return count;
    }
    
    public void reset() {
        pause(true);
        
        generation = 0;
        ui.setGeneration(0);
        
        for (Cell[] row : cells)
            for (Cell cell : row)
                cell.reset();
        
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
                if (vlc < MAX_VLC)
                    vlc *= 2;
                break;
            case VLC_DECREASE:
                if (vlc > 1)
                    vlc /= 2;
                break;
            default:
                break;
        }
        
        diff = DEFAULT_DIFF / vlc;
        ui.getBtns().setVlc(vlc);
    }
    
    public void changeCell(int row, int col) {
        cells[row][col].changeCell();
    }
    
    public void selectCorner(int row, int col) {
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
    
    private void highlightSelected(boolean highlight) {
        if (corners[1] == null)
            cells[corners[0].y][corners[0].x].setSelected(highlight);
        else {
            int minY = corners[0].y < corners[1].y ? corners[0].y : corners[1].y;
            int maxY = corners[0].y > corners[1].y ? corners[0].y : corners[1].y;

            int minX = corners[0].x < corners[1].x ? corners[0].x : corners[1].x;
            int maxX = corners[0].x > corners[1].x ? corners[0].x : corners[1].x;

            for (int i = minY; i <= maxY; i++)
                for (int j = minX; j <= maxX; j++)
                    cells[i][j].setSelected(highlight);
        }
    }
}
