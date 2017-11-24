/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyzdrivers.services;

import com.xyzdrivers.models.User;
import com.xyzdrivers.repositories.RepositoryException;
import com.xyzdrivers.repositories.UserRepo;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Toneo
 */
@RequestScoped
public class UserService {
    
    @Inject
    private UserRepo userRepo;
    
    public UserService() {
        // Parameterless constructor for Dependency Injection
    }
    
    public UserService(UserRepo userRepo) {
        // For unit testing.
        this.userRepo = userRepo;
    }
    
    public boolean checkLoginDetails(String userId, String password) throws RepositoryException {
        
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null or blank.");
        }
        
        if (password == null) {
            throw new IllegalArgumentException("password cannot be null or blank.");
        }
        
        User user = getUserRepo().get(userId);
        
        if (user == null) {
            return false;
        }
        
        return password.equals(user.getPassword());
        
    }
    
    private UserRepo getUserRepo()
    {
        if (userRepo == null) {
            throw new IllegalStateException("UserRepo has not been properly initialised. Check dependency injection.");
        }
        
        return userRepo;
    }
    
}
