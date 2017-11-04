/**
 * @file ClaimsController.java
 * @author Nathan
 * @created 30/10/17
 * @modified 03/11/17
 * @notes -
 */
package com.xyzdrivers.controllers;

import com.xyzdrivers.models.Claim;
import com.xyzdrivers.services.InsertClaimService;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ClaimsController extends HttpServlet {
    
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
            LocalDate date = LocalDate.now();

            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TestDatabase_DEV", "lenfred", "fruitgums1");
            String reason = request.getParameter("reason");
            String amount = request.getParameter("amount");

            Claim c = new Claim("null", date, reason, "NEW", Integer.parseInt(amount));

            InsertClaimService ic = new InsertClaimService(con);

            ic.InsertClaim(c);
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
