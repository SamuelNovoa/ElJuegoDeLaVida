package ui;

import static config.Config.*;

import java.awt.GridLayout;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import models.Profile;

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
    private final UI uiPanel;
    
    private final CellButton[][] cells;
    
    private class CellButton extends JButton implements MouseListener {
        private final byte row;
        private final byte col;

        public CellButton(byte row, byte col) {
            this.row = row;
            this.col = col;

            addMouseListener(this);

            setBackground(Color.WHITE);
            setBorder(new LineBorder(Color.GRAY, 1, false));
            setFocusable(false);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (e.getButton()) {
                case MouseEvent.BUTTON1:
                    uiPanel.getUniverse().mouseLeftClicked(row, col);
                    break;
                case MouseEvent.BUTTON3:
                    uiPanel.getUniverse().mouseRightClicked(row, col);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    public TableTop(UI ui) {
        super();
        
        this.uiPanel = ui;
        
        cells = new CellButton[TP_HEIGHT][TP_WIDTH];
        
        for (byte i = 0; i < TP_HEIGHT; i++) {
            for (byte j = 0; j < TP_WIDTH; j++) {
                CellButton cell = new CellButton(i, j);
                
                cells[i][j] = cell;
                add(cell);
            }
        }
        
        setLayout(new GridLayout(TP_HEIGHT, TP_WIDTH));
    }
    
    public void changeCellColor(int row, int col, Color color) {
        cells[row][col].setBackground(color);
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
