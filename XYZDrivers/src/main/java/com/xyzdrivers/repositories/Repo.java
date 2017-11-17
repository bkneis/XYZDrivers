package com.xyzdrivers.repositories;

import com.xyzdrivers.services.SQLService;
import java.util.List;
import javax.inject.Inject;

/**
 * Abstract repo for model specific repositories to inherit as an interface and include the sqlService for db operations
 * 
 * @author arthur
 * @param <Model>
 * @param <PrimaryKey>
 */
public abstract class Repo<Model, PrimaryKeyType> {
    
    @Inject
    protected SQLService sqlService;

    abstract Model get(PrimaryKeyType id) throws RepositoryException;

    abstract List<Model> get() throws RepositoryException;
    
    abstract List<Model> getWhere(String keyColumn, Object keyValue) throws RepositoryException;
    
    abstract void insert(Model model) throws RepositoryException;
    
    abstract void update(Model model) throws RepositoryException;
    
    abstract void delete(Model model) throws RepositoryException;
    
}
