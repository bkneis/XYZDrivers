/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webservices.xyzdriverswebservice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            @WebParam(name = "joinedDate") String joinedDate,
            @WebParam(name = "listOfClaimDates") ArrayList<String> listOfClaimDates,
            @WebParam(name = "listOfClaimStatuses") ArrayList<String> listOfClaimStatuses) {

        Calendar calendarSixMonths = getSixMonthsAgo();  //set to six months ago
        Calendar calendarStartYear = getStartYear();  //set to start of current year
        Calendar calendarEndYear = getEndYear();    //set to end of current year
        Calendar calendarJoinedDate = Calendar.getInstance(); //incoming joined date is converted to calender

        List<Calendar> calendarClaimDates = new ArrayList();  //incoming List<String> is converted to List<Calender>

        String returnStatement = "";

        try {
            if (username == null || username.isEmpty()) {
                throw new IllegalArgumentException("Username is null or empty: " + username);
            }
            if (joinedDate == null) {
                throw new IllegalArgumentException("Joined Date is null: " + joinedDate);
            }
            if (listOfClaimDates == null) {
                throw new IllegalArgumentException("List of claim dates is null: " + listOfClaimDates);
            }

            if (listOfClaimStatuses == null) {
                throw new IllegalArgumentException("List of claim statuses is null: " + listOfClaimStatuses);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(joinedDate);
            calendarJoinedDate.setTime(date);

            for (int i = 0; i < listOfClaimDates.size(); i++) //convert dates list
            {
                Calendar tempCalendar = Calendar.getInstance();
                Date tempDate = sdf.parse(listOfClaimDates.get(i));
                tempCalendar.setTime(tempDate);
                calendarClaimDates.add(tempCalendar);
            }

            if (calendarJoinedDate.after(calendarSixMonths)) {
                returnStatement = " has joined less than six months ago. As such, they are not yet eligible to make a claim.";
            } else {
                returnStatement = checkNumberOfClaims(calendarStartYear, calendarEndYear, calendarClaimDates, listOfClaimStatuses);
            }

        } catch (IllegalArgumentException | ParseException ex) {
            Logger.getLogger(ClaimEligibility.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }

        return username + returnStatement;
    }

    private String checkNumberOfClaims(Calendar calendarStartYear, Calendar calendarEndYear, List<Calendar> calendarClaimDates, List<String> listOfClaimStatuses) {
        int claimCounter = 0;

        for (int i = 0; i < calendarClaimDates.size(); i++) {
            Calendar currentClaim = calendarClaimDates.get(i);
            
            if (currentClaim.after(calendarStartYear) && currentClaim.before(calendarEndYear)) {
                if ("APPROVED".equals(listOfClaimStatuses.get(i))) { //only increment claim counter if the claim has been approved
                    claimCounter++;
                }
            }
        }

        if (claimCounter >= 2) { //if user has made more than two claims in current year
            return " has made too many claims this year. As such, they are not yet eligible to make a claim.";
        }

        return " is eligible to make a claim! They have made " + claimCounter + " claims this year.";
    }

    private Calendar getSixMonthsAgo() {
        Calendar calendarSixMonths = Calendar.getInstance();

        calendarSixMonths.add(Calendar.MONTH, -6); //six months ago

        return calendarSixMonths;
    }

    private Calendar getStartYear() {
        Calendar calendarStartYear = Calendar.getInstance();

        calendarStartYear.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR)); //start of current year
        calendarStartYear.set(Calendar.MONTH, Calendar.JANUARY);
        calendarStartYear.set(Calendar.DAY_OF_MONTH, 1);

        return calendarStartYear;
    }

    private Calendar getEndYear() {
        Calendar calendarEndYear = Calendar.getInstance();

        calendarEndYear.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR)); //end of current year
        calendarEndYear.set(Calendar.MONTH, Calendar.DECEMBER);
        calendarEndYear.set(Calendar.DAY_OF_MONTH, 31);

        return calendarEndYear;
    }
}
