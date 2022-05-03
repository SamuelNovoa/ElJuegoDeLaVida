/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

/**
 *
 * @author a21samuelnc
 */
public class Button extends JButton {
    Button(String text) {
        super(text);
        
        setFocusPainted(false);
        setMargin(new Insets(3, 15, 3, 15));
        
        setMinimumSize(new Dimension(200, 50));
        setMaximumSize(new Dimension(200, 50));
        
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        
        setFont(new Font("Helvetica Neue", Font.ROMAN_BASELINE, 16));
        setForeground(new Color(0xFFFFFF));
        setBackground(new Color(0x80A7A9));
    }
}
