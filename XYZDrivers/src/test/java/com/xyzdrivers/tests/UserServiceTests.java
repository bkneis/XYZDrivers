/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyzdrivers.tests;

import com.xyzdrivers.models.User;
import com.xyzdrivers.repositories.RepositoryException;
import com.xyzdrivers.repositories.UserRepo;
import com.xyzdrivers.services.UserService;
import org.junit.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.function.Executable;
import static org.mockito.Mockito.*;

/**
 *
 * @author Toneo
 */
@SuppressWarnings("ThrowableResultIgnored")
public class UserServiceTests {
    
    private UserRepo userRepo;
    private UserService userService;
    
    // Todo - tests for delete and getWhere
    
    @Before
    public void Before()
    {
        userRepo = mock(UserRepo.class);
        userService = new UserService(userRepo);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void checkLoginDetails_shouldThrowExceptionIfUserIdIsNull() throws RepositoryException
    {
        userService.checkLoginDetails(null, "password");
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void checkLoginDetails_shouldThrowExceptionIfPasswordIsNull() throws RepositoryException
    {
        userService.checkLoginDetails("userId", null);
    }
    
    @Test
    public void checkLoginDetails_shouldReturnTrueIfDetailsMatch() throws RepositoryException
    {
        User bob = new User("bob", "bob-password", "status");
        
        when(userRepo.get(bob.getId())).thenReturn(bob);
        
        assertTrue(userService.checkLoginDetails(bob.getId(), bob.getPassword()));
    }
    
    @Test
    public void checkLoginDetails_shouldReturnFalseIfUserDoesNotExist() throws RepositoryException
    {
        when(userRepo.get((String)any())).thenReturn(null);
        
        assertFalse(userService.checkLoginDetails("wrongUserId", "wrongPassword"));
    }
    
    @Test
    public void checkLoginDetails_shouldReturnTrueIfPasswordIsWrong() throws RepositoryException
    {
        User bob = new User("bob", "bob-password", "status");
        
        when(userRepo.get(bob.getId())).thenReturn(bob);
        
        assertFalse(userService.checkLoginDetails(bob.getId(), "wrong-password"));
    }
    
}
