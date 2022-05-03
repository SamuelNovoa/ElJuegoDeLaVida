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
    
    public ButtonsGame(UI ui) {
        this.ui = ui;
        
        pause = new Button("Iniciar");
        reset = new Button("Reiniciar");
        vlc = new Button("Velocidad: x" + ui.getTp().getVlc());
        addPattern = new Button("Cargar forma");
        back = new Button("Volver atrás");
        
        pause.addActionListener(this);
        reset.addActionListener(this);
        vlc.addActionListener(this);
        addPattern.addActionListener(this);
        back.addActionListener(this);
        
        JPanel btns = new JPanel();
        btns.setLayout(new BoxLayout(btns, BoxLayout.X_AXIS));
        btns.setAlignmentX(CENTER_ALIGNMENT);
        
        btns.add(pause);
        btns.add(Box.createRigidArea(new Dimension(25, 0)));
        btns.add(reset);
        btns.add(Box.createRigidArea(new Dimension(25, 0)));
        btns.add(vlc);
        btns.add(Box.createRigidArea(new Dimension(25, 0)));
        btns.add(addPattern);
        btns.add(Box.createRigidArea(new Dimension(25, 0)));
        btns.add(back);
        
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(btns);
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == reset) {
            ui.getTp().reset();
            pause.setText("Iniciar");
        } else if (event.getSource() == pause) {
            pause.setText(ui.getTp().pause() ? "Iniciar" : "Pausar");
        } else if (event.getSource() == vlc) {
            ui.getTp().changeVelocity();
            vlc.setText("Velocidad: x" + ui.getTp().getVlc());
        } else if (event.getSource() == addPattern) {
            //
        } else if (event.getSource() == back) {
            ui.endGame();
        }
    }
}
