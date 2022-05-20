/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBUtils;


import static config.Config.*;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import logging.Log;
import models.Figure;
import models.Profile;

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
    
    
    
    
    
    
    /**
     * Guarda SOLO en figuras
     * @param table
     * @param name
     * @param profile
     * @param data 
     */
    public static void insert(String table, String name, int profile, InputStream data) {
        System.out.println("SQPMgr.insert-->" + data);
        String cons = "insert into figures (profile, name, data) values (?,?,?)";
        Connection con = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_HOST, DB_USER, DB_PWD);
            PreparedStatement ps = con.prepareStatement(cons);
            ps.setInt(1, profile);
            ps.setString(2, name);
            ps.setBlob(3, data);
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }
    
    
    public static void insert(String table, String name) {
        String cons = "INSERT INTO profiles (name) VALUES (?)";
        Connection con = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_HOST, DB_USER, DB_PWD);
            PreparedStatement ps = con.prepareStatement(cons);
//            ps.setString(1, table);
            ps.setString(1, name);
            
            ps.executeUpdate();
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            Log.writeErr(ex.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
            Log.writeErr(e.getMessage());
        }
    }
    
    
    /**
     * Lee SOLO figuras
     * @param prf
     * @param table
     * @return 
     */
    public static List<Figure> select(Profile prf, String table) {
        List<Figure> list = new ArrayList<>();
        String cons = "select name,data from " + table + " where profile = " + prf.id;
        Connection con = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_HOST, DB_USER, DB_PWD);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(cons);
            
            int i = 0;
            while (rs.next()) {
                System.out.println(i++);
                list.add(new Figure(prf, rs.getString(1), rs.getBinaryStream(2)));
            }
            
            
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
        
        return list;
    }
    
    
    
}
