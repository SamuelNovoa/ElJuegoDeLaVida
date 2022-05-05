package game;

import java.awt.Color;

/**
 *
 * @author a21samuelnc
 */
public class Cell {
    private Universe universe;
    
    private boolean isAlive;
    private boolean isAliveTemp;
    private boolean isSelected;
    
    private int row;
    private int col;
    
    public Cell(Universe universe, int row, int col) {
        this.universe = universe;
        this.row = row;
        this.col = col;
        
        this.isAlive = false;
        this.isAliveTemp = false;
    }
    
    public void update() {
        // Comprobar alrededores y establecer isAliveTemp
        int cellsAlive = universe.checkAlives(row, col);
        
        if (!isAlive && cellsAlive == 3)
            isAliveTemp = true;
        else if (isAlive && (cellsAlive == 2 || cellsAlive == 3))
            isAliveTemp = true;
        else 
            isAliveTemp = false;
    }
    
    public void saveState() {
        if (isAlive != isAliveTemp)
            changeCell();
    }
    
    public boolean isAlive() {
        return isAlive;
    }
    
    public void reset() {
        changeCell(false);
    }
    
    public void changeCell() {
        changeCell(!isAlive);
    }
    
    public void changeCell(boolean alive) {
        isAlive = alive;
        
        universe.getUI().getTp().changeCellColor(row, col, getCellColor());
    }
    
    public void setSelected(boolean selected) {
        isSelected = selected;
        
        universe.getUI().getTp().changeCellColor(row, col, getCellColor());
    }
    
    private Color getCellColor() {
        Color color;
        
        if (isSelected && isAlive)
            color = new Color(0x260000);
        else if (isSelected && !isAlive)
            color = new Color(0xffb2b2);
        else if (!isSelected && isAlive)
            color = Color.BLACK;
        else
            color = Color.WHITE;
        
        return color;
    }
}
