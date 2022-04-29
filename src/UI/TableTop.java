/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.GridLayout;
import javax.swing.JPanel;

import static config.Config.*;

/**
 *
 * @author a21samuelnc
 */
public class TableTop extends JPanel {
    private UI ui;
    
    private int diff;
    
    private Cell[][] cells;
    
    boolean isRunning;
    boolean isPaused;
    
    public TableTop(UI ui) {
        this.ui = ui;
        
        diff = DEFAULT_DIFF;
        
        cells = new Cell[TP_HEIGTH][TP_WIDTH];
        isRunning = true;
        isPaused = true;

        for (int i = 0; i < TP_HEIGTH; i++) {
            for (int j = 0; j < TP_WIDTH; j++) {
                Cell cell = new Cell(this, i, j);
                cells[i][j] = cell;
                add(cell);
            }
        }
        
        setSize(TP_WIDTH * CELL_WIDTH, TP_HEIGTH * CELL_WIDTH);
        setLayout(new GridLayout(TP_HEIGTH, TP_WIDTH));
    }
    
    public void run() throws InterruptedException {
        while (isRunning) {
            if (isPaused) {
                Thread.sleep(10);
                continue;
            }

            Thread.sleep(diff);
            update();
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
                    _row = TP_HEIGTH - 1;
                if (_row >= TP_HEIGTH)
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
    
    // Si no está pausado antes no hace nada, no da tiempo a resetear todo, y si se pausa antes, a veces, queda el anterior tablero al volver start
    public void reset() { 
        for (Cell[] row : cells)
            for (Cell cell : row)
                cell.reset();
    }
    
    public void pause() {
        isPaused = !isPaused;
    }
}
