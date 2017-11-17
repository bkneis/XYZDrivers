/**
 * @author Alex
 */
package com.xyzdrivers.services;

import java.sql.*;
import java.util.*;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class MembersService
{
    @Inject
    private SQLService sqlService;
    
//public methods
    /**
     * Fetches every row from Members table.
     * 
     * @return List<Object[]> members. Each Object[] is a row of data.
     * @throws java.sql.SQLException
     */
    public List<Object[]> getMembers()
            throws SQLException
    {
        List<Object[]> members = null;
        
        // TODO - Convert to use a MembersRepo
        members = sqlService.retrieve("MEMBERS");
        
        return members;
    }
}
