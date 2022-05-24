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
 * Interface de usuario principal. É a raiz de toda a interface, e almacena
 * as referencias a todos os seus compoñentes.
 * 
 * @author Iago Oitavén Fraga e Samuel Novoa Comesaña
 */
public class UI extends JFrame implements KeyEventDispatcher, ComponentListener {
    private final Universe universe;

    private final TableTop tp;
    private final ButtonsPanel btns;
    private final MainPanel mp;

    private Profile profile;
    private final JLabel generation;

    private boolean blockKeyboard;

    /**
     * Construtor da interface.
     * 
     * @param universe Universo ó que está asignada
     */
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

    /**
     * Método para iniciar o xogo.
     * 
     * @param profile Perfil que inicia o xogo
     */
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

        refresh();

        tp.resize();
    }

    /**
     * Método para terminar o xogo e volver ó menú principal.
     */
    public void endGame() {
        remove(btns);
        remove(tp);
        remove(generation);

        setTitle(GAME_TITLE);

        add(mp);

        refresh();
    }

    /**
     * Método para actualizar a xeración actual na interface.
     * 
     * @param newGeneration Nova xeración
     */
    public void setGeneration(long newGeneration) {
        generation.setText("Generación: " + newGeneration);
    }

    /**
     * Método para obter o universo.
     * 
     * @return Retorna unha referencia ó universo
     */
    public Universe getUniverse() {
        return universe;
    }

    /**
     * Método para obter o taboeiro.
     * @return Retorna unha referencia ó taboeiro
     */
    public TableTop getTp() {
        return tp;
    }

    /**
     * Método para obter o menú de botóns do taboeiro.
     * @return Retorna unha referencia ó menú de botóns do taboeiro
     */
    public ButtonsPanel getBtns() {
        return btns;
    }
    
    /**
     * Método para obter o menú princiapl.
     * @return Retorna unha referencia ó menú principal
     */
    public MainPanel getMainPanel() {
        return mp;
    }

    /**
     * Método para obter o perfil en uso.
     * @return Retorna o perfil en uso
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Método para mostrar un menú de diálogo solicitando unha cadea de texto.
     * 
     * @param msg O mesaxe a mostrar
     * @return A cadea introducida polo usuario ou null se a operación é cancelada.
     */
    public String showStringDialog(String msg) {
        blockKeyboard = true;
        String resp;
        do {
            resp = JOptionPane.showInputDialog(msg);
        } while (resp != null && resp.isEmpty());
        blockKeyboard = false;

        return resp;
    }
    
    /**
     * Método para mostrar un diálogo de información.
     * 
     * @param msg Mesaxe a mostrar
     */
    public void showDialog(String msg) {
        blockKeyboard = true;
        JOptionPane.showMessageDialog(rootPane, msg);
        blockKeyboard = false;
    }
    
    /**
     * Método para mostrar un diálogo de confirmación.
     * 
     * @param msg O mesaxe a mostrar
     * @return True en caso o usuario acepte o diálogo e false en caso contrario
     */
    public boolean showConfirmDialog(String msg) {
        blockKeyboard = true;
        boolean result = JOptionPane.showConfirmDialog(rootPane, msg, msg, JOptionPane.YES_NO_OPTION) == 0;
        blockKeyboard = false;
        
        return result;
    }
    
    /**
     * Método par aobter o diálogo de figuras.
     */
    public void openFiguresDialog() {
        FiguresDialog figuresDialog = new FiguresDialog(this);
        
        figuresDialog.setVisible(true);
    }

    /**
     * Método para refrescar a interface.
     */
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
