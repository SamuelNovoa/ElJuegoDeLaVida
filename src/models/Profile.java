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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        
        List<Map<String, Object>> results = SQLMgr.read(table, primary, "=", Integer.toString(id));
        
        prof.id = ((Long)results.get(0).get("id")).intValue();
        prof.name = results.get(0).get("name").toString();
        
        return prof;
    }
    
    public static Profile[] getAll() {
        ArrayList<Profile> profiles = new ArrayList<>();
        
        List<Map<String, Object>> results = SQLMgr.read(table);
        
        for (Map<String, Object> row : results) {
            Profile prof = new Profile();
            
            prof.id = ((Long)row.get("id")).intValue();
            prof.name = row.get("name").toString();
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
        this.id = 0;
    }
    
    public Profile(String name, int id) {
        this();
        
        this.name = name;
        this.id = id;
    }
    
    public void save() {
        Map<String, Object> tableData = new HashMap<>();
        
        if (id != 0)
            tableData.put("id", id);
        
        tableData.put("name", name);
        
        int newId = SQLMgr.update(table, primary, id, tableData);
        if (id == 0)
            id = newId;
    }
}
