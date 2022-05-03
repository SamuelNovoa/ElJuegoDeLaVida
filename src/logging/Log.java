package logging;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 *
 * @author a21iagoof
 */
public class Log {
    public static void write(String msg) {
        FileWriter fichero = null;
        try {
            fichero = new FileWriter("logUsers.txt", true);
            fichero.write(msg + " -- DateTTime: " + LocalDateTime.from(LocalDateTime.now()));
            
        } catch (Exception e) {
        } finally {
            try {
                fichero.close();
            } catch(IOException e) {
            }
        }
    }
}
