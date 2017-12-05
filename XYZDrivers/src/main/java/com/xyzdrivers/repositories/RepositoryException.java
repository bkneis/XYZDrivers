/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyzdrivers.repositories;

/**
 *
 * @author Toneo
 */
public class RepositoryException extends Exception {
    
    public RepositoryException(String message)
    {
        super(message);
    }
    
    public RepositoryException(String message, Exception innerException)
    {
        super(message, innerException);
    }
    
}
