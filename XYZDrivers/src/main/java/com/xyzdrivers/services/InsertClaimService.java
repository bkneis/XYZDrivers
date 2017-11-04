/**
 * @file InsertClaimSService.java
 * @author Nathan
 * @created 02/11/17
 * @modified 03/11/17
 * @notes -
 */
package com.xyzdrivers.services;

import com.xyzdrivers.models.Claim;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertClaimService {

    private Connection con;

    public InsertClaimService(Connection con) {
        this.con = con;
    }

    public void InsertClaim(Claim c) throws IllegalAccessException, SQLException {
       
            if (c == null) {
                throw new IllegalArgumentException("The object c is null.");
            }

            for (Field field : c.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(c);
                if (value == null) {
                    throw new IllegalArgumentException("One of the declared fields in object c is null.");
                }
            }

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
    }
