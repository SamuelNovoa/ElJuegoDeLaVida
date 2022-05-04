package game;

/**
 *
 * @author a21samuelnc
 */
public class Cell {
    private Universe universe;
    
    private boolean isAlive;
    private boolean isAliveTemp;
    
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
        isAlive = !isAlive;
        
        universe.getUI().getTp().changeCell(row, col, isAlive);
    }
}
