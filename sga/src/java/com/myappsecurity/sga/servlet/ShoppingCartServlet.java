package com.myappsecurity.sga.servlet;

import com.myappsecurity.sga.dao.CartDAO;
import com.myappsecurity.sga.dao.WishlistDAO;
import com.myappsecurity.sga.util.ApplicationUtil;
import com.myappsecurity.sga.vo.CartVO;
import com.myappsecurity.sga.vo.ProductVO;
import com.myappsecurity.sga.vo.ShoppingCartVO;
import com.myappsecurity.sga.vo.UserVO;
import com.myappsecurity.sga.vo.WishlistVO;
import java.io.*;
import java.net.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 *
 * @author Chander Singh
 */
public class ShoppingCartServlet extends HttpServlet {   
    /**
     * 
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Logger logger = Logger.getLogger(ShoppingCartServlet.class);
        
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
        
        //Handles request to show users current shopping cart
        if ("cart".equals (operation)) {
            operation = handleCurrentCart(request, apptype, appname, operation);
        }
        
        //Handles request to add item in shopping cart
        if ("addcart".equals (operation)) {
            operation = handleAddCart (request, apptype, appname, operation);
        }       
        
        //Handles request to update an item in shopping cart
        if ("updcart".equals (operation)) {
            operation = handleUpdateCart (request, apptype, appname, operation);
        }
        
        //Handles request to save cart for future use.
        if ("savecart".equals (operation)) {
            HttpSession session = request.getSession();
            Object userVOObj = session.getAttribute("USER");
            if (userVOObj != null) {
                operation = handleSaveCart (request, apptype, appname, operation);
            } else {
                operation = apptype + "-" + appname + "/" + operation; 
                session.setAttribute ("REDIRECT", operation);
                operation = "login";                
            }
        }

        //Handles 
        if ("wishlist".equals(operation)) {
            Object userVOObj = request.getSession().getAttribute("USER");
            if (userVOObj == null) {
                operation = apptype + "-" + appname + "/" + operation + "?productId=" + request.getParameter("productId");
                request.getSession().setAttribute("REDIRECT", operation);
                operation = "login";                
            } else {
                UserVO userVO = (UserVO) userVOObj;
                try {
                    String productid = request.getParameter("productId");
                    long productId = Long.parseLong(productid);
                    Object wishListObj = request.getSession().getAttribute("WISHLIST");
                    WishlistVO wishListVO = null;
                    WishlistVO[] wishlistVOs = new WishlistVO[0];
                    if (wishListObj != null) {
                        wishlistVOs = (WishlistVO[]) wishListObj;
                        for (int cnt = 0; cnt < wishlistVOs.length; cnt++) {
                            if (wishlistVOs[cnt].getProductId() == productId) {
                                wishListVO = wishlistVOs[cnt];
                                break;
                            }
                        }
                    } else {                  
                        WishlistVO wishListvo = new WishlistVO ();
                        wishListvo.setAppName(appname);
                        wishListvo.setAppTypeCd(apptype);
                        wishListvo.setUserTypeCd(userVO.getUserTypeCd());
                        wishListvo.setUsername(userVO.getUserName());
                        WishlistDAO wishlistDAO = new WishlistDAO();
                        wishlistVOs = wishlistDAO.fetchWishlist(wishListvo);
                        if (wishlistVOs != null && wishlistVOs.length > 0) {
                            request.getSession().setAttribute("WISHLIST", wishlistVOs);
                        }
                        for (int cnt = 0; cnt < wishlistVOs.length; cnt++) {
                            if (wishlistVOs[cnt].getProductId() == productId) {
                                wishListVO = wishlistVOs[cnt];
                                break;
                            }
                        }
                    }
                    if (wishListVO == null) {
                        Object productObj = request.getSession().getAttribute("PRODUCTS");
                        ProductVO[] productVOs = (ProductVO[]) productObj;
                        ProductVO productVO = new ProductVO ();
                        productVO.setProductId(productId);
                        ArrayList productVOAL =  new ArrayList (Arrays.asList (productVOs));
                        int loc = productVOAL.indexOf(productVO);
                        productVO = (ProductVO)productVOAL.get(loc);
                        String prodprice = productVO.getProductPrice();
                        double price = Double.parseDouble (prodprice);
                        wishListVO = new WishlistVO ();

                        wishListVO.setProductId(productId);
                        wishListVO.setProductName(productVO.getProductName());
                        wishListVO.setProductPrice(price);
                        wishListVO.setAppName(appname);
                        wishListVO.setAppTypeCd(apptype);
                        wishListVO.setUserTypeCd(userVO.getUserTypeCd());
                        wishListVO.setUsername(userVO.getUserName());
                    } 
                    request.setAttribute ("WISHLIST", wishListVO);
                } catch (Exception exp) {
                    MDC.put ("MyMDC2", "ERROR");
                    logger.error(exp.getMessage(), exp);
                    request.setAttribute ("EXCEPTION", exp);
                    operation = "exception";
                }
            }
        }
        
        if ("delwishlist".equals (operation)) {
            Object userVOObj = request.getSession().getAttribute("USER");
            if (userVOObj == null) {
                operation = apptype + "-" + appname + "/" + operation + "?productId=" + request.getParameter("productId");
                request.getSession().setAttribute("REDIRECT", operation);
                operation = "login";                
            } else {
                UserVO userVO = (UserVO) userVOObj;
                try {
                    String productid = request.getParameter("productId");
                    long productId = Long.parseLong(productid);
                    Object wishListObj = request.getSession().getAttribute("WISHLIST");
                    if (wishListObj != null) {
                        WishlistVO[] wishlistVOs = (WishlistVO[]) wishListObj;
                        ArrayList wishlistVOAL = new ArrayList (Arrays.asList (wishlistVOs));
                        for (int cnt = 0; cnt < wishlistVOAL.size (); cnt++) {
                            WishlistVO wishListVO = (WishlistVO) wishlistVOAL.get(cnt);
                            if (wishListVO.getProductId() == productId) {                            
                                wishListVO.setUserTypeCd(userVO.getUserTypeCd());
                                wishListVO.setUsername(userVO.getUserName());
                                WishlistDAO wishlistDAO = new WishlistDAO ();
                                wishlistDAO.deleteWishlist(wishListVO);
                            }
                            wishlistVOAL.remove(cnt);
                            break;
                        }
                        wishlistVOs = (WishlistVO[]) wishlistVOAL.toArray(new WishlistVO [wishlistVOAL.size()]);
                        request.getSession().setAttribute("WISHLIST", wishlistVOs);
                    }                
                 } catch (Exception exp) {
                    MDC.put ("MyMDC2", "ERROR");
                    logger.error(exp.getMessage(), exp);
                    request.setAttribute ("EXCEPTION", exp);
                    operation = "exception";
                }
            }
        }
                
        if ("wishlistsub".equals (operation)) {
           Object userVOObj = request.getSession().getAttribute("USER");
            if (userVOObj == null) {
                operation = apptype + "-" + appname + "/" + operation + "?productId=" + request.getParameter("productId") + ";comments=" + request.getParameter("comments");
                request.getSession().setAttribute("REDIRECT", operation);
                operation = "login";                
            } else {
                UserVO userVO = (UserVO) userVOObj;
                try {
                    String productId = request.getParameter("productId");
                    String comments = request.getParameter("comments");

                    Object productObj = request.getSession().getAttribute("PRODUCTS");
                    ProductVO[] productVOs = (ProductVO[]) productObj;
                    ProductVO productVO = new ProductVO ();
                    long productid = Long.parseLong(productId);
                    productVO.setProductId(productid);
                    ArrayList productVOAL =  new ArrayList (Arrays.asList (productVOs));
                    int loc = productVOAL.indexOf(productVO);
                    productVO = (ProductVO)productVOAL.get(loc);
                    String prodprice = productVO.getProductPrice();
                    double price = Double.parseDouble (prodprice);

                    Object wishListObj = request.getSession().getAttribute("WISHLIST");
                    WishlistVO[] wishListVOs = null;            
                    ArrayList wishlistVOAL = new ArrayList (10);
                    if (wishListObj != null) {
                        wishListVOs = (WishlistVO[])wishListObj;
                        wishlistVOAL = new ArrayList (Arrays.asList(wishListVOs));
                    }
                    WishlistVO wishListVO = new WishlistVO ();
                    wishListVO.setProductId(productid);
                    wishListVO.setProductName(productVO.getProductName());
                    wishListVO.setProductPrice(price);
                    wishListVO.setAppName(appname);
                    wishListVO.setAppTypeCd(apptype);
                    wishListVO.setComments(comments);

                    int location = wishlistVOAL.indexOf(wishListVO);
                    if (location > -1) {
                        WishlistVO wishlistvo = (WishlistVO)wishlistVOAL.get (location);
                        wishListVO.setWishid (wishlistvo.getWishid());
                        wishListVO.setUserTypeCd(wishlistvo.getUserTypeCd());
                        wishListVO.setUsername(wishlistvo.getUsername());
                        wishlistVOAL.set(location, wishListVO);

                        wishListVO.setUserTypeCd(userVO.getUserTypeCd());
                        wishListVO.setUsername(userVO.getUserName());
                        WishlistDAO wishlistDAO = new WishlistDAO ();
                        wishlistDAO.updateWishlist (wishListVO);
                    } else {
                        WishlistDAO wishlistDAO = new WishlistDAO ();
                        if (wishlistVOAL.size() > 0) {
                            WishlistVO wishlistvo = (WishlistVO)wishlistVOAL.get (0);
                            wishListVO.setWishid (wishlistvo.getWishid());     
                            wishListVO.setUserTypeCd(wishlistvo.getUserTypeCd());
                            wishListVO.setUsername(wishlistvo.getUsername());

                            wishListVO.setUserTypeCd(userVO.getUserTypeCd());
                            wishListVO.setUsername(userVO.getUserName());
                            wishlistDAO.updateWishlist (wishListVO);                    
                            wishlistVOAL.add(wishListVO);
                        } else {
                            long wishid = Calendar.getInstance().getTimeInMillis();
                            wishListVO.setWishid (wishid);

                            wishListVO.setUserTypeCd(userVO.getUserTypeCd());
                            wishListVO.setUsername(userVO.getUserName());
                            wishlistDAO.insertWishlist (wishListVO);                    
                            wishlistVOAL.add (wishListVO);                        
                        }                    
                    }
                    wishListVOs = (WishlistVO[]) wishlistVOAL.toArray(new WishlistVO[wishlistVOAL.size()]);
                    wishlistVOAL = null;
                    request.getSession().setAttribute("WISHLIST", wishListVOs);
                } catch (Exception exp) {
                    MDC.put ("MyMDC2", "ERROR");
                    logger.error(exp.getMessage(), exp);
                    request.setAttribute ("EXCEPTION", exp);
                    operation = "exception";
                }
            }
        }        
        
        if ("listwishlist".equals(operation)) {
            Object userVOObj = request.getSession().getAttribute("USER");
            if (userVOObj == null) {
                operation = apptype + "-" + appname + "/" + operation;
                request.getSession().setAttribute("REDIRECT", operation);
                operation = "login";                
            } else {
                UserVO userVO = (UserVO) userVOObj;
                try {
                    if (request.getSession().getAttribute("WISHLIST") == null) {
                        WishlistDAO wishlistDAO = new WishlistDAO();                        
                        WishlistVO wishListVO = new WishlistVO();
                        wishListVO.setAppName(appname);
                        wishListVO.setAppTypeCd(apptype);
                        wishListVO.setUserTypeCd(userVO.getUserTypeCd());
                        wishListVO.setUsername(userVO.getUserName());
                        WishlistVO[] wishlistVOs = wishlistDAO.fetchWishlist(wishListVO);
                        request.getSession ().setAttribute("WISHLIST", wishlistVOs);
                    }
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
     * @param request
     * @param apptype
     * @param appname
     * @param operation
     * @return
     */
    private String handleCurrentCart (HttpServletRequest request, String apptype, String appname, String operation) {
        Logger logger = Logger.getLogger (ShoppingCartServlet.class);
        try {
            HttpSession session = request.getSession ();
            Object shoppingCartVOObj = session.getAttribute("SHOPPINGCART");
            ShoppingCartVO shoppingCartVO = new ShoppingCartVO ();
            if (shoppingCartVOObj != null) {
                shoppingCartVO = (ShoppingCartVO) shoppingCartVOObj;
            }
            Object userVOObj = request.getSession ().getAttribute("USER");
            if (userVOObj != null) {
                UserVO userVO = (UserVO)userVOObj;
                CartDAO cartDAO = new CartDAO ();
                ShoppingCartVO shoppingcartVO = cartDAO.fetchCurrentCart(apptype, appname, userVO.getUserTypeCd(), userVO.getUserName());    
                if (shoppingcartVO.getCartId() > 0) {
                    shoppingCartVO.setCartId(shoppingcartVO.getCartId());
                    CartVO[] cartVOs = shoppingcartVO.getCartVOs();
                    CartVO[] cartvos = shoppingCartVO.getCartVOs();
                    shoppingCartVO.setUserName(shoppingcartVO.getUserName());
                    shoppingCartVO.setUserTypeCd(shoppingcartVO.getUserTypeCd());
                    shoppingCartVO.setAppName(shoppingcartVO.getAppName());
                    shoppingCartVO.setAppTypeCd(shoppingcartVO.getAppTypeCd());
                    ArrayList cartVOAL = new ArrayList (Arrays.asList(cartvos));
                    for (int cnt = 0; cnt < cartVOs.length; cnt++) {
                        int index = cartVOAL.indexOf(cartVOs[cnt]);
                        if (index < 0) { 
                            shoppingCartVO.addCart(cartVOs[cnt]);
                        }
                    }
                } else {
                    long cartId = Calendar.getInstance().getTimeInMillis();
                    shoppingCartVO.setCartId(cartId);
                    shoppingCartVO.setUserName(userVO.getUserName());
                    shoppingCartVO.setUserTypeCd(userVO.getUserTypeCd());
                    shoppingCartVO.setAppName(userVO.getAppName());
                    shoppingCartVO.setAppTypeCd(userVO.getAppTypeCd());
                }                
            } 
            session.setAttribute ("SHOPPINGCART", shoppingCartVO);
        } catch (Exception exp) {
            MDC.put ("MyMDC2", "ERROR");
            logger.error(exp.getMessage(), exp);
            request.setAttribute ("EXCEPTION", exp);
            operation = "exception";
        }
        return operation;
    }
    
    /**
     * 
     * @param request
     * @param apptype
     * @param appname
     * @param operation
     * @return
     */
    private String handleAddCart (HttpServletRequest request, String apptype, String appname, String operation) {
        Logger logger = Logger.getLogger (ShoppingCartServlet.class);
        try {
            String productId = request.getParameter("productId");
            String qty = request.getParameter("qty");
            Object productObj = request.getSession().getAttribute("PRODUCTS");
            ProductVO[] productVOs = (ProductVO[]) productObj;
            ProductVO productVO = new ProductVO ();
            long productid = Long.parseLong(productId);
            productVO.setProductId(productid);
            ArrayList productVOAL =  new ArrayList (Arrays.asList (productVOs));
            int loc = productVOAL.indexOf(productVO);
            productVO = (ProductVO)productVOAL.get(loc);
            String prodprice = productVO.getProductPrice();
            double price = Double.parseDouble (prodprice);
            int quantity = Integer.parseInt (qty);
            Object shoppingCartObj = request.getSession().getAttribute("SHOPPINGCART");
            ShoppingCartVO shoppingCartVO = null;            
            if (shoppingCartObj != null) {
                shoppingCartVO = (ShoppingCartVO)shoppingCartObj;
                CartVO cartVO = shoppingCartVO.getCart (productid);
                if (cartVO != null) {
                    cartVO.setProductPrice(price);
                    cartVO.setProductName(productVO.getProductName());
                    cartVO.setQuantity(quantity);
                    shoppingCartVO.addCart (cartVO);
                } else {
                    cartVO = new CartVO ();
                    cartVO.setProductId(productid);
                    cartVO.setProductName(productVO.getProductName());
                    cartVO.setProductPrice(price);
                    cartVO.setQuantity(quantity);
                    shoppingCartVO.addCart (cartVO);
                }
            } else {
                shoppingCartVO = new ShoppingCartVO ();
                long cartId = Calendar.getInstance().getTimeInMillis();
                shoppingCartVO.setCartId(cartId);
                Object userVOObj = request.getSession ().getAttribute("USER");
                if (userVOObj != null) {
                    UserVO userVO = (UserVO)userVOObj;
                    shoppingCartVO.setUserName(userVO.getUserName());
                    shoppingCartVO.setUserTypeCd(userVO.getUserTypeCd());
                    shoppingCartVO.setAppName(userVO.getAppName());
                    shoppingCartVO.setAppTypeCd(userVO.getAppTypeCd());
                }
                CartVO cartVO = new CartVO ();
                cartVO.setProductId(productid);
                cartVO.setProductName(productVO.getProductName());
                cartVO.setProductPrice(price);
                cartVO.setQuantity(quantity);
                shoppingCartVO.addCart (cartVO);
             }
            shoppingCartVO.setAppName(appname);
            shoppingCartVO.setAppTypeCd(apptype);
            request.getSession().setAttribute("SHOPPINGCART", shoppingCartVO);
        } catch (Exception exp) {
            MDC.put ("MyMDC2", "ERROR");
            logger.error(exp.getMessage(), exp);
            request.setAttribute ("EXCEPTION", exp);
            operation = "exception";
        }
        return operation;
    }
    
    /**
     * 
     * @param request
     * @param apptype
     * @param appname
     * @param operation
     * @return
     */
    private String handleUpdateCart (HttpServletRequest request, String apptype, String appname, String operation) {
        String[] productIds = request.getParameterValues("productId");
        String[] qtys = request.getParameterValues("qty");
        Object shoppingCartObj = request.getSession().getAttribute("SHOPPINGCART");
        ShoppingCartVO shoppingCartVO = null;            
         if (shoppingCartObj != null) {
            shoppingCartVO = (ShoppingCartVO)shoppingCartObj;
            for (int cnt = 0; cnt < productIds.length; cnt++) {
                int productid = Integer.parseInt (productIds[cnt]);
                CartVO cartVO = shoppingCartVO.getCart (productid);
                if ("0".equals (qtys[cnt])) {
                    shoppingCartVO.deleteCart(cartVO);
                } else {
                    int quantity = Integer.parseInt(qtys[cnt]);
                    cartVO.setQuantity(quantity);
                    shoppingCartVO.addCart (cartVO);
                }
            }
         }
        shoppingCartVO.setAppName(appname);
        shoppingCartVO.setAppTypeCd(apptype);
        request.getSession().setAttribute("SHOPPINGCART", shoppingCartVO);
        return operation;
    }
    
    /**
     * 
     * @param request
     * @param apptype
     * @param appname
     * @param operation
     * @return
     */
    private String handleSaveCart (HttpServletRequest request, String apptype, String appname, String operation) {
        Logger logger = Logger.getLogger (ShoppingCartServlet.class);
        try {
            HttpSession session = request.getSession ();
            Object shoppingCartVOObj = session.getAttribute ("SHOPPINGCART");
            ShoppingCartVO shoppingCartVO = null;
            if (shoppingCartVOObj != null) {
                shoppingCartVO = (ShoppingCartVO) shoppingCartVOObj;
            }
            if (shoppingCartVO != null) {
                UserVO userVO = (UserVO)session.getAttribute("USER");
                shoppingCartVO.setUserTypeCd (userVO.getUserTypeCd ());
                shoppingCartVO.setUserName(userVO.getUserName());
                shoppingCartVO.setAppName(userVO.getAppName());
                shoppingCartVO.setAppTypeCd(userVO.getAppTypeCd());
                CartDAO cartDAO = new CartDAO ();
                cartDAO.updateCart(shoppingCartVO);
            }
        } catch (Exception exp) {
            MDC.put ("MyMDC2", "ERROR");
            logger.error(exp.getMessage(), exp);
            request.setAttribute ("EXCEPTION", exp);
            operation = "exception";
        }
        return operation;
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
    // </editor-fold>
}
