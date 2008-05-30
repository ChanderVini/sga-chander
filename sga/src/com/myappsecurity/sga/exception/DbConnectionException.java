/*
 * DbConnectionException.java
 */

package com.myappsecurity.sga.exception;

/**
 *
 * @author Chander Singh
 * Created on October 15, 2007, 5:22 PM
 */
public class DbConnectionException  extends Exception {    
    /** Creates a new instance of DbConnectionException */
    public DbConnectionException() {
        super ();
    }
    
    public DbConnectionException (String message) {
        super (message);
    }
}

