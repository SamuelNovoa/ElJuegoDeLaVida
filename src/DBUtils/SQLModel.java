/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBUtils;

import DBUtils.SQLMgr.Condition;

/**
 *
 * @author a21samuelnc
 */
public interface SQLModel {
    public abstract Condition[] getPrimary();
    public abstract void save();
    public abstract void delete();
}
