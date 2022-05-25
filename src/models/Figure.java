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
 * Modelo das figuras.
 * 
 * @author Iago Oitavén Fraga e Samuel Novoa Comesaña
 */
public class Figure implements SQLModel {
    /**
     * Nome da táboa <strong>CAMPO OBRIGATORIO NOS MODELOS.</strong>
     */
    private static String table = "figures";
    
    /**
     * Perfil ó que pertence a figura.
     */
    public Profile profile;
    
    /**
     * Id da figura.
     */
    public int id;
    
    /**
     * Nome da figura.
     */
    public String name;
    
    /**
     * Array de bytes que define a figura.
     */
    public byte[] data;
    
    /**
     * Método para obter as figuras pertencentes a un determinado perfil.
     * 
     * @param profile Perfil do que obter as figuras
     * @return Array de figuras pertencentes ó perfil dado
     */
    public static Figure[] get(Profile profile) {
        List<Map<String, Object>> results = SQLMgr.read(table, new Condition[] { new Condition("profile", "=", profile.id) });
        Figure[] figures = new Figure[results.size()];
        
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
    
    /**
     * Método para obter unha figura a partir da cadea hexadecimal que a define.
     * 
     * @param hexa A cadea hexadecimal que define a figura
     * @return A figura definida pola cadea hexadecimal
     */
    public static Figure getFromHexa(String hexa) {
        Figure figure = null;
        
        try {
            hexa = hexa.replaceAll(" ", "").replaceAll("0x", "").toUpperCase();
            byte[] data = new byte[hexa.length() / 2];

            for (int i = 0; i < hexa.length() / 2; i++) {
                String byteStr = hexa.substring(i * 2, i * 2 + 2);

                data[i] = (byte)Integer.parseInt(byteStr, 16);
            }

            figure = new Figure();
            figure.data = data;
        } catch (NumberFormatException ex) {
        }
        
        return figure;
    }
    
    /**
     * Construtor vacío da figura.
     */
    public Figure() { }
    
    /**
     * Construtor principal da figura.
     * 
     * @param profile Perfil ó que pertence a figura
     * @param name Nome da figura
     * @param data Array de bytes que define la figura
     */
    public Figure(Profile profile, String name, byte[] data) {
        this.profile = profile;
        this.id = 0;
        this.name = name;
        this.data = data;
    }
    
    /**
     * Método para obter a cadea hexadecimal que define unha figura.
     * 
     * @return A cadea hexadecimal que define a figura
     */
    public String getHexa() {
        String hexa = "0x";
        
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            
            hexa += String.format("%02X", b);
        }
        
        return hexa;
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