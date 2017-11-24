/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyzdrivers.services;

/**
 *
 * @author Toneo
 * @param <Value>
 */
public class ColumnValuePair<Value> {
    
    private String columnName;
    
    private Value value;
    
    public ColumnValuePair()
    {
    }
    
    public ColumnValuePair(String columnName, Value value)
    {
        this.columnName = columnName;
        this.value = value;
    }

    /**
     * @return the column name
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param ColumnName the new column name to set
     */
    public void setColumnName(String ColumnName) {
        this.columnName = ColumnName;
    }

    /**
     * @return the value stored under the column
     */
    public Value getValue() {
        return value;
    }

    /**
     * @param Value the value to set
     */
    public void setValue(Value Value) {
        this.value = Value;
    }
    
    @Override
    public String toString() {
        return columnName + " = " + value;
    }
    
}
