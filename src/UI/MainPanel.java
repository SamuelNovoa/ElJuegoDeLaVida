package UI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;



/**
 *
 * @author a21iagoof
 */
public class MainPanel extends JPanel implements ActionListener {
    private UI ui;
    
    private JLabel title;
    
    private Button start;
    private Button profile;
    private Button exit;
    
    public MainPanel(UI ui) {
        this.ui= ui;
        
        title = new JLabel("El Juego de la Vida");
        start = new Button("Iniciar juego");
        profile = new Button("Seleccionar perfil");
        exit = new Button("Salir");
        
        start.addActionListener(this);
        profile.addActionListener(this);
        exit.addActionListener(this);
        
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("Linux Libertine Display G", Font.ITALIC, 41));
        
        start.setAlignmentX(CENTER_ALIGNMENT);
        profile.setAlignmentX(CENTER_ALIGNMENT);
        exit.setAlignmentX(CENTER_ALIGNMENT);
        
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(title);
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(start);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(profile);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(exit);
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == start) {
            ui.startGame();
        } else if (event.getSource() == profile) {
            
        } else if (event.getSource() == exit) {
            System.exit(0);
        }
    }
}
