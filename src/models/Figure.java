/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import DBUtils.SQLModel;
import java.io.InputStream;

/**
 *
 * @author a21samuelnc
 */
public class Figure extends SQLModel {
    public InputStream data;
    
    public Figure() {
        super("figures", "id");
        
        
    }
}