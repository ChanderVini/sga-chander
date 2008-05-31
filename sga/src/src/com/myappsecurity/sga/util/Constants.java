/*
 * Constants.java
 */

package com.myappsecurity.sga.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

/**
 *
 * @author Chander Singh
 * Created on October 15, 2007, 4:31 PM
 */
/** Creates a new instance of Constants */
   public class Constants {
    private static HashMap propMap = new HashMap (10);
    private static Logger logger;

    /** Creates a new instance of Constants */
    public Constants (String propertyFile) {
        logger = Logger.getLogger (Constants.class);
        MDC.put("MyMDC1", "");
        MDC.put("MyMDC2", "DEBUG");
        MDC.put("MyMDC3", "");
        MDC.put("MyMDC4", "");
        MDC.put("MyMDC5", "");
        MDC.put("MyMDC6", this.getClass().getName());
        try {
            InputStream is = this.getClass ().getResourceAsStream (propertyFile);
            Properties prop = new Properties ();
            prop.load (is);
            Enumeration propNameEnum = prop.propertyNames ();
            while (propNameEnum.hasMoreElements ()) {
                String propName = (String) propNameEnum.nextElement ();
                String propValue = prop.getProperty (propName);
                logger.debug (propName + "=" + propValue);
                propMap.put (propName, propValue);
            }
        } catch (IOException ioexp) {
            MDC.remove("MyMDC2");
            MDC.put("MyMDC2", "ERROR");
            logger.error (ioexp.getMessage(), ioexp);
            MDC.remove("MyMDC1");
            MDC.remove("MyMDC2");
            MDC.remove("MyMDC3");
            MDC.remove("MyMDC4");
            MDC.remove("MyMDC6");
        }
    }

    public static String getProperty (String propName) {
        String propValue = null;
        Object propValueObj = propMap.get (propName);
        if (propValueObj != null) {
            propValue = (String) propValueObj;
        }
        return propValue;
    }

    public static void setProperty (String propName, String propValue) {
        Object propValueObj = propMap.get (propName);
        if (propValueObj != null) {
            propMap.put (propName, propValue);
        }
    }

    public static void unloadResources () {
        MDC.put("MyMDC1", "");
        MDC.put("MyMDC2", "DEBUG");
        MDC.put("MyMDC3", "");
        MDC.put("MyMDC4", "");
        MDC.put("MyMDC5", "");
        MDC.put("MyMDC6", Constants.class.getName());
        logger.debug ("Start Unloading Resources in Constants");
        propMap = null;
        logger.debug ("End Unloading of Resources in Constants");
        MDC.remove("MyMDC1");
        MDC.remove("MyMDC2");
        MDC.remove("MyMDC3");
        MDC.remove("MyMDC4");
        MDC.remove("MyMDC6");
        logger = null;
    }
}
