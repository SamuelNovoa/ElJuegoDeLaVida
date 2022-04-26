/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author a21samuelnc
 */
public class TableTop extends JPanel {
    private UI ui;
    
    private int diff;
    private int heigth;
    private int width;
    
    private Cell[][] cells;
    
    boolean isRunning;
    boolean isPaused;
    
    public TableTop(UI ui) {
        this.ui = ui;
        
        diff = 1000;
        heigth = 100;
        width = 100;
        
        cells = new Cell[heigth][width];
        isRunning = true;
        setSize(heigth * 10, width * 10);

        for (int i = 0; i < heigth; i++) {
            for (int j = 0; j < width; j++) {
                Cell cell = new Cell(this, i, j);
                cells[i][j] = cell;
                add(cell);
            }
        }
        
        setLayout(new GridLayout(heigth, width));
    }
    
    public void run() throws InterruptedException {
        while (isRunning) {
            if (!isPaused) {
                Thread.sleep(diff);
                update();
            }
        }
    }
    
    public void update() {
        for (Cell[] row : cells)
            for (Cell cell : row)
                cell.update();
        
        for (Cell[] row : cells)
            for (Cell cell : row)
                cell.saveState();
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
                    _row = heigth - 1;
                if (_row >= heigth)
                    _row = 0;
                
                if (_col < 0)
                    _col = width - 1;
                if (_col >= width)
                    _col = 0;
                
                if (cells[_row][_col].isAlive())
                    count++;
            }
        }

        return count;
    }
    
    public void reset() {
        for (Cell[] row : cells)
            for (Cell cell : row)
                cell.reset();
    }
    
    public void pause() {
        isPaused = !isPaused;
    }
}
