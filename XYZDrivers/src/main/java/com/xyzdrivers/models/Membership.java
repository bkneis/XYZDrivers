package com.xyzdrivers.models;

import java.time.LocalDate;

/**
 *
 * @author arthur
 */
public class Membership extends Model {
    
    private int id;
    private String name;
    private String address;
    private LocalDate dob;
    private LocalDate dor;
    private String status;
    private float balance;
    
    public static String TABLE_NAME = "members";
    public static String PRIMARY_KEY = "id";
    
    public Membership(String name, String address, LocalDate dob, LocalDate dor, String status, float balance) {
        this.name = name;
        this.address = address;
        this.dob = dob;
        this.dor = dor;
        this.status = status;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getDob() {
        return dob;
    }

    public LocalDate getDor() {
        return dor;
    }

    public String getStatus() {
        return status;
    }

    public float getBalance() {
        return balance;
    }
    
}
