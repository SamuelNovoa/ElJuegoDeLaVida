/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBUtils;

import DBUtils.SQLMgr.Condition;

/**
 * Interfaz que define os métodos obrigatorios dos modelos.
 * 
 * @author Iago Oitavén Fraga e Samuel Novoa Comesaña
 */
public interface SQLModel {
    
    /**
     * Método para obter as condicións que definen a selección unequívoca dunha
     * tupla (Primary Key)
     * 
     * @return O array de condicións que identifica de forma unequívoca a tupla
     */
    public abstract Condition[] getPrimary();
    
    /**
     * Método para gardar unha tupla na base de datos.
     */
    public abstract void save();
    
    /**
     * Método para eliminar unha tupla da base de datos.
     */
    public abstract void delete();
}
