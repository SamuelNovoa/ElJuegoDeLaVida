package ui;

import java.awt.GridLayout;
import javax.swing.JPanel;

import static config.Config.*;
import static game.Universe.VlcChanges.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

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
    private JButton[][] cells;

    public TableTop(UI ui) {
        super();
        
        this.ui = ui;
        cells = new JButton[TP_HEIGHT][TP_WIDTH];
        
        for (int i = 0; i < TP_HEIGHT; i++) {
            for (int j = 0; j < TP_WIDTH; j++) {
                final int row = i;
                final int col = j;
                
                JButton cell = new JButton();
                
                cell.addActionListener((ActionEvent event) -> {
                    ui.getUniverse().changeCell(row, col);
                });
        
                cell.setBackground(Color.WHITE);
                cell.setBorder(new LineBorder(Color.GRAY, 1, false));
                cell.setFocusable(false);
                
                cells[i][j] = cell;
                add(cell);
            }
        }
        
        setLayout(new GridLayout(TP_HEIGHT, TP_WIDTH));
    }
    
    public void changeCell(int row, int col, boolean isAlive) {
        cells[row][col].setBackground(isAlive ? Color.BLACK : Color.WHITE);
    }
    
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                ui.getUniverse().pause();
                break;
            case KeyEvent.VK_COMMA:
                ui.getUniverse().changeVelocity(VLC_DECREASE);
                break;
            case KeyEvent.VK_PERIOD:
                ui.getUniverse().changeVelocity(VLC_INCREASE);
                break;
            case KeyEvent.VK_F:
                ui.getUniverse().pause(true);
                break;
            default:
                break;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_F:
                ui.getUniverse().pause(false);
                break;
            default:
                break;
        }
    }
    
    public void resize() {
        float rel = (float)TP_WIDTH / (float)TP_HEIGHT;
        
        Dimension dim;
        if (getWidth() > getHeight() * rel)
            dim = new Dimension(Math.round(getHeight() * rel / TP_WIDTH), getHeight());
        else
            dim = new Dimension(getWidth(), Math.round(getWidth() / rel));
    }
}
