/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

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
    private boolean inGame;
    
    public UI() {
        tp = new TableTop(this);
        btns = new ButtonsGame(this);
        mp = new MainPanel(this);
        
        inGame = false;
        add(mp);
        
        setTitle("El Juego de la Vida");
        
        setSize(tp.getWidth(), tp.getHeight() + btns.getHeight());
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    
    public void run() {
        try {
            tp.run();
        } catch (InterruptedException ex) {
        }
    }
    
    public void changeUI() {
        if (inGame) {
            removeAll();
            add(mp);
            validate();
            inGame= false;
        } else {
            remove(mp);
            add(btns);        
            add(tp);
            validate();
            inGame = true;
        }
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
