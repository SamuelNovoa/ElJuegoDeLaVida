/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import models.Figure;

/**
 *
 * @author iagoo
 */
public class FiguresBtns extends Button implements ActionListener {
    private UI ui;
    
    private Figure figure;

    public FiguresBtns(Figure figure, UI ui) {
        super(figure.name);
        this.figure = figure;
        this.ui = ui;
        addActionListener(this);
    }

    public Figure getFigure() {
        return figure;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        ui.getUniverse().spawnFigure(figure, 10, 10);
//        ui.getFiguresPanel().closePanel();
    }
    
    
}
