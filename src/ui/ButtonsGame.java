package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import static config.Config.*;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;

/**
 *
 * @author a21iagoof
 */
public class ButtonsGame extends JPanel implements ActionListener {
    private UI ui;
    
    private Button pause;
    private Button reset;
    private Button vlc;
    private Button back;
    private Button addPattern;
    
    private int vlcStr;
    
    public ButtonsGame(UI ui) {
        vlcStr = 1;
        this.ui = ui;
        
        pause = new Button("Iniciar/Pausar");
        reset = new Button("Reiniciar");
        vlc = new Button("Velocidad: x" + vlcStr);
        addPattern = new Button("Cargar forma");
        back = new Button("Volver atrás");
        
        pause.addActionListener(this);
        reset.addActionListener(this);
        vlc.addActionListener(this);
        addPattern.addActionListener(this);
        back.addActionListener(this);
        
        add(pause);
        add(Box.createRigidArea(new Dimension(25, 0)));
        add(reset);
        add(Box.createRigidArea(new Dimension(25, 0)));
        add(vlc);
        add(Box.createRigidArea(new Dimension(25, 0)));
        add(addPattern);
        add(Box.createRigidArea(new Dimension(25, 0)));
        add(back);
        
        
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == reset) {
            ui.getTp().reset();
        } else if (event.getSource() == pause) {
            ui.getTp().pause();
        } else if (event.getSource() == vlc) {
            vlc.setText("Velocidad: x" + changeVelocity());
            ui.getTp().changeVelocity();
        } else if (event.getSource() == addPattern) {
            //
        } else if (event.getSource() == back) {
            ui.endGame();
        }
    }
    
    
    public int changeVelocity() {
        if (vlcStr >= 16)
            vlcStr = 1;
        else
            vlcStr *= 2;
        
        return vlcStr;
    }
}
