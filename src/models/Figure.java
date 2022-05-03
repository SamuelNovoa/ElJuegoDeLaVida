/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import DBUtils.SQLModel;

/**
 *
 * @author a21samuelnc
 */
public class Figure extends SQLModel {
    public String otra;
    
    public Figure() {
        super("figures", "id");
    }
    
    public Figure(String otra) {
        this();
        
        this.otra = otra;
    }
}