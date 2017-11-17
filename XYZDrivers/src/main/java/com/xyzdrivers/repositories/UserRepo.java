/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyzdrivers.repositories;

import com.xyzdrivers.models.User;
import com.xyzdrivers.services.ColumnValuePair;
import com.xyzdrivers.services.SQLService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Toneo
 */
@RequestScoped
public class UserRepo extends Repo<User, String> {

    public UserRepo() {
        // The injection spec requires a parameterless constructor
    }

    public UserRepo(SQLService sqlService) {
        this.sqlService = sqlService;
    }

    @Override
    public List<User> get() throws RepositoryException {

        List<Object[]> users;
        try {
            users = sqlService.retrieve("users");
        } catch (SQLException ex) {
            throw new RepositoryException("Retrieval failed.", ex);
        }

        List<User> fullUsers = new ArrayList<>();

        for (Object[] parts : users) {
            fullUsers.add(mapPartsToUser(parts));
        }

        return fullUsers;
    }

    private User mapPartsToUser(Object[] parts) {
        return new User((String) parts[0],
                (String) parts[1],
                (String) parts[2]);
    }

    @Override
    public User get(String id) throws RepositoryException {

        if (id == null || id.length() == 0) {
            throw new IllegalArgumentException("Argument 'id' cannot be null or empty.");
        }

        User match = null;

        // Find a user matching id. There will only ever be 1 or 0 matches.
        for (User user : get()) {
            if (user.getId().equals(id)) {
                match = user;
                break;
            }
        }

        return match;
    }

    @Override
    public List<User> getWhere(String keyColumn, Object keyValue) throws RepositoryException {
        
        if (keyColumn == null) {
            throw new IllegalArgumentException("keyColumn cannot be null.");
        }
        
        if (keyValue == null) {
            throw new IllegalArgumentException("keyValue cannot be null.");
        }
        
        List<Object[]> rawUsers;
        try {
            rawUsers = sqlService.retrieve("users", "*", keyColumn, keyValue);
        } catch (SQLException | IllegalArgumentException ex) {
            throw new RepositoryException("Unable to retrieve matching users. See inner exception for details.", ex);
        }
        
        List<User> users = new ArrayList<>();
        
        for (Object[] parts : rawUsers) {
            users.add(mapPartsToUser(parts));
        }
        
        return users;
        
    }

    @Override
    public void insert(User user) throws RepositoryException {
        validateUser(user);

        try {
            sqlService.insert("users", new Object[]{
                user.getId(),
                user.getPassword(),
                user.getStatus()
            });
        } catch (SQLException ex) {
            throw new RepositoryException("Insertion failed.", ex);
        }
    }

    @Override
    public void delete(User user) throws RepositoryException {
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null.");
        }

        try {
            sqlService.remove("users", "id", user.getId());
        } catch (SQLException ex) {
            throw new RepositoryException("Delete failed.", ex);
        }
    }

    @Override
    public void update(User user) throws RepositoryException {
        validateUser(user);

        List<ColumnValuePair> columnValues = new ArrayList<>();
        columnValues.add(new ColumnValuePair("password", user.getPassword()));
        columnValues.add(new ColumnValuePair("status", user.getStatus()));

        try {
            sqlService.update("users", "id", user.getId(), columnValues);
        } catch (SQLException ex) {
            throw new RepositoryException("Update failed.", ex);
        }
    }

    private void validateUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("Argument 'user' cannot be null.");
        }

        String id = user.getId();
        if (id == null || id.length() == 0) {
            throw new IllegalArgumentException("User id cannot be null or empty.");
        }

        String password = user.getPassword();
        if (password == null || password.length() == 0) {
            throw new IllegalArgumentException("User password cannot be null or empty.");
        }

        String status = user.getStatus();
        if (status == null || status.length() == 0) {
            throw new IllegalArgumentException("User status cannot be null or empty.");
        }
    }

}
