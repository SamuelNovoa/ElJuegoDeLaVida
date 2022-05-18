/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 *
 * @author a21samuelnc
 */
public class Config {
    public final static String DB_HOST = "jdbc:mysql://localhost:3306/bd_gameoflife";
    public final static String DB_USER = "root";
    public final static String DB_PWD  = "root";
    
    public final static int TP_WIDTH = 120;
    public final static int TP_HEIGHT = 80;
    public final static int DEFAULT_DIFF = 500;

    public final static int MIN_WIDTH = 800;
    public final static int MIN_HEIGTH = 600;
    
    public final static int MAX_VLC = 32;
    
    public final static String GAME_TITLE = "El Juego de la Vida";
    
    public final static String INFO = "\t\t\t\t\t\tCONTROLES" + "\n\t> Pulsar espacio: Iniciar/Pausar" + "\n\t> Pulsar R: Reiniciar tablero";
}
