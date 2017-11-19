/**
 * @file InsertClaimSService.java
 * @author Nathan
 * @created 02/11/17
 * @modified 04/11/17
 * @notes -
 */
package com.xyzdrivers.services;

import com.xyzdrivers.models.Claim;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import com.xyzdrivers.connection.ConnectionProvider;

@RequestScoped
public class InsertClaimService {

    @Inject
    private ConnectionProvider connectionProvider;

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

            Connection connection = connectionProvider.getConnection();
            PreparedStatement p = connection.prepareStatement(insertSQL);           
            
            p.setString(1, c.getMemberID());
            p.setDate(2, new Date(c.getDate().getTimeInMillis()));
            p.setString(3, c.getReason());
            p.setString(4, c.getStatus());
            p.setDouble(5, c.getAmount());

            p.executeUpdate();

            connection.close();
            
        }
    }
