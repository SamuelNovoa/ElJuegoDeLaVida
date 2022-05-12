/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBUtils;


import static config.Config.*;

import java.sql.*;

/**
 *
 * @author a21samuelnc
 */
public class SQLMgr {
    private static Connection conn;
    
    public static ResultSet read(String table) {
        return read(table, null, null, null);
    }
    
    public static ResultSet read(String table, String field, String op, String data) {
        ResultSet rset = null;
        
        try {
            updateConn();
            
            String query = field == null ?
                    "SELECT * FROM " + table :
                    "SELECT * FROM " + table + " WHERE " + field + " " + op + " " + data + ";";
            
            System.out.println(query);
            
            Statement stmt = conn.createStatement();
            
            rset = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return rset;
    }
    
    public static boolean update(String table, String primary, int id, String[][] data) {
        if (exists(table, primary, id))
            delete(table, primary, id);
        
        
        return true;
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
    
    public static boolean exists(String table, String primary, int id) {
        return false;
    }
    
    private static void updateConn() throws SQLException {
        if (conn == null || conn.isClosed())
            conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PWD);
    }
}
