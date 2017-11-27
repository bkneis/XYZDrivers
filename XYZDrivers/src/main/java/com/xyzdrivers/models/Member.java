/**
 * @file    jdbcDriver.java
 * @author  alexander collins
 * @created 06/11/2017
 * @updated
 * @notes
 */

package com.xyzdrivers.models;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

public class Member extends Model {

    private String id;
    private String name;
    private String address;
    private Calendar dob;  //date of birth
    private Calendar dor;  //date of registration
    private String status;
    private double balance;

    private List<String> allowedStatuses;

    public static final String TABLE_NAME = "members";
    public static final String PRIMARY_KEY = "id";

//constructors
    public Member(String id, String name, String address, LocalDate dob, LocalDate dor, String status, double balance) {


    public Member(String id, String name, String address, Calendar dob, Calendar dor, String status, double balance) {
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

        setDor(dor);
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

    public Calendar getDob() {
        return dob;
    }

    public void setDob(Calendar dob) {
        this.dob = dob;
    }

    public Calendar getDor() {
        return dor;
    }

    public void setDor(Calendar dor) {
        if (!isDateOfRegistrationValid(dor)) {
            throw new IllegalArgumentException("Argument 'dor' is a date in the future, which is not permitted.");
        }

        this.dor = dor;
    }

    private boolean isDateOfRegistrationValid(Calendar dor) {
        Calendar now = Calendar.getInstance();

        // Proposed date of registration must be in the past!
        return now.after(dor);
    }

    public String getStatus() {
        return status;
    }

    private boolean isStatusValid(String status) {
        for (String test : validStatuses) {
            if (test.equals(status)) {
                return true;
            }
        }

        return false;
    }

    public void setStatus(String status) {
        if (status == null) {
            throw new IllegalArgumentException("Argument 'status' cannot be null.");
        }

        if (!isStatusValid(status)) {
            throw new IllegalArgumentException("Argument 'status' is not valid.");
        }

        this.status = status;
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


    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("balance cannot be negative.");
        }

        this.balance = balance;
    }
}
