/*
 * XSSFilter.java
 *
 * Created on February 13, 2007, 10:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.attacklabs.xssfilter;

import com.attacklabs.request.wrapper.xss.XssRequestWrapper;
import com.myappsecurity.sga.vo.AdminVO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author anurag agarwal
 */
public class XSSFilter implements Filter {
    private ServletContext context;
    
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Object object = context.getAttribute("ADMINDET");
        if (object != null) {
            AdminVO[] adminVOs = (AdminVO[]) object;
            ArrayList adminVOAL = new ArrayList(Arrays.asList (adminVOs));
            String contextPath = req.getContextPath();
            String requestURI = req.getRequestURI();
            int index = requestURI.indexOf(contextPath);
            requestURI = requestURI.substring(index + contextPath.length() + 1);

            index = requestURI.indexOf("/");
            
            String appdetail = requestURI.substring(0, index);
            String[] appdetails = appdetail.split("-");
            if (appdetails.length > 1) {
                String apptype = appdetails[0];
                String appname = appdetails[1];
                AdminVO adminVO = new AdminVO ();
                adminVO.setApplicationName(appname);
                adminVO.setApplicationType(apptype);
                int loc = adminVOAL.indexOf(adminVO);
                if (loc > -1) {
                    adminVO = (AdminVO)adminVOAL.get(loc);
                     if ("true".equals (adminVO.getFilterStatus())) {
                         chain.doFilter( new XssRequestWrapper((HttpServletRequest) request), response);
                     } else {
                        chain.doFilter(request, response);
                     }
                } else {
                    chain.doFilter(request, response);
                }
            } else {
                chain.doFilter(request, response);
            }           
        } else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        context = filterConfig.getServletContext();
    }
}