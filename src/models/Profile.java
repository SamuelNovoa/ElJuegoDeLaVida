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
public class Profile extends SQLModel {
    public String name;
    public long maxGen;

    public Profile() {
        super("profiles", "id");
    }
    
    public Profile(String name) {
        this();
        
        this.name = name;
        this.maxGen = 0;
    }
}
