<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%-- 
    Document   : exception
    Created on : Mar 23, 2008, 1:31:28 PM
    Author     : Chander Singh
--%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@ include file="../common/header.jsp"%>
<%@ page import="java.lang.Exception"%>
<html>    
    <head>
        <% 
            Exception exp = new Exception ("");
            Object errorObj = request.getAttribute("EXCEPTION");
            if (errorObj != null) {
                exp = (Exception) errorObj;
            }
        %>                  
        <script type="text/javascript" src="../js/common.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Exception Page</title>
    </head>
    <body>
        <%@ include file="../common/menu.jsp"%>
        <table class="normalTable" cellpadding="0" cellspacing="4">
            <tr class="normalTR">
                <td class="fillerTD">
                    &nbsp;
                </td>
                <td align="center">
                    <table class="errorHeaderTable" cellpadding="2" cellspacing="1">
                        <tr>
                            <td align="left">
                                <b>Exception Detail</b>
                            </td>
                        </tr>
                    </table>                    
                </td>
            </tr>     
            <tr class="normalTR">
                <td class="fillerTD">
                    &nbsp;
                </td>
                <td align="center">
                    <table class="contentTable" cellpadding="2" cellspacing="1">
                        <tr class="dataGridRowDark" align="left">
                            <td>                                                                
                                User-agent&nbsp;
                            </td>
                            <td colspan="3">                                                                
                                <%
                                    out.println (request.getHeader("user-agent"));
                                %>                            
                            </td>
                        </tr>
                        <tr class="dataGridRowDark" align="left">
                            <td>                                                                
                                Referer&nbsp;
                            </td>
                            <td colspan="3">                                                                
                                <%
                                    out.println (request.getRequestURL());
                                %>                            
                            </td>
                        </tr>
                        <tr class="dataGridRowDark" align="left">
                            <td>                                                                
                                Message&nbsp;
                            </td>
                            <td colspan="3">                                                                
                                <%
                                    out.println (exp.getMessage());
                                %>                            
                            </td>
                        </tr>
                        <thead>
                            <tr>
                                <th class="dataGridHeader" align="center">
                                    Line #
                                </th>
                                <th class="dataGridHeader" align="center">
                                    Class Name
                                </th>
                                <th class="dataGridHeader" align="left">
                                    File Name
                                </th>  
                                <th class="dataGridHeader" align="left">
                                    Method Name
                                </th>  
                            </tr>
                        </thead>
                        <% 
                            StackTraceElement[] ste = exp.getStackTrace();
                            for (int cnt = 0; cnt < ste.length; cnt++) {        
                                if (cnt % 2 == 0) {
                        %>                        
                        <tr class="dataGridRowLight">
                        <%
                                } else {
                        %>
                        <tr class="dataGridRowDark">
                        <%
                                }
                        %>
                            <td align="left">
                                <%
                                    out.println (ste[cnt].getLineNumber());                                    
                                %>                            
                            </td>
                            <td align="left">
                                <%
                                    out.println (ste[cnt].getClassName());                                    
                                %>                            
                            </td>
                            <td align="left">
                                <%
                                    out.println (ste[cnt].getFileName());                                    
                                %>                            
                            </td>
                            <td align="left">
                                <%
                                    out.println (ste[cnt].getMethodName());                                    
                                %>                            
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>