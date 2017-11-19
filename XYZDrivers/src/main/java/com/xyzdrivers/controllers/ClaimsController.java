/**
 * @file ClaimsController.java
 * @author Nathan
 * @created 30/10/17
 * @modified 04/11/17
 * @notes -
 */
package com.xyzdrivers.controllers;

import com.xyzdrivers.models.Claim;
import com.xyzdrivers.services.InsertClaimService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ClaimsController extends HttpServlet {
    
    @Inject
    private InsertClaimService insertClaimService;
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Calendar date = Calendar.getInstance();
            
            String reason = request.getParameter("reason");
            String amount = request.getParameter("amount");
            
            HttpSession session = request.getSession();
            String username = (String)session.getAttribute("username");
            
            Claim claim = new Claim(username, date, reason, "SUBMITTED", Float.parseFloat(amount));

            insertClaimService.InsertClaim(claim);
        } catch (SQLException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
