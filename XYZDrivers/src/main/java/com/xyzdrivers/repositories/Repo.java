/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyzdrivers.repositories;

import com.xyzdrivers.services.SQLService;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author arthur
 * @param <Model>
 * @param <PrimaryKey>
 */
public abstract class Repo<Model, PrimaryKey> {
    
    @Inject
    protected SQLService sqlService;
    
    abstract Model get(PrimaryKey id) throws RepositoryException;
    
    abstract List<Model> get() throws RepositoryException;
    
    abstract List<Model> getWhere(String keyColumn, Object keyValue) throws RepositoryException;
    
    abstract void insert(Model model) throws RepositoryException;
    
    abstract void update(Model model) throws RepositoryException;
    
    abstract void delete(Model model) throws RepositoryException;
    
}
