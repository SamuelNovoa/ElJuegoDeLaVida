/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import DBUtils.SQLModel;

/**
 *
 * @author a21samuelnc
 */
public class Player extends SQLModel {
    public String name;
    public long maxGen;

    public Player() {
        super("players", "id");
    }
    
    public Player(String name) {
        this();
        
        this.name = name;
        this.maxGen = 0;
    }
}
