package com.xyzdrivers.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    
    private List<String> allowedStatuses;
    
    public static String TABLE_NAME = "members";
    public static String PRIMARY_KEY = "id";
    
    public Membership(String name, String address, LocalDate dob, LocalDate dor, String status, float balance) {
        this.name = name;
        this.address = address;
        this.dob = dob;
        this.dor = dor;
        this.status = status;
        this.balance = balance;
        this.allowedStatuses = new ArrayList<>();
        allowedStatuses.add("SUSPENDED");
        allowedStatuses.add("OK");
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
    
    public boolean setStatus(String status) {
        if (allowedStatuses.contains(status)) {
            this.status = status;
            return true;
        }
        return false;
    }
    
}
