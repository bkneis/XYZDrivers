/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyzdrivers.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Antony Wyatt
 * Provides a database connection.
 */
@RequestScoped
public class ConnectionProvider {
    
    private Connection connection;
    
    /**
     * Provides a database connection to the caller.
     * @return A new connection, unless a connection has already been provided and is still open.
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        
        if (connection == null || connection.isClosed())
        {            
            // todo BEFORE merge - replace connection strings with environment vars
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/TestDatabase_DEV", "lenfred", "fruitgums1");
        }
        
        return connection;
        
    }
    
    /**
     * Closes the provided connection if it is still in use.
     * @throws java.sql.SQLException
     */
    public void close() throws SQLException {
        
        if (connection != null && !connection.isClosed())
        {
            connection.close();
        }
        
    }
    
}
