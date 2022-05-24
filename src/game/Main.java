/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import logging.Log;

/**
 *
 * @author a21samuelnc
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Universe universe = new Universe();
        
        try {
            universe.run();
        } catch (InterruptedException ex) {
            Log.writeErr(ex.getMessage());
        }
    }
    
}
