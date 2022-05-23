/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import DBUtils.SQLMgr;
import DBUtils.SQLModel;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author a21samuelnc
 */
public class Figure extends SQLModel {
    public Profile profile;
    public String name;
    public int id;
    public InputStream data;
    
    public static Figure[] get(int id) {
        return null;
    }
    
    public static Figure[] get(String field, String op, String value) {
        return null;
    }
    
    public static Figure[] getAll() {
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
    
    public void save() {
        Map<String, Object> tableData = new HashMap<>();
        
        if (id != 0)
            tableData.put("id", id);
        
        tableData.put("profile", profile);
        tableData.put("name", name);
        tableData.put("data", data);
        
        SQLMgr.update(table, primary, id, tableData);
    }
}