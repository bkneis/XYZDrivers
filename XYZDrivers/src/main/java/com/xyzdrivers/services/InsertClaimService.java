/**
 * @file InsertClaimSService.java
 * @author Nathan
 * @created 02/11/17
 * @modified 03/11/17
 * @notes -
 */

package com.xyzdrivers.services;

import com.xyzdrivers.models.Claim;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertClaimService {

    private Connection con;
    
    public InsertClaimService(Connection con) {
        this.con = con;
    }

    public void InsertClaim(Claim c) {

        try {

            Class.forName("org.apache.derby.jdbc.ClientDriver");

            String insertSQL = "INSERT INTO Claims (MEM_ID, DATE, RATIONALE, STATUS, AMOUNT) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement p = con.prepareStatement(insertSQL);

            p.setString(1, c.getMemberID());
            p.setDate(2, Date.valueOf(c.getDate()));
            p.setString(3, c.getReason());
            p.setString(4, c.getStatus());
            p.setInt(5, c.getAmount());

            p.executeUpdate();

            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e);
        }
    }
}
