package ui;

import static config.Config.*;
import java.awt.Color;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

/**
 *
 * @author a21samuelnc
 */
public class UI extends JFrame {
    private TableTop tp;
    private ButtonsGame btns;
    private MainPanel mp;
    
    public UI() {
        tp = new TableTop(this);
        btns = new ButtonsGame(this);
        mp = new MainPanel(this);
        
        add(mp);
        
        setTitle("El Juego de la Vida");
        
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGTH));
        
        setBackground(new Color(0xCFD6A6));
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
    
    public void run() {
        try {
            tp.run();
        } catch (InterruptedException ex) {
        }
    }
    
    public void startGame() {
        remove(mp);
        
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(btns);        
        add(tp);
        
        revalidate();
        repaint();
    }
    
    public void endGame() {
        remove(btns);
        remove(tp);
        
        add(mp);
        
        revalidate();
        repaint();
    }

    public TableTop getTp() {
        return tp;
    }

    public void setTp(TableTop tp) {
        this.tp = tp;
    }

    public ButtonsGame getBtns() {
        return btns;
    }

    public void setBtns(ButtonsGame btns) {
        this.btns = btns;
    }
}
