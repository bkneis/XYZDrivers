/**
 * @author alex
 */
package com.xyzdrivers.controllers;

import com.webservices.xyzdriverswebservice.ClaimEligibility_Service;
import com.webservices.xyzdriverswebservice.ClaimEligibility;
import com.xyzdrivers.models.Claim;
import com.xyzdrivers.models.Member;
import com.xyzdrivers.repositories.ClaimsRepo;
import com.xyzdrivers.repositories.RepositoryException;
import com.xyzdrivers.repositories.MembersRepo;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.ws.WebServiceRef;

public class AdminController extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_9090/ClaimEligibility/ClaimEligibility.wsdl")
    private ClaimEligibility_Service service;

    @Inject
    private MembersRepo membersRepo;

    @Inject
    private ClaimsRepo claimsRepo;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     * @throws com.xyzdrivers.repositories.RepositoryException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, RepositoryException {
        List<Member> members = membersRepo.get();
        List<Member> outstandingBalance = membersRepo.getWhere("STATUS", "OUTSTANDING");

        List<Claim> claims = claimsRepo.get();
        
        ClaimEligibility port = service.getClaimEligibilityPort();
        List<String> eligibleClaims = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 

        for (Member member : members) {
            String username = member.getId();
            java.util.Date tempDate = member.getDor().getTime();
            String joinedDate = sdf.format(tempDate);
            List<String> listOfClaimDates = new ArrayList();
            List<String> listOfClaimStatuses = new ArrayList();

            for (Claim c : claims) {
                if (c.getMemberID().equals(username)) {
                    String str = sdf.format(c.getDate().getTime());                                      
                    listOfClaimDates.add(str); 
                    listOfClaimStatuses.add(c.getStatus());
                }
            }
            eligibleClaims.add(port.eligibility(username, joinedDate, listOfClaimDates, listOfClaimStatuses));
        }

        //set attributes
        request.setAttribute("members", members);
        request.setAttribute("outstandingBalance", outstandingBalance);
        request.setAttribute("claims", claims);
        request.setAttribute("eligibleClaims", eligibleClaims);

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
        } catch (SQLException | RepositoryException ex) {
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
