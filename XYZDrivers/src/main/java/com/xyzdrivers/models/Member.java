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
//variables
    private String id;
    private String name;
    private String address;
    private LocalDate dob;  //date of birth
    private LocalDate dor;  //date of registration
    private String status;
    private double balance;
//constructors
    public Member(String id, String name, String address, LocalDate dob, LocalDate dor, String status, double balance) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.dob = dob;
        this.dor = dor;
        this.status = status;
        this.balance = balance;
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
    public float getBalance() {
        return balance;
    }
}
