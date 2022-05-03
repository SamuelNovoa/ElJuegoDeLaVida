package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import models.Profile;



/**
 *
 * @author a21iagoof
 */
public class MainPanel extends JPanel implements ActionListener {
    private UI ui;
    
    private JLabel title;
    
    private Button newProfile;
    private Button exit;
    
    private Profile[] profiles;
    
    public MainPanel(UI ui) {
        this.ui= ui;
        
        title = new JLabel("El Juego de la Vida");
        
        newProfile = new Button("Crear perfil");
        exit = new Button("Salir");
        
        profiles = getProfiles();
        
        newProfile.addActionListener(this);
        exit.addActionListener(this);
        
        JPanel footBtns = new JPanel();
        footBtns.add(newProfile);
        footBtns.add(Box.createRigidArea(new Dimension(10, 0)));
        footBtns.add(exit);
        
        
        footBtns.setLayout(new BoxLayout(footBtns, BoxLayout.X_AXIS));
        
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("Linux Libertine Display G", Font.ITALIC, 41));
        
        footBtns.setAlignmentX(CENTER_ALIGNMENT);
        
        add(Box.createRigidArea(new Dimension(0, 40)));
        add(title);
        add(Box.createRigidArea(new Dimension(0, 40)));
        
        for (Profile profile : profiles) {
            ProfileBtn btn = new ProfileBtn(profile);
            btn.addActionListener(this);
            btn.setAlignmentX(CENTER_ALIGNMENT);
            
            add(btn);
            add(Box.createRigidArea(new Dimension(0, 10)));
        }

        add(Box.createRigidArea(new Dimension(0, 200)));
        add(footBtns);
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() instanceof ProfileBtn) {
            ui.startGame(((ProfileBtn)event.getSource()).getProfile());
        } else if (event.getSource() == newProfile) {
            
        } else if (event.getSource() == exit) {
            System.exit(0);
        }
    }
    
    private Profile[] getProfiles() {
        return new Profile[] {
            new Profile("samuel"),
            new Profile("iago"),
            new Profile("pruebas"),
            new Profile("ssss"),
        };
    }
}
