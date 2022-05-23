/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBUtils;


import static config.Config.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import logging.Log;

/**
 *
 * @author a21samuelnc
 */
public class SQLMgr {
    private static Connection conn;
    
    public static List<Map<String, Object>> read(String table) {
        return read(table, null, null, null);
    }
    
    public static List<Map<String, Object>> read(String table, String field, String op, String data) {
        List<Map<String, Object>> result = new ArrayList<>();
        
        try {
            updateConn();
            
            String query = field == null ?
                    "SELECT * FROM " + table :
                    "SELECT * FROM " + table + " WHERE " + field + " " + op + " " + data + ";";
            
            System.out.println(query);
            
            Statement stmt = conn.createStatement();
            
            ResultSet rset = stmt.executeQuery(query);
            
            
            while (rset.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= rset.getMetaData().getColumnCount(); i++) {
                    String col = rset.getMetaData().getColumnName(i);
                    
                    row.put(col, rset.getObject(col));
                }
                
                result.add(row);
            }
            
            for (Map<String, Object> row : result) {
                for (Map.Entry<String, Object> col : row.entrySet()) {
                    System.out.println(col.getKey() + ": " + col.getValue().toString());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return result;
    }
    
    public static boolean exists(String table, String primary, int id) {
        return false;
    }
    
    public static int update(String table, String primary, int id, Map<String, Object> data) {
        if (id != 0 && exists(table, primary, id))
            delete(table, primary, id);
        
        return insert(table, data);
    }
    
    public static boolean delete(String table, String primary, int id) {
        boolean result = false;
        
        try {
            updateConn();
            
            String query = "DELETE FROM " + table + " WHERE " + primary + " = " + id + ";";
            
            Statement stmt = conn.createStatement();

            result = stmt.executeUpdate(query) > 0;
        } catch (SQLException ex) {
            result = false;
            System.out.println(ex.getMessage());
        }
        
        return result;
    }
    
    private static int insert(String table, Map<String, Object> data) {
        int result = 0;
        
        try {
            updateConn();
            
            String cols = "";
            String values = "";
            for (Map.Entry<String,Object> entry : data.entrySet()) {
                if (!cols.equals("")) {
                    cols += ", ";
                    values += ", ";
                }
                
                cols += "\"" + entry.getKey() +"\"";
                values += "\"" + entry.getValue().toString() + "\"";
            }
            
            System.out.println(cols);
            System.out.println(values);
            
            String query = "INSERT INTO " + table + " (" + cols + ") VALUES (" + values + ");";
            
            Statement stmt = conn.createStatement();
            
            result = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            
            System.out.println(query);
            System.out.println(result);
        } catch (SQLException ex) {
            Log.writeErr(ex.getMessage());
        }
        
        return result;
    }
    
    private static void updateConn() throws SQLException {
        if (conn == null || conn.isClosed())
            conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PWD);
    }
}