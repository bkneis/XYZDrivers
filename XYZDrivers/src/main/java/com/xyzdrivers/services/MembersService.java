/**
 * @author Alex
 */
package com.xyzdrivers.services;

import java.sql.*;
import java.util.*;

public class MembersService
{
//public methods
    /**
     * Fetches every row from Members table.
     * 
     * @param sqlService a <code>SQLService</code> object to talk to the DB with a "MEMBERS" table.
     * 
     * @return List<Object[]> members. Each Object[] is a row of data.
     * @throws java.sql.SQLException
     */
    //static is tmp until this file has more code.
    public static List<Object[]> getMembers(SQLService sqlService)
            throws SQLException
    {
        List<Object[]> members = null;
        
        members = sqlService.retrieve("MEMBERS");
        
        return members;
    }
}
