package com.xyzdrivers.repositories;

import com.xyzdrivers.services.SQLService;
import java.util.List;
import javax.inject.Inject;

/**
 * Abstract repo for model specific repositories to inherit as an interface and include the sqlService for db operations
 * 
 * @author arthur
 * @param <Model>
 */
public abstract class Repo<Model, PrimaryKeyType> {
    
    @Inject
    protected SQLService sqlService;
    
    abstract Model get(PrimaryKeyType id);
    
    abstract List<Model> get();
    
    abstract List<Model> getWhere(String[] conditions);
    
    abstract List<Model> getWhere(String keyColumn, Object keyValue);
    
    abstract Model update(Model model);
    
    abstract void delete(Model model);
    
}
