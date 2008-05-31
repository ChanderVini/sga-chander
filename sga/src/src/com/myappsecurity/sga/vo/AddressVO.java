/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.myappsecurity.sga.vo;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Chander Singh
 * @created.on Jan 18, 2008
 */
public class AddressVO implements Serializable {
    private long cartId = 0;
    private String addressType = "";
    private String firstName = "";
    private String lastName = "";
    private String address1 = "";
    private String address2 = "";
    private String city = "";
    private String state = "";
    private String country = "";
    private String zipCode = "";
    private String offPhone = "";
    private String homePhone1 = "";
    private String homePhone2 = "";
    private String mobile = "";
    private String fax = "";
    private String email1 = "";
    private String email2 = "";
    private Date addressDt = null;
    private boolean editable = true;

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getOffPhone() {
        return offPhone;
    }

    public void setOffPhone(String offPhone) {
        this.offPhone = offPhone;
    }

    public String getHomePhone1() {
        return homePhone1;
    }

    public void setHomePhone1(String homePhone1) {
        this.homePhone1 = homePhone1;
    }

    public String getHomePhone2() {
        return homePhone2;
    }

    public void setHomePhone2(String homePhone2) {
        this.homePhone2 = homePhone2;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public Date getAddressDt() {
        return addressDt;
    }

    public void setAddressDt(Date addressDt) {
        this.addressDt = addressDt;
    }
    
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
