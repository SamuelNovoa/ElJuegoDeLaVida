package logging;

import java.io.FileWriter;
import java.time.LocalDateTime;

/**
 *
 * @author a21iagoof
 */
public class Log {
    public static void whriteFile() {
        FileWriter fichero = null;
        try {
            fichero = new FileWriter("logUsers.txt", true);
            fichero.write("User: " + ", DateTTime: " + LocalDateTime.from(LocalDateTime.now()));
            fichero.close();
        } catch (Exception e) {
        }
    }
}
