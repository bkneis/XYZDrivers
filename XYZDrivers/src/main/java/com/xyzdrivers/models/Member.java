/**
 * @file    jdbcDriver.java
 * @author  alexander collins
 * @created 06/11/2017
 * @updated
 * @notes
 */

package com.xyzdrivers.models;

import java.time.LocalDate;
import java.util.Calendar;

public class Member extends Model
{
//variables
    private String id;
    private String name;
    private String address;
    private Calendar dob;  //date of birth
    private Calendar dor;  //date of registration
    private String status;
    private double balance;
//constructors
    public Member(String id, String name, String address, Calendar dob, Calendar dor, String status, double balance) {
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
    public Calendar getDob() {
        return dob;
    }
    public Calendar getDor() {
        return dor;
    }
    public String getStatus() {
        return status;
    }
    public double getBalance() {
        return balance;
    }
}
