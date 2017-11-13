package com.xyzdrivers.controllers;

import com.xyzdrivers.models.Claim;
import com.xyzdrivers.repositories.ClaimsRepo;
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
public class ClaimStatusController extends HttpServlet {
    
    @Inject
    ClaimsRepo claimsRepo;

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
        
        // Get the http request params
        String status = request.getParameter("status");
        Integer id = Integer.parseInt(request.getParameter("claim_id"));
        
        // Sanity check to ensure required parameters were required, if not respond with failure
        if (status == null || status.equals("")) {
            ResponseService.fail(request, response, "Failure. Please submit a status and claim id", "admin");
            return;
        }
        
        // Get the claim to be updated by id
        Claim claim = claimsRepo.get(id);
        
        // Check we found the claim by ID
        if (claim == null) {
            ResponseService.fail(request, response, "Failure. The claim could not be found by id", "admin");
            return;
        }
        
        // Set the status using model's setter to ensure status is valid, if not respond with failure
        if (! claim.setStatus(status)) {
            ResponseService.fail(request, response, "Failure. Please submit a valid status", "admin");
            return;
        }
        
        // Update the model and persist to DB
        claim = claimsRepo.update(claim);
        
        // Respond with success
        ResponseService.success(request, response, "Success. The claim status has been updated to " + claim.getStatus(), "admin");
        
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
