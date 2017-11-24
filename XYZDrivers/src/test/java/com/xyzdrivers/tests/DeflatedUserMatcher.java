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
public class DeflatedUserMatcher implements ArgumentMatcher<Object[]> {

    private final User user;
    
    public DeflatedUserMatcher(User user)
    {
        this.user = user;
    }
    
    @Override
    public boolean matches(Object[] needle) {
        // If either is null, check that both are null to avoid dereferencing null references
        if (needle == null || user == null)
        {
            return user == null && null == needle;
        }
        
        // Should have 3 elements - Id, Password, Status, in that order
        if (needle.length != 3)
            return false;
        
        if (!needle[0].equals(user.getId()))
            return false;
        
        if (!needle[1].equals(user.getPassword()))
            return false;
        
        return (needle[2].equals(user.getStatus()));
    }
    
}
