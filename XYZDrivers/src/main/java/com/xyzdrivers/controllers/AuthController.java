/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyzdrivers.controllers;

import com.xyzdrivers.models.User;
import com.xyzdrivers.repositories.RepositoryException;
import com.xyzdrivers.repositories.UserRepo;
import com.xyzdrivers.services.UserService;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author arthur
 */
public class AuthController extends BaseController {
    
    @Inject
    private UserService userService;
    
    @Inject
    private UserRepo userRepo;

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
        
        // Pass userType along from Home index
        request.setAttribute("userType", request.getParameter("userType"));
        
        String actionType = request.getParameter("actionType");
        
        RequestDispatcher dispatcher;
        
        if ("login".equals(actionType))
        {
            dispatcher = request.getRequestDispatcher("login.jsp");
        }
        else {
            dispatcher = request.getRequestDispatcher("register.jsp");
        }
        
        dispatcher.forward(request, response);
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
        
        String username = (String)request.getParameter("username");
        String password = (String)request.getParameter("password");
        String userType = (String)request.getParameter("userType");
        
        User loggedInUser = null;
        HttpSession session = request.getSession(true);
        
        if (username != null && password == null)
        {      
            try {            
                if (userService.checkLoginDetails(username, password))
                {
                    session.setAttribute("username", username);
                    loggedInUser = userRepo.get(username);
                }
            } catch (RepositoryException ex) {
                redirectError(ex.getMessage(), "error.jsp", request, response);
                return;
            }
        }       
        
        if (loggedInUser == null) {
            request.setAttribute("userType", userType);
            request.setAttribute("loginFailed", true);
            
            redirectError("Login failed. Please check your credentials are correct.", "login.jsp", request, response);
        }
        else {
            String status = loggedInUser.getStatus();
            
            // AuthorisationFilter hasn't had a chance to run yet, so fill in the user here
            session.setAttribute("user", loggedInUser);
            
            if ("admin".equals(userType)) {
                response.sendRedirect(request.getContextPath() + "/admin");
            }
            else {
                response.sendRedirect(request.getContextPath() + "/member");
            }
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
