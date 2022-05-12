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
    
    public static Figure get(int id) {
        return null;
    }
    
    public static Figure get(String field, String op, String value) {
        return null;
    }
    
    public Figure() {
        table = "figures";
        primary = "id";
    }
    
    public Figure(Profile profile, String name, InputStream data) {
        this();
        
        this.profile = profile;
        this.name = name;
        this.data = data;
        
        id = 2;
    }
    
    
}