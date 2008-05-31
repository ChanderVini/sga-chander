<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%-- 
    Document   : register
    Created on : Mar 23, 2008, 12:31:06 PM
    Author     : Chander Singh
--%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.myappsecurity.sga.vo.UserVO"%>
<%@ include file="../common/header.jsp"%>
<html>
    <head>
        <% 
            String action = actionprefix + "/updateprofile";
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
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Profile Page</title>
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
                        UserVO uservo = (UserVO)session.getAttribute("USER");
                        String firstName = uservo.getFirstName().substring(0, 1).toUpperCase().concat (uservo.getFirstName().substring(1));
                        String lastName = uservo.getLastName().substring(0, 1).toUpperCase().concat (uservo.getLastName().substring(1));
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
                                    <b>User Details</b>
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
                                <td align="right">
                                    <label for="sfname">User Name&nbsp;</label><label for="userName" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="userName" class="edittextfield"  readonly value="<%=userVO.getUserName()%>">
                                </td>
                            </tr>
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="sfname">First Name&nbsp;</label><label for="firstName" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left" width="200">
                                    <input type="text" name="firstName" class="edittextfield"  value="<%=userVO.getFirstName()%>">
                                     <%
                                        Object firstNameErrObj = errorsMap.get("firstName");
                                        if (firstNameErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)firstNameErrObj+"</font>");
                                        }
                                     %>   
                                </td>
                            </tr>
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="lastname">Last Name&nbsp;</label><label for="lastName" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="lastName" class="edittextfield" value="<%=userVO.getLastName()%>">
                                    <%
                                        Object lastNameErrObj = errorsMap.get("lastName");
                                        if (lastNameErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)lastNameErrObj+"</font>");
                                        }
                                     %>   
                                </td>
                            </tr>
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="address1">Street Address 1&nbsp;</label><label for="address1" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="address1" class="edittextfield" value="<%=userVO.getAddress1()%>">
                                    <%
                                        Object address1ErrObj = errorsMap.get("address1");
                                        if (address1ErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)address1ErrObj+"</font>");
                                        }
                                     %>       
                                </td>
                            </tr>
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="address2">Address 2&nbsp;&nbsp;&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="address2" class="edittextfield" value="<%=userVO.getAddress2()%>">
                                    <%
                                        Object address2ErrObj = errorsMap.get("address2");
                                        if (address2ErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)address2ErrObj+"</font>");
                                        }
                                     %>       
                                </td>
                            </tr>
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="city">City&nbsp;</label><label for="city" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="city" class="edittextfield" value="<%=userVO.getCity()%>">
                                    <%
                                        Object cityErrObj = errorsMap.get("city");
                                        if (cityErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)cityErrObj+"</font>");
                                        }
                                     %>                                               
                                </td>
                            </tr>
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="state">State&nbsp;</label><label for="state" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="state" class="edittextfield" value="<%=userVO.getState()%>">
                                    <%
                                        Object stateErrObj = errorsMap.get("state");
                                        if (stateErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)stateErrObj+"</font>");
                                        }
                                     %>                                               
                                </td>
                            </tr>      
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="country">Country&nbsp;</label><label for="country" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="country" class="edittextfield" value="<%=userVO.getCountry()%>">
                                    <%
                                        Object countryErrObj = errorsMap.get("country");
                                        if (countryErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)countryErrObj+"</font>");
                                        }
                                     %>                                               
                                </td>
                            </tr>
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="zipCode">Zip&nbsp;</label><label for="zipCode" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="zipCode" class="edittextfield" value="<%=userVO.getZipCode()%>">
                                    <%
                                        Object zipCodeErrObj = errorsMap.get("zipCode");
                                        if (zipCodeErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)zipCodeErrObj+"</font>");
                                        }
                                     %>                                               
                                </td>
                            </tr>   
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="offPhone">Office Phone&nbsp;</label><label for="offPhone" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="offPhone" class="edittextfield" value="<%=userVO.getOffPhone()%>">
                                    <%
                                        Object phoneErrObj = errorsMap.get("offPhone");
                                        if (phoneErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)phoneErrObj+"</font>");
                                        }
                                     %>                                               
                                </td>
                            </tr>
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="homePhone1">Home Phone 1&nbsp;</label><label for="offPhone" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="homePhone1" class="edittextfield" value="<%=userVO.getHomePhone1()%>">
                                    <%
                                        Object homePhone1ErrObj = errorsMap.get("homePhone1");
                                        if (homePhone1ErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)homePhone1ErrObj+"</font>");
                                        }
                                     %>                                               
                                </td>
                            </tr>   
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="homePhone2">Home Phone 2&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="homePhone2" class="edittextfield" value="<%=userVO.getHomePhone2()%>">
                                    <%
                                        Object homePhone2ErrObj = errorsMap.get("homePhone2");
                                        if (homePhone2ErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)homePhone2ErrObj+"</font>");
                                        }
                                     %>                                               
                                </td>
                            </tr>
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="mobile">Mobile&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="mobile" class="edittextfield" value="<%=userVO.getMobile()%>">
                                    <%
                                        Object mobileErrObj = errorsMap.get("mobile");
                                        if (mobileErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)mobileErrObj+"</font>");
                                        }
                                     %>                                               
                                </td>
                            </tr>   
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="fax">Fax&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="fax" class="edittextfield" value="<%=userVO.getFax()%>">
                                    <%
                                        Object faxErrObj = errorsMap.get("fax");
                                        if (faxErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)faxErrObj+"</font>");
                                        }
                                     %>                                               
                                </td>
                            </tr>                            
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="email1">Email 1&nbsp;</label><label for="offPhone" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="email1" class="edittextfield" value="<%=userVO.getEmail1()%>">
                                    <%
                                        Object email1ErrObj = errorsMap.get("email1");
                                        if (email1ErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)email1ErrObj+"</font>");
                                        }
                                     %>                                               
                                </td>
                            </tr>   
                            <tr class="dataGridRowdark">
                                <td align="right">
                                    <label for="email2">Email 2&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="email2" class="edittextfield" value="<%=userVO.getEmail2()%>">
                                    <%
                                        Object email2ErrObj = errorsMap.get("email2");
                                        if (email2ErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)email2ErrObj+"</font>");
                                        }
                                     %>                                               
                                </td>
                            </tr>                                                        
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="secretQues">Secret Question&nbsp;</label><label for="offPhone" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="secretQues" class="edittextfield" value="<%=userVO.getSecretQues()%>">
                                    <%
                                        Object secretQuesErrObj = errorsMap.get("secretQues");
                                        if (secretQuesErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)secretQuesErrObj+"</font>");
                                        }
                                     %>                                               
                                </td>
                            </tr>   
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="secretAns">Secrets Answer&nbsp;</label><label for="offPhone" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="password" name="secretAns" class="edittextfield" value="<%=userVO.getSecretAns()%>">
                                    <%
                                        Object secretAnsErrObj = errorsMap.get("secretAns");
                                        if (secretAnsErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)secretAnsErrObj+"</font>");
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
                                    <input type="submit" value="Update" class="btn">
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
