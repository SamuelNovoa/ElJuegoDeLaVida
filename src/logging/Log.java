package logging;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import models.Profile;

/**
 *
 * @author a21iagoof
 */
public class Log {
    /**
     * Escritura en arquivo marcando como error
     * @param msg co string do error
     */
    public static void writeErr(String msg) {
        write("log.txt", ("Error: " + msg));
    }
    
    /**
     * Escritura en arquivo marcando como advertencia
     * @param msg co string da advertencia
     */
    public static void writeWarning(String msg) {
        write("log.txt", ("Warning: " + msg));
    }
    
    /**
     * Rexistrar os datos obtidos polo usuario no xogo
     * @param user Obxeto co perfil do usuario
     */
    public static void writePlayer(Profile user) {
        write("logUsers.txt", ("User: " + user.name + ", Max. generación: " + user.maxGen));
    }
    
    /**
     * Escribe no ficheiro, <strong>engadindo ao final deste</strong> os novos datos
     * @param txt string co nome do arquivo de escritura
     * @param msg string co mensaxe a escribir no arquivo
     */
    private static void write(String txt, String msg) {
        FileWriter fichero = null;
        try {
            fichero = new FileWriter(txt, true);
            fichero.write(msg + "-" + LocalDateTime.from(LocalDateTime.now()) + "\n");
        } catch (Exception e) {
        } finally {
            try {
                fichero.close();
            } catch(IOException e) {
            }
        }
    }
    
}
