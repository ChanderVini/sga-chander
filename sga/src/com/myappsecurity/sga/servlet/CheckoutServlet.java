/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.myappsecurity.sga.servlet;

import com.myappsecurity.sga.dao.CartDAO;
import com.myappsecurity.sga.dao.UserDAO;
import com.myappsecurity.sga.util.ApplicationUtil;
import com.myappsecurity.sga.vo.AddressVO;
import com.myappsecurity.sga.vo.CreditCardVO;
import com.myappsecurity.sga.vo.ShoppingCartVO;
import com.myappsecurity.sga.vo.UserVO;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.sql.Date;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 *
 * @author Chander Singh
 */
public class CheckoutServlet extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Logger logger = Logger.getLogger(CheckoutServlet.class);
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
        
        if ("shipdet".equals(operation)) {
            request.setAttribute("ERRROS", new HashMap ());
            Object shoppingCartObj = request.getSession().getAttribute("SHOPPINGCART");
            ShoppingCartVO shoppingCartVO = (ShoppingCartVO)shoppingCartObj;
            long cartId = shoppingCartVO.getCartId();
            String userName = shoppingCartVO.getUserName();
            String appName = shoppingCartVO.getAppName();
            String appType = shoppingCartVO.getAppTypeCd();
            String userType = shoppingCartVO.getUserTypeCd();
            AddressVO shipAddressVO = new AddressVO();
            
            AddressVO billAddressVO = new AddressVO();
            System.out.println ("UserName: " + userName);
            if (userName != null && !"".equals(userName.trim())) {
                try {
                    UserVO userVO = new UserVO ();
                    userVO.setUserName(userName);
                    userVO.setUserTypeCd(userType);
                    userVO.setAppName(appName);
                    userVO.setAppTypeCd(appType);

                    UserDAO userDAO = new UserDAO ();
                    userVO = userDAO.fetchUserProfile(userVO);
                    
                    shipAddressVO.setCartId(cartId);
                    shipAddressVO.setAddress1(userVO.getAddress1());
                    shipAddressVO.setAddress2(userVO.getAddress2());
                    shipAddressVO.setCity(userVO.getCity());
                    shipAddressVO.setState(userVO.getState());
                    shipAddressVO.setCountry(userVO.getCountry());
                    shipAddressVO.setZipCode(userVO.getZipCode());
                    shipAddressVO.setEmail1(userVO.getEmail1());
                    shipAddressVO.setEmail2 (userVO.getEmail2());
                    shipAddressVO.setFax(userVO.getFax());
                    shipAddressVO.setFirstName(userVO.getFirstName());
                    shipAddressVO.setHomePhone1(userVO.getHomePhone1());
                    shipAddressVO.setHomePhone2(userVO.getHomePhone2());
                    shipAddressVO.setLastName(userVO.getLastName());
                    shipAddressVO.setMobile(userVO.getMobile());
                    shipAddressVO.setOffPhone(userVO.getOffPhone());
                    shipAddressVO.setEditable(false);

                    billAddressVO.setCartId(cartId);
                    billAddressVO.setAddress1(userVO.getAddress1());
                    billAddressVO.setAddress2(userVO.getAddress2());
                    billAddressVO.setCity(userVO.getCity());
                    billAddressVO.setState(userVO.getState());
                    billAddressVO.setCountry(userVO.getCountry());
                    billAddressVO.setZipCode(userVO.getZipCode());
                    billAddressVO.setEmail1(userVO.getEmail1());
                    billAddressVO.setEmail2 (userVO.getEmail2());
                    billAddressVO.setFax(userVO.getFax());
                    billAddressVO.setFirstName(userVO.getFirstName());
                    billAddressVO.setHomePhone1(userVO.getHomePhone1());
                    billAddressVO.setHomePhone2(userVO.getHomePhone2());
                    billAddressVO.setLastName(userVO.getLastName());
                    billAddressVO.setMobile(userVO.getMobile());
                    billAddressVO.setOffPhone(userVO.getOffPhone());
                    
                    request.setAttribute("shipbillsame", "on");
                } catch (Exception exp) {
                     MDC.put ("MyMDC2", "ERROR");
                    logger.error(exp.getMessage(), exp);
                    request.setAttribute ("EXCEPTION", exp);
                    operation = "exception";
                }
            }
            shipAddressVO.setCartId(cartId);            
            shipAddressVO.setAddressType("SHIP");
            billAddressVO.setCartId(cartId);
            billAddressVO.setAddressType("BILL");
            request.setAttribute ("SHIPADDR", shipAddressVO);
            request.setAttribute ("BILLADDR", billAddressVO);
        }
        
        if ("checkout1".equals(operation)) {
            AddressVO shipAddressVO = new AddressVO ();
            shipAddressVO.setAddressType("SHIP");
            shipAddressVO.setAddressDt(new Date (0));
            AddressVO billAddressVO = new AddressVO ();
            billAddressVO.setAddressType("BILL");
            boolean isValid = validate (shipAddressVO, billAddressVO, null, request);
            if (!isValid) {
                operation = "checkout1error";           
            }
            CreditCardVO creditCardVO = new CreditCardVO ();
            Object shoppingCartObj = request.getSession().getAttribute("SHOPPINGCART");
            ShoppingCartVO shoppingCartVO = (ShoppingCartVO)shoppingCartObj;
            String userName = shoppingCartVO.getUserName();
            String appName = shoppingCartVO.getAppName();
            String appType = shoppingCartVO.getAppTypeCd();
            String userType = shoppingCartVO.getUserTypeCd();
            try {
                UserVO userVO = new UserVO ();
                userVO.setUserName(userName);
                userVO.setUserTypeCd(userType);
                userVO.setAppName(appName);
                userVO.setAppTypeCd(appType);
                CartDAO cartDAO = new CartDAO ();
                creditCardVO = cartDAO.fetchCreditCard(userVO);
             } catch (Exception exp) {
                 MDC.put ("MyMDC2", "ERROR");
                logger.error(exp.getMessage(), exp);
                request.setAttribute ("EXCEPTION", exp);
                operation = "exception";
            }

            long cartId = shoppingCartVO.getCartId();
            
            shipAddressVO.setCartId(cartId);            
            shipAddressVO.setAddressType("SHIP");
            billAddressVO.setCartId(cartId);
            billAddressVO.setAddressType("BILL");
            request.setAttribute ("SHIPADDR", shipAddressVO);
            request.setAttribute ("BILLADDR", billAddressVO);
            request.setAttribute ("CREDITCARD", creditCardVO);
        }
        if ("checkout".equals (operation)) {
            AddressVO shipAddressVO = new AddressVO ();
            shipAddressVO.setAddressType("SHIP");
            shipAddressVO.setAddressDt(new Date (0));
            AddressVO billAddressVO = new AddressVO ();
            billAddressVO.setAddressType("BILL");
            CreditCardVO creditCardVO = new CreditCardVO ();        
            boolean isValid = validate (shipAddressVO, billAddressVO, creditCardVO, request);
            if (!isValid) {
                operation = "checkouterror";                
            } else {
                CartDAO shoppingCartDAO = new CartDAO ();
                Object shoppingCartObj = request.getSession().getAttribute("SHOPPINGCART");
                ShoppingCartVO shoppingCartVO = (ShoppingCartVO)shoppingCartObj;
                shipAddressVO.setCartId(shoppingCartVO.getCartId());
                billAddressVO.setCartId(shoppingCartVO.getCartId());
                if (shoppingCartVO.getUserTypeCd() == null || "".equals(shoppingCartVO.getUserTypeCd())) {
                    shoppingCartVO.setUserTypeCd("USER");
                }
                if (shoppingCartVO.getUserName() == null || "".equals(shoppingCartVO.getUserName())) {
                    shoppingCartVO.setUserName (shipAddressVO.getFirstName() + " " + shipAddressVO.getLastName());
                }
                shoppingCartVO.setAddressVOs(new AddressVO [] {shipAddressVO, billAddressVO});
                creditCardVO.setCardUserName(shipAddressVO.getFirstName() + " " + shipAddressVO.getLastName());
                creditCardVO.setCartId(shoppingCartVO.getCartId());
                shoppingCartVO.setCreditCardVO(creditCardVO);
                try {
                    shoppingCartVO.setCompleted("Y");
                    shoppingCartDAO.updateCart(shoppingCartVO);
                    request.getSession().removeAttribute("SHOPPINGCART");
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
     * @param shipAddressVO
     * @param billAddressVO
     * @param creditCardVO
     * @param request
     * @return
     */
    private boolean validate (AddressVO shipAddressVO, AddressVO billAddressVO, CreditCardVO creditCardVO, HttpServletRequest request) {
        extractRequestParameters(shipAddressVO, billAddressVO, creditCardVO, request);
        String shipbillsame = request.getParameter("shipbillsame");
                
        if ("on".equalsIgnoreCase(shipbillsame)) {
            request.setAttribute ("shipbillsame", "on");
            try {
                BeanUtils.copyProperties(billAddressVO, shipAddressVO);
                billAddressVO.setAddressType("BILL");
            } catch (IllegalAccessException ex) {                
            } catch (InvocationTargetException ex) {                
            }
        }   
        HashMap errorMap = new HashMap ();        
        if (shipAddressVO.getFirstName() == null || "".equals(shipAddressVO.getFirstName ())) {
            errorMap.put("sfirstName", "required");            
        }
        
        if (shipAddressVO.getLastName() == null || "".equals(shipAddressVO.getLastName ())) {
            errorMap.put("slastName", "required");            
        }
        
        if (shipAddressVO.getAddress1() == null || "".equals(shipAddressVO.getAddress1 ())) {
            errorMap.put("saddress1", "required");            
        }
        
        if (shipAddressVO.getCity() == null || "".equals(shipAddressVO.getCity ())) {
            errorMap.put("scity", "required");            
        }
        
        if (shipAddressVO.getState() == null || "".equals(shipAddressVO.getState ())) {
            errorMap.put("sstate", "required");            
        }
        
        if (shipAddressVO.getCountry() == null || "".equals(shipAddressVO.getCountry ())) {
            errorMap.put("scountry", "required");            
        }
        
        if (shipAddressVO.getZipCode() == null || "".equals(shipAddressVO.getZipCode())) {
            errorMap.put("szipCode", "required");            
        }
        
        if (shipAddressVO.getOffPhone() == null || "".equals(shipAddressVO.getOffPhone ())) {
            errorMap.put("soffPhone", "required");            
        }
        
        if (!"on".equalsIgnoreCase(shipbillsame)) {
            if (billAddressVO.getFirstName() == null || "".equals(billAddressVO.getFirstName ())) {
                errorMap.put("bfirstName", "required");            
            }

            if (billAddressVO.getLastName() == null || "".equals(billAddressVO.getLastName ())) {
                errorMap.put("blastName", "required");            
            }

            if (billAddressVO.getAddress1() == null || "".equals(billAddressVO.getAddress1 ())) {
                errorMap.put("baddress1", "required");            
            }

            if (billAddressVO.getCity() == null || "".equals(billAddressVO.getCity ())) {
                errorMap.put("bcity", "required");            
            }

            if (billAddressVO.getState() == null || "".equals(billAddressVO.getState ())) {
                errorMap.put("bstate", "required");            
            }

            if (billAddressVO.getCountry() == null || "".equals(billAddressVO.getCountry ())) {
                errorMap.put("bcountry", "required");            
            }

            if (billAddressVO.getZipCode() == null || "".equals(billAddressVO.getZipCode())) {
                errorMap.put("bzipCode", "required");            
            }

            if (billAddressVO.getOffPhone() == null || "".equals(billAddressVO.getOffPhone ())) {
                errorMap.put("boffPhone", "required");            
            }
        }
        
        if (creditCardVO != null) {
            if (creditCardVO.getCcNbr() == null || "".equals(creditCardVO.getCcNbr())) {
                errorMap.put ("ccardnbr", "required");
            }
            if (creditCardVO.getCvvNbr() == null || "".equals(creditCardVO.getCvvNbr())) {
                errorMap.put ("cvvnbr", "required");
            }
        }
        request.setAttribute("ERRORS", errorMap);
        if (errorMap.isEmpty()) {
            return true;
        } else {
            request.setAttribute("SHIPADDR", shipAddressVO);
            request.setAttribute("BILLADDR", billAddressVO);
            if (creditCardVO != null) {
                request.setAttribute("CREDITCARD", creditCardVO);
            }
            return false;
        }
    }
    
    /**
     * 
     * @param shipAddressVO
     * @param billAddressVO
     * @param request
     */
    private void extractRequestParameters (AddressVO shipAddressVO, AddressVO billAddressVO, CreditCardVO creditCardVO, HttpServletRequest request) {
        Enumeration requestParamNames = request.getParameterNames();
        while (requestParamNames.hasMoreElements()) {
            String paramName = (String) requestParamNames.nextElement();
            String addressType = paramName.substring(0, 1);
            Class shipClass = shipAddressVO.getClass();
            Class billClass = billAddressVO.getClass ();
            
            if ("s".equals(addressType)) {
                String paramValue = request.getParameter (paramName);
                String objParamName = "set".concat(paramName.substring (1, 2).toUpperCase()).concat (paramName.substring(2));
                try {
                    Method method = shipClass.getMethod(objParamName, new Class [] {String.class});
                    method.invoke(shipAddressVO, new Object[] {paramValue});
                } catch (NoSuchMethodException nsmexp) {
                } catch (IllegalAccessException iaexp) {                    
                } catch (InvocationTargetException itexp) {}
            }
            
            if ("b".equals(addressType)) {
                String paramValue = request.getParameter (paramName);
                String objParamName = "set".concat(paramName.substring (1, 2).toUpperCase()).concat (paramName.substring(2));
                try {
                    Method method = billClass.getMethod(objParamName, new Class [] {String.class});
                    method.invoke(billAddressVO, new Object[] {paramValue});
                } catch (NoSuchMethodException nsmexp) {
                } catch (IllegalAccessException iaexp) {                    
                } catch (InvocationTargetException itexp) {}
            }
        }
        if (creditCardVO != null) {
            String creditCardType = request.getParameter("ccardtype");
            creditCardVO.setCcType(creditCardType);
            String creditCardNbr = request.getParameter ("ccardnbr");
            creditCardVO.setCcNbr(creditCardNbr);        
            String creditCardCvvNbr = request.getParameter ("cvvnbr");
            creditCardVO.setCvvNbr(creditCardCvvNbr);
            String creditCardExpMon = request.getParameter ("ccardexpmon");
            creditCardVO.setExpMon(creditCardExpMon);
            String creditCardExpYear = request.getParameter ("ccardexpyear");
            creditCardVO.setExpYear(creditCardExpYear);
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
