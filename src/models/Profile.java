/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import DBUtils.SQLMgr;
import DBUtils.SQLModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author a21samuelnc
 */
public class Profile extends SQLModel {
    public int id;
    public String name;
    
    public List<Figure> figures;
    
    public static Profile get(int id) {
        Profile prof = new Profile();
        
        ResultSet results = SQLMgr.read(table, primary, "=", Integer.toString(id));
        
        try {
            results.next();
            
            prof.id = results.getInt("id");
            prof.name = results.getString("name");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            prof = null;
        }
        
        return prof;
    }
    
    public static Profile[] getAll() {
        ArrayList<Profile> profiles = new ArrayList<>();
        
        ResultSet results = SQLMgr.read(table);
        
        try {
            while (results.next()) {
                Profile prof = new Profile();
                
                prof.id = results.getInt("id");
                prof.name = results.getString("name");
                
                profiles.add(prof);
            }
        } catch (SQLException ex) {
            profiles = null;
        }
        
        return profiles.toArray(new Profile[profiles.size()]);
    }

    public Profile() {
        table = "profiles";
        primary = "id";
    }
    
    public Profile(String name) {
        this();
        
        this.name = name;
    }
    
    public Profile(String name, int id) {
        this();
        
        this.name = name;
        this.id = id;
    }
    
    public void save() {
        SQLMgr.insert(table, name);
    }
    
    
}
