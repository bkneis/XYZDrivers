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
import javax.inject.Inject;

/**
 *
 * @author arthur
 * @param <Model>
 */
public abstract class Repo<Model> {
    
    @Inject
    protected SQLService sqlService;
    
    abstract Model get(int id);
    
    abstract List<Model> get();
    
    abstract List<Model> getWhere(String[] conditions);
    
    abstract Model update(Model model);
    
    abstract void delete(Model model);
    
}
