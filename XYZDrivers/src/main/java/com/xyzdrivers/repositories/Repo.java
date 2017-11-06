/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyzdrivers.repositories;

import com.xyzdrivers.services.SQLService;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author arthur
 * @param <Model>
 */
public abstract class Repo<Model> {
    
    protected SQLService sql;
    
    public Repo() {
        // Update this when connection provider is added from toneos PR
        try {
            this.sql = new SQLService(DriverManager.getConnection("jdbc:derby://localhost:1527/xyzdrivers", "root", "root"));
        }
        catch (SQLException ex) {
            Logger.getLogger(Repo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    abstract Model get(int id);
    
    abstract List<Model> get();
    
    abstract List<Model> getWhere(String[] conditions);
    
    abstract List<Model> getWhere(String keyColumn, Object keyValue);
    
    abstract Model update(Model model);
    
    abstract void delete(Model model);
    
}
