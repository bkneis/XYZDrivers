package com.xyzdrivers.models;

import java.time.LocalDate;
import java.time.LocalTime;

/*
 * @author Joe Dicker
 */
public class MembershipPayment {

    private String mem_id;
    private String type_of_payment;
    private LocalDate date;
    private float amount;
    private LocalTime time;

    public MembershipPayment(String mem_id, String type_of_payment, float amount, LocalDate date, LocalTime time) {
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

    public LocalDate getDate() {
        return this.date;
    }

    public float getPaymentAmount() {
        return this.amount;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public void updateBalance() {

    }

}
