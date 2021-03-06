package ui;

import static game.Universe.VlcChanges.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;

/**
 * Panel de botóns no taboeiro.
 * 
 * @author Iago Oitavén Fraga
 */
public class ButtonsPanel extends JPanel implements ActionListener {
    private UI ui;
    
    private Button pause;
    private Button reset;
    private Button vlc;
    private Button loadPattern;
    private Button savePattern;
    private Button back;
    
    /**
     * Construtor do panel.
     * 
     * @param ui A interface de usuario
     */
    public ButtonsPanel(UI ui) {
        super();
        
        this.ui = ui;
        
        setBackground(new Color(0, 0, 0, 0));
        
        pause = new Button("Iniciar");
        reset = new Button("Reiniciar");
        vlc = new Button("Velocidad: x1");
        loadPattern = new Button("Cargar figura");
        savePattern = new Button("Guardar figura");
        back = new Button("Volver atrás");
        
        pause.addActionListener(this);
        reset.addActionListener(this);
        vlc.addActionListener(this);
        loadPattern.addActionListener(this);
        savePattern.addActionListener(this);
        back.addActionListener(this);
        
        JPanel btns = new JPanel();
        btns.setLayout(new BoxLayout(btns, BoxLayout.X_AXIS));
        btns.setAlignmentX(CENTER_ALIGNMENT);
        btns.setBackground(new Color(0, 0, 0, 0));
        
        btns.add(pause);
        btns.add(Box.createRigidArea(new Dimension(25, 0)));
        btns.add(reset);
        btns.add(Box.createRigidArea(new Dimension(25, 0)));
        btns.add(vlc);
        btns.add(Box.createRigidArea(new Dimension(25, 0)));
        btns.add(loadPattern);
        btns.add(Box.createRigidArea(new Dimension(25, 0)));
        btns.add(savePattern);
        btns.add(Box.createRigidArea(new Dimension(25, 0)));
        btns.add(back);
        
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(btns);
        
        setBackground(new Color(0, 0, 0, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    
    /**
     * Método para actualizar a velocidade mostrada.
     * 
     * @param newVlc Novo multiplicador de velocidade
     */
    public void setVlc(int newVlc) {
        vlc.setText("Velocidad: x" + newVlc);
    }
    
    /**
     * Método para actualizar o estado de pausa mostrado.
     * 
     * @param newPause Novo estado de pausa
     */
    public void setPause(boolean newPause) {
        pause.setText(newPause ? "Iniciar" : "Pausar");
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == reset) {
            ui.getUniverse().reset();
        } else if (event.getSource() == pause) {
            ui.getUniverse().pause();
        } else if (event.getSource() == vlc) {
            ui.getUniverse().changeVelocity(VLC_AUTO);
        } else if (event.getSource() == loadPattern) {
            ui.openFiguresDialog();
        } else if (event.getSource() == savePattern) {
            ui.getUniverse().saveFigure();
        } else if (event.getSource() == back) {
            ui.endGame();
        }
    }
}
