/**
 * @file Claim.java
 * @author Nathan
 * @created 02/11/17
 * @modified 02/11/17
 * @notes -
 */

package com.xyzdrivers.models;

import java.time.LocalDate;

public class Claim {

    private String member_id;
    private LocalDate date;
    private String reason;
    private String status;
    private int amount;

    public Claim(String member_id, LocalDate date, String reason, String status, int amount) {
        this.member_id = member_id;
        this.date = date;
        this.reason = reason;
        this.status = status;
        this.amount = amount;
    }

    public String getMemberID() {
        return this.member_id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public String getReason() {
        return this.reason;
    }

    public String getStatus() {
        return this.status;
    }

    public int getAmount() {
        return this.amount;
    }

}
