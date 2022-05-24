/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import logging.Log;

/**
 * Clase principal do xogo.
 * 
 * @author Fraga Oitavén Fraga e Samuel Novoa Comesaña
 */
public class Main {

    /**
     * Método de entrada del programa.
     * 
     * @param args argumentos de comando (Sin uso)
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
