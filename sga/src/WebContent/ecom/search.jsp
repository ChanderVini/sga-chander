<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%-- 
    Document   : productdesc
    Created on : Jan 23, 2008, 7:34:03 AM
    Author     : Chander Singh
--%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.myappsecurity.sga.vo.ProductVO" %>
<%@ include file="../common/header.jsp"%>
<% 
    String action = actionprefix + "/searchsub";
    HashMap errorsMap = new HashMap ();
    Object errorObj = request.getAttribute("ERRORS");
    if (errorObj != null) {
        errorsMap = (HashMap) errorObj;
    }
%>
<html>
    <head>
        <link href="../css/styles.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../js/common.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Product Search</title>
    </head>
    <body class="center">
        <%@ include file="../common/menu.jsp"%>
        <%
            if (loggedin) {
        %>
        <table class="normalTable" cellpadding="0" cellspacing="4">
            <tr class="normalTR">
                <td class="fillerTD" width="100">
                    &nbsp;
                </td>
                <td align="left">
                    <%
                        UserVO userVO = (UserVO)session.getAttribute("USER");
                        String firstName = userVO.getFirstName().substring(0, 1).toUpperCase().concat (userVO.getFirstName().substring(1));
                        String lastName = userVO.getLastName().substring(0, 1).toUpperCase().concat (userVO.getLastName().substring(1));
                    %>
                    <b><%=firstName + " " + lastName%></b>
                </td>
            </tr>     
        </table>
        <%
            }
        %>        
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
                                    <b>Product Search</b>
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
                            <%
                                Object errorsObj = errorsMap.get ("error");
                                if (errorsObj != null) {
                            %>
                            <tr class="dataGridRowLight">
                                <td colspan="2">
                                    <font color="red"><%=(String)errorsObj%></font>
                                </td>
                            </tr>
                            <%
                                }
                            %>
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="searchinput">Search input&nbsp;:</label>
                                </td>
                                <td align="left">                                                          
                                    <input type="text" name="searchinput" class="edittextfield">    
                                    <%
                                        Object searchinputErrObj = errorsMap.get("searchinput");
                                        if (searchinputErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)searchinputErrObj+"</font>");
                                        }
                                     %>   
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
                            <tr class="dataGridRowLight">
                                <td align="center">
                                    <input type="submit" value="Search" class="btn">
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </form>
    </body>