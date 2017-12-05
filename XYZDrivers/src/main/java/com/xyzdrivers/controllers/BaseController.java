/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyzdrivers.controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Toneo
 */
public abstract class BaseController extends HttpServlet {
    
    public void redirectError(String errorMessage, String redirectPath, HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        if (errorMessage == null)
        {
            throw new IllegalArgumentException("errorMessage cannot be null.");
        }
        
        if (redirectPath == null)
        {
            throw new IllegalArgumentException("redirectPath cannot be null.");
        }
        
        if (request == null)
        {
            throw new IllegalArgumentException("request cannot be null.");
        }
        
        if (response == null)
        {
            throw new IllegalArgumentException("response cannot be null.");
        }
        
        request.setAttribute("errorMessage", errorMessage);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher(redirectPath);
        dispatcher.forward(request, response);
    }
    
}
