/**
 * @file Claim.java
 * @author Nathan
 * @created 02/11/17
 * @modified 02/11/17
 * @notes -
 */

package com.xyzdrivers.models;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

public class Claim extends Model {

    private Integer id;
    private String member_id;
    private Calendar date;
    private String reason;
    private String status;
    private double amount;

    private List<String> allowedStatuses;

    public static final String TABLE_NAME = "claims";
    public static final String PRIMARY_KEY = "id";

    public Claim(String member_id, Calendar date, String reason, String status, double amount) {
        this.member_id = member_id;
        this.date = date;
        this.reason = reason;
        this.status = status;
        this.amount = amount;
        this.allowedStatuses = new ArrayList<>();
        allowedStatuses.add("APPROVED");
        allowedStatuses.add("REJECTED");
        allowedStatuses.add("PENDING");
    }

    public Integer getId() {
        return this.id;
    }

    public String getMemberID() {
        return this.member_id;
    }

    public Calendar getDate() {
        return this.date;
    }

    public String getReason() {
        return this.reason;
    }

    public String getStatus() {
        return this.status;
    }

    public double getAmount() {
        return this.amount;
    }

    public boolean setStatus(String status) {
        if (allowedStatuses.contains(status)) {
            this.status = status;
            return true;
        }
        return false;
    }

}
