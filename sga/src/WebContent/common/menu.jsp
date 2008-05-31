<%-- 
    Document   : menu
    Created on : Feb 18, 2008, 7:09:05 PM
    Author     : Chander Singh
--%>
<%@ page import="com.myappsecurity.sga.vo.UserVO" %>
<%
    boolean loggedin = false;
    if (session.getAttribute("USER") != null) {
        loggedin = true;
    }
%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<table class="normalTable" cellpadding="0" cellspacing="4">
    <tr class="normalTR">
        <td class="fillerTD">
            &nbsp;
        </td>
        <td>
            <table class="headerTable" cellpadding="2" cellspacing="1" align="center">
                <tr>
                    <td align="left">
                        <a href='<%=actionprefix + "/category"%>'>Category</a>
                        |
                        <a href='<%=actionprefix + "/cart"%>'>Shopping Cart</a>
                         |
                        <a href='<%=actionprefix + "/listwishlist"%>'>Wishlist</a>
                        |
                        <%
                            if (!loggedin) {
                        %>
                        <a href='<%=actionprefix + "/register"%>'>Register</a>
                        |
                        <a href='<%=actionprefix + "/login"%>'>Login</a>
                        <%
                            } else {
                        %>
                        <a href='<%=actionprefix + "/profile?username=" + ((UserVO)session.getAttribute("USER")).getUserName ()%>'>Profile</a>
                        |
                        <a href='<%=actionprefix + "/changepass"%>'>Change Password</a>
                        |
                        <a href='<%=actionprefix + "/logout"%>'>Logout</a>
                        <%
                            }
                        %>
                        |
                        <a href='<%=actionprefix + "/search"%>'>Search</a>
                        |
                        <a href='<%=actionprefix + "/contact"%>'>Contact Us</a>
                    </td>
                </tr>
            </table>                    
        </td>
    </tr>
</table>
