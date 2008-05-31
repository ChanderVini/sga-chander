<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%-- 
    Document   : wishlist
    Created on : Apr 5, 2008, 12:40:08 AM
    Author     : Chander Singh
--%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@ page import="com.myappsecurity.sga.vo.WishlistVO"%>
<%@ include file="../common/header.jsp"%>
<% 
    WishlistVO[] wishlistVOs =  new WishlistVO [0];
    Object wishlistObj = session.getAttribute("WISHLIST");
    if (wishlistObj != null) {
        wishlistVOs = (WishlistVO[])session.getAttribute ("WISHLIST");
    }
    String deleteaction = actionprefix + "/delwishlist?productId=";    
    String updateaction = actionprefix + "/wishlist?productId=";    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Wishlist Page</title>
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
        <table class="normalTable" cellpadding="0" cellspacing="4">
            <tr class="normalTR">
                <td class="fillerTD">
                    &nbsp;
                </td>
                <td align="center">
                    <table class="headerTable" cellpadding="2" cellspacing="1">
                        <tr>
                            <td align="left">
                                <b>Products List</b>
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
                                    Product Name
                                </th>  
                                <th class="dataGridHeader" align="left">
                                    Price
                                </th>  
                                <th class="dataGridHeader" align="left">
                                    Comments
                                </th>  
                                <th class="dataGridHeader" align="left">
                                    Operations
                                </th>  
                            </tr>
                        </thead>
                        <%
                            for (int cnt = 0; cnt < wishlistVOs.length; cnt++) {
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
                        <td align="left"><%=wishlistVOs[cnt].getProductName()%></td>
                        <td align="left">
                            <%=wishlistVOs[cnt].getProductPrice()%>
                        </td>
                        <td align="left">
                            <%=wishlistVOs[cnt].getComments()%>
                        </td>
                        <td align="left">
                            <a href="<%=deleteaction + wishlistVOs[cnt].getProductId()%>">Remove</a> | 
                            <a href="<%=updateaction + wishlistVOs[cnt].getProductId()%>">Edit</a>
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
