package game;

import java.awt.Color;

/**
 * Clase que define a lóxica dunha célula.
 * 
 * @author Iago Oitavén Fraga e Samuel Novoa Comesaña
 */
public class Cell {
    private final Universe universe;
    
    private boolean isAlive;
    private boolean isAliveTemp;
    private boolean isSelected;
    
    private final int row;
    private final int col;
    
    /**
     * Constructor da célula.
     * 
     * @param universe Universo no que a célula vive
     * @param row Fila na que a célula está ubicada
     * @param col Columna na que a célula está ubicada
     */
    public Cell(Universe universe, int row, int col) {
        this.universe = universe;
        this.row = row;
        this.col = col;
        
        this.isAlive = false;
        this.isAliveTemp = false;
    }
    
    /**
     * Método chamado con cada cambio de xeración.
     * A célula analiza os seus redores en busca de células vivas, e en función
     * disto, se marca para morrer ou revivir.
     */
    public void update() {
        int cellsAlive = universe.checkAlives(row, col);
        
        isAliveTemp = (isAlive && (cellsAlive == 2 || cellsAlive == 3)) || (!isAlive && cellsAlive == 3);
    }
    
    /**
     * Método para aplicar os cambios de estado sobre a célula. Isto debe
     * facerse en dous pasos independentes para simular a simultaneidade no
     * cambio de estado de todas as células.
     */
    public void saveState() {
        if (isAlive != isAliveTemp)
            changeCell();
    }
    
    /**
     * Método para comprobar se unha célula está viva.
     * 
     * @return True en caso de que esté viva, False en caso contrario
     */
    public boolean isAlive() {
        return isAlive;
    }
    
    /**
     * Método para resetear unha célula.
     */
    public void reset() {
        changeCell(false);
    }
    
    /**
     * Método para alternar unha célula.
     */
    public void changeCell() {
        changeCell(!isAlive);
    }
    
    /**
     * Método para establecer o estado dunha célula.
     * 
     * @param alive Se a célula debe pasar a estar viva ou morta
     */
    public void changeCell(boolean alive) {
        isAlive = alive;
        
        universe.getUI().getTp().changeCellColor(row, col, getCellColor());
    }
    
    /**
     * Método para establecer unha célula como seleccionada (Cambia a cor de fondo)
     * 
     * @param selected Célula seleccionada ou liberada
     */
    public void setSelected(boolean selected) {
        isSelected = selected;
        
        universe.getUI().getTp().changeCellColor(row, col, getCellColor());
    }
    
    /**
     * Método para obter a cor da célula, dependendo do seu estado.
     * 
     * @return Cor que a célula debe ter
     */
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
