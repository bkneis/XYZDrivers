/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyzdrivers.tests;

import com.xyzdrivers.models.User;
import org.mockito.ArgumentMatcher;

/**
 *
 * @author Toneo
 */
public class UserMatcher implements ArgumentMatcher<User> {

    private final User user;
    
    public UserMatcher(User user)
    {
        this.user = user;
    }
    
    @Override
    public boolean matches(User needle) {
        // If either is null, check that both are null to avoid dereferencing null references
        if (needle == null || user == null)
        {
            return user == needle;
        }
        
        if (!needle.getId().equals(user.getId()))
            return false;
        
        if (!needle.getPassword().equals(user.getPassword()))
            return false;
        
        return (needle.getStatus().equals(user.getStatus()));
    }
    
}
