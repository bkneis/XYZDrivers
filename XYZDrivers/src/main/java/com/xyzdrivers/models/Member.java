package com.xyzdrivers.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Member extends Model
{
//variables
    private String id;
    private String name;
    private String address;
    private LocalDate dob;  //date of birth
    private LocalDate dor;  //date of registration
    private String status;
    private double balance;
    
    private List<String> allowedStatuses;
    
    public static String TABLE_NAME = "members";
    public static String PRIMARY_KEY = "id";
    
//constructors
    public Member(String id, String name, String address, LocalDate dob, LocalDate dor, String status, double balance) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.dob = dob;
        this.dor = dor;
        this.status = status;
        this.balance = balance;
        this.allowedStatuses = new ArrayList<>();
        allowedStatuses.add("SUSPENDED");
        allowedStatuses.add("APPROVED");
        allowedStatuses.add("REJECTED");
    }
//getters & setters
    public String getId() {
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
    public double getBalance() {
        return balance;
    }
    
    public boolean isProvisional() {
        return this.status.equals("APPLIED");
    }
    
    public boolean setStatus(String status) {
        if (allowedStatuses.contains(status)) {
            this.status = status;
            return true;
        }
        return false;
    }
    
}
