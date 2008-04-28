/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.myappsecurity.sga.vo;

import java.io.Serializable;

/**
 *
 * @author Chander Singh
 * @created.on Apr 1, 2008
 */
public class WishlistVO implements Serializable {
    private String appTypeCd = "";
    private String appName = "";
    private String userTypeCd = "";
    private String username = "";
    
    private long wishid = -1;
     private long productId = 0;
    private String productName = "";
    private double productPrice = 0;
    private String comments = "";

    public long getWishid() {
        return wishid;
    }

    public void setWishid(long wishid) {
        this.wishid = wishid;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getAppTypeCd() {
        return appTypeCd;
    }

    public void setAppTypeCd(String appTypeCd) {
        this.appTypeCd = appTypeCd;
    }


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
    
    public boolean equals (Object obj) {
        WishlistVO wishlistVO = (WishlistVO) obj;
        long productId = wishlistVO.getProductId();
        return (this.productId == productId);
    }

    public String getUserTypeCd() {
        return userTypeCd;
    }

    public void setUserTypeCd(String userTypeCd) {
        this.userTypeCd = userTypeCd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
