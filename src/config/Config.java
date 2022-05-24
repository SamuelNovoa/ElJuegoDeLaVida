/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

/**
 * Definición das constantes globais de configuración.
 * 
 * @author Iago Oitavén Fraga e Samuel Novoa Comesaña
 */
public class Config {
    /**
     * Ruta de conexión coa base de datos.
     */
    public final static String DB_HOST = "jdbc:mysql://localhost:3306/bd_gameoflife";
    
    /**
     * Usuario da base de datos.
     */
    public final static String DB_USER = "root";
    
    /**
     * Constrasinal da base de datos.
     */
    public final static String DB_PWD  = "root";
    
    /**
     * Ancho do taboerio (0 - 255)
     */
    public final static int TP_WIDTH = 120;
    
    /**
     * Alto do taboeiro (0 - 255)
     */
    public final static int TP_HEIGHT = 80;
    
    /**
     * Tempo en milisegundos de pausa por defecto entre xeracións. Este valor define a velocidade base de todo o xogo.
     */
    public final static int DEFAULT_DIFF = 500;

    /**
     * Ancho mínimo da ventá.
     */
    public final static int MIN_WIDTH = 800;
    
    /**
     * Alto mínimo da ventá.
     */
    public final static int MIN_HEIGTH = 600;
    
    /**
     * Multiplicador máximo de velocidade permitido.
     */
    public final static int MAX_VLC = 32;
    
    /**
     * Titulo da ventá.
     */
    public final static String GAME_TITLE = "O Xogo da Vida";
    
    /**
     * Texto da ventá de información.
     */
    public final static String INFO = "\t\t\t\t\t\tCONTROLES" + "\n\t> Pulsar espacio: Iniciar/Pausar" + "\n\t> Pulsar R: Reiniciar tablero";
}
