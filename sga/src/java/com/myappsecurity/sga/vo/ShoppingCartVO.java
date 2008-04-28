package com.myappsecurity.sga.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Chander Singh
 * @created.on Jan 18, 2008
 */
public class ShoppingCartVO implements Serializable {
    private long cartId = 0;
    private String appTypeCd = "";
    private String appName = "";
    private String userTypeCd = "";
    private String userName = "";
    private AddressVO[] addressVOs = new AddressVO [0];
    private CreditCardVO creditCardVO = new CreditCardVO ();

    private CartVO[] cartVOs = new CartVO [0];

    private String completed = "N";
        
    private double totalAmount = 0;

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }
    
    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public CartVO[] getCartVOs() {
        return cartVOs;
    }

    public void setCartVOs(CartVO[] cartVOs) {
        this.cartVOs = cartVOs;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
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

    public String getUserTypeCd() {
        return userTypeCd;
    }

    public void setUserTypeCd(String userTypeCd) {
        this.userTypeCd = userTypeCd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public AddressVO[] getAddressVOs() {
        return addressVOs;
    }

    public void setAddressVOs(AddressVO[] addressVOs) {
        this.addressVOs = addressVOs;
    }    

    public CreditCardVO getCreditCardVO() {
        return creditCardVO;
    }

    public void setCreditCardVO(CreditCardVO creditCardVO) {
        this.creditCardVO = creditCardVO;
    }
    
    public void addCart (CartVO cartVO)   {
        ArrayList cartVOAL = new ArrayList (Arrays.asList (cartVOs));
        int index = cartVOAL.indexOf(cartVO);
        if (index > -1) {
            cartVOAL.remove(index);
        }
        cartVOAL.add (cartVO);
        cartVOs = (CartVO[]) cartVOAL.toArray(new CartVO [cartVOAL.size()]);
        cartVOAL = null;
    }
    
    public void deleteCart (CartVO cartVO) {
         ArrayList cartVOAL = new ArrayList (Arrays.asList (cartVOs));
        int index = cartVOAL.indexOf(cartVO);
        if (index > -1) {
            cartVOAL.remove(index);
        }
        cartVOs = (CartVO[]) cartVOAL.toArray(new CartVO [cartVOAL.size()]);
        cartVOAL = null;
    }
    
    public CartVO getCart (long productId) {
        ArrayList cartVOAL = new ArrayList (Arrays.asList (cartVOs));
        CartVO cartVO = new CartVO ();
        cartVO.setProductId(productId);
        int index = cartVOAL.indexOf(cartVO);
        if (index > -1) {
            cartVO = (CartVO) cartVOAL.get(index);
            cartVOAL = null;
            return cartVO;
        } 
        return null;
    }
}
