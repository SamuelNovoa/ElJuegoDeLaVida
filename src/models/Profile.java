/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import DBUtils.SQLMgr;
import DBUtils.SQLMgr.Condition;
import DBUtils.SQLModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author a21samuelnc
 */
public class Profile implements SQLModel {
    private static final String table = "profiles";
    
    public int id;
    public String name;
    
    public List<Figure> figures;
    
    public static Profile get(int id) {
        List<Map<String, Object>> results = SQLMgr.read(table, new Condition[] { new Condition("id", "=", id) });
        if (results.isEmpty())
            return null;
        
        Map<String, Object> tuple = results.get(0);
        Profile prof = new Profile();
        
        prof.id = ((Long)tuple.get("id")).intValue();
        prof.name = tuple.get("name").toString();
        
        return prof;
    }
    
    public static Profile[] getAll() {
        List<Map<String, Object>> results = SQLMgr.read(table, null);
        Profile[] profiles = new Profile[results.size()];

        for (int i = 0; i < results.size(); i++) {
            Map<String, Object> tuple = results.get(i);
            Profile prof = new Profile();
            
            prof.id = ((Long)tuple.get("id")).intValue();
            prof.name = tuple.get("name").toString();
            
            profiles[i] = prof;
        }
        
        return profiles;
    }
    
    public Profile() { }
    
    public Profile(String name) {
        this.name = name;
        this.id = 0;
    }
    
    @Override
    public Condition[] getPrimary() {
        return new Condition[] {
            new Condition("id", "=", id)
        };
    }
    
    @Override
    public void save() {
        Map<String, Object> tuple = new HashMap<>();
        
        tuple.put("name", name);
        
        if (id == 0)
            id = SQLMgr.insert(table, tuple);
        else
            SQLMgr.update(table, tuple, getPrimary());
    }
    
    @Override
    public void delete() {
        if (id == 0)
            return;
        
        SQLMgr.delete(table, getPrimary());
    }
}
