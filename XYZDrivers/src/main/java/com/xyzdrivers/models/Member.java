/**
 * @file    jdbcDriver.java
 * @author  alexander collins
 * @created 06/11/2017
 * @updated
 * @notes
 */

package com.xyzdrivers.models;

import java.time.LocalDate;

public class Member extends Model
{
    private String id;
    private String name;
    private String address;
    private LocalDate dateOfBirth;
    private LocalDate dateOfRegistration;
    private String status;
    private double balance;
    
    public Member(String id, String name, String address, LocalDate dob, LocalDate dor, String status, double balance) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.dateOfBirth = dob;
        this.dateOfRegistration = dor;
        this.status = status;
        this.balance = balance;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dob) {
        this.dateOfBirth = dob;
    }

    public LocalDate getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(LocalDate dor) {
        this.dateOfRegistration = dor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
