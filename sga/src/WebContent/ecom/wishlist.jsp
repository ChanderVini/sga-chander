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
    WishlistVO wishlistVO =  (WishlistVO)request.getAttribute ("WISHLIST");
    String action = actionprefix + "/wishlistsub";    
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
                                <b>Add/Edit Wishlist</b>
                            </td>
                        </tr>
                    </table>                    
                </td>
            </tr>
            <form action="<%=action%>" method="post">
                <tr class="normalTR">
                    <td class="fillerTD">
                        &nbsp;
                    </td>
                    <td align="center">
                        <table class="contentTable" cellpadding="2" cellspacing="1">
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="productName">Product Name:&nbsp;</label
                                </td>
                                <td align="left">
                                    <input type="hidden" name="productId" value="<%=wishlistVO.getProductId()%>">
                                    <%=wishlistVO.getProductName()%>
                                </td>  
                            </tr>
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="productPrice">Product Price:&nbsp;</label>
                                </td>  
                                <td align="left">
                                    <%=wishlistVO.getProductPrice()%>
                                </td>  
                            </tr>           
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="comments">Comments:&nbsp;</label>
                                </td>
                                <td align="left">
                                    <textarea rows="5" cols="40" name="comments" class="edittextfield"><%=wishlistVO.getComments()%></textarea>
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
                                    <input type="submit" value="Save" class="btn">
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </form>
        </table>
    </body>
</html>
