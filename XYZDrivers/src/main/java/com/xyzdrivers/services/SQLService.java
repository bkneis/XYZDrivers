/**
 * @file    jdbcDriver.java
 * @author  alexander collins
 * @created 02/11/2017
 * @updated - 06/11/2017, alexander collins
 *          - 03/11/2017, alexander collins
 * @notes   - The private functions could probably be made public.
 *          - Not fully tested
 */
package com.xyzdrivers.services;

import java.sql.*;
import java.util.*;

public class SQLService
{
//variables
    private Connection DB;
    private DatabaseMetaData DBMetaData;
    
    private PreparedStatement statement;
    private ResultSetMetaData resultsMetaData;
    private ResultSet results;
//constructors
    /**
     * Constructor for jdbcDriver. <code>dbConnection</code> is tested by 
     * retrieving <code>DatabaseMetaData</code> from the <code>Connection</code>.
     * 
     * @param dbConnection A <code>Connection</code> to the DB being accessed.
     * 
     * @throws SQLException
     */
    public SQLService(Connection dbConnection)
            throws SQLException
    {
        //pass DB connection
        DB = dbConnection;
        
        //get database metadata
        DBMetaData = DB.getMetaData();
    }
    
//private methods
    /**
     * Creates a preparedStatement using <code>sql</code> and <code>parameters</code>,
     * then runs <code>.executeQuery</code> on that statement.
     * 
     * @param sql An SQL query statement.
     * @param parameters Any parameters to be passed to the SQL query statement
     * 
     * @return the resulting <code>ResultSet</code> of running <code>.executeQuery</code>
     * of <code>statement</code>.
     * 
     * @throws SQLException 
     */
    private ResultSet executeQueryStatement(String sql, Object... parameters)
            throws SQLException
    {
        //prepare statement
        statement = DB.prepareStatement(sql);
        for (int i = 0; i < parameters.length; i++)
            statement.setObject(i + 1, parameters[i]);
        
        //execute statement
        return statement.executeQuery();
    }
    
    /**
     * Creates a preparedStatement using <code>sql</code> and <code>parameters</code>,
     * then runs <code>.executeUpdate</code> on that statement.
     * 
     * @param sql An SQL query statement.
     * @param parameters Any parameters to be passed to the SQL query statement
     * 
     * @return the resulting <code>int</code> of running <code>.executeUpdate</code>
     * of <code>statement</code>.
     * 
     * @throws SQLException 
     */
    private int executeUpdateStatement(String sql, Object... parameters)
            throws SQLException
    {
        //prepare statement
        statement = DB.prepareStatement(sql);
        for (int i = 0; i < parameters.length; i++)
            statement.setObject(i + 1, parameters[i]);
        
        //execute statement
        return statement.executeUpdate();
    }
    
    /**
     * Gets the name of every table in <code>DB</code>.
     *  
     * @return A List of Strings where each String is a tables name found in DB.
     * 
     * @throws SQLException 
     */
    private List<String> getAllTableNames() throws SQLException
    {
        List<String> tableNames = new ArrayList();
        
        results = DBMetaData.getTables(null, null, "%", null);
        while(results.next())
            if (!results.getString(3).contains("SYS"))
                tableNames.add(results.getString(3));
            
        System.out.println(tableNames.toString());
        return tableNames;
    }
    
    /**
     * Gets the name of every column in DB->tableName.
     *  
     * @param tableName The name of the Table in DB to retrieve column names from.
     * 
     * @return A List of Strings where each String is a column name found in DB->tableName.
     * 
     * @throws SQLException 
     */
    private List<String> getAllColumnNames(String tableName) throws SQLException
    {
        List<String> columnNames = new ArrayList();
        
        results = executeQueryStatement("SELECT * FROM "+tableName+" FETCH FIRST 1 ROWS ONLY");
        
        resultsMetaData = results.getMetaData();
        for (int i = 1; i < resultsMetaData.getColumnCount(); i++)
            columnNames.add(resultsMetaData.getColumnName(i));
        
        return columnNames;
    }
        
//public methods
    /**
     * Closes all connections to DB. Use on exit.
     * 
     * @throws SQLException 
     */
    public void close() throws SQLException
    {
        results.close();
        statement.close();
        DB.close();
    }
    
    //<editor-fold defaultstate="collapsed" desc="exists() functions...">
    /**
     * Checks if <code>table</code> exists.
     * 
     * @param table The name of the table to look for.
     * 
     * @return The boolean result of <code>(query results).next()</code>
     * 
     * @throws SQLException 
     */
    public boolean exists(String table)
            throws SQLException
    {
        //execute statement
        results = executeQueryStatement("SELECT * FROM "+table);
        //return results
        return results.next();
    }
        
    /**
     * Checks if <code>column</code> exists in <code>table</code>.
     * 
     * @param table The name of the table containing the data to retrieve data from
     * @param column The name of the column to look for in <code>table</code>
     * 
     * @return The boolean result of <code>(query results).next()</code>
     * 
     * @throws SQLException 
     */
    public boolean exists(String table, String column)
            throws SQLException
    {
        //execute statement
        results = executeQueryStatement("SELECT "+column+" FROM "+table+" WHERE "+column+" = ?");
        //return results
        return results.next();
    }
    
    /**
     * Checks if <code>query</code> exists in <code>table->column</code>.
     * 
     * @param table The name of the table containing the item
     * @param column The name of the column containing the item to retrieve from table
     * @param item The item to be look for in <code>table->column</code>
     * 
     * @return The boolean result of <code>(query results).next()</code>
     * 
     * @throws SQLException 
     */
    public boolean exists(String table, String column, Object item)
            throws SQLException
    {
        //execute statement
        results = executeQueryStatement("SELECT "+column+" FROM "+table+" WHERE "+column+" = ?", item);
        //return results
        return results.next();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="retrieve() functions...">
    /**
     * "SELECT * FROM <code>table</code>"
     * 
     * @param table The table to retrieve from DB
     * 
     * @return Each Object[] is a rows data containing <code>column</code>, each List item is a row.
     * 
     * @throws SQLException 
     */
    public List<Object[]> retrieve(String table)
            throws SQLException
    {
        List<Object[]> data;
        Object[] column;
        
        //check table and table->column exist
        if (!exists(table))
            throw new IllegalArgumentException();
        //execute SQL statement
        results = executeQueryStatement("SELECT * FROM "+table);
        resultsMetaData = results.getMetaData();
        //add results to data
        int columnCount = resultsMetaData.getColumnCount();
        data = new ArrayList<>();
        for (int row = 0; results.next(); row++)
        {   
            column = new Object[columnCount];
            for (int col = 1; col <= columnCount; col++)
                column[col-1] = results.getObject(col);
            
            data.add(column);
        }
        //return results
        return data;
    }
    
    /**
     * "SELECT <code>column</code> FROM <code>table</code>"
     * 
     * @param table the table containing the item be to retrieved
     * @param column the column containing the item to be retrieved from table
     * 
     * @return an Object containing the item found
     * 
     * @throws SQLException 
     */
    public List<Object> retrieve(String table, String column)
            throws SQLException
    {
        List<Object> data;
        
        //check table and table->column exist
        if (!exists(table, column))
            throw new IllegalArgumentException();
        //execute statement
        results = executeQueryStatement("SELECT "+column+" FROM "+table);
        //add results to data
        data = new ArrayList();
        for (int i = 0; results.next(); i++)
            data.add(results.getObject(column));
        //return results
        return data;
    }
    
    /**
     * "SELECT <code>column</code> FROM <code>table</code> WHERE <code>primaryKeyColumn</code> = <code>primaryKey</code>"s
     * 
     * @param table the table containing the item be to retrieved
     * @param column the column containing the item to be retrieved from table ("*" for all).
     * @param primaryKeyColumn the name of the PRIMARY KEY column
     * @param primaryKey the PRIMARY KEY of the item to be retrieved
     * 
     * @return Each Object[] is a rows data containing <code>column</code>, each List item is a row.
     * 
     * @throws IllegalArgumentException if <code>(query results).next()</code> returns false.
     * @throws SQLException 
     */
    public List<Object[]> retrieve(String table, String column, String primaryKeyColumn, Object... primaryKey)
            throws SQLException, IllegalArgumentException
    {
        List<Object[]> data;
        Object[] rowColumn;
        
        //execute statement
        results = executeQueryStatement("SELECT "+column+" FROM "+table+" WHERE "+primaryKeyColumn+" = ?", primaryKey);
        resultsMetaData = results.getMetaData();
        //add results to data
        int columnCount = resultsMetaData.getColumnCount();
        data = new ArrayList<>();
        for (int row = 0; results.next(); row++)
        {   
            rowColumn = new Object[columnCount];
            for (int col = 1; col <= columnCount; col++)
                rowColumn[col-1] = results.getObject(col);
            
            data.add(rowColumn);
        }
        //return results
        return data;
    } 
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="update() functions...">
    /**
     * Update an item from <code>table->column</code>, where the PRIMARY KEY
     * is <code>primaryKey</code>.
     * 
     * @param table the table containing the item be to retrieved
     * @param column the column containing the item to be retrieved from table
     * @param primaryKeyColumn the name of the PRIMARY KEY column
     * @param primaryKey the PRIMARY KEY of the item to be retrieved
     * @param value the value to update the found item with
     * 
     * @throws SQLException 
     */
    public void update(String table, String column, String primaryKeyColumn, Object primaryKey, Object value)
            throws SQLException
    {
        //execute statement
        executeUpdateStatement("UPDATE "+table+" SET "+column+" = ? WHERE "+primaryKeyColumn+" = ?", value, primaryKey);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insert() functions...">
    /**
     * Insert a new row of <code>values</code> into <code>table</code>.
     * The array of values are inserted in the order they are found (index 0 to 
     * index values.length).
     * 
     * @param table the table you want to insert a row into
     * @param values an array containing the values to insert
     * 
     * @throws SQLException 
     */
    public void insert(String table, Object[] values) throws SQLException
    {
        //prepare statement query "INSERT INTO table VALUES (?, ...)"
        String insertQuery = "INSERT INTO "+table+" VALUES (";
        for (int i = 0; i < values.length; i++)
        {
            if (i == values.length-1)
                insertQuery += ("?)");
            else
                insertQuery += ("?, ");
        }
        //execute statement
        executeUpdateStatement(insertQuery, values);
    }
    /**
     * Insert a set of <code>values</code> into <code>table</code>.
     * The array of values are inserted in the order they are found (index 0 to 
     * index values.length).
     * 
     * @param table the table you want to insert a row into
     * @param values an array containing array(s) of values to be inserted
     * 
     * @throws SQLException 
     */
    public void insert(String table, Object[][] values) throws SQLException
    {
        for (int j = 0; j < values.length; j++)
        {
            //prepare statement query "INSERT INTO table VALUES (?, ...)"
            String insertQuery = "INSERT INTO "+table+" VALUES (";
            for (int i = 0; i < values[j].length; i++)
            {
                if (i == values[j].length-1)
                    insertQuery += ("?)");
                else
                    insertQuery += ("?, ");
            }
            //execute statement
            executeUpdateStatement(insertQuery, values[j]);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="remove() functions...">
    /**
     * Remove a row of data from <code>table</code> where <code>query</code> is
     * true. <code>query</code> must be valid SQL syntax, the <code>query</code>
     * String is appended to the end of "DELETE FROM "+table+" WHERE ".
     * 
     * @param table the table you want to remove the row from
     * @param query a string containing an SQL removal condition, appended to 
     * the end of "DELETE FROM "+table+" WHERE ".
     * 
     * @throws SQLException 
     */
    public void remove(String table, String query) throws SQLException
    {
        executeUpdateStatement("DELETE FROM "+table+" WHERE "+query);
    }
    //</editor-fold>
}
