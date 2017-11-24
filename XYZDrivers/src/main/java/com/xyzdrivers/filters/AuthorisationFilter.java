/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyzdrivers.filters;

import com.xyzdrivers.models.User;
import com.xyzdrivers.repositories.RepositoryException;
import com.xyzdrivers.repositories.UserRepo;
import com.xyzdrivers.services.UserService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Toneo
 */
public class AuthorisationFilter implements Filter {

    @Inject
    private UserService userService;
    
    @Inject
    private UserRepo userRepo;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No implementation required
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        HttpSession session = httpRequest.getSession(true);
        
        String loggedInUsername = getLoggedInUsername(session);
        
        if (loggedInUsername == null) {
            String loginUrl = httpRequest.getContextPath() + "/login.jsp";
            
            httpResponse.sendRedirect(loginUrl);           
            
            return;
        }
        else {
            // Update the user variable from the database, just in case it changed
            User user;
            
            try {
                user = userRepo.get(loggedInUsername);
            } catch (RepositoryException ex) {
                throw new ServletException("AuthorisationFilter encountered an exception when retrieving the logged-in user.", ex);
            }
            
            if (user == null) {
                throw new RuntimeException("The logged-in user does not exist in the database.");
            }
            else {
                session.setAttribute("user", user);
            }
        }
        
        chain.doFilter(request, response);
        
    }
    
    private String getLoggedInUsername(HttpSession session)
    {
        Object rawValue = session.getAttribute("username");
         
        return rawValue == null ? null : (String)rawValue;
    }

    @Override
    public void destroy() {
        // No implementation required
    }
    
}
