<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%-- 
    Document   : login
    Created on : Jan 19, 2008, 4:28:07 PM
    Author     : Chander Singh
--%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.myappsecurity.sga.vo.UserVO"%>
<%@ include file="../common/header.jsp"%>
<html>
    <head>
        <% 
            String action = actionprefix + "/authenticate";
            HashMap errorsMap = new HashMap ();
            Object errorObj = request.getAttribute("ERRORS");
            if (errorObj != null) {
                errorsMap = (HashMap) errorObj;
            }       
            UserVO userVO = new UserVO ();
            Object userVOObj = request.getAttribute ("USER");
            if (userVOObj != null) {
                userVO = (UserVO) userVOObj;
            }
            String redirecturl = "";
            Object redirecturlObj = session.getAttribute("REDIRECT");
            if (redirecturlObj != null) {
                redirecturl = (String) redirecturlObj;
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Login Page</title>
    </head>
    <body>
        <%@ include file="../common/menu.jsp"%>
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
                                    <b>Login Details</b>
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
                                    <label for="userName">User Name&nbsp;</label><label for="userName" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="hidden" name="redirectURL" value="<%=redirecturl%>">
                                    <input type="text" name="userName" class="edittextfield"  value="<%=userVO.getUserName()%>">
                                     <%
                                        Object userNameErrObj = errorsMap.get("userName");
                                        if (userNameErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)userNameErrObj+"</font>");
                                        }
                                     %>   
                                </td>
                            </tr>
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="userPassword">Password&nbsp;</label><label for="userPassword" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="password" name="userPassword" class="edittextfield">
                                     <%
                                        Object userPasswordErrObj = errorsMap.get("userPassword");
                                        if (userPasswordErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)userPasswordErrObj+"</font>");
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
                                    <input type="submit" value="Login" class="btn">
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
                                    <a href='<%=actionprefix + "/forgotpass"%>'>Forgot Passport</a>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
