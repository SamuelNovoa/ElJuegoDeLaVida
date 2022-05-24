/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import DBUtils.SQLMgr;
import DBUtils.SQLMgr.Condition;
import DBUtils.SQLModel;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author a21samuelnc
 */
public class Figure implements SQLModel {
    private static String table = "figures";
    
    public Profile profile;
    public int id;
    public String name;
    public byte[] data;
    
    public static Figure[] get(Profile profile) {
        List<Map<String, Object>> results = SQLMgr.read(table, new Condition[] { new Condition("profile", "=", profile.id) });
        Figure[] figures = new Figure[results.size()];
        
        System.out.println(results.size());
        for (int i = 0; i < results.size(); i++) {
            Map<String, Object> tuple = results.get(i);
            Figure figure = new Figure();
            
            figure.profile = profile;
            figure.name = tuple.get("name").toString();
            figure.data = (byte[])tuple.get("data");

            figures[i] = figure;
        }
        
        return figures;
    }
    
    public Figure() { }
    
    public Figure(Profile profile, String name, byte[] data) {
        this.profile = profile;
        this.id = 0;
        this.name = name;
        this.data = data;
    }
    
    @Override
    public Condition[] getPrimary() {
        return new Condition[] {
            new Condition("profile", "=", profile.id),
            new Condition("id", "=", id)
        };
    }
    
    @Override
    public void save() {
        Map<String, Object> tuple = new HashMap<>();
        
        tuple.put("profile", profile.id);
        tuple.put("name", name);
        tuple.put("data", new ByteArrayInputStream(data));
        
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