package com.xyzdrivers.controllers;

import com.xyzdrivers.models.Member;
import com.xyzdrivers.repositories.MembersRepo;
import com.xyzdrivers.services.ResponseService;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author arthur
 */
public class MembershipStatusController extends HttpServlet {
    
    @Inject
    MembersRepo membersRepo;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String status = request.getParameter("status");
        String id = request.getParameter("member_id");
        
        if (status == null || status.equals("")) {
            ResponseService.fail(request, response, "Failure. Please submit a status and membership id", "/admin");
            return;
        }
        
        Member member = membersRepo.get(id);
        
        if (! member.setStatus(status)) {
            ResponseService.fail(request, response, "Failure. Please submit a valid status", "admin");
            return;
        }
        
        member = membersRepo.update(member);
        
        ResponseService.success(request, response, "Success. The membership status has been updated to " + member.getStatus(), "admin");
        
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
        processRequest(request, response);
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
        processRequest(request, response);
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
