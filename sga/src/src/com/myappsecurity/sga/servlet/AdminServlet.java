package com.myappsecurity.sga.servlet;

import com.myappsecurity.sga.dao.AdminDAO;
import com.myappsecurity.sga.util.ApplicationUtil;
import com.myappsecurity.sga.vo.AdminVO;
import java.io.*;
import java.net.*;

import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 *
 * @author csingh
 */
public class AdminServlet extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Logger logger = Logger.getLogger(AdminServlet.class);
        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();
        int index = requestURI.indexOf(contextPath);
        requestURI = requestURI.substring(index + contextPath.length() + 1);
                
        index = requestURI.indexOf("/");
        String operation = requestURI.substring(index + 1);
        String appTypeCd = requestURI.substring(0, index);
        String redirectURL = null;        
        
        MDC.put("MyMDC1", request.getRemoteAddr());
        MDC.put("MyMDC2", "DEBUG");
        MDC.put("MyMDC3", "");
        MDC.put("MyMDC4", "");
        MDC.put("MyMDC5", "");
        MDC.put("MyMDC6", request.getRequestURL().toString());
        logger.debug (operation);
        
        if ("appfilter".equals (operation)) {
            String[] values = request.getParameterValues("xssfilter");
            if (values == null) {
                ServletContext context = getServletContext();
                Object adminVOObj = context.getAttribute("ADMINDET");
                
                if (adminVOObj != null) {
                    AdminVO[] adminVOs = (AdminVO[]) adminVOObj;
                    for (int cnt = 0; cnt < adminVOs.length; cnt++) {
                        adminVOs[cnt].setFilterStatus("false");
                    }
                    context.setAttribute("ADMINDET", adminVOs);
                }   
            } else {
                for (int cnt = 0; cnt < values.length; cnt++) {
                    values [cnt] = values[cnt].toUpperCase();
                }
                ArrayList valuesAL = new ArrayList (Arrays.asList(values));
                ServletContext context = getServletContext();
                Object adminVOObj = context.getAttribute("ADMINDET");
                if (adminVOObj != null) {
                    AdminVO[] adminVOs = (AdminVO[]) adminVOObj;
                    for (int cnt = 0; cnt < adminVOs.length; cnt++) {
                        String appname = adminVOs[cnt].getApplicationName();
                        String apptype = adminVOs[cnt].getApplicationType();
                        String app = "SGA-" + apptype + "-" + appname;                    
                        int loc = valuesAL.indexOf(app.toUpperCase());
                        if (loc > -1) { 
                            adminVOs[cnt].setFilterStatus("true");
                        } else {
                            adminVOs[cnt].setFilterStatus("false");
                        }                    
                    }
                    context.setAttribute("ADMINDET", adminVOs);
                }   
                valuesAL = null;
            }
        }
        if ("console".equals (operation)) {
            Object adminObj = getServletContext().getAttribute("ADMINDET");
            if (adminObj == null) {
                try {
                    AdminDAO adminDAO = new AdminDAO ();
                    AdminVO[] adminVOs = adminDAO.fetchApplicationNames();
                    getServletContext().setAttribute ("ADMINDET", adminVOs);
                } catch (Exception exp) {
                    MDC.put ("MyMDC2", "ERROR");
                    logger.error(exp.getMessage(), exp);
                    request.setAttribute ("EXCEPTION", exp);
                    operation = "exception";                
                }
            }
        }
        
        redirectURL = new ApplicationUtil ().getRedirectPage(appTypeCd, null, operation);
        MDC.put ("MyMDC2", "DEBUG");
        MDC.put("MyMDC6", redirectURL);
        logger.debug("Redirecting page.");
        MDC.remove("MyMDC6");
        RequestDispatcher reqDispatcher = getServletContext().getRequestDispatcher("/" + redirectURL);
        reqDispatcher.forward(request, response);
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
    * Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
    * Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
