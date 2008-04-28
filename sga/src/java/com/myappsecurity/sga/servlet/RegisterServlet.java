package com.myappsecurity.sga.servlet;

import com.myappsecurity.sga.dao.UserDAO;
import com.myappsecurity.sga.util.ApplicationUtil;
import com.myappsecurity.sga.vo.UserVO;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;

import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 *
 * @author Chander Singh
 *  @created.on Mar 23, 2008
 */
public class RegisterServlet extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Logger logger = Logger.getLogger (RegisterServlet.class);
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
        
        if ("register".equals(operation)) {
            request.setAttribute("ERRROS", new HashMap ());
        }
        if ("profile".equals(operation)) {
            try {
                String username = request.getParameter ("username");
                UserVO userVO = new UserVO ();
                userVO.setUserName(username);
                userVO.setAppName(appname);
                userVO.setAppTypeCd(apptype);
                userVO.setUserTypeCd("USER");
                UserDAO userDAO = new UserDAO ();
                userVO = userDAO.fetchUserProfile(userVO);
                request.setAttribute("USER", userVO);
            } catch (Exception exp) {
                MDC.put ("MyMDC2", "ERROR");
                logger.error(exp.getMessage(), exp);
                request.setAttribute ("EXCEPTION", exp);
                operation = "exception";
            }
        }
        
        if ("registersub".equals(operation)) {
            UserVO userVO = new UserVO ();
            userVO.setUserTypeCd("USER");
            userVO.setAppTypeCd(apptype);
            userVO.setAppName(appname);
            extractRequestParameters(userVO, request);
            boolean isValid = validate(userVO, request, true);
            if (!isValid) {
                operation = "registererror";
            } else {
                UserDAO userDAO = new UserDAO ();
                try {
                    userDAO.insertUser(userVO);
                } catch (Exception exp) {
                    MDC.put ("MyMDC2", "ERROR");
                    logger.error(exp.getMessage(), exp);
                    request.setAttribute ("EXCEPTION", exp);
                    operation = "exception";
                }
            }
        }
        if ("updateprofile".equals(operation)) {
            UserVO userVO = new UserVO ();
            userVO.setUserTypeCd("USER");
            userVO.setAppTypeCd(apptype);
            userVO.setAppName(appname);
            extractRequestParameters(userVO, request);
            boolean isValid = validate(userVO, request, false);
            if (!isValid) {
                operation = "profileerror";
            } else {
                UserDAO userDAO = new UserDAO ();
                try {
                    userVO = userDAO.updateUser(userVO);
                    request.getSession().setAttribute ("USER", userVO);
                } catch (Exception exp) {
                    MDC.put ("MyMDC2", "ERROR");
                    logger.error(exp.getMessage(), exp);
                    request.setAttribute ("EXCEPTION", exp);
                    operation = "exception";
                }
            }
        }
        String redirectURL = new ApplicationUtil ().getRedirectPage(apptype, appname, operation);
        MDC.put ("MyMDC2", "DEBUG");
        MDC.put("MyMDC6", redirectURL);
        logger.debug("Redirecting page.");
        MDC.remove("MyMDC6");
        RequestDispatcher reqDispatcher = request.getRequestDispatcher("../" + redirectURL);
        reqDispatcher.forward(request, response);
    } 

    /**
     * 
     * @param userVO
     * @param request
     * @return
     */
    private boolean validate (UserVO userVO, HttpServletRequest request, boolean insert) {
        extractRequestParameters(userVO, request);
        
        HashMap errorMap = new HashMap ();      
        if (userVO.getUserName() == null || "".equals(userVO.getUserName())) {
            errorMap.put("userName", "required");            
        }
                
        if (insert) {
            if (userVO.getUserRePassword () == null || "".equals(userVO.getUserRePassword ())) {
                errorMap.put("userRePassword", "required");            
            } else if (!userVO.getUserRePassword().equals(userVO.getUserPassword())) {
                errorMap.put("userPassword", "passwords do not match");
                errorMap.put("userRePassword", "passwords do not match");
            }
        }
        
        if (userVO.getFirstName() == null || "".equals(userVO.getFirstName ())) {
            errorMap.put("firstName", "required");            
        }
        
        if (userVO.getLastName() == null || "".equals(userVO.getLastName ())) {
            errorMap.put("lastName", "required");            
        }
        
        if (userVO.getAddress1() == null || "".equals(userVO.getAddress1 ())) {
            errorMap.put("address1", "required");            
        }
        
        if (userVO.getCity() == null || "".equals(userVO.getCity ())) {
            errorMap.put("city", "required");            
        }
        
        if (userVO.getState() == null || "".equals(userVO.getState ())) {
            errorMap.put("state", "required");            
        }
        
        if (userVO.getCountry() == null || "".equals(userVO.getCountry ())) {
            errorMap.put("country", "required");            
        }
        
        if (userVO.getZipCode() == null || "".equals(userVO.getZipCode())) {
            errorMap.put("zipCode", "required");            
        }
        
        if (userVO.getOffPhone() == null || "".equals(userVO.getOffPhone ())) {
            errorMap.put("offPhone", "required");            
        }
        
        if (userVO.getHomePhone1() == null || "".equals(userVO.getHomePhone1 ())) {
            errorMap.put("homePhone1", "required");            
        }
        
        if (userVO.getEmail1() == null || "".equals(userVO.getEmail1 ())) {
            errorMap.put("email1", "required");            
        }
        
        if (userVO.getSecretQues() == null || "".equals(userVO.getSecretQues())) {
            errorMap.put("secretQues", "required");            
        }
        
        if (userVO.getSecretAns() == null || "".equals(userVO.getSecretAns())) {
            errorMap.put("secretAns", "required");            
        }        
        request.setAttribute("ERRORS", errorMap);
        if (errorMap.isEmpty()) {
            return true;
        } else {
            request.setAttribute("USER", userVO);
            return false;
        }
    }
    
    /**
     * 
     * @param userVO
     * @param billAddressVO
     * @param request
     */
    private void extractRequestParameters (UserVO userVO, HttpServletRequest request) {
        Enumeration requestParamNames = request.getParameterNames();
        while (requestParamNames.hasMoreElements()) {
            String paramName = (String) requestParamNames.nextElement();
            Class userClass = userVO.getClass();
            
            String paramValue = request.getParameter (paramName);
            String objParamName = "set".concat(paramName.substring (0, 1).toUpperCase()).concat (paramName.substring(1));
            try {
                Method method = userClass.getMethod(objParamName, new Class [] {String.class});
                method.invoke(userVO, new Object[] {paramValue});
            } catch (NoSuchMethodException nsmexp) {
            } catch (IllegalAccessException iaexp) {                    
            } catch (InvocationTargetException itexp) {}
        }        
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
