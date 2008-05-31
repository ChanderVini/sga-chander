/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.myappsecurity.sga.servlet;

import com.myappsecurity.sga.dao.CategoryDAO;
import com.myappsecurity.sga.util.ApplicationUtil;
import com.myappsecurity.sga.vo.CategoryVO;
import com.myappsecurity.sga.vo.UserVO;
import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 *
 * @author Chander Singh
 */
public class CategoryServlet extends HttpServlet {
   
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
        String appdetail = requestURI.substring(0, index);
        String[] appdetails = appdetail.split("-");
        String apptype = appdetails[0];
        String appname = appdetails[1];
        
        MDC.put("MyMDC1", request.getRemoteAddr());
        MDC.put("MyMDC2", "DEBUG");
        MDC.put("MyMDC3", apptype);
        MDC.put("MyMDC4", appname);
        if (request.getSession ().getAttribute("USER") == null) {
            MDC.put("MyMDC5", "'");
        } else {
            UserVO userVO = (UserVO) (request.getSession ().getAttribute("USER"));            
            MDC.put("MyMDC5", userVO.getUserName());
        }
        MDC.put("MyMDC6", request.getRequestURL().toString());
        logger.debug (operation);
        
        Object object = request.getSession ().getAttribute("CATEGORIES");
        request.getSession ().setAttribute("APPTYPE", apptype);
        request.getSession ().setAttribute("APPNAME", appname);
        CategoryVO[] categoryVOs = null;
        if (object == null) {
            try {
                CategoryDAO categoryDAO = new CategoryDAO();
                categoryVOs = categoryDAO.fetchCategories();
            } catch (Exception exp) {
                MDC.put ("MyMDC2", "ERROR");
                logger.error(exp.getMessage(), exp);
                request.setAttribute ("EXCEPTION", exp);
                operation = "exception";
            } 
            request.getSession().setAttribute("CATEGORIES", categoryVOs);
        } else {
            categoryVOs =  (CategoryVO[]) object;
        }
        String redirectURL = new ApplicationUtil ().getRedirectPage(apptype, appname, operation);
        MDC.put ("MyMDC2", "DEBUG");
        MDC.put("MyMDC6", redirectURL);
        logger.debug("Redirecting page.");
        MDC.remove("MyMDC6");
        RequestDispatcher reqDispatcher = request.getRequestDispatcher("../" + redirectURL);
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
