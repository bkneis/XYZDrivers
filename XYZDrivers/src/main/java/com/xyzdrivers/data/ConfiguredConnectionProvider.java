package com.xyzdrivers.data;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Antony Wyatt
 * Provides a database connection.
 */
@RequestScoped
public class ConfiguredConnectionProvider implements ConnectionProvider {
        
    private Connection connection;
    private String url, username, password;
    
    /**
     * Provides a database connection to the caller.
     * @return A new connection, unless a connection has already been provided and is still open.
     * @throws SQLException
     */
    @Override
    public Connection getConnection() throws SQLException {
        
        if (connection == null || connection.isClosed())
        {           
            if (!isConfigured()) {
                readConfiguration();
            }
            
            connection = DriverManager.getConnection(url, username, password);
        }
        
        return connection;
        
    }
    
    /**
     * Closes the provided connection if it is still in use.
     * @throws java.sql.SQLException
     */
    @Override
    public void close() throws SQLException {
        
        if (connection != null && !connection.isClosed())
        {
            connection.close();
        }
        
    }
    
    private Properties readConfigurationProperties(String configurationFileName) {
        
        Properties properties    = new Properties();        
        InputStream fileStream = getClass().getResourceAsStream("/" + configurationFileName);
        
        if (fileStream == null)
        {
            throw new IllegalArgumentException("Configuration file " + configurationFileName + " could not be opened.");
        }
        
        try {
            properties.load(fileStream);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to open connection configuration file " + configurationFileName + ". See inner exception for details", ex);
        }    
        
        return properties;
        
    }
    
    private void readConfiguration() {
        
        String configurationFileName = "connection.properties";
        Properties properties = readConfigurationProperties(configurationFileName);        
        
        String url      = properties.getProperty("ConnectionUrl");
        String username = properties.getProperty("ConnectionUsername");
        String password = properties.getProperty("ConnectionPassword");
            
        if (url == null) {
            throw new IllegalArgumentException("ConnectionUrl is null. Please check " + configurationFileName + ".");
        }

        if (username == null) {
            throw new IllegalArgumentException("ConnectionUsername is null. Please check " + configurationFileName + ".");
        }

        if (password == null) {
            throw new IllegalArgumentException("ConnectionPassword is null. Please check " + configurationFileName + ".");
        }
        
        this.url = url;
        this.username = username;
        this.password = password; 
        
    }
    
    private boolean isConfigured() {
        
        return (url != null) && (username != null) && (password != null);
        
    }
    
}
