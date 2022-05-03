package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

/**
 *
 * @author a21samuelnc
 */
public class Cell extends JButton implements ActionListener {
    private TableTop tp;
    
    private boolean isAlive;
    private boolean isAliveTemp;
    
    private int row;
    private int col;
    
    public Cell(TableTop tp, int row, int col) {
        this.tp = tp;
        this.row = row;
        this.col = col;
        
        this.isAlive = false;
        this.isAliveTemp = false;
        
        addActionListener(this);
        
        setBackground(Color.WHITE);
        setBorder(new LineBorder(Color.GRAY, 1, false));
    }
    
    public void update() {
        // Comprobar alrededores y establecer isAliveTemp
        int cellsAlive = tp.checkAlives(row, col);
        
        if (!isAlive && cellsAlive == 3)
            isAliveTemp = true;
        else if (isAlive && (cellsAlive == 2 || cellsAlive == 3))
            isAliveTemp = true;
        else 
            isAliveTemp = false;
    }
    
    public void saveState() {
        if (isAlive != isAliveTemp)
            killCell(!isAliveTemp);
    }
    
    public boolean isAlive() {
        return isAlive;
    }
    
    public void reset() {
        killCell(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        killCell(isAlive);
    }
    
    private void killCell(boolean isAlive) {
        this.isAlive = !isAlive;
        setBackground(isAlive ? Color.WHITE : Color.BLACK);
    }
}
