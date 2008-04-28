/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.myappsecurity.sga.servlet;

import com.myappsecurity.sga.util.ApplicationUtil;
import com.myappsecurity.sga.vo.ContactusVO;
import com.myappsecurity.sga.vo.UserVO;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 *
 * @author Chander Singh
 * @created.on Mar 25, 2008
 * */
public class ContactusServlet extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Logger logger = Logger.getLogger (ContactusServlet.class);
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
        request.getSession ().setAttribute("APPTYPE", apptype);
        request.getSession ().setAttribute("APPNAME", appname); 
        
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
        
        if ("contact".equals(operation)) {
            request.setAttribute("ERRROS", new HashMap ());
        }
        if ("contactsub".equals(operation)) {
            HashMap errorMap = new HashMap ();
            ContactusVO contactusVO = new ContactusVO ();
            String name = request.getParameter("username");
            contactusVO.setUsername(name);
            if (name == null || "".equals (name)) {
                errorMap.put ("username", "required");
            }
            String email = request.getParameter("email");
            contactusVO.setEmail(email);
            if (email == null || "".equals (email)) {
                errorMap.put ("email", "required");
            }
            String subject = request.getParameter("subject");
            contactusVO.setSubject(subject);
            if (subject == null || "".equals (subject)) {
                errorMap.put ("subject", "required");
            }
            String emailbody = request.getParameter("emailbody");
            contactusVO.setEmailbody(emailbody);
            if (emailbody == null || "".equals (emailbody)) {
                errorMap.put ("emailbody", "required");
            }
            if (errorMap.isEmpty ()) {
                StringBuffer messageBuf = new StringBuffer ("Hi ");
                messageBuf.append (name);
                messageBuf.append (", your comments have been mailed. Some one will contact you shortly on ");
                messageBuf.append (email);
                request.setAttribute("MESSAGE", messageBuf.toString());
                messageBuf = null;
                /**String recipient = Constants.getProperty("ADMIN_EMAIL");
                String[] recipients = SGAUtil.getArray(recipient, ",");
                EmailSender emailSender = new EmailSender();
                try {
                    emailSender.postMail(recipients, subject, emailbody, email, name);
                } catch (Exception exp) {
                    request.setAttribute ("EXCEPTION", exp);
                    operation = "exception";
                }**/
            } else {
                operation = "contactsuberror";
                request.setAttribute ("ERRORS", errorMap);
            }
            request.setAttribute ("CONTACT", contactusVO);
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
