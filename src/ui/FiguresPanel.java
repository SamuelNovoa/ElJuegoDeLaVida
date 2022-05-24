package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class FiguresPanel extends JDialog implements ActionListener {
    private final UI uiPanel;
    private final JScrollPane scrollPanel;
    private final JPanel contentPanel;
    private final JTextField manualInput;
    private final Button manualInputOk;
    
    private class FigureButton extends Button implements ActionListener {
        private final Figure figure;
        
        public FigureButton(Figure figure) {
            super(figure.name);
            
            this.figure = figure;
            
            setAlignmentX(CENTER_ALIGNMENT);
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
        manualInput = new JTextField();
        manualInputOk = new Button("Ok");
        manualInputOk.addActionListener(this);
        
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        if (ui.getProfile() == null)
            return;
        
        for (Figure figure : Figure.get(ui.getProfile())) {
            System.out.println(figure.name);
            contentPanel.add(new FigureButton(figure));
        }
        
        contentPanel.add(manualInput);
        contentPanel.add(manualInputOk);
        
        setSize(300, 250);
        add(scrollPanel);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String hexa = manualInput.getText();
        
        System.out.println(hexa);
        Figure figure = new Figure();
    }
}
