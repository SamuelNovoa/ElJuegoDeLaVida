package ui;

import static config.Config.INFO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    private final UI uiPanel;

    private final JPanel footBtns;

    private final JLabel title;

    private final Button newProfile;
    private final Button info;
    private final Button exit;

    private class ProfileButton extends Button implements MouseListener {

        private final Profile profile;

        public ProfileButton(Profile profile) {
            super(profile.name);

            this.profile = profile;

            addMouseListener(this);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (e.getButton()) {
                case MouseEvent.BUTTON1:
                    uiPanel.startGame(profile);
                    break;
                case MouseEvent.BUTTON3:
                    deleteProfile(this);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    public MainPanel(UI ui) {
        super();

        this.uiPanel = ui;

        title = new JLabel("El Juego de la Vida");

        newProfile = new Button("Crear perfil");
        info = new Button("Instruccións");
        exit = new Button("Saír");

        newProfile.addActionListener(this);
        info.addActionListener(this);
        exit.addActionListener(this);

        footBtns = new JPanel();
        footBtns.add(newProfile);
        footBtns.add(Box.createRigidArea(new Dimension(10, 0)));
        footBtns.add(info);
        footBtns.add(Box.createRigidArea(new Dimension(10, 0)));
        footBtns.add(exit);

        setBackground(new Color(0, 0, 0, 0));
        footBtns.setLayout(new BoxLayout(footBtns, BoxLayout.X_AXIS));
        footBtns.setBackground(new Color(0, 0, 0, 0));

        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("Linux Libertine Display G", Font.ITALIC, 41));

        footBtns.setAlignmentX(CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(0, 40)));
        add(title);
        add(Box.createRigidArea(new Dimension(0, 40)));

        for (Profile profile : Profile.getAll()) {
            ProfileButton btn = new ProfileButton(profile);
            btn.setAlignmentX(CENTER_ALIGNMENT);

            add(btn);
            add(Box.createRigidArea(new Dimension(0, 10)));
        }

        add(footBtns);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private void newProfile() {
        String name = uiPanel.showStringDialog("Introduce el nombre del perfil:");
        if (name == null) {
            return;
        }

        Profile profile = new Profile(name);
        profile.save();

        ProfileButton btn = new ProfileButton(profile);
        btn.setAlignmentX(CENTER_ALIGNMENT);

        add(btn, getComponents().length - 1);
        add(Box.createRigidArea(new Dimension(0, 10)), getComponents().length - 1);

        uiPanel.refresh();
    }

    private void deleteProfile(ProfileButton profileBtn) {
        Profile profile = profileBtn.profile;
        
        if (uiPanel.showConfirmDialog("¿Seguro que quieres eliminar el perfil?") && profile.id != 1) {
            remove(profileBtn);
            profile.delete();
            uiPanel.refresh();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newProfile) {
            newProfile();
        } else if (e.getSource() == info) {
            uiPanel.showDialog(INFO);
        } else if (e.getSource() == exit) {
            System.exit(0);
        }
    }
}
