/**
 * @author alex, bryan
 */
package com.xyzdrivers.controllers;

import com.xyzdrivers.models.Claim;
import com.xyzdrivers.models.Member;
import com.xyzdrivers.models.MembershipPayment;
import com.xyzdrivers.repositories.ClaimsRepo;
import com.xyzdrivers.repositories.MembersRepo;
import com.xyzdrivers.repositories.PaymentRepo;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.*;

public class AdminController extends HttpServlet {
    
    @Inject
    private MembersRepo membersRepo;
    
    @Inject
    private ClaimsRepo  claimsRepo;
    
    @Inject
    private PaymentRepo paymentRepo;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException
    {
        //get DB data
        List<Member> members = membersRepo.get();
        List<Member> provisionalMembers = new ArrayList<>();
        List<Member> normalMembers = new ArrayList<>();
        for (Member member: members) {
            if (member.isProvisional()) {
                provisionalMembers.add(member);
            } else {
                normalMembers.add(member);
            }
        }
        
        List<Member> outstandingBalance = membersRepo.getWhere("status", "OUTSTANDING");
        List<Claim> claims = claimsRepo.get();
        List<MembershipPayment> payments = paymentRepo.get();
        float totalTurnover = 0;
        for (MembershipPayment payment: payments) {
            totalTurnover += payment.getPaymentAmount();
        }
        //set attributes
        request.setAttribute("members", normalMembers);
        request.setAttribute("provisionalMembers", provisionalMembers);
        request.setAttribute("outstandingBalance", outstandingBalance);
        request.setAttribute("claims", claims);
        request.setAttribute("totalTurnover", totalTurnover);
        //fwd .jsp page
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }

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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        // processRequest(request, response);
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
