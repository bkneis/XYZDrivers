/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.xyzdriverswebservice;

import java.util.Calendar;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

/**
 *
 * @author Nathan
 */
@WebService(serviceName = "ClaimEligibility")
@Stateless()
public class ClaimEligibility {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "Eligibility")
    public String Eligibility(
            @WebParam(name = "username") String username,
            @WebParam(name = "joinedDate") Calendar joinedDate,
            @WebParam(name = "listOfClaimDates") ArrayList<Calendar> listOfClaimDates,
            @WebParam(name = "listOfClaimStatuses") ArrayList<String> listOfClaimStatuses) {

        int claimCounter = 0;
        Calendar calendar = Calendar.getInstance();

        try {
            NullCheck(username, joinedDate, listOfClaimDates, listOfClaimStatuses);

            calendar.add(Calendar.MONTH, -6); //this takes us to six months ago

            if (joinedDate.after(calendar)) {
                return username + ", you have joined less than six months ago. As such, you are not yet eligible to make a claim.";
            }

            calendar.add(Calendar.MONTH, -6); //this takes us to a year ago

            for (int i = 0; i < listOfClaimDates.size(); i++) {
                if (listOfClaimDates.get(i).after(calendar)) {
                    if (!"REJECTED".equals(listOfClaimStatuses.get(i))) {
                        claimCounter++;
                    }
                }
            }

            if (claimCounter >= 2) {
                return username + ", you have made too many claims in the past year. As such, you are not yet eligible to make a claim.";
            }

        } catch (NullPointerException ex) {
            return ex.toString();
        }

        return "You are eligible to make a claim! You have made " + claimCounter + " claims this year.";
    }

    private void NullCheck(String username, Calendar joinedDate, ArrayList listOfClaimDates, ArrayList listOfClaimStatuses) throws NullPointerException {
        if (username == null || username.isEmpty()) {
            throw new NullPointerException("Username is null or empty: " + username);
        }
        if (joinedDate == null) {
            throw new NullPointerException("Joined Date is null: " + joinedDate);
        }

        if (listOfClaimDates.isEmpty()) {
            throw new NullPointerException("List of claim dates is empty: " + listOfClaimDates);
        }

        if (listOfClaimStatuses.isEmpty()) {
            throw new NullPointerException("List of claim statuses is empty: " + listOfClaimStatuses);
        }

    }
}
