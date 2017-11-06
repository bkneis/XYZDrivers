/**
 * @author Alex
 */
package com.xyzdrivers.services;

import java.sql.*;
import java.util.*;

public class MembersService
{
//variables
    Connection DB = null;
    SQLService SQL = null;
//constructors
    /**
     * Creates a MembersService instance, inits SQLService with <code>conn</code>
     * 
     * @param conn a <code>Connection</code> to talk to the DB with a "MEMBERS" table.
     * 
     * @throws SQLException 
     */
    public MembersService(Connection conn)
            throws SQLException
    {
        //connect to DB
        this.DB = conn;
        //create SQLService instance to DB
        this.SQL = new SQLService(DB);
    }
//public methods
    /**
     * Fetches every row from Members table.
     * 
     * @return List<Object[]> members. Each Object[] is a row of data.
     */
    //static is tmp until this file has more code.
    public List<Object[]> getMembers()
            throws SQLException
    {
        List<Object[]> members = null;
        
        members = SQL.retrieve("MEMBERS");
        
        return members;
    }
    /**
     * Fetches every row from Members table where "STATUS" is "OUTSTANDING".
     * 
     * @return List<Object[]> members. Each Object[] is a row of data.
     */
    //static is tmp until this file has more code.
    public List<Object[]> getOutstandingBalances()
            throws SQLException
    {
        List<Object[]> members = null;
        
        members = SQL.retrieve("MEMBERS", "*", "status", "OUTSTANDING");
        
        return members;
    }
}
