/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyzdrivers.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Nathan
 */
public class InsertClaim {
    
  Connection con;

  public void InsertClaim(Claim c) {
            
    try {
             
      Class.forName("org.apache.derby.jdbc.ClientDriver");
      con = DriverManager.getConnection("jdbc:derby://localhost:1527/xyzdrivers");
      
      String insertSQL = "INSERT INTO Claims (MEM_ID, DATE, RATIONALE, STATUS, AMOUNT) VALUES (?, ?, ?, ?, ?)";
      
      PreparedStatement p = con.prepareStatement(insertSQL);
      
      p.setString(1, c.getMemberID());
      p.setDate(2, Date.valueOf(c.getDate()));
      p.setString(3, c.getReason());
      p.setString(4, c.getStatus());
      p.setInt(5, c.getAmount());
      
      p.executeUpdate();
    
      con.close();
    } 
    catch (ClassNotFoundException | SQLException e) {
      System.err.println("Error: " + e);
    }
  }
}
