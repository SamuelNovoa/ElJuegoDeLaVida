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
public class Figure extends Model {
    public String otra;
    
    public Figure(String otra) {
        super("jajuas", "id2");
        
        this.otra = otra;
    }
    
    
}