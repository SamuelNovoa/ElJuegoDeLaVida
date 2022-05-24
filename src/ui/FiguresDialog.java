package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import models.Figure;

/**
 * Diálogo coa lista de figuras do perfil.
 * 
 * @author Iago Oitavén Fraga e Samuel Novoa Comesaña
 */
public class FiguresDialog extends JDialog implements ActionListener {
    private final UI uiPanel;
    private final JScrollPane scrollPanel;
    private final JPanel contentPanel;
    private final JTextField manualInput;
    private final Button manualInputOk;
    
    /**
     * Clase que modela os botóns de figuras.
     */
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
    
    /**
     * Construtor do diálogo.
     * 
     * @param ui A interface de usuario
     */
    public FiguresDialog(UI ui) {
        this.uiPanel = ui;
        
        setTitle("Cargar figura");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        contentPanel = new JPanel();
        scrollPanel = new JScrollPane(contentPanel);
        manualInput = new JTextField();
        manualInput.setMaximumSize(new Dimension(200, 50));
        manualInput.setMinimumSize(new Dimension(200, 50));
        manualInput.setAlignmentX(CENTER_ALIGNMENT);
        
        manualInputOk = new Button("Ok");
        manualInputOk.addActionListener(this);
        manualInputOk.setAlignmentX(CENTER_ALIGNMENT);
        
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
        
        contentPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        contentPanel.add(manualInput);
        contentPanel.add(manualInputOk);
        
        setMaximumSize(new Dimension(300, 400));
        setMinimumSize(new Dimension(300, 400));
        
        add(scrollPanel);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String hexa = manualInput.getText();
        
        System.out.println(hexa);
        Figure figure = new Figure();
    }
}
