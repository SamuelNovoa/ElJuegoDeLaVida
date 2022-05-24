package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import models.Figure;

/**
 *
 * @author iagoo
 */
public class FiguresPanel extends JDialog {
    private final UI uiPanel;
    private final JScrollPane scrollPanel;
    private final JPanel contentPanel;
    
    private class FigureButton extends Button implements ActionListener {
        private final Figure figure;
        
        public FigureButton(Figure figure) {
            super(figure.name);
            
            this.figure = figure;
            
            addActionListener(this);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            uiPanel.getUniverse().setFigureToSpawn(figure);
            dispose();
        }
        
    }
    
    public FiguresPanel(UI ui) {
        this.uiPanel = ui;
        
        setTitle("Cargar figura");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        contentPanel = new JPanel();
        scrollPanel = new JScrollPane(contentPanel);
        
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        if (ui.getProfile() == null)
            return;
        
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        for (Figure figure : Figure.get(ui.getProfile())) {
            FigureButton figureButton = new FigureButton(figure);
            
            figureButton.setAlignmentX(CENTER_ALIGNMENT);
            contentPanel.add(figureButton);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        setMaximumSize(new Dimension(300, 400));
        setMinimumSize(new Dimension(300, 400));
        
        add(scrollPanel);
    }
}
