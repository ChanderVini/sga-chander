package com.myappsecurity.sga.vo;

import java.io.Serializable;

/**
 *
 * @author Chander Singh
 * @created.on Jan 18, 2008
 */
public class CartVO implements Serializable {
    private long cartId = 0;
    private long productId = 0;
    private String productName = "";
    private int quantity = 0;
    private double productPrice = 0;

    /**
     * 
     * @return
     */
    public long getCartId() {
        return cartId;
    }

    /**
     * 
     * @param cartId
     */
    public void setCartId(long cartId) {
        this.cartId = cartId;
    }
    
    /**
     * 
     * @return
     */
    public long getProductId() {
        return productId;
    }

    /**
     * 
     * @param productId
     */
    public void setProductId(long productId) {
        this.productId = productId;
    }

    /**
     * 
     * @return
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    /**
     * 
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * 
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * 
     * @return
     */
    public double getProductPrice() {
        return productPrice;
    }

    /**
     * 
     * @param productPrice
     */
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
    
    /**
     * 
     * @param obj
     * @return
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CartVO other = (CartVO) obj;
        if (this.productId != other.productId) {
            return false;
        }
        return true;
    }
 }