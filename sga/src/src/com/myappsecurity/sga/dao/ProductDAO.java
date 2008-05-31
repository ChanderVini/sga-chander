/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.myappsecurity.sga.dao;

import com.myappsecurity.sga.vo.ProductVO;
import com.myappsecurity.sga.util.DbConnection;

import com.myappsecurity.sga.vo.ReviewVO;
import com.myappsecurity.sga.vo.SearchVO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 *
 * @author Chander Singh
 * @created.on Jan 17, 2008
 */
public class ProductDAO {
    private Logger logger = null;
    
    public ProductDAO () {
        logger = Logger.getLogger (ProductDAO.class);
    }
    
    /**
     * 
     * @param productVO
     * @throws java.lang.Exception
     */
    public void createProduct (ProductVO productVO) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DbConnection.getConnection();
            StringBuffer queryBuf = new StringBuffer ("INSERT INTO product_tbl (PRODUCT_ID, " +
                    "CATEGORY_ID, PRODUCT_CD, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMG, DESCRIPTION) VALUES (");
            long productId = Calendar.getInstance().getTimeInMillis();
            productVO.setProductId(productId);
            queryBuf.append (productId);
            queryBuf.append (", ");
            queryBuf.append (productVO.getCategoryId());
            queryBuf.append (", '");
            queryBuf.append (productVO.getProductCd ());
            queryBuf.append ("', '");
            queryBuf.append (productVO.getProductName());
            queryBuf.append ("', ");
            queryBuf.append (productVO.getProductPrice());
            queryBuf.append (", '");
            queryBuf.append (productVO.getProductImg());
            queryBuf.append ("', '");
            queryBuf.append (productVO.getDescription());
            queryBuf.append ("')");
            MDC.remove ("MyMDC6");
            MDC.put("MyMDC6", this.getClass().getName());
            logger.debug (queryBuf.toString ());
            MDC.remove("MyMDC6");
            stmt = connection.createStatement();
            
            stmt.executeUpdate(queryBuf.toString());
            queryBuf = null;
            DbConnection.close(connection, stmt, null);
        } finally {
            DbConnection.close (connection, stmt, null);
        }
    }
    
    /**
     * 
     * @param searchInput
     * @return
     * @throws java.lang.Exception
     */
    public SearchVO[] searchProduct (String searchInput) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("SELECT * FROM product_tbl WHERE DESCRIPTION LIKE '%");
            queryBuf.append (searchInput);
            queryBuf.append ("%'");
            MDC.remove ("MyMDC6");
            MDC.put("MyMDC6", this.getClass().getName());
            logger.debug (queryBuf.toString ());
            MDC.remove("MyMDC6");
            connection = DbConnection.getConnection();
            stmt = connection.createStatement();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(queryBuf.toString());
            queryBuf = null;
            ArrayList productVOAL = new ArrayList (10);
            while (rs.next ()) {
                SearchVO  productVO = new SearchVO ();
                String categoryId = rs.getString (1);
                String productId = rs.getString (2);
                String categoryName = rs.getString (3);
                String productName = rs.getString (4);
                String productPrice = rs.getString(5);
                String description = rs.getString(6);
                String productImg = rs.getString (7);
                
                productVO.setCategoryId(categoryId);
                productVO.setProductId(productId);
                productVO.setCategoryName(categoryName);
                productVO.setProductName(productName);
                productVO.setProductPrice(String.valueOf(productPrice));
                productVO.setDescription(description);
                productVO.setProductImg(productImg);
                
                productVOAL.add(productVO);
            }            
            DbConnection.close(connection, stmt, null);
            SearchVO[] productVOs = (SearchVO[]) productVOAL.toArray(new SearchVO [productVOAL.size()]);
            productVOAL = null;
            return productVOs;
        } finally {
            DbConnection.close (connection, stmt, null);
        }
    }
    
    /**
     * 
     * @param productVO
     * @throws java.lang.Exception
     */
    public void updateProduct (ProductVO productVO) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DbConnection.getConnection();
            StringBuffer queryBuf = new StringBuffer ("UPDATE product_tbl SET PRODUCT_NAME = '");
            queryBuf.append (productVO.getProductName());
            if (productVO.getProductImg() != null && !"".equals (productVO.getProductImg())) {
                queryBuf.append ("', PRODUCT_IMG = '");
                queryBuf.append (productVO.getProductImg());
            }
            queryBuf.append ("', PRODUCT_PRICE = ");
            queryBuf.append (productVO.getProductPrice());
            queryBuf.append (", DESCRIPTION = '");
            queryBuf.append (productVO.getDescription());
            queryBuf.append ("' WHERE PRODUCT_ID = ");
            queryBuf.append (productVO.getProductId());
            queryBuf.append (" AND CATEGORY_ID = ");
            queryBuf.append (productVO.getCategoryId());
            MDC.remove ("MyMDC6");
            MDC.put("MyMDC6", this.getClass().getName());
            logger.debug (queryBuf.toString ());
            MDC.remove("MyMDC6");
            stmt = connection.createStatement();
            stmt.executeUpdate(queryBuf.toString ());
            queryBuf = null;
            DbConnection.close(connection, stmt, null);
        } finally {
            DbConnection.close (connection, stmt, null);
        }
    }
    
    /**
     * 
     * @param categoryId
     * @return
     * @throws java.lang.Exception
     */
    public ProductVO[] fetchProducts (long categoryId) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = DbConnection.getConnection();
            StringBuffer queryBuf = new StringBuffer ("SELECT CAT.APP_TYPE_CD ATC, CAT.APP_NAME AN, CAT.CATEGORY_NAME CN, PROD.PRODUCT_ID PI, " +
                    "PROD.PRODUCT_CD PPC, PROD.PRODUCT_NAME PPN, PROD.PRODUCT_IMG PPI, PROD.PRODUCT_PRICE PPP, " +
                    "PROD.DESCRIPTION PD FROM product_tbl PROD, category_tbl CAT WHERE PROD.CATEGORY_ID = ");
            queryBuf.append (categoryId);
            queryBuf.append (" AND CAT.CATEGORY_ID = PROD.CATEGORY_ID");
            stmt = connection.createStatement();
            MDC.remove ("MyMDC6");
            MDC.put("MyMDC6", this.getClass().getName());
            logger.debug (queryBuf.toString ());
            MDC.remove("MyMDC6");
            rs = stmt.executeQuery(queryBuf.toString());
            queryBuf = null;
            ArrayList productVOAL = new ArrayList (10);
            while (rs.next ()) {
                String appTypeCd = rs.getString ("ATC");
                String appName = rs.getString ("AN");
                String categoryName = rs.getString ("CN");
                long productId = rs.getLong ("PI");
                String productCd = rs.getString ("PPC");
                String productName = rs.getString ("PPN");
                String productImage = rs.getString ("PPI");
                double productPrice = rs.getDouble ("PPP");
                String description = rs.getString ("PD");
                
                ProductVO productVO = new ProductVO ();
                productVO.setAppTypeCd(appTypeCd);
                productVO.setAppName(appName);
                productVO.setCategoryName(categoryName);
                productVO.setProductId(productId);
                productVO.setCategoryId(categoryId);
                productVO.setProductCd(productCd);
                productVO.setProductName(productName);
                productVO.setProductImg(productImage);
                productVO.setProductPrice(String.valueOf (productPrice));
                productVO.setDescription(description);
                productVOAL.add (productVO);
            }
            ProductVO[] productVOs = (ProductVO[]) productVOAL.toArray(new ProductVO [productVOAL.size()]);
            productVOAL = null;
            return productVOs;
        } finally {
            DbConnection.close (connection, stmt, rs);
        }
    }
    
    /**
     * 
     * @param productVO
     * @return
     * @throws java.lang.Exception
     */
    public ReviewVO[] fetchReviews (ProductVO productVO) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;        
        try {
            connection = DbConnection.getConnection();
            StringBuffer queryBuf = new StringBuffer ("SELECT REVIEW_ID, PRODUCT_ID, USER_TYPE_CD, USER_NAME, RATING, REVIEW FROM customer_review_tbl WHERE PRODUCT_ID = ");
            queryBuf.append (productVO.getProductId());
            MDC.remove ("MyMDC6");
            MDC.put("MyMDC6", this.getClass().getName());
            logger.debug (queryBuf.toString ());
            MDC.remove("MyMDC6");
            stmt = connection.createStatement();
            rs = stmt.executeQuery(queryBuf.toString());
            queryBuf = null;
            ArrayList reviewVOAL = new ArrayList (10);
            while (rs.next ()) {
                ReviewVO reviewVO = new ReviewVO ();
                long reviewId = rs.getLong ("REVIEW_ID");
                long productId = rs.getLong ("PRODUCT_ID");
                String userTypeCd = rs.getString ("USER_TYPE_CD");
                String userName = rs.getString ("USER_NAME");
                int rating = rs.getInt ("RATING");
                String review = rs.getString ("REVIEW");
                reviewVO.setReviewId (reviewId);
                reviewVO.setProductId (String.valueOf (productId));
                reviewVO.setUserType(userTypeCd);
                reviewVO.setUsername(userName);
                reviewVO.setRating(String.valueOf (rating));
                reviewVO.setMessage(review);
                reviewVOAL.add(reviewVO);                
            }
            ReviewVO[] reviewVOs = (ReviewVO[]) reviewVOAL.toArray(new ReviewVO [reviewVOAL.size()]);
            reviewVOAL = null;
            return reviewVOs;
        } finally {
            DbConnection.close (connection, stmt, rs);
        }
    }
    
    /**
     * 
     * @param reviewVO
     * @throws java.lang.Exception
     */
    public void insertReview (ReviewVO reviewVO) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DbConnection.getConnection();
            StringBuffer queryBuf = new StringBuffer ("INSERT INTO customer_review_tbl (REVIEW_ID, PRODUCT_ID,  USER_TYPE_CD, USER_NAME, RATING, REVIEW) VALUES (");
            queryBuf.append (reviewVO.getReviewId());
            queryBuf.append (", ");
            queryBuf.append (reviewVO.getProductId());
            queryBuf.append (", '");
            queryBuf.append (reviewVO.getUserType());
            queryBuf.append ("', '");
            queryBuf.append (reviewVO.getUsername());
            queryBuf.append ("', ");
            queryBuf.append (reviewVO.getRating());
            queryBuf.append (", '");
            queryBuf.append (reviewVO.getMessage());
            queryBuf.append ("')");
            MDC.remove ("MyMDC6");
            MDC.put("MyMDC6", this.getClass().getName());
            logger.debug (queryBuf.toString ());
            MDC.remove("MyMDC6");
            stmt = connection.createStatement();
            stmt.executeUpdate(queryBuf.toString());
            queryBuf = null;
        } finally {
            DbConnection.close (connection, stmt, null);
        }
    }
}