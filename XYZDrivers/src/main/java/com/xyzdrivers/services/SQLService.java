/**
 * @file    jdbcDriver.java
 * @author alexander collins
 * @created 02/11/2017
 * @updated - 06/11/2017, alexander collins - 03/11/2017, alexander collins
 * @notes - Not fully tested
 */
package com.xyzdrivers.services;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import com.xyzdrivers.connection.ConnectionProvider;

@RequestScoped
public class SQLService {
//variables    

    private DatabaseMetaData DBMetaData;
    private PreparedStatement statement;
    private ResultSetMetaData resultsMetaData;
    private ResultSet results;
    @Inject
    private ConnectionProvider connectionProvider;

    @PostConstruct
    private void postConstruct() {
        try {
            DBMetaData = connectionProvider.getConnection().getMetaData();
        } catch (SQLException ex) {
            Logger.getLogger(SQLService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//private methods
    /**
     * Creates a preparedStatement using <code>sql</code> and
     * <code>parameters</code>, then runs <code>.executeQuery</code> on that
     * statement.
     *
     * @param sql An SQL query statement.
     * @param parameters Any parameters to be passed to the SQL query statement
     *
     * @return the resulting <code>ResultSet</code> of running
     * <code>.executeQuery</code> of <code>statement</code>.
     *
     * @throws SQLException
     */

    private ResultSet executeQueryStatement(String sql, Object... parameters)
            throws SQLException {

        Connection connection = connectionProvider.getConnection();

        //prepare statement
        statement = connection.prepareStatement(sql);
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }

        //execute statement
        return statement.executeQuery();
    }

    /**
     * Creates a preparedStatement using <code>sql</code> and
     * <code>parameters</code>, then runs <code>.executeUpdate</code> on that
     * statement.
     *
     * @param sql An SQL query statement.
     * @param parameters Any parameters to be passed to the SQL query statement
     *
     * @return the resulting <code>int</code> of running
     * <code>.executeUpdate</code> of <code>statement</code>.
     *
     * @throws SQLException
     */
    public int executeUpdateStatement(String sql, Object... parameters)
            throws SQLException {
        Connection connection = connectionProvider.getConnection();

        //prepare statement
        statement = connection.prepareStatement(sql);
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }

        //execute statement
        return statement.executeUpdate();
    }

//public methods
    /**
     * Closes all connections to DB. Use on exit.
     *
     * @throws SQLException
     */
    public void close()
            throws SQLException {
        results.close();
        statement.close();
    }

    /**
     * Gets the name of every table in <code>DB</code>.
     *
     * @return A List of Strings where each String is a tables name found in DB.
     *
     * @throws SQLException
     */
    public List<String> getAllTableNames()
            throws SQLException {
        List<String> tableNames = new ArrayList();

        results = DBMetaData.getTables(null, null, "%", null);
        while (results.next()) {
            if (!results.getString(3).contains("SYS")) {
                tableNames.add(results.getString(3));
            }
        }

        System.out.println(tableNames.toString());
        return tableNames;
    }

    /**
     * Gets the name of every column in DB->tableName.
     *
     * @param tableName The name of the Table in DB to retrieve column names
     * from.
     *
     * @return A List of Strings where each String is a column name found in
     * DB->tableName.
     *
     * @throws SQLException
     */
    public List<String> getAllColumnNames(String tableName)
            throws SQLException {
        List<String> columnNames = new ArrayList();

        results = executeQueryStatement("SELECT * FROM " + tableName + " FETCH FIRST 1 ROWS ONLY");

        resultsMetaData = results.getMetaData();
        for (int i = 1; i < resultsMetaData.getColumnCount(); i++) {
            columnNames.add(resultsMetaData.getColumnName(i));
        }

        return columnNames;
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
            throws SQLException {
        //execute statement
        results = executeQueryStatement("SELECT * FROM " + table);
        //return results
        return results.next();
    }

    /**
     * Checks if <code>column</code> exists in <code>table</code>.
     *
     * @param table The name of the table containing the data to retrieve data
     * from
     * @param column The name of the column to look for in <code>table</code>
     *
     * @return The boolean result of <code>(query results).next()</code>
     *
     * @throws SQLException
     */
    public boolean exists(String table, String column)
            throws SQLException {
        //execute statement
        results = executeQueryStatement("SELECT " + column + " FROM " + table + " WHERE " + column + " = ?");
        //return results
        return results.next();
    }

    /**
     * Checks if <code>item</code> exists in <code>table->column</code>.
     *
     * @param table The name of the table containing the item
     * @param column The name of the column containing the item to retrieve from
     * table
     * @param item The item to be look for in <code>table->column</code>
     *
     * @return The boolean result of <code>(query results).next()</code>
     *
     * @throws SQLException
     */
    public boolean exists(String table, String column, Object item)
            throws SQLException {
        //execute statement
        results = executeQueryStatement("SELECT " + column + " FROM " + table + " WHERE " + column + " = ?", item);
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
     * @return Each Object[] is a rows data containing <code>column</code>, each
     * List item is a row.
     *
     * @throws SQLException
     */
    public List<Object[]> retrieve(String table)
            throws SQLException {
        List<Object[]> data;
        Object[] column;

        //check table and table->column exist
        if (!exists(table)) {
            throw new IllegalArgumentException();
        }
        //execute SQL statement
        results = executeQueryStatement("SELECT * FROM " + table);
        resultsMetaData = results.getMetaData();
        //add results to data
        int columnCount = resultsMetaData.getColumnCount();
        data = new ArrayList<>();
        for (int row = 0; results.next(); row++) {
            column = new Object[columnCount];
            for (int col = 1; col <= columnCount; col++) {
                column[col - 1] = results.getObject(col);
            }

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
            throws SQLException {
        List<Object> data;

        //check table and table->column exist
        if (!exists(table, column)) {
            throw new IllegalArgumentException();
        }
        //execute statement
        results = executeQueryStatement("SELECT " + column + " FROM " + table);
        //add results to data
        data = new ArrayList();
        for (int i = 0; results.next(); i++) {
            data.add(results.getObject(column));
        }
        //return results
        return data;
    }

    /**
     * "SELECT <code>column</code> FROM <code>table</code> WHERE
     * <code>keyColumn</code> = <code>keyValue</code>"
     *
     * @param table the table containing the item be to retrieved
     * @param column the column containing the item to be retrieved from table
     * ("*" for all).
     * @param keyColumn the name of the keyColumn
     * @param keyValue the keyValue of the item(s) to be retrieved
     *
     * @return Each Object[] is a rows data containing <code>column</code>, each
     * List item is a row.
     *
     * @throws IllegalArgumentException if <code>(query results).next()</code>
     * returns false.
     * @throws SQLException
     */
    public List<Object[]> retrieve(String table, String column, String keyColumn, Object keyValue)
            throws SQLException, IllegalArgumentException {
        List<Object[]> data;
        Object[] rowColumn;

        //execute statement
        results = executeQueryStatement("SELECT " + column + " FROM " + table + " WHERE \"" + keyColumn + "\" = ?", keyValue);
        resultsMetaData = results.getMetaData();
        //add results to data
        int columnCount = resultsMetaData.getColumnCount();
        data = new ArrayList<>();
        for (int row = 0; results.next(); row++) {
            rowColumn = new Object[columnCount];
            for (int col = 1; col <= columnCount; col++) {
                rowColumn[col - 1] = results.getObject(col);
            }

            data.add(rowColumn);
        }
        //return results
        return data;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update() functions...">
    /**
     * "UPDATE <code>table</code> SET <code>column</code> =
     * <code>updateValue</code> WHERE <code>keyColumn</code> =
     * <code>keyValue</code>"
     *
     * @param table the table containing the item be to retrieved
     * @param column the column containing the item to be updated
     * @param value the value to update
     * @param keyColumn the name of the keyColumn
     * @param keyValue the keyColumn value of the item(s) to be updated
     *
     * @throws SQLException
     */
    public void update(String table, String column, Object value, String keyColumn, Object keyValue)
            throws SQLException {
        //execute statement
        executeUpdateStatement("UPDATE " + table + " SET " + column + " = ? WHERE " + keyColumn + " = ?", value, keyValue);
    }

    /**
     * Update an item within <code>table</code> where
     * <code>primaryKeyColumn = primaryKey</code>. Columns to update are given
     * by <code>columnValues</code>.
     *
     * @param table the table containing the item to update. e.g. 'users'.
     * @param primaryKeyColumn the primary key column for the given table. e.g.
     * 'id'.
     * @param primaryKey the primary key value of the record which should be
     * updated. e.g. 3.
     * @param columnValues a list of column/value pairs representing changes to
     * be made to the record in the database.
     *
     * @throws java.sql.SQLException
     */
    public void update(String table, String primaryKeyColumn, Object primaryKey, List<ColumnValuePair> columnValues)
            throws SQLException {
        if (table == null) {
            throw new IllegalArgumentException("table cannot be null.");
        }

        if (primaryKeyColumn == null) {
            throw new IllegalArgumentException("primaryKeyColumn cannot be null.");
        }

        if (primaryKey == null) {
            throw new IllegalArgumentException("primaryKey cannot be null.");
        }

        if (columnValues == null) {
            throw new IllegalArgumentException("columnValues cannot be null.");
        }

        if (columnValues.isEmpty()) {
            return;
        }

        String updateTemplate = "UPDATE ? SET ? = ? WHERE ? = ?";

        for (ColumnValuePair pair : columnValues) {
            executeUpdateStatement(updateTemplate, table, pair.getColumnName(), pair.getValue(), primaryKeyColumn, primaryKey);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insert() functions...">
    /**
     * Insert a new row of <code>values</code> into <code>table</code>. The
     * array of values are inserted in the order they are found (index 0 to
     * index values.length).
     *
     * @param table the table you want to insert a row into
     * @param values an array containing the values to insert
     *
     * @throws SQLException
     */
    public void insert(String table, Object[] values)
            throws SQLException {
        //prepare statement query "INSERT INTO table VALUES (?, ...)"
        String insertQuery = "INSERT INTO " + table + " VALUES (";
        for (int i = 0; i < values.length; i++) {
            if (i == values.length - 1) {
                insertQuery += ("?)");
            } else {
                insertQuery += ("?, ");
            }
        }
        //execute statement
        executeUpdateStatement(insertQuery, values);
    }

    /**
     * "INSERT INTO <code>table</code> VALUES (<code>values...</code>)" The
     * array of values are inserted in the order they are found (index 0 to
     * index values.length).
     *
     * @param table the table you want to insert a row into
     * @param values an array containing array(s) of values to be inserted
     *
     * @throws SQLException
     */
    public void insert(String table, List<Object[]> values)
            throws SQLException {
        for (Object[] value : values) {
            //prepare statement query "INSERT INTO table VALUES (?, ...)"
            String insertQuery = "INSERT INTO " + table + " VALUES (";
            for (int i = 0; i < value.length; i++) {
                if (i == value.length - 1) {
                    insertQuery += ("?)");
                } else {
                    insertQuery += ("?, ");
                }
            }
            //execute statement
            executeUpdateStatement(insertQuery, value);
        }
    }

    /**
     * "INSERT INTO <code>table</code> () VALUES (<code>values...</code>)" The
     * array of values are inserted in the order they are found (index 0 to
     * index values.length).
     *
     * @param table the table you want to insert a row into
     * @param columns an array containing columns specified to be inserted into
     * @param values an array containing array(s) of values to be inserted
     *
     * @throws SQLException
     */
    public void insert(String table, String columns, Object[] values)
            throws SQLException {
        
        //prepare statement query "INSERT INTO table VALUES (?, ...)"
        String insertQuery = "INSERT INTO " + table + columns + " VALUES (";
        for (int i = 0; i < values.length; i++) {
            if (i == values.length - 1) {
                insertQuery += ("?)");
            } else {
                insertQuery += ("?, ");
            }
        }
        //execute statement
        executeUpdateStatement(insertQuery, values);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="remove() functions...">
    /**
     * Remove a row of data from <code>table</code>, identified by
     * <code>primaryKeyColumn</code> and <code>primaryKeyValue</code>.
     *
     * NOTE: query should be updated to match standards in retrieve (if we have
     * time)
     *
     * @param table the table you want to remove the row from
     * @param primaryKeyColumn the primary key column of the table
     * @param primaryKeyValue the value of the primary key for the record that
     * will be deleted
     *
     * @throws SQLException
     */
    public void remove(String table, String primaryKeyColumn, Object primaryKeyValue) throws SQLException {
        executeUpdateStatement("DELETE FROM ? WHERE ? = ?", table, primaryKeyColumn, primaryKeyValue);
    }
    //</editor-fold>
}
