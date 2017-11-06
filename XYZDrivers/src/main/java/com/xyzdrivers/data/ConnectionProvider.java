/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyzdrivers.data;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Toneo
 * Represents an object that can provide a database connection.
 */
public interface ConnectionProvider {
    
    /**
     * Provides a connection on demand.
     * @return A valid connection.
     * @throws SQLException
     */
    Connection getConnection() throws SQLException;
    
    /**
     * Closes the provided connection if it is still in use.
     * @throws java.sql.SQLException
     */
    void close() throws SQLException;
    
}
