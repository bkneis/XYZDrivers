package com.xyzdrivers.controllers;

import com.xyzdrivers.models.MembershipPayment;
import com.xyzdrivers.repositories.RepositoryException;
import com.xyzdrivers.services.InsertPaymentService;
import java.io.IOException;
import java.util.Calendar;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Joe Dicker
 */
public class PaymentController extends BaseController {

    @Inject
    private InsertPaymentService insertPaymentService;

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

        Calendar date = Calendar.getInstance();
        Calendar time = Calendar.getInstance();
        float amount;

        try {
            amount = Float.parseFloat(request.getParameter("amount"));
        } catch (NumberFormatException ex) {
            amount = 0;
        }

        if (amount <= 0) {
            redirectError("Amount was not valid.", "submitpayment.jsp", request, response);
            return;
        }

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        MembershipPayment p = new MembershipPayment(username, "FEE", amount, date, time);

        try {
            insertPaymentService.InsertPayment(p);
        } catch (RepositoryException | IllegalAccessException ex) {
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
