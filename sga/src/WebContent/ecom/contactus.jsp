<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%-- 
    Document   : contactus
    Created on : Mar 25, 2008, 7:27:08 PM
    Author     : Chander Singh
--%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.myappsecurity.sga.vo.ContactusVO"%>
<%@ include file="../common/header.jsp"%>
<html>
    <head>
        <% 
            String action = actionprefix + "/contactsub";
            HashMap errorsMap = new HashMap ();
            Object errorObj = request.getAttribute("ERRORS");
            if (errorObj != null) {
                errorsMap = (HashMap) errorObj;
            }       
            ContactusVO contactusVO = new ContactusVO ();
            Object contactusVOObj = request.getAttribute ("CONTACT");
            if (contactusVOObj != null) {
                contactusVO = (ContactusVO) contactusVOObj;
            }
            String message = null;
            Object messageObj = request.getAttribute ("MESSAGE");
            if (messageObj != null) {
                message = (String) messageObj;
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Contact us Page</title>
    </head>
    <body>
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
        <%
            if (message == null || "".equals (message)) {
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
                                    <b>Contact us</b>
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
                                    <label for="username">Name&nbsp;</label><label for="username" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="username" class="edittextfield"  value="<%=contactusVO.getUsername()%>">
                                     <%
                                        Object userNameErrObj = errorsMap.get("username");
                                        if (userNameErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)userNameErrObj+"</font>");
                                        }
                                     %>   
                                </td>
                            </tr>
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="email">Email Address&nbsp;</label><label for="email" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="email" class="edittextfield" value="<%=contactusVO.getEmail()%>">
                                     <%
                                        Object emailErrObj = errorsMap.get("email");
                                        if (emailErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)emailErrObj+"</font>");
                                        }
                                     %>   
                                </td>
                            </tr>
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="subject">Subject&nbsp;</label><label for="subject" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="subject" class="edittextfield" value="<%=contactusVO.getSubject()%>">
                                     <%
                                        Object subjectErrObj = errorsMap.get("subject");
                                        if (subjectErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)subjectErrObj+"</font>");
                                        }
                                     %>   
                                </td>
                            </tr>       
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="emailbody">Message&nbsp;</label><label for="emailbody" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <textarea name="emailbody" class="edittextfield" rows="15" cols="30"> <%=contactusVO.getEmailbody()%></textarea>
                                     <%
                                        Object emailbodyErrObj = errorsMap.get("emailbody");
                                        if (emailbodyErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)emailbodyErrObj+"</font>");
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
                                    <input type="submit" value="Submit" class="btn">
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </form>
        <%
            } else {
        %>
        <table class="normalTable" cellpadding="0" cellspacing="4">
            <tr class="normalTR">
                <td class="fillerTD">
                    &nbsp;
                </td>
                <td align="center">
                    <table class="normalTable" cellpadding="2" cellspacing="1">
                        <tr>
                            <td align="left">
                                <%=message%>
                            </td>
                        </tr>
                    </table>                    
                </td>
            </tr>
        </table>
        <%
            }
        %>
    </body>
</html>
