package ui;

import static config.Config.*;
import game.Universe;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import models.Profile;

/**
 *
 * @author a21samuelnc
 */
public class UI extends JFrame implements KeyEventDispatcher, ComponentListener {

    private final Universe universe;

    private final TableTop tp;
    private final ButtonsPanel btns;
    private final MainPanel mp;

    private Profile profile;
    private final JLabel generation;

    private boolean blockKeyboard;

    public UI(Universe universe) {
        super(GAME_TITLE);

        this.universe = universe;

        tp = new TableTop(this);
        btns = new ButtonsPanel(this);
        generation = new JLabel("Generación: 0");

        blockKeyboard = false;

        mp = new MainPanel(this);
        
        profile = null;

        add(mp);

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGTH));

        getContentPane().setBackground(new Color(0xCFD6A6));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        addComponentListener(this);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
    }

    public void startGame(Profile profile) {
        if (this.profile != null && this.profile != profile) {
            universe.reset();
        }

        this.profile = profile;

        remove(mp);

        setTitle(GAME_TITLE + " (" + profile.name + ")");

        add(btns);
        add(tp);
        add(generation);
        add(Box.createRigidArea(new Dimension(0, 25)));

        revalidate();
        repaint();

        tp.resize();
    }

    public void endGame() {
        remove(btns);
        remove(tp);
        remove(generation);

        setTitle(GAME_TITLE);

        add(mp);

        revalidate();
        repaint();
    }

    public void setGeneration(long newGeneration) {
        generation.setText("Generación: " + newGeneration);
    }

    public Universe getUniverse() {
        return universe;
    }

    public TableTop getTp() {
        return tp;
    }

    public ButtonsPanel getBtns() {
        return btns;
    }

    public Profile getProfile() {
        return profile;
    }

    public MainPanel getMainPanel() {
        return mp;
    }

    public String showStringDialog(String msg) {
        blockKeyboard = true;
        String resp;
        do {
            resp = JOptionPane.showInputDialog(msg);
        } while (resp != null && resp.isEmpty());
        blockKeyboard = false;

        return resp;
    }
    
    public void showDialog(String msg) {
        blockKeyboard = true;
        JOptionPane.showMessageDialog(rootPane, msg);
        blockKeyboard = false;
    }
    
    public boolean showConfirmDialog(String msg) {
        blockKeyboard = true;
        boolean result = JOptionPane.showConfirmDialog(rootPane, null, msg, JOptionPane.YES_NO_OPTION) == 0;
        blockKeyboard = false;
        
        return result;
    }
    
    public void openFiguresPanel() {
        FiguresPanel figPanel = new FiguresPanel(this);
        
        figPanel.setVisible(true);
    }

    public void refresh() {
        revalidate();
        repaint();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (blockKeyboard) {
            return false;
        }

        switch (e.getID()) {
            case KeyEvent.KEY_PRESSED:
                universe.keyPressed(e);
                break;
            case KeyEvent.KEY_RELEASED:
                universe.keyReleased(e);
                break;
            default:
                break;
        }

        e.consume();
        return true;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        tp.resize();
    }

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}
}
