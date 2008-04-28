/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.myappsecurity.sga.dao;

import com.myappsecurity.sga.util.DbConnection;
import com.myappsecurity.sga.vo.WishlistVO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 *
 * @author Chander Singh
 * @created.on Apr 1, 2008
 */
public class WishlistDAO {
    private Logger logger = null;

    public WishlistDAO() {
        logger = Logger.getLogger(this.getClass());
    }    
    
    public void insertWishlist (WishlistVO wishlistVO) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        try {
            Date currdate = new Date (Calendar.getInstance().getTimeInMillis());
            StringBuffer queryBuf = new StringBuffer ("INSERT INTO wishlist_tbl (WISHLIST_ID, APP_TYPE_CD, APP_NAME, PRODUCT_ID, USER_TYPE_CD, USER_NAME, " +
                    "PRODUCT_PRICE, CREATE_DT, COMMENTS) VALUE (");
            queryBuf.append (wishlistVO.getWishid());
            queryBuf.append (", '");
            queryBuf.append (wishlistVO.getAppTypeCd());
            queryBuf.append ("', '");
            queryBuf.append (wishlistVO.getAppName());
            queryBuf.append ("', ");
            queryBuf.append (wishlistVO.getProductId());
            queryBuf.append (", '");
            queryBuf.append (wishlistVO.getUserTypeCd());
            queryBuf.append ("', '");
            queryBuf.append (wishlistVO.getUsername());
            queryBuf.append ("', ");
            queryBuf.append (wishlistVO.getProductPrice());
            queryBuf.append (", '");
            queryBuf.append (currdate.toString());
            queryBuf.append ("', '");
            queryBuf.append (wishlistVO.getComments());
            queryBuf.append ("')");
            MDC.remove ("MyMDC6");
            MDC.put("MyMDC6", this.getClass().getName());
            logger.debug (queryBuf.toString ());
            MDC.remove("MyMDC6");
            connection = DbConnection.getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate(queryBuf.toString());
            queryBuf = null;
            DbConnection.close(connection, stmt, null);
        } finally {
            DbConnection.close(connection, stmt, null);
        }
    }
    
    public void deleteWishlist (WishlistVO wishlistVO) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("DELETE FROM wishlist_tbl WHERE WISHLIST_ID = ");
            queryBuf.append (wishlistVO.getWishid());
            queryBuf.append (" AND APP_TYPE_CD = '");
            queryBuf.append (wishlistVO.getAppTypeCd ());
            queryBuf.append ("' AND APP_NAME = '");
            queryBuf.append(wishlistVO.getAppName ());
            queryBuf.append ("' AND USER_TYPE_CD = '");
            queryBuf.append (wishlistVO.getUserTypeCd ());
            queryBuf.append ("' AND USER_NAME = '");
            queryBuf.append (wishlistVO.getUsername ());
            queryBuf.append ("' AND PRODUCT_ID = ");
            queryBuf.append (wishlistVO.getProductId());
            MDC.remove ("MyMDC6");
            MDC.put("MyMDC6", this.getClass().getName());
            logger.debug (queryBuf.toString ());
            MDC.remove("MyMDC6");
            connection = DbConnection.getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate(queryBuf.toString());
            queryBuf = null;
            DbConnection.close(connection, stmt, null);
        } finally {
            DbConnection.close(connection, stmt, null);
        }
    }
    
    public void updateWishlist (WishlistVO wishlistVO) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("UPDATE wishlist_tbl SET COMMENTS = '");
            queryBuf.append(wishlistVO.getComments());
            queryBuf.append("' WHERE WISHLIST_ID = ");
            queryBuf.append (wishlistVO.getWishid());
            queryBuf.append (" AND APP_TYPE_CD = '");
            queryBuf.append (wishlistVO.getAppTypeCd ());
            queryBuf.append ("' AND APP_NAME = '");
            queryBuf.append(wishlistVO.getAppName ());
            queryBuf.append ("' AND USER_TYPE_CD = '");
            queryBuf.append (wishlistVO.getUserTypeCd ());
            queryBuf.append ("' AND USER_NAME = '");
            queryBuf.append (wishlistVO.getUsername ());
            queryBuf.append ("' AND PRODUCT_ID = ");
            queryBuf.append (wishlistVO.getProductId());
            
            connection = DbConnection.getConnection();
            stmt = connection.createStatement();
            MDC.remove ("MyMDC6");
            MDC.put("MyMDC6", this.getClass().getName());
            logger.debug (queryBuf.toString ());
            MDC.remove("MyMDC6");
            stmt.executeUpdate(queryBuf.toString());
            queryBuf = null;
            DbConnection.close(connection, stmt, null);
        } finally {
            DbConnection.close(connection, stmt, null);
        }
    }
    
    public WishlistVO[] fetchWishlist (WishlistVO wishlistVO) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            StringBuffer queryBuf = new StringBuffer ("SELECT WISH.WISHLIST_ID WI, WISH.APP_TYPE_CD ATC, WISH.APP_NAME AN, WISH.USER_TYPE_CD UTC, " +
                    "WISH.USER_NAME UN, WISH.PRODUCT_ID PI, WISH.PRODUCT_PRICE PP, WISH.COMMENTS COM, PROD.PRODUCT_NAME PN FROM " +
                    "wishlist_tbl WISH, product_tbl PROD WHERE PROD.PRODUCT_ID = WISH.PRODUCT_ID AND WISH.APP_TYPE_CD = '");
            queryBuf.append (wishlistVO.getAppTypeCd ());
            queryBuf.append ("' AND WISH.APP_NAME = '");
            queryBuf.append(wishlistVO.getAppName ());
            queryBuf.append ("' AND WISH.USER_TYPE_CD = '");
            queryBuf.append (wishlistVO.getUserTypeCd ());
            queryBuf.append ("' AND WISH.USER_NAME = '");
            queryBuf.append (wishlistVO.getUsername ());
            queryBuf.append ("' AND WISH.USER_TYPE_CD = '");
            queryBuf.append (wishlistVO.getUserTypeCd());
            queryBuf.append ("'");
            
            MDC.remove ("MyMDC6");
            MDC.put("MyMDC6", this.getClass().getName());
            logger.debug (queryBuf.toString ());
            MDC.remove("MyMDC6");
            connection = DbConnection.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(queryBuf.toString ());
            queryBuf = null;
            
            ArrayList wishlistVOAL = new ArrayList (10);
            while (rs.next()) {
                long wishid = rs.getLong ("WI");
                String appTypeCd = rs.getString("ATC");
                String appName = rs.getString ("AN");
                String userTypeCd = rs.getString ("UTC");
                String username = rs.getString ("UN");
                long productid = rs.getLong("PI");
                double productPrice = rs.getDouble("PP");
                String productName = rs.getString ("PN");
                String comments = rs.getString("COM");
                
                WishlistVO wishlistvo = new WishlistVO ();
                wishlistvo.setWishid(wishid);
                wishlistvo.setAppName(appName);
                wishlistvo.setAppTypeCd(appTypeCd);
                wishlistvo.setProductId(productid);
                wishlistvo.setProductPrice(productPrice);
                wishlistvo.setProductName(productName);
                wishlistvo.setUserTypeCd(userTypeCd);
                wishlistvo.setUsername(username);
                wishlistvo.setComments (comments);
                
                wishlistVOAL.add (wishlistvo);
            }
            WishlistVO[] wishlistVOs = (WishlistVO[]) wishlistVOAL.toArray(new WishlistVO [wishlistVOAL.size()]);
            wishlistVOAL = null;
            DbConnection.close(connection, stmt, rs);
            return wishlistVOs;
        } finally {
            DbConnection.close(connection, stmt, rs);
        }
    }
}
