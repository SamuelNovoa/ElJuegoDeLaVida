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
import java.sql.ResultSet;
import java.sql.PreparedStatement;
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
    public static class Condition {
        public String field;
        public String op;
        public Object value;
        
        public Condition(String field, String op, Object value) {
            this.field = field;
            this.op = op;
            this.value = value;
        }
    }
    
    public static List<Map<String, Object>> read(String table, Condition[] conditions) {
        List<Map<String, Object>> result = new ArrayList<>();
        
        try (Connection conn = getConn()) {
            String query;
            
            if (conditions != null && conditions.length != 0)
                query = "SELECT * FROM `" + table + "` WHERE " + getConds(conditions) + ";";
            else
                query = "SELECT * FROM `" + table + "`;";

            PreparedStatement stmt = conn.prepareStatement(query);
            
            if (conditions != null && conditions.length != 0)
                for (int i = 0; i < conditions.length; i++)
                    stmt.setObject(i + 1, conditions[i].value);
            
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                Map<String, Object> tuple = new HashMap<>();
                for (int i = 1; i <= rset.getMetaData().getColumnCount(); i++) {
                    String col = rset.getMetaData().getColumnName(i);
                    
                    tuple.put(col, rset.getObject(col));
                }
                
                result.add(tuple);
            }
        } catch (SQLException ex) {
            Log.writeErr(ex.getMessage());
        }
        
        return result;
    }
    
    public static boolean exists(String table, Condition[] conditions) {
        return !read(table, conditions).isEmpty();
    }
    
    public static int insert(String table, Map<String, Object> tuple) {
        int result = -1;
        
        try (Connection conn = getConn()) {
            String query = "INSERT INTO `" + table + "` " + getCols(tuple) + " VALUES " + getColsValues(tuple) + ";";
            PreparedStatement stmt = conn.prepareStatement(query, new String[]{"id"});
            
            Object[] values = tuple.values().toArray();
            for (int i = 0; i < values.length; i++)
                stmt.setObject(i + 1, values[i]);
            
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next())
                result = keys.getInt(1);
        } catch (SQLException ex) {
            Log.writeErr(ex.getMessage());
        }
        
        return result;
    }
    
    public static void update(String table, Map<String, Object> tuple, Condition[] conditions) {
        try (Connection conn = getConn()) {
            String query = "UPDATE " + table + " SET " + getColsUpdate(tuple) + " WHERE " + getConds(conditions) + ";";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            Object[] values = tuple.values().toArray();
            
            int i = 1;
            for (int j = 0; j < values.length; i++, j++)
                stmt.setObject(i, values[j]);
            
            for (int j = 0; j < conditions.length; i++, j++)
                stmt.setObject(i, conditions[j].value);
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Log.writeErr(ex.getMessage());
        }
    }
    
    public static void delete(String table, Condition[] conditions) {
        try (Connection conn = getConn()) {
            String query = "DELETE FROM " + table + " WHERE " + getConds(conditions) + ";";
            
            System.out.println(query + " : " + conditions[0].value);
            PreparedStatement stmt = conn.prepareStatement(query);
            for (int i = 0; i < conditions.length; i++)
                stmt.setObject(i + 1, conditions[i].value);
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Log.writeErr(ex.getMessage());
        }
    }
    
    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(DB_HOST, DB_USER, DB_PWD);
    }
    
    private static String getConds(Condition[] conditions) {
        String conds = "";
        for (Condition condition : conditions) {
            if (!conds.equals(""))
                conds += " AND ";

            conds += "`" + condition.field + "` " + condition.op + " ?";
        }

        return conds;
    }
    
    private static String getCols(Map<String, Object> tuple) {
        String cols = "(";
        
        for (String col : tuple.keySet()) {
            if (!cols.equals("("))
                cols += ", ";
            
            cols += "`" + col + "`";
        }
        
        cols += ")";
        return cols;
    }
    
    private static String getColsValues(Map<String, Object> tuple) {
        String colsValues = "(";
        
        for (int i = 0; i < tuple.size(); i++) {
            if (!colsValues.equals("("))
                colsValues += ", ";
            
            colsValues += "?";
        }
        
        colsValues += ")";
        
        return colsValues;
    }
    
    private static String getColsUpdate(Map<String, Object> tuple) {
        String colsUpdate = "";
        
        for (Map.Entry<String, Object> entry : tuple.entrySet()) {
            if (!colsUpdate.equals(""))
                colsUpdate += ", ";
            
            colsUpdate += "`" + entry.getKey() + "`" + " = ?";
        }
        
        return colsUpdate;
    }
}