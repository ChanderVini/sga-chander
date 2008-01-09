/*
 * LoginServlet.java
 *
 * Created on January 8, 2008, 8:17 AM
 */

package com.whitehat.sga.servlet;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 *
 * @author csingh
 * @version
 */
public class LoginServlet extends HttpServlet {
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Logger logger = Logger.getLogger(LoginServlet.class);
        logger.debug("In Login Servlet");
        
        MDC.put("MyMDC3", "Hi");
        logger.debug("Errors during processing Request: ");
        MDC.remove("MyMDC3");
            
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet LoginServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet LoginServlet at " + request.getContextPath () + "</h1>");
        out.println("</body>");
        out.println("</html>");         
        out.close();
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
