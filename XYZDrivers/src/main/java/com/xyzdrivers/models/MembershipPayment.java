package com.xyzdrivers.models;

import java.util.Calendar;

/*
 * @author Joe Dicker
 */
public class MembershipPayment {

    private String mem_id;
    private String type_of_payment;
    private Calendar date;
    private float amount;
    private Calendar time;

    public MembershipPayment(String mem_id, String type_of_payment, float amount, Calendar date, Calendar time) {
        this.mem_id = mem_id;
        this.type_of_payment = type_of_payment;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }

    public String getMemberID() {
        return this.mem_id;
    }

    public String getPaymentType() {
        return this.type_of_payment;
    }

    public Calendar getDate() {
        return this.date;
    }

    public float getPaymentAmount() {
        return this.amount;
    }

    public Calendar getTime() {
        return this.time;
    }

}
