/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.myappsecurity.sga.vo;

import java.io.Serializable;

/**
 *
 * @author Chander Singh
 * @created.on Mar 23, 2008
 */
public class CreditCardVO implements Serializable {
    private long cartId;
    private String username = "";
    private String userTypeCd = "";
    private String appTypeCd = "";
    private String appName = "";
            
    private String ccType = "";
    private String ccNbr = "";
    private String cvvNbr = "";
    private String expMon = "";
    private String expYear = "";
    private String cardUserName = "";

    public String getCardUserName() {
        return cardUserName;
    }

    public void setCardUserName(String cardUserName) {
        this.cardUserName = cardUserName;
    }
    
    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public String getCcType() {
        return ccType;
    }

    public void setCcType(String ccType) {
        this.ccType = ccType;
    }

    public String getCcNbr() {
        return ccNbr;
    }

    public void setCcNbr(String ccNbr) {
        this.ccNbr = ccNbr;
    }

    public String getCvvNbr() {
        return cvvNbr;
    }

    public void setCvvNbr(String cvvNbr) {
        this.cvvNbr = cvvNbr;
    }

    public String getExpMon() {
        return expMon;
    }

    public void setExpMon(String expMon) {
        this.expMon = expMon;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserTypeCd() {
        return userTypeCd;
    }

    public void setUserTypeCd(String userTypeCd) {
        this.userTypeCd = userTypeCd;
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
}
