package com.xyzdrivers.services;

import com.xyzdrivers.models.MembershipPayment;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

/*
 * @author Joe Dicker
 */
public class InsertPaymentService {

    private Connection con;
    private float balance;
    private float currentBalance;
    private ResultSet results;

    public InsertPaymentService(Connection con) {
        this.con = con;
    }

    public void InsertPayment(MembershipPayment mp) throws IllegalAccessException, SQLException {

        if (mp == null) {
            throw new IllegalArgumentException("The object p is null.");
        }

        for (Field f : mp.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            Object value = f.get(mp);
            if (value == null) {
                throw new IllegalArgumentException("One of the declared fields in object p is null.");
            }
        }
        
        String query = "INSERT INTO PAYMENTS (MEM_ID, TYPE_OF_PAYMENT, AMOUNT, DATE, TIME) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, mp.getMemberID());
        ps.setString(2, mp.getPaymentType());
        ps.setFloat(3, mp.getPaymentAmount());
        ps.setDate(4, Date.valueOf(mp.getDate()));
        ps.setTime(5, Time.valueOf(mp.getTime()));

                ps.executeUpdate();
        
        String query2 = "SELECT BALANCE FROM MEMBERS WHERE ID = ?";
        
        PreparedStatement ps2 = con.prepareStatement(query2);
        
        ps2.setString(1, mp.getMemberID());
        
        ResultSet rs = ps2.executeQuery();
        
        while(rs.next()){
            currentBalance = rs.getFloat("BALANCE");
        }

        balance = currentBalance - mp.getPaymentAmount();
        
        String query3 = "UPDATE MEMBERS SET BALANCE = ? WHERE ID = ?";
        
        PreparedStatement ps3 = con.prepareStatement(query3);
        
        ps3.setFloat(1, balance);
        ps3.setString(2, mp.getMemberID());
        
        ps3.execute();
        
        con.close();
    }

}
