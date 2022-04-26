/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import DBUtils.Model;

/**
 *
 * @author a21samuelnc
 */
public class Player extends Model {
    public String name;
    
    public Player(String name) {
        super("players", "id");
        
        this.name = name;
    }
    
    
}
