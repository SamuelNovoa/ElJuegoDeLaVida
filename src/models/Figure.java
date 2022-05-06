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
    public Profile profile;
    public String name;
    public int id;
    public InputStream data;
    
    public Figure() {
        this(null, "None", null);
    }
    
    public Figure(Profile profile, String name, InputStream data) {
        super("figures", "id");
        
        this.profile = profile;
        this.name = name;
        this.data = data;
        
        id = 2;
    }
    
    
}