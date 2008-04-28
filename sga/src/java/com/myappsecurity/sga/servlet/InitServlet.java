/*
 * InitServlet.java
 */

package com.myappsecurity.sga.servlet;

import com.myappsecurity.sga.dao.AdminDAO;
import com.myappsecurity.sga.util.Constants;
import com.myappsecurity.sga.vo.AdminVO;
import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 *
 * @author Chander Singh
 * Created on October 15, 2007, 4:26 PM
 */
public class InitServlet extends HttpServlet {
    private Logger logger = null;
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        /* TODO output your page here
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet InitServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet InitServlet at " + request.getContextPath () + "</h1>");
        out.println("</body>");
        out.println("</html>");
         */
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

    public void init(ServletConfig config) throws ServletException {
        logger = Logger.getLogger (InitServlet.class);
        String propertyFile = config.getInitParameter("PROPERTY_FILE_LOC");
        MDC.put("MyMDC1", "");
        MDC.put("MyMDC2", "DEBUG");
        MDC.put("MyMDC3", "");
        MDC.put("MyMDC4", "");
        MDC.put("MyMDC5", "");
        MDC.put("MyMDC6", this.getClass().getName());
        logger.debug("Property File: " + propertyFile);
        MDC.remove("MyMDC1");
        MDC.remove("MyMDC2");
        MDC.remove("MyMDC3");
        MDC.remove("MyMDC4");
        MDC.remove("MyMDC6");
        new Constants (propertyFile);        
        ServletContext context = config.getServletContext();
        try {
            AdminDAO adminDAO = new AdminDAO ();
            AdminVO[] adminVOs = adminDAO.fetchApplicationNames();            
            context.setAttribute("ADMINDET", adminVOs);
        } catch (Exception exp) {
            throw new ServletException (exp.getMessage());
        }
    }
}
