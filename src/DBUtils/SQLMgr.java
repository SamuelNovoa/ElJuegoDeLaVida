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
 * Manager SQL
 * 
 * Esta clase ten por obxectivo centralizar toda a xestión SQL ofrecendo un API xenérico
 * para ser utilizado nos modelos.
 * 
 * @author Iago Oitavén Fraga e Samuel Novoa Comesaña
 */
public class SQLMgr {
    /**
     * Estrutura de datos para almacenar an condicións de selección.
     * 
     * @author Iago Oitavén Fraga e Samuel Novoa Comesaña
     */
    public static class Condition {
        /**
         * Columna a comprobar
         */
        public String field;
        
        /**
         * Tipo de comprobación (=, >, !=, ...)
         */
        public String op;
        
        /**
         * Valor co que se comparará a columna
         */
        public Object value;
        
        /**
         * Constructor  da condición.
         * 
         * @param field Columna a comprobar
         * @param op Tipo de comprobación (=, >, !=, ...)
         * @param value Valor co que se comparará a columna
         */
        public Condition(String field, String op, Object value) {
            this.field = field;
            this.op = op;
            this.value = value;
        }
    }
    
    /**
     * Método para ler da base de datos.
     * 
     * @param table Tabla da que se leerá
     * @param conditions Condicións de selección que se deben de cumprir
     * @return A lista de tuplas que cumpran as condicións
     */
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
    
    /**
     * Método para comprobar se un rexistro existe na base de datos.
     * 
     * @param table Tabla na que se buscará
     * @param conditions Condicións de selección que se deben de cumprir
     * @return True no caso de que algunha tupla cumpra as condicións, False en caso contrario
     */
    public static boolean exists(String table, Condition[] conditions) {
        return !read(table, conditions).isEmpty();
    }
    
    /**
     * Método para insertar unha nova tupla na base de datos.
     * 
     * @param table Tabla onde se deberá insertar
     * @param tuple Tupla a insertar
     * @return O id asignado á tupla
     */
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
    
    /**
     * Método para actualizar tuplas na base de datos.
     * 
     * @param table Tabla que se deberá de actualizar
     * @param tuple Novos datos da tupla
     * @param conditions Condicións de selección que as tuplas deben cumprir
     */
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
    
    /**
     * Método para eliminar tuplas da base de datos.
     * 
     * @param table Tabla na que se deberá eliminar
     * @param conditions Condicións de selección que se deben cumprir
     */
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
    
    /**
     * Método para establecer conexión coa base de datos.
     * 
     * @return Unha nova conexión coa base de datos
     * @throws SQLException 
     */
    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(DB_HOST, DB_USER, DB_PWD);
    }
    
    /**
     * Método para obter a string SQL de condicións de selección
     * 
     * @param conditions Condicións de selección
     * @return String SQL que define as condicións de selección
     */
    private static String getConds(Condition[] conditions) {
        String conds = "";
        for (Condition condition : conditions) {
            if (!conds.equals(""))
                conds += " AND ";

            conds += "`" + condition.field + "` " + condition.op + " ?";
        }

        return conds;
    }
    
    /**
     * Método para obter a string SQL coas columnas dunha tabla.
     * 
     * @param tuple Tupla da que se queren obter as columnas
     * @return String SQL coas columnas dunha tabla
     */
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
    
    /**
     * Método para obter a string SQL cos valores para unha inserción.
     * @param tuple Tupla da que obter a string de valores
     * @return String SQL cos valores para unha nova inserción
     */
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
    
    /**
     * Método para obter a string SQL coa relación de columnas e novos valores para un update.
     * 
     * @param tuple Tupla da que se queren obter a relación de valores para un update
     * @return String SQL coa relación de columnas e novos valores para un update
     */
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