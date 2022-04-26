package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import static config.Config.*;
import javax.swing.BoxLayout;

/**
 *
 * @author a21iagoof
 */
public class ButtonsGame extends JPanel implements ActionListener {
    private UI ui;
    
    private JButton pause;
    private JButton reset;
    private JButton vlc;
    private JButton back;

    public ButtonsGame(UI ui) {
        this.ui = ui;
        
        pause = new JButton("Iniciar/Pausar");
        reset = new JButton("Reiniciar");
        vlc = new JButton("Aumentar velocidade");
        back = new JButton("Volver atrás");
        
        pause.addActionListener(this);
        reset.addActionListener(this);
        vlc.addActionListener(this);
        back.addActionListener(this);
        
        add(pause);
        add(reset);
        add(vlc);
        add(back);
        
        setSize(TP_WIDTH * CELL_WIDTH, BTNS_HEIGTH);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == reset) {
            // 
        } else if (event.getSource() == pause) { //  start - pause
            ui.getTp().pause();
        } else if (event.getSource() == vlc) {
            // 
        } else if (event.getSource() == back) {
            // 
        }
    }
    
}
