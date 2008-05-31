/*
 * LoginServlet.java
 *
 * Created on January 8, 2008, 8:17 AM
 */

package com.myappsecurity.sga.servlet;

import com.myappsecurity.sga.dao.UserDAO;
import com.myappsecurity.sga.util.ApplicationUtil;
import com.myappsecurity.sga.vo.ShoppingCartVO;
import com.myappsecurity.sga.vo.UserVO;
import java.io.*;
import java.net.*;

import java.util.HashMap;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 *
 * @author Chander Singh
 * @version
 */
public class LoginServlet extends HttpServlet {
    
   /**
    * 
    * @param request
    * @param response
    * @throws javax.servlet.ServletException
    * @throws java.io.IOException
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Logger logger = Logger.getLogger(LoginServlet.class);
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
        String redirectURL = null;        
        
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
        
         if ("authenticate".equals (operation)) {
            String userName = request.getParameter("userName");
            String userpassword = request.getParameter("userPassword");
            System.out.println ("User Name: " + userName);
            UserDAO userDAO = new UserDAO ();
            try {
                UserVO userVO = userDAO.authenticate(userName, userpassword, "USER", apptype, appname);
                if (userVO == null) {
                    userVO = new UserVO ();
                    userVO.setUserName(userName);
                    request.setAttribute ("USER", userVO);
                    operation = "loginerror";
                    HashMap errorMap = new HashMap ();
                    errorMap.put("error", "User name and/or password is incorrect");
                    request.setAttribute ("ERRORS", errorMap);
                } else {   
                    Object shoppingCartVOObj = request.getSession().getAttribute("SHOPPINGCART");
                    if (shoppingCartVOObj != null) {
                        ShoppingCartVO shoppingCartVO = (ShoppingCartVO) shoppingCartVOObj;
                        shoppingCartVO.setUserName(userVO.getUserName());
                        shoppingCartVO.setUserTypeCd(userVO.getUserTypeCd());
                        request.setAttribute("SHOPPINGCART", shoppingCartVO);
                    }
                    redirectURL = request.getParameter("redirectURL");
                    HttpSession session = request.getSession(true);
                    session.setAttribute("USER", userVO);
                    session.removeAttribute("REDIRECT");                        
                }
            } catch (Exception exp) {
                MDC.put ("MyMDC2", "ERROR");
                logger.error(exp.getMessage(), exp);
                request.setAttribute ("EXCEPTION", exp);
                operation = "exception";                
            } 
        }
        if ("changepass".equals (operation)) {
            Object userVOObj = request.getSession ().getAttribute("USER");
            if (userVOObj != null) {
                UserVO userVO = (UserVO) userVOObj;
                request.setAttribute("USER", userVO);
            }
        }
        if ("forgotpasssub".equals (operation)) {
            HashMap errorMap = new HashMap ();
            String userName = request.getParameter ("userName");
            UserVO userVO = new UserVO ();
            userVO.setUserTypeCd("USER");
            userVO.setAppTypeCd(apptype);
            userVO.setAppName(appname);
            userVO.setUserName (userName);
            if (userName == null || "".equals (userName)) {
                errorMap.put ("userName", "required");
            }
            
            String email = request.getParameter("email");
            if (email == null || "".equals (email)) {
                errorMap.put ("email", "required");
            }
            userVO.setEmail1(email);
            
            if (errorMap.isEmpty()) {
                try {
                    UserDAO userDAO = new UserDAO ();
                    userVO = userDAO.validateUser (userVO);
                    if (userVO.getSecretQues() == null || "".equals (userVO.getSecretQues())) {
                        errorMap.put ("error", "Invalid User name/Email address.");
                        operation = "forgotpasserror";
                    }
                } catch (Exception exp) {
                    MDC.put ("MyMDC2", "ERROR");
                    logger.error(exp.getMessage(), exp);
                    request.setAttribute ("EXCEPTION", exp);
                    operation = "exception";   
                }
            } else {
                operation = "forgotpasserror";
            }
            request.setAttribute ("USER", userVO);
            request.setAttribute ("ERRORS", errorMap);
        }
        if ("login".equals (operation)) {
            request.setAttribute("appname", appname);
            request.setAttribute("apptype", apptype);
            request.setAttribute("actionprefix", apptype + "-" + appname);
        }        
        if ("logout".equals(operation)) {
            request.getSession().invalidate();
        }
        if ("resetpass".equals (operation)) {
            HashMap errorMap = new HashMap ();
            String userName = request.getParameter ("userName");
            UserVO userVO = new UserVO ();
            userVO.setUserTypeCd("USER");
            userVO.setAppTypeCd(apptype);
            userVO.setAppName(appname);
            userVO.setUserName (userName);
            String secretQues = request.getParameter ("secretQues");
            userVO.setSecretQues (secretQues);
            
            String secretAns = request.getParameter ("secretAns");
            if (secretAns == null || "".equals (secretAns)) {
                errorMap.put ("secretAns", "required");
            }
            userVO.setSecretAns (secretAns);
            if (errorMap.isEmpty()) {
                try {
                    UserDAO userDAO = new UserDAO ();
                    boolean isValid = userDAO.validateUserSecret (userVO);
                    if (!isValid) {
                        errorMap.put ("error", "Invalid Secret answer.");
                        operation = "resetpasserror";
                    }
                } catch (Exception exp) {
                    MDC.put ("MyMDC2", "ERROR");
                    logger.error(exp.getMessage(), exp);
                    request.setAttribute ("EXCEPTION", exp);
                    operation = "exception";   
                }
            } else {
                operation = "resetpasserror";
            }            
            request.setAttribute ("USER", userVO);
            request.setAttribute ("ERRORS", errorMap);
        }
        
        if ("resetpasssub".equals (operation)) {
            HashMap errorMap = new HashMap ();
            String userName = request.getParameter ("userName");
            UserVO userVO = new UserVO ();
            userVO.setUserTypeCd("USER");
            userVO.setAppTypeCd(apptype);
            userVO.setAppName(appname);
            userVO.setUserName (userName);
            String password = request.getParameter("userPassword");
            if (password == null || "".equals (password.trim())) {
                errorMap.put ("userPassword", "required");
            } else if (password.length() < 5) {
                errorMap.put ("userPassword", "must be minimum 5 characters.");
            }
            userVO.setUserPassword(password);
            String repassword = request.getParameter("userRePassword");
            if (repassword == null || "".equals(repassword)) {
                errorMap.put("userRePassword", "required");            
            } else if (!repassword.equals(password)) {
                errorMap.put("userPassword", "passwords do not match");
                errorMap.put("userRePassword", "passwords do not match");
            } 
            userVO.setUserRePassword(repassword);
            if (errorMap.isEmpty()) {
                try {
                    UserDAO userDAO = new UserDAO ();
                    userDAO.resetPassword (userVO);
                    Object userVOObj = request.getSession ().getAttribute("USER");
                    if (userVOObj != null) {
                        operation = "changepasssub";
                    }
                } catch (Exception exp) {
                    MDC.put ("MyMDC2", "ERROR");
                    logger.error(exp.getMessage(), exp);
                    request.setAttribute ("EXCEPTION", exp);
                    operation = "exception";   
                }
            } else {
                operation = "resetpasssuberror";
            }   
            request.setAttribute ("USER", userVO);
            request.setAttribute ("ERRORS", errorMap);
        }
        if (redirectURL == null || "".equals(redirectURL)) {
            redirectURL = new ApplicationUtil ().getRedirectPage(apptype, appname, operation);
        }
        MDC.put ("MyMDC2", "DEBUG");
        MDC.put("MyMDC6", redirectURL);
        logger.debug("Redirecting page.");
        MDC.remove("MyMDC6");
        RequestDispatcher reqDispatcher = request.getRequestDispatcher("../" + redirectURL);
        reqDispatcher.forward(request, response);
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * 
     * @return
     */
    public String getServletInfo() {
        return "Short description";
    }    
}
