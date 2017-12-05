package com.xyzdrivers.models;

/**
 *
 * @author Toneo
 */
public class User {
    
    private String id;
    
    private String password;
        
    private String status;

    public User(String id, String password, String status) {
        this.id = id;
        this.password = password;
        this.status = status;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Checks to see if this user is an administrator or not.
     * @return whether or not this user is an administrator
     */
    public boolean isAdministrator() {
        if (status == null) {
            return false;
        }
        
        return status.toLowerCase().trim().equals("admin");
    }
    
}
