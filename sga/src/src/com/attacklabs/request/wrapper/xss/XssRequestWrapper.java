/*
 * XssRequestWrapper.java
 *
 * Created on February 13, 2007, 10:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.attacklabs.request.wrapper.xss;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 *
 * @author anurag agarwal
 */
public class XssRequestWrapper extends HttpServletRequestWrapper {
    
    //These are the characters which we want to filter.
    private String[] filterChars = {"<", ">", "&lt", "&gt", "&#", "\"", "\\", "0x"};
    
    //These are the characters which will get replaced with the subsequent character in the same position from filterChars array.
    private String[] replacementChars = {" ", " ", " ", " ", "#", "'", "/", "0 x"};
    
    /** Creates a new instance of XssRequestWrapper */
    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }


    public String getParameter(String name) {
        String value = super.getParameter(name);        
        InputValidator valilidator = new InputValidator();
        String newValue = valilidator.filterRequest(value);
        return newValue;
    }

    /**
     * TODO: Need to complete this method.
     */
    public Map getParameterMap() {
        Map map = super.getParameterMap();
        HashMap hmap = new HashMap();
        //Keys are string and values are String arrays
        for(Iterator iter = map.keySet().iterator(); iter.hasNext();) {
            Object key = iter.next();
            Object value = map.get(key);            
            //sanitize here            
            hmap.put(key, value);
        }        
        return hmap;
    }

    public Enumeration getParameterNames() {
        Enumeration names = super.getParameterNames();
        Vector v = new Vector();
        
        while(names.hasMoreElements())  {
            String paramName = (String)names.nextElement();
            
            //Sanitize here
            
            v.addElement(paramName);
        }
        
        return v.elements();
    }

    public String[] getParameterValues(String name) {
        String values[] = super.getParameterValues(name);
        String newValues[] = new String[values.length];
        
        for(int i = 0; i < values.length; i++)  {
            String value = values[i];
            InputValidator valilidator = new InputValidator();
            valilidator.filterRequest(value);
            
            newValues[i] = value;
        }        
        return newValues;
    }

    public String getHeader(String name) {
        String header = super.getHeader(name);
        
        //sanitize here
        
        return header;
    }

    public Enumeration getHeaderNames() {
        Enumeration headerNames = super.getHeaderNames();
        Vector v = new Vector();
        
        while(headerNames.hasMoreElements())    {
            String headerName = (String) headerNames.nextElement();
            
            //sanitize here
            
            v.addElement(headerName);
        }
        
        return v.elements();
    }

    public Enumeration getHeaders(String name) {
        Enumeration headers = super.getHeaders(name);
        Vector v = new Vector();
        
        while(headers.hasMoreElements())    {
            String header = (String) headers.nextElement();
            
            //sanitize here
            
            v.addElement(header);
        }
        
        return v.elements();
    }

    public String getQueryString() {
        String queryString = super.getQueryString();
        
        //sanitize here
        
        return queryString;
    }

    public String getContextPath() {
        String context = super.getContextPath();
        
        //sanitize context;
        
        return context;
    }

    public Cookie[] getCookies() {
        Cookie[] cookies = super.getCookies();
        for(int i = 0; i < cookies.length; i++)     {
            Cookie cookie = cookies[i];
            String name = cookie.getName();
            String value = cookie.getValue();
            
            //sanitize name and value here
            Cookie newCookie = new Cookie(name, value);
            cookies[i] = newCookie;
        }
        
        return cookies;
    }

    public String getRemoteAddr() {
        String addr = super.getRemoteAddr();
        
        //sanitize addr;
        
        return addr;
    }

    public String getServerName() {
        String server = super.getServerName();
        
        //sanitize server
        
        return server;
    }
    
    public String getRequestURI() {
        String uri = super.getRequestURI();
        
        //sanitize url
        
        return uri;
    }

    public StringBuffer getRequestURL() {
        StringBuffer url = super.getRequestURL();
        
        //sanitize url;
        
        return url;
    }

    public String getServletPath() {
        String path = super.getServletPath();
        
        //sanitize path;
        
        return path;
    }

    public String getRemoteHost() {
        String host = super.getRemoteHost();
        
        //saitize host;
        
        return host;
    }

    public String getRealPath(String path) {
        String realPath = super.getRealPath(path);
        
        //sanitize realPath;
        
        return realPath;
    }

    public String getPathInfo() {
        String pathInfo = super.getPathInfo();
        
        //sanitize pathInfo
        
        return pathInfo;
    }

    public String getPathTranslated() {
        String path = super.getPathTranslated();
        
        //sanitize path
        
        return path;
    }
}
