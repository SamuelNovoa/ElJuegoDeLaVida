package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author a21iagoof
 */
public class ButtonsGame extends JPanel implements ActionListener {
    private JButton pause;
    private JButton reset;
    private JButton vlc;
    private JButton back;

    public ButtonsGame(TableTop tp) {
        add(pause = new JButton("Iniciar/Pausar"));
        add(reset = new JButton("Reiniciar"));
        add(vlc = new JButton("Aumentar velocidade"));
        add(back = new JButton("Volver atrás"));
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == reset) {
            // 
        } else if (event.getSource() == pause) { //  start - pause
            // 
        } else if (event.getSource() == vlc) {
            // 
        } else if (event.getSource() == back) {
            // 
        }
    }
    
}
