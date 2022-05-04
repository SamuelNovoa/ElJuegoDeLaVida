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
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import models.Profile;

/**
 *
 * @author a21samuelnc
 */
public class UI extends JFrame implements KeyEventDispatcher, ComponentListener {
    private Universe universe;
    
    private TableTop tp;
    private ButtonsGame btns;
    private MainPanel mp;
    
    private Profile profile;
    private JLabel generation;
    
    public UI(Universe universe) {
        this.universe = universe;
        
        tp = new TableTop(this);
        btns = new ButtonsGame(this);
        generation = new JLabel("Generación: 0");
        
        mp = new MainPanel(this);
        
        add(mp);
        
        setTitle(GAME_TITLE);
        
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGTH));
        
        setBackground(new Color(0xCFD6A6));
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        
        addComponentListener(this);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
    }
    
    public void startGame(Profile profile) {
        if (this.profile != null && this.profile != profile)
            universe.reset();
        
        this.profile = profile;
        
        remove(mp);
        
        setTitle(GAME_TITLE + " (" + profile.name + ")");
        
        add(btns);        
        add(tp);
        add(generation);
        
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
    
    public ButtonsGame getBtns() {
        return btns;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        switch (e.getID()) {
            case KeyEvent.KEY_PRESSED:
                tp.keyPressed(e);
                break;
            case KeyEvent.KEY_RELEASED:
                tp.keyReleased(e);
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
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}
