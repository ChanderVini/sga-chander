package com.myappsecurity.sga.servlet;

import com.myappsecurity.sga.dao.ProductDAO;
import com.myappsecurity.sga.util.ApplicationUtil;
import com.myappsecurity.sga.util.Constants;
import com.myappsecurity.sga.vo.CartVO;
import com.myappsecurity.sga.vo.ProductVO;
import com.myappsecurity.sga.vo.ReviewVO;
import com.myappsecurity.sga.vo.SearchVO;
import com.myappsecurity.sga.vo.ShoppingCartVO;
import com.myappsecurity.sga.vo.UserVO;
import java.io.*;
import java.lang.reflect.Method;
import java.net.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 *
 * @author Chander Singh
 */
public class ProductServlet extends HttpServlet {
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger = Logger.getLogger(ProductServlet.class);
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
        
        if ("addproduct".equals(operation)) {
            String categoryId = request.getParameter("categoryId");
            try {
                long categoryid = Long.parseLong(categoryId);
                ProductVO productVO = new ProductVO ();
                productVO.setCategoryId(categoryid);
                productVO.setAppName(appname);
                productVO.setAppTypeCd(apptype);
                request.setAttribute("PRODUCT", productVO);
            } catch (Exception exp) {
                MDC.put ("MyMDC2", "ERROR");
                logger.error(exp.getMessage(), exp);
                request.setAttribute ("EXCEPTION", exp);
                operation = "exception";
            }
        }
        
        if ("addreview".equals(operation)) {
            HttpSession session = request.getSession();
            Object userVOObj = session.getAttribute("USER");
            if (userVOObj == null) {
                operation = apptype + "-" + appname + "/" + operation; 
                session.setAttribute ("REDIRECT", operation);
                operation = "login";                
            } else {                
                HashMap errorMap = new HashMap ();
                ReviewVO reviewVO = new ReviewVO ();
                String username = request.getParameter ("username");      
                if (username == null || "".equals (username.trim())) {
                    errorMap.put ("username", "required");
                }
                String rating = request.getParameter ("rating");
                String productId = request.getParameter ("productId");
                String message = request.getParameter ("message");
                if (message == null || "".equals (message.trim())) {
                    errorMap.put ("message", "required");
                }
                reviewVO.setUserType("USER");
                reviewVO.setUsername(username);
                reviewVO.setProductId(productId);
                reviewVO.setRating(rating);
                reviewVO.setMessage(message);
                if (!errorMap.isEmpty()) {
                    operation = "addreviewerror";
                    request.setAttribute ("REVIEW", reviewVO);
                } else {                
                    long reviewId = Calendar.getInstance().getTimeInMillis();
                    reviewVO.setReviewId(reviewId);
                    try {
                        ProductDAO productDAO = new ProductDAO ();
                        productDAO.insertReview(reviewVO);
                    } catch (Exception exp) {
                        MDC.put ("MyMDC2", "ERROR");
                        logger.error(exp.getMessage(), exp);
                        request.setAttribute ("EXCEPTION", exp);
                        operation = "exception";
                    }
                }
                try {    
                    ProductDAO productDAO = new ProductDAO ();
                    ProductVO productVO = new ProductVO ();
                    productVO.setProductId(Long.parseLong(productId));
                    ReviewVO[] reviewVOs = productDAO.fetchReviews(productVO);
                    request.setAttribute ("REVIEWS", reviewVOs);
                } catch (Exception exp) {
                    MDC.put ("MyMDC2", "ERROR");
                    logger.error(exp.getMessage(), exp);
                    request.setAttribute ("EXCEPTION", exp);
                    operation = "exception";
                }
                Object productObj = request.getSession().getAttribute("PRODUCTS");
                ProductVO[] productVOs = (ProductVO[]) productObj;
                ProductVO productVO = new ProductVO ();
                long productid = Long.parseLong(productId);
                productVO.setProductId(productid);
                ArrayList productVOAL =  new ArrayList (Arrays.asList (productVOs));
                int loc = productVOAL.indexOf(productVO);
                productVO = (ProductVO)productVOAL.get(loc);      

                StringBuffer uploaddirectory = new StringBuffer ();
                uploaddirectory.append (Constants.getProperty ("DEFAULT_UPLOAD_DIR"));
                uploaddirectory.append ("/");
                uploaddirectory.append (apptype);
                uploaddirectory.append ("/");
                uploaddirectory.append (appname);
                uploaddirectory.append ("/");
                uploaddirectory.append (String.valueOf (productVO.getCategoryId()));
                uploaddirectory.append ("/");
                productVO.setUploaddir(uploaddirectory.toString());
                uploaddirectory = null;

                try {
                    ProductDAO productDAO = new ProductDAO ();
                    ReviewVO[] reviewVOs = productDAO.fetchReviews(productVO);
                    request.setAttribute ("REVIEWS", reviewVOs);
                } catch (Exception exp) {
                    MDC.put ("MyMDC2", "ERROR");
                    logger.error(exp.getMessage(), exp);
                    request.setAttribute ("EXCEPTION", exp);
                    operation = "exception";
                }
                request.setAttribute ("PRODUCT", productVO);
                request.setAttribute ("ERRORS", errorMap);
                Object shoppingCartObj = request.getSession().getAttribute("SHOPPINGCART");
                 if (shoppingCartObj != null) {
                    ShoppingCartVO shoppingCartVO = (ShoppingCartVO)shoppingCartObj;
                    CartVO cartVO = shoppingCartVO.getCart (productid);
                    if (cartVO != null) {
                        int quantity = cartVO.getQuantity();
                        if (quantity < 1) {
                            quantity = 0;
                        }
                        request.setAttribute("QTY", new Integer (quantity));
                    }
                }
            }
        }
        
        if ("product".equals (operation)) {
            String categoryId = request.getParameter("categoryId");
            ProductDAO productDAO = new ProductDAO();
            ProductVO[] productVOs = new ProductVO[0];
            try {
                long categoryid = Long.parseLong(categoryId);
                productVOs = productDAO.fetchProducts(categoryid);
            } catch (Exception exp) {
                MDC.put ("MyMDC2", "ERROR");
                logger.error(exp.getMessage(), exp);
                request.setAttribute ("EXCEPTION", exp);
                operation = "exception";
            } 
            request.getSession().setAttribute("PRODUCTS", productVOs);
        }
        
        if ("productdesc".equals (operation)) {
            String productId = request.getParameter("productId");
            Object productObj = request.getSession().getAttribute("PRODUCTS");
            ProductVO[] productVOs = (ProductVO[]) productObj;
            ProductVO productVO = new ProductVO ();
            long productid = Long.parseLong(productId);
            productVO.setProductId(productid);
            ArrayList productVOAL =  new ArrayList (Arrays.asList (productVOs));
            int loc = productVOAL.indexOf(productVO);
            productVO = (ProductVO)productVOAL.get(loc);      
            
            StringBuffer uploaddirectory = new StringBuffer ();
            uploaddirectory.append (Constants.getProperty ("DEFAULT_UPLOAD_DIR"));
            uploaddirectory.append ("/");
            uploaddirectory.append (apptype);
            uploaddirectory.append ("/");
            uploaddirectory.append (appname);
            uploaddirectory.append ("/");
            uploaddirectory.append (String.valueOf (productVO.getCategoryId()));
            uploaddirectory.append ("/");
            productVO.setUploaddir(uploaddirectory.toString());
            uploaddirectory = null;
            
            try {
                ProductDAO productDAO = new ProductDAO ();
                ReviewVO[] reviewVOs = productDAO.fetchReviews(productVO);
                request.setAttribute ("REVIEWS", reviewVOs);
            } catch (Exception exp) {
                MDC.put ("MyMDC2", "ERROR");
                logger.error(exp.getMessage(), exp);
                request.setAttribute ("EXCEPTION", exp);
                operation = "exception";
            }
            request.setAttribute ("PRODUCT", productVO);
            
            Object shoppingCartObj = request.getSession().getAttribute("SHOPPINGCART");
             if (shoppingCartObj != null) {
                ShoppingCartVO shoppingCartVO = (ShoppingCartVO)shoppingCartObj;
                CartVO cartVO = shoppingCartVO.getCart (productid);
                if (cartVO != null) {
                    int quantity = cartVO.getQuantity();
                    if (quantity < 1) {
                        quantity = 0;
                    }
                    request.setAttribute("QTY", new Integer (quantity));
                }
            }
        }
        
        if ("productsub".equals(operation)) {            
            try {
                ProductVO productVO = new ProductVO ();
                extractRequestParameters (productVO, request);
                boolean isValid = validate (productVO, request, false);
                if (isValid) {
                    ProductDAO productDAO = new ProductDAO();
                    productDAO.createProduct(productVO);
                    FileItem item = productVO.getProductImage();                    
                    String realPath = getServletContext().getRealPath("");
                    StringBuffer uploaddirectory = new StringBuffer (realPath);
                    uploaddirectory.append ("/");
                    uploaddirectory.append (Constants.getProperty ("DEFAULT_UPLOAD_DIR"));
                    uploaddirectory.append ("/");
                    uploaddirectory.append (apptype);
                    uploaddirectory.append ("/");
                    uploaddirectory.append (appname);
                    uploaddirectory.append ("/");
                    uploaddirectory.append (String.valueOf (productVO.getCategoryId()));
                    uploaddirectory.append ("/");
                    File file = new File (realPath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    file = new File (realPath.concat (productVO.getProductImg()));
            
                    File uploadedDir = new File(uploaddirectory.toString());
                    if (!uploadedDir.exists()) {
                        uploadedDir.mkdirs();
                    }
                    uploadedDir = null;
                    uploaddirectory.append ("/");
                    uploaddirectory.append (productVO.getProductImg());
                    File uploadedFile = new File(uploaddirectory.toString());
                     if (!uploadedFile.exists()) {
                        uploadedFile.createNewFile();
                    }
                    item.write(uploadedFile);       
                } else {
                    operation = "producterror";
                }
            } catch (Exception exp) {
                request.setAttribute ("EXCEPTION", exp);
                operation = "exception";
            }
        }
        
        if ("searchsub".equals(operation)) {
            HashMap errorMap = new HashMap ();
            String searchinput = request.getParameter("searchinput");
            request.setAttribute ("SEARCH", searchinput);
            if (searchinput == null || "".equals(searchinput)) {
                errorMap.put("searchinput", "required");
            }
            if (errorMap.isEmpty()) {
                try {
                    ProductDAO productDAO = new ProductDAO ();
                    SearchVO[] productVOs = productDAO.searchProduct(searchinput);
                    request.getSession().setAttribute ("PRODUCTS", productVOs);
                } catch (Exception exp) {
                    MDC.put ("MyMDC2", "ERROR");
                    logger.error(exp.getMessage(), exp);
                    request.setAttribute ("EXCEPTION", exp);
                    operation = "exception";
                }
            } else {
                operation = "searcherror";
                request.setAttribute("ERRORS", errorMap);
            }
        }
        
        if ("updateproduct".equals(operation)) {
            String productId = request.getParameter("productId");
            Object productObj = request.getSession().getAttribute("PRODUCTS");
            ProductVO[] productVOs = (ProductVO[]) productObj;
            ProductVO productVO = new ProductVO ();
            long productid = Long.parseLong(productId);
            productVO.setProductId(productid);
            ArrayList productVOAL =  new ArrayList (Arrays.asList (productVOs));
            int loc = productVOAL.indexOf(productVO);
            productVO = (ProductVO)productVOAL.get(loc);           
            request.setAttribute ("PRODUCT", productVO);
        }
        
         if ("updateproductsub".equals(operation)) {
            try {
                ProductVO productVO = new ProductVO ();
                extractRequestParameters(productVO, request);
                boolean isValid = validate (productVO, request, true);
                if (isValid) {
                    ProductDAO productDAO = new ProductDAO();
                    productDAO.updateProduct(productVO);
                    FileItem item = productVO.getProductImage();    
                    if (item != null) {
                        String realPath = getServletContext().getRealPath("");
                        StringBuffer uploaddirectory = new StringBuffer (realPath);
                        uploaddirectory.append ("/");
                        uploaddirectory.append (Constants.getProperty ("DEFAULT_UPLOAD_DIR"));
                        uploaddirectory.append ("/");
                        uploaddirectory.append (apptype);
                        uploaddirectory.append ("/");
                        uploaddirectory.append (appname);
                        uploaddirectory.append ("/");
                        uploaddirectory.append (String.valueOf (productVO.getCategoryId()));
                        uploaddirectory.append ("/");

                        File file = new File (realPath);
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        file = new File (realPath.concat (productVO.getProductImg()));

                        File uploadedDir = new File(uploaddirectory.toString());
                        if (!uploadedDir.exists()) {
                            uploadedDir.mkdirs();
                        }
                        uploadedDir = null;
                        uploaddirectory.append ("/");
                        uploaddirectory.append (productVO.getProductImg());
                        File uploadedFile = new File(uploaddirectory.toString());
                         if (!uploadedFile.exists()) {
                            uploadedFile.createNewFile();
                        }
                        item.write(uploadedFile);       
                    }
                } else {
                    operation = "producterror";
                }
             } catch (Exception exp) {
                MDC.put ("MyMDC2", "ERROR");
                logger.error(exp.getMessage(), exp);
                request.setAttribute ("EXCEPTION", exp);
                operation = "exception";
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
     * @param productVO
     * @param request
     * @param update
     * @return
     * @throws java.lang.Exception
     */
    private boolean validate (ProductVO productVO, HttpServletRequest request, boolean update) throws Exception {
        HashMap errorMap = new HashMap ();
        if (!update) {
            if (productVO.getProductImg() == null || "".equals (productVO.getProductImg())) {
                errorMap.put("productImg", "required");
            } else if (productVO.getProductImage() == null || productVO.getProductImage().getSize() < 1) {
                errorMap.put("productImg", "required");
            }
        }
        if (productVO.getProductName() == null || "".equals (productVO.getProductName ())) {
            errorMap.put ("productName", "required");
        }
        String prodPrice = productVO.getProductPrice();
        if (prodPrice == null || "".equals (prodPrice)) {
            errorMap.put ("productPrice", "required");
        } else {
            double productPrice = Double.parseDouble(prodPrice);
            if (productPrice < 1) {
                errorMap.put ("productPrice", "cannot be less than $0.0");
            }
        }
        if (productVO.getDescription() == null || "".equals(productVO.getDescription())) {
            errorMap.put ("description", "required");
        }
        if (!errorMap.isEmpty()) {
            request.setAttribute("ERRORS", errorMap);
            return false;
        } else {
            if (!update) {
                long productId = Calendar.getInstance().getTimeInMillis();
                productVO.setProductId(productId);
            }
            return true;
        }
    }

    /**
     * 
     * @param productVO
     * @param request
     * @throws java.lang.Exception
     */
    private void extractRequestParameters (ProductVO productVO, HttpServletRequest request) throws Exception {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        Class productClass = productVO.getClass();
        List items = sfu.parseRequest(request);
        Iterator fileIter = items.iterator();
        while (fileIter.hasNext()) {
            FileItem item = (FileItem) fileIter.next();
            String fieldName = item.getFieldName();
            String fileName = item.getName();
            if (!item.isFormField()) {            
                productVO.setProductImage(item);     
                int index = fileName.lastIndexOf("/");
                if (index > -1) {
                    fileName = fileName.substring(index);
                }
                productVO.setProductImg(fileName);
            } else {
                fileName = item.getString();
                String parameter = "set".concat (fieldName.substring (0,1).toUpperCase()).concat (fieldName.substring(1));
                Method method = null;
                if ("categoryId".equals(fieldName)) {
                    productVO.setCategoryId(Long.parseLong (fileName));
                } else if ("productId".equals (fieldName)){
                    productVO.setProductId(Long.parseLong (fileName));
                } else {
                    method = productClass.getMethod(parameter, new Class [] {String.class});
                    method.invoke(productVO, new Object [] {fileName});
                }                                
            }            
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
