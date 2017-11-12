package com.xyzdrivers.services;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Service to provide static short hand functions for returning the user to a success or error page with a custom message and back link
 * 
 * @author arthur
 */
public class ResponseService {
    
    /**
     * Short hand function to return user to success page
     * 
     * @param request
     * @param response
     * @param message
     * @param link
     * @throws ServletException
     * @throws IOException 
     */
    public static void success(HttpServletRequest request, HttpServletResponse response, String message, String link) throws ServletException, IOException {
       ResponseService.respond(request, response, message, link, "success.jsp"); 
    }
    
    /**
     * Short hand function to return suer to failure page
     * 
     * @param request
     * @param response
     * @param message
     * @param link
     * @throws ServletException
     * @throws IOException 
     */
    public static void fail(HttpServletRequest request, HttpServletResponse response, String message, String link) throws ServletException, IOException {
        ResponseService.respond(request, response, message, link, "error.jsp");
    }
    
    /**
     * Set the required attributes and return user to either success or failure page
     * 
     * @param request
     * @param response
     * @param message
     * @param link
     * @param page
     * @throws ServletException
     * @throws IOException 
     */
    private static void respond(HttpServletRequest request, HttpServletResponse response, String message, String link, String page) throws ServletException, IOException {
        request.setAttribute("message", message);
        request.setAttribute("link", link);
        request.getRequestDispatcher(page).forward(request, response);
    }
    
}
