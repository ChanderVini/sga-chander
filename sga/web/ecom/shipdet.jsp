<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%-- 
    Document   : shipdet
    Created on : Feb 17, 2008, 5:45:18 PM
    Author     : Chander Singh
--%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.myappsecurity.sga.vo.AddressVO, com.myappsecurity.sga.vo.CreditCardVO"%>
<%@ include file="../common/header.jsp"%>
<html>
    <head>  
        <% 
            String action = actionprefix + "/checkout1";
            HashMap errorsMap = new HashMap ();
            Object errorObj = request.getAttribute("ERRORS");
            if (errorObj != null) {
                errorsMap = (HashMap) errorObj;
            }       
            AddressVO shipAddressVO = new AddressVO ();
            Object shipAddressVOObj = request.getAttribute ("SHIPADDR");
            if (shipAddressVOObj != null) {
                shipAddressVO = (AddressVO) shipAddressVOObj;
            }
            AddressVO billAddressVO = new AddressVO ();
            Object billAddressVOObj = request.getAttribute ("BILLADDR");
            if (billAddressVOObj != null) {
                billAddressVO = (AddressVO) billAddressVOObj;
            }

            String shipbillsame = "off";
            Object shipbillsameobj = request.getAttribute ("shipbillsame");
            if (shipbillsameobj != null) {
                shipbillsame = (String) shipbillsameobj;
            }
        %>        
        <script type="text/javascript" src="../js/common.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Check out</title>
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
                                    <b>Shipping Address</b>
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
                                    <label for="sfname">First Name&nbsp;</label><label for="sfirstName" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left" width="200">
                                    <input type="hidden" name="scartId" class="edittextfield"  value="<%=shipAddressVO.getCartId()%>">
                                    <input type="text" name="sfirstName" class="edittextfield"  value="<%=shipAddressVO.getFirstName()%>">
                                     <%
                                        Object sFirstNameErrObj = errorsMap.get("sfirstName");
                                        if (sFirstNameErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)sFirstNameErrObj+"</font>");
                                        }
                                     %>   
                                </td>
                            </tr>
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="slname">Last Name&nbsp;</label><label for="llastName" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="slastName" class="edittextfield" value="<%=shipAddressVO.getLastName()%>">
                                    <%
                                        Object sLastNameErrObj = errorsMap.get("slastName");
                                        if (sLastNameErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)sLastNameErrObj+"</font>");
                                        }
                                     %>   
                                </td>
                            </tr>
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="sstreet1address">Street Address 1&nbsp;</label><label for="saddress1" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="saddress1" class="edittextfield" value="<%=shipAddressVO.getAddress1()%>">
                                    <%
                                        Object sAddress1ErrObj = errorsMap.get("saddress1");
                                        if (sAddress1ErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)sAddress1ErrObj+"</font>");
                                        }
                                     %>       
                                </td>
                            </tr>
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="saddress2">Street Address 2&nbsp;&nbsp;&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="saddress2" class="edittextfield" value="<%=shipAddressVO.getAddress2()%>">
                                    <%
                                        Object sAddress2ErrObj = errorsMap.get("saddress2");
                                        if (sAddress2ErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)sAddress2ErrObj+"</font>");
                                        }
                                     %>       
                                </td>
                            </tr>
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="scity">City&nbsp;</label><label for="scity" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="scity" class="edittextfield" value="<%=shipAddressVO.getCity()%>">
                                    <%
                                        Object sCityErrObj = errorsMap.get("scity");
                                        if (sCityErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)sCityErrObj+"</font>");
                                        }
                                     %>                                               
                                </td>
                            </tr>
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="sstate">State&nbsp;</label><label for="sstate" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="sstate" class="edittextfield" value="<%=shipAddressVO.getState()%>">
                                    <%
                                        Object sStateErrObj = errorsMap.get("sstate");
                                        if (sStateErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)sStateErrObj+"</font>");
                                        }
                                     %>                                               
                                </td>
                            </tr>      
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="scountry">Country&nbsp;</label><label for="scountry" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="scountry" class="edittextfield" value="<%=shipAddressVO.getCountry()%>">
                                    <%
                                        Object sCountryErrObj = errorsMap.get("scountry");
                                        if (sCountryErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)sCountryErrObj+"</font>");
                                        }
                                     %>                                               
                                </td>
                            </tr>
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="szipCode">Zip&nbsp;</label><label for="szipCode" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="szipCode" class="edittextfield" value="<%=shipAddressVO.getZipCode()%>">
                                    <%
                                        Object sZipCodeErrObj = errorsMap.get("szipCode");
                                        if (sZipCodeErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)sZipCodeErrObj+"</font>");
                                        }
                                     %>                                               
                                </td>
                            </tr>   
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="soffPhone">Phone&nbsp;</label><label for="soffPhone" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="soffPhone" class="edittextfield" value="<%=shipAddressVO.getOffPhone()%>">
                                    <%
                                        Object sPhoneErrObj = errorsMap.get("soffPhone");
                                        if (sPhoneErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)sPhoneErrObj+"</font>");
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
                        <table class="headerTable" cellpadding="2" cellspacing="1">
                            <tr>
                                <td align="left">
                                    <%
                                        if ("on".equals (shipbillsame)) {
                                    %>
                                    <input type="checkbox" id="shipbillsame" name="shipbillsame" checked onclick="javascript:handleshipBillAdd()">
                                    <%      
                                        } else {
                                    %>
                                    <input type="checkbox" id="shipbillsame" name="shipbillsame" onclick="javascript:handleshipBillAdd()">
                                    <%      
                                        } 
                                    %>
                                    Billing Address Same as Shipping?                         
                                </td>
                                </tr>
                        </table>
                    </td>
                </tr>
                <tr class="normalTR" id ="billheaderid">
                    <td class="fillerTD">
                        &nbsp;
                    </td>
                    <td align="center">
                        <table class="headerTable" cellpadding="2" cellspacing="1">
                            <tr>
                                <td align="left">
                                    <b>Billing Address</b>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr class="normalTR" id="billcontentid">
                    <td class="fillerTD">
                        &nbsp;
                    </td>
                    <td align="center">
                        <table class="contentTable" cellpadding="2" cellspacing="1">
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="bfirstName">First Name&nbsp;</label><label for="bfirstName" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left" width="200">
                                    <input type="hidden" name="bcartId" class="edittextfield"  value="<%=billAddressVO.getCartId()%>">
                                    <input type="text" name="bfirstName" class="edittextfield" value="<%=billAddressVO.getFirstName()%>">
                                     <%
                                        Object bFirstNameErrObj = errorsMap.get("bfirstName");
                                        if (bFirstNameErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)bFirstNameErrObj+"</font>");
                                        }
                                     %>   
                                </td>
                            </tr>
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="blastName">Last Name&nbsp;</label><label for="blastName" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="blastName" class="edittextfield" value="<%=billAddressVO.getLastName()%>">
                                    <%
                                        Object bLastNameErrObj = errorsMap.get("blastName");
                                        if (bLastNameErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)bLastNameErrObj+"</font>");
                                        }
                                     %>   
                                </td>
                            </tr>   
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="baddress1">Street Address 1&nbsp;</label><label for="baddress1" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="baddress1" class="edittextfield" value="<%=billAddressVO.getAddress1()%>">
                                    <%
                                        Object bAddress1ErrObj = errorsMap.get("baddress1");
                                        if (bAddress1ErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)bAddress1ErrObj+"</font>");
                                        }
                                     %>       
                                </td>
                            </tr>
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="baddress2">Street Address 2&nbsp;&nbsp;&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="baddress2" class="edittextfield" value="<%=billAddressVO.getAddress2()%>">
                                    <%
                                        Object bAddress2ErrObj = errorsMap.get("baddress2");
                                        if (bAddress2ErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)bAddress2ErrObj+"</font>");
                                        }
                                     %>       
                                </td>
                            </tr>
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="bcity">City&nbsp;</label><label for="bcity" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="bcity" class="edittextfield" value="<%=billAddressVO.getCity()%>">
                                    <%
                                        Object bCityErrObj = errorsMap.get("bcity");
                                        if (bCityErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)bCityErrObj+"</font>");
                                        }
                                     %>                                               
                                </td>
                            </tr>
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="bstate">State&nbsp;</label><label for="bstate" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="bstate" class="edittextfield" value="<%=billAddressVO.getState()%>">
                                    <%
                                        Object bStateErrObj = errorsMap.get("bstate");
                                        if (bStateErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)bStateErrObj+"</font>");
                                        }
                                     %>          
                                </td>
                            </tr>   
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="bcountry">Country&nbsp;</label><label for="bcountry" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="bcountry" class="edittextfield" value="<%=billAddressVO.getCountry()%>">
                                    <%
                                        Object bCountryErrObj = errorsMap.get("bcountry");
                                        if (bCountryErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)bCountryErrObj+"</font>");
                                        }
                                     %>                                               
                                </td>
                            </tr>
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="bzipCode">Zip&nbsp;</label><label for="bzipCode" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="bzipCode" class="edittextfield" value="<%=billAddressVO.getZipCode()%>">
                                    <%
                                        Object bZipCodeErrObj = errorsMap.get("bzipCode");
                                        if (bZipCodeErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)bZipCodeErrObj+"</font>");
                                        }
                                     %>                                               
                                </td>
                            </tr>
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="boffPhone">Phone&nbsp;</label><label for="boffPhone" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="boffPhone" class="edittextfield" value="<%=billAddressVO.getOffPhone()%>">
                                    <%
                                        Object bPhoneErrObj = errorsMap.get("boffPhone");
                                        if (bPhoneErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)bPhoneErrObj+"</font>");
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
                                    <input type="submit" value="Check Out" class="btn">
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
<% 
    if ("on".equals (shipbillsame)) {
%>
<script type="text/javascript">
    document.getElementById('shipbillsame').checked = true;
    handleshipBillAdd ();
</script>
<%
    }
%>