/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyzdrivers.controllers;

import com.xyzdrivers.models.Member;
import com.xyzdrivers.models.User;
import com.xyzdrivers.repositories.MembersRepo;
import com.xyzdrivers.repositories.RepositoryException;
import com.xyzdrivers.repositories.UserRepo;
import com.xyzdrivers.services.ResponseService;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author arthur
 */
public class RegisterController extends HttpServlet {
    
    @Inject
    private UserRepo userRepo;
    
    @Inject
    private MembersRepo memberRepo;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.text.ParseException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String strDob = request.getParameter("dob");
        
        // Generate username from name
        String[] tokens = name.split("\\s+");
        if (tokens.length < 2) {
            ResponseService.fail(request, response, "Registering failed, please provide a first and second name", "register.jsp");
        }
        String username = tokens[0].charAt(0) + "-" + tokens[1];
        
        // Format dates
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat passwordFormat = new SimpleDateFormat("ddMMyy");
        Calendar dob = Calendar.getInstance();
        Calendar dor = Calendar.getInstance();
        dob.setTime(dateFormat.parse(strDob));
       
        // Generate password
        Date date = dateFormat.parse(strDob);
        String password = passwordFormat.format(date);
        
        // Create auth user
        User user = new User(username, password, "PENDING");
        try {
            userRepo.insert(user);
        } catch (RepositoryException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            ResponseService.fail(request, response, "Registering failed, please try again", "register.jsp");
            return;
        }
        
        // Create membership
        Member member = new Member(username, name, address, dob, dor, "APPROVED", 0.00);
        try {
            memberRepo.insert(member);
        } catch (RepositoryException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            ResponseService.fail(request, response, "Registering failed, please try again", "register.jsp");
            return;
        }
        
        request.getRequestDispatcher("login.jsp").forward(request, response);
        
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
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
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
