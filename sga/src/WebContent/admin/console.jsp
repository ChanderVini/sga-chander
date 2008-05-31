<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%-- 
    Document   : console
    Created on : Apr 11, 2008, 7:03:45 AM
    Author     : Chander Singh
--%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@ page import="com.myappsecurity.sga.vo.AdminVO"%>
<%@ include file="../common/header.jsp"%>
<html>
    <head>
        <%
            String action = "appfilter";
            Object adminVOObj = config.getServletContext().getAttribute("ADMINDET");
            AdminVO[] adminVOs = new AdminVO [0];
            if (adminVOObj != null) {
                adminVOs = (AdminVO[])adminVOObj;
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Admin Console</title>
    </head>
    <body>
        <form action="<%=action%>" method="post">                
            <table class="normalTable" cellpadding="0" cellspacing="4">
                <tr class="normalTR">
                    <td class="fillerTD">
                        &nbsp;
                    </td>
                    <td align="center">
                        <table class="headerTable" cellpadding="2" cellspacing="1">
                            <tr>
                                <td align="left">
                                    <b>XSS Filter settings</b>
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
                            <thead>
                                <tr>
                                    <th class="dataGridHeader" align="center">
                                        #
                                    </th>
                                    <th class="dataGridHeader" align="left">
                                        Application Type
                                    </th>  
                                    <th class="dataGridHeader" align="left">
                                        Application Name
                                    </th>  
                                    <th class="dataGridHeader" align="left">
                                        Apply filter
                                    </th>  
                                </tr>
                            </thead>
                                    <%
                                        for (int cnt = 0; cnt < adminVOs.length; cnt++) {
                                            if ((cnt % 2) == 0) {
                                    %>
                                        <tr class="dataGridRowLight">
                                    <%
                                            } else {
                                    %>
                                        <tr class="dataGridRowDark">
                                    <%
                                            }
                                    %>                
                                    <td align="center" class="dataGridHeader"><%=new Integer (cnt+1)%></td>
                                    <td align="left"><%=adminVOs[cnt].getApplicationType()%></td>
                                    <td align="left"><%=adminVOs[cnt].getApplicationName()%></td>
                                    <td align="left">
                                        <%
                                           if ("true".equals (adminVOs[cnt].getFilterStatus())) {
                                        %>
                                        <input type="checkbox" name="xssfilter" checked value="<%="SGA-"+adminVOs[cnt].getApplicationType() + "-" + adminVOs[cnt].getApplicationName ()%>">
                                        <%
                                            } else  {
                                        %>
                                        <input type="checkbox" name="xssfilter" value="<%="SGA-"+adminVOs[cnt].getApplicationType() + "-" + adminVOs[cnt].getApplicationName ()%>">
                                        <%
                                            }
                                        %>
                                    </td>
                                </tr>
                            <%
                                    }
                            %>
                        </table>
                    </td>
                </tr>
                <tr class="normalTR">
                    <td class="fillerTD">
                        &nbsp;
                    </td>
                    <td align="center">
                        <table class="contentTable" cellpadding="2" cellspacing="1">
                            <tr class="dataGridRowLight">
                                <td align="center">
                                    <input type="submit" value="Apply" class="btn">
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
 