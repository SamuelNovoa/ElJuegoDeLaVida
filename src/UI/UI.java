/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import javax.swing.JFrame;

/**
 *
 * @author a21samuelnc
 */
public class UI extends JFrame {
    private TableTop tp;
    private ButtonsGame btns;
    
    public UI() {
        tp = new TableTop(this);
        btns = new ButtonsGame(this);
        
        add(tp);
        add(btns);
        
        setVisible(true);
    }
    
    public void run() {
        try {
            tp.run();
        } catch (InterruptedException ex) {
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
