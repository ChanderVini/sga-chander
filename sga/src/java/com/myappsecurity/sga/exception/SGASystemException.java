/*
 * DbConnectionException.java
 */

package com.myappsecurity.sga.exception;

/**
 *
 * @author Chander Singh
 * Created on October 15, 2007, 5:22 PM
 */
public class SGASystemException  extends Exception {    
    private String errorCode = "";
    
    /** Creates a new instance of DbConnectionException */
    public SGASystemException() {
        super ();
    }
    
    public SGASystemException (String message) {
        super (message);
    }
    
    public SGASystemException (String code, String message) {
        super (message);
        errorCode = code;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }    
}

