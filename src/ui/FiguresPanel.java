package ui;

import DBUtils.SQLMgr;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import models.Figure;
import javax.swing.*;

/**
 *
 * @author iagoo
 */
public class FiguresPanel extends JDialog {
    
    private UI ui;
    
    private List<Figure> figureList;
    
    private JPanel panel;
    private JPanel contentPane;
    private JScrollPane scrollPane;
    
    public FiguresPanel(UI ui) {
        this.ui = ui;
        setTitle("Cargar figura");
        
        
        panel = new JPanel();
        figureList = new ArrayList<>();
    }
    
    public void loadFigure() {
        figureList = SQLMgr.select(ui.getProfile(), "figures");
        getButtons();
        
        
        scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(30, 30, 300, 250);
        scrollPane.setSize(300, 250);
        contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(400, 350));
        contentPane.add(scrollPane);
        setContentPane(contentPane);
        pack();
        
        repaint();
        setVisible(true);
    }
    
        
    private void getButtons() {
        for (Figure f : figureList) {
            panel.add(new FiguresBtns(f, ui));
            panel.add(Box.createRigidArea(new Dimension(25, 0)));
        }
    }
    
    public void addFigure(FiguresBtns fig) {
        panel.add(fig);
        panel.add(Box.createRigidArea(new Dimension(25, 0)));
    }
    
    public void closePanel() {
        setVisible(false);
    }
    
}
