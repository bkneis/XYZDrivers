/**
 * @file UpgradeMemberService.java
 * @author Nathan
 * @created 11/11/17
 * @modified 11/11/17
 * @notes -
 */
package com.xyzdrivers.services;

import com.xyzdrivers.connection.ConnectionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class UpgradeMemberService {

    @Inject
    private ConnectionProvider connectionProvider;

    public void UpgradeMember(String user) throws SQLException {
        if (user == null || user.isEmpty()) {
            throw new IllegalArgumentException("The string used to identify a user is null or empty: " + user);
        }

        Connection connection = connectionProvider.getConnection();

        String updateSQL = "UPDATE MEMBERS SET status = ? WHERE id = ?";

        PreparedStatement p = connection.prepareStatement(updateSQL);

        p.setString(1, "APPROVED");
        p.setString(2, user);

        p.executeUpdate();

        connection.close();
    }

}
