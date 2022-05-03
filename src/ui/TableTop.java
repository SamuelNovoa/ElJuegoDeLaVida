package ui;

import java.awt.GridLayout;
import javax.swing.JPanel;

import static config.Config.*;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

/**
 * TODO:
 *      - Click derecho para marcar figuras.
 *      - Logger en archivo de texto.
 *      - Base de datos
 *      - Menú de figuras
 * 
 * @author a21samuelnc
 */
public class TableTop extends JPanel {
    private UI ui;
    
    private int diff;
    private int vlc;
    
    private Cell[][] cells;
    
    private boolean isRunning;
    private boolean isPaused;
    
    private JLabel genLabel;
    private long generation;
    
    public TableTop(UI ui) {
        this.ui = ui;
        
        diff = DEFAULT_DIFF;
        vlc = 1;
        
        cells = new Cell[TP_HEIGTH][TP_WIDTH];
        isRunning = true;
        isPaused = true;
        
        genLabel = new JLabel("Generación: 0");
        genLabel.setAlignmentX(CENTER_ALIGNMENT);
        
        generation = 0L;
        
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(TP_HEIGTH, TP_WIDTH));

        for (int i = 0; i < TP_HEIGTH; i++) {
            for (int j = 0; j < TP_WIDTH; j++) {
                Cell cell = new Cell(this, i, j);
                cells[i][j] = cell;
                grid.add(cell);
            }
        }

        add(grid);
        add(genLabel);
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    
    public void run() throws InterruptedException {
        while (isRunning) {
            if (isPaused) {
                Thread.sleep(10);
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
        genLabel.setText("Generación: " + generation);
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
    
    public void reset() {
        isPaused = true;
        
        generation = 0;
        genLabel.setText("Generación: 0");
        
        for (Cell[] row : cells)
            for (Cell cell : row)
                cell.reset();
    }
    
    public boolean pause() {
        isPaused = !isPaused;
        
        return isPaused;
    }
    
    public void changeVelocity() {
        if (vlc >= MAX_VLC)
            vlc = 1;
        else
            vlc *= 2;
        
        diff = DEFAULT_DIFF / vlc;
    }
    
    public int getVlc() {
        return vlc;
    }
}
