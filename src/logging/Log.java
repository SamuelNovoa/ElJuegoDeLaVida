package logging;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import models.Profile;

/**
 * Clase para xestionar os logs.
 * 
 * @author Iago Oitavén Fraga e Samuel Novoa Comesaña
 */
public class Log {
    /**
     * Escritura en arquivo marcando como error.
     * 
     * @param msg co string do error
     */
    public static void writeErr(String msg) {
        write("log.txt", ("Error: " + msg));
    }
    
    /**
     * Escritura en arquivo marcando como advertencia.
     * 
     * @param msg co string da advertencia
     */
    public static void writeWarning(String msg) {
        write("log.txt", ("Warning: " + msg));
    }
    
    /**
     * Rexistrar os datos obtidos polo usuario no xogo.
     * 
     * @param user Obxeto co perfil do usuario
     */
    public static void writePlayer(Profile user) {
        write("logUsers.txt", ("User: " + user.name));
    }
    
    /**
     * Escribe no ficheiro, engadindo ao final deste os novos datos.
     * 
     * @param txt string co nome do arquivo de escritura
     * @param msg string co mensaxe a escribir no arquivo
     */
    private static void write(String txt, String msg) {
        FileWriter fichero = null;
        try {
            fichero = new FileWriter(txt, true);
            fichero.write(msg + "\t(" + LocalDateTime.from(LocalDateTime.now()) + ")\n");
        } catch (Exception e) {
        } finally {
            try {
                fichero.close();
            } catch(IOException e) {
            }
        }
    }
    
}
