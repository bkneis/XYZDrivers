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
import java.util.List;
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

        int claimCounter = 0;
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        Calendar calendarJoinedDate = Calendar.getInstance();
        List<Calendar> calendarClaimDates = new ArrayList();
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");        
            calendarJoinedDate.setTime(sdf.parse(joinedDate));
            
            for(int i = 0; i < listOfClaimDates.size(); i++)
            {
                Calendar tempCalendar = Calendar.getInstance();
                tempCalendar.setTime(sdf.parse(listOfClaimDates.get(i)));
                calendarClaimDates.add(tempCalendar);
            }
            
            NullCheck(username, calendarJoinedDate, listOfClaimDates, listOfClaimStatuses);

            calendar.add(Calendar.MONTH, -6); //six months ago

            if (calendarJoinedDate.after(calendar)) {
                return username + " has joined less than six months ago. As such, they are not yet eligible to make a claim.";
            }

            calendar.set(Calendar.YEAR, 2017); //start of current year
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            
            calendar2.set(Calendar.YEAR, 2017); //end of current year
            calendar2.set(Calendar.MONTH, Calendar.DECEMBER);
            calendar2.set(Calendar.DAY_OF_MONTH, 31);

            for (int i = 0; i < calendarClaimDates.size(); i++) {
                if (calendarClaimDates.get(i).after(calendar) && calendarClaimDates.get(i).before(calendar2)) {
                    if ("APPROVED".equals(listOfClaimStatuses.get(i))) {
                        claimCounter++;
                    }
                }
            }

            if (claimCounter >= 2) {
                return username + "has made too many claims this year. As such, they are not yet eligible to make a claim.";
            }

        } catch (NullPointerException | ParseException ex) {
            return ex.toString();
        }

        return "This user is eligible to make a claim! They have made " + claimCounter + " claims this year.";
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
