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

/**
 * Panel do taboeiro. É o menú principal de xogo, e nel móstranse as células.
 * 
 * @author Iago Oitavén Fraga e Samuel Novoa Comesaña
 */
public class TableTop extends JPanel {
    private final UI uiPanel;
    
    private final CellButton[][] cells;
    
    /**
     * Clase que modela os botóns ós que están asignadas as células.
     */
    private class CellButton extends JButton implements MouseListener {
        private final byte row;
        private final byte col;

        /**
         * Construtor dos botóns ós que están asignadas as células.
         * 
         * @param row Fila da célula asignada
         * @param col Columna da célula asignada
         */
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

    /**
     * Construtor do taboeiro.
     * 
     * @param ui A interface de usuario
     */
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
        
        setBackground(new Color(0, 0, 0, 0));
        setLayout(new GridLayout(TP_HEIGHT, TP_WIDTH));
    }
    
    /**
     * Método para cambiar o cor dunha célula.
     * 
     * @param row Fila da célula a cambiar
     * @param col Columna da célula a cambiar
     * @param color Novo cor da célula a cambiar
     */
    public void changeCellColor(int row, int col, Color color) {
        cells[row][col].setBackground(color);
    }
    
    /**
     * Método para reescalar o taboeiro. É chamado cada vez que se redimensiona
     * a ventá.
     */
    public void resize() {
        float rel = (float)TP_WIDTH / (float)TP_HEIGHT;
        
        Dimension dim;
        if (getWidth() > getHeight() * rel)
            dim = new Dimension(Math.round(getHeight() * rel / TP_WIDTH), getHeight());
        else
            dim = new Dimension(getWidth(), Math.round(getWidth() / rel));
    }
}
