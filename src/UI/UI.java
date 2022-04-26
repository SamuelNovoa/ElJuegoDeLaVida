/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author a21samuelnc
 */
public class UI extends JFrame {
    private TableTop tp;
    
    public UI() {
        tp = new TableTop();
        
        add(tp);
        
        setVisible(true);
    }
    
    public void run() {
        try {
            tp.run();
        } catch (InterruptedException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
