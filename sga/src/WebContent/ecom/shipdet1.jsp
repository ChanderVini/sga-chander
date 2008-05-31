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
            String action = actionprefix + "/checkout";
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
            CreditCardVO creditCardVO = new CreditCardVO ();
            Object creditCardVOObj = request.getAttribute ("CREDITCARD");
            if (creditCardVOObj != null) {
                creditCardVO = (CreditCardVO) creditCardVOObj;
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
                 <input type="hidden" name="scartId" class="edittextfield"  value="<%=shipAddressVO.getCartId()%>">
                <input type="hidden" name="sfirstName" class="edittextfield"  value="<%=shipAddressVO.getFirstName()%>">
                <input type="hidden" name="slastName" class="edittextfield" value="<%=shipAddressVO.getLastName()%>">
                <input type="hidden" name="saddress1" class="edittextfield" value="<%=shipAddressVO.getAddress1()%>">
                <input type="hidden" name="saddress2" class="edittextfield" value="<%=shipAddressVO.getAddress2()%>">
                <input type="hidden" name="scity" class="edittextfield" value="<%=shipAddressVO.getCity()%>">
                <input type="hidden" name="sstate" class="edittextfield" value="<%=shipAddressVO.getState()%>">
                <input type="hidden" name="scountry" class="edittextfield" value="<%=shipAddressVO.getCountry()%>">
                <input type="hidden" name="szipCode" class="edittextfield" value="<%=shipAddressVO.getZipCode()%>">
                <input type="hidden" name="soffPhone" class="edittextfield" value="<%=shipAddressVO.getOffPhone()%>">
                <input type="hidden" name="bcartId" class="edittextfield" value="<%=shipAddressVO.getCartId()%>">
                <input type="hidden" name="bfirstName" class="edittextfield" value="<%=billAddressVO.getFirstName()%>">
                <input type="hidden" name="blastName" class="edittextfield" value="<%=billAddressVO.getLastName()%>">
                <input type="hidden" name="baddress1" class="edittextfield" value="<%=billAddressVO.getAddress1()%>">
                <input type="hidden" name="baddress2" class="edittextfield" value="<%=billAddressVO.getAddress2()%>">
                <input type="hidden" name="bcity" class="edittextfield" value="<%=billAddressVO.getCity()%>">
                <input type="hidden" name="bstate" class="edittextfield" value="<%=billAddressVO.getState()%>">
                <input type="hidden" name="bcountry" class="edittextfield" value="<%=billAddressVO.getCountry()%>">
                <input type="hidden" name="bzipCode" class="edittextfield" value="<%=billAddressVO.getZipCode()%>">
                <input type="hidden" name="boffPhone" class="edittextfield" value="<%=billAddressVO.getOffPhone()%>">
                <tr class="normalTR">
                    <td class="fillerTD">
                        &nbsp;
                    </td>
                    <td align="center">
                        <table class="headerTable" cellpadding="2" cellspacing="1">
                            <tr>
                                <td align="left">
                                    <b>Payment Details</b>
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
                                    <label for="ccardtype">Credit Card Type&nbsp;</label><label for="ccardtype" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left"  width="200">
                                    <select id="ccardtype" name="ccardtype" class="edittextfield">
                                        <%
                                            if ("Visa".equals (creditCardVO.getCcType())) {
                                        %>
                                        <option value="Visa" selected>Visa    
                                        <%
                                            } else {
                                        %>
                                        <option value="Visa">Visa
                                        <%
                                            }
                                            if ("MasterCard".equals (creditCardVO.getCcType())) {
                                        %>
                                        <option value="MasterCard" selected>Master Card
                                        <%
                                            } else {
                                        %>
                                        <option value="MasterCard">Master Card
                                        <%
                                            }
                                            if ("DinersClub".equals (creditCardVO.getCcType())) {
                                        %>
                                        <option value="DinersClub" selected>Diners Club
                                        <%
                                            } else {
                                        %>
                                        <option value="DinersClub">Diners Club
                                        <%
                                            }
                                            if ("CarteBlanche".equals (creditCardVO.getCcType())) {
                                        %>
                                        <option value="CarteBlanche" selected>Carte Blanche
                                        <%
                                            } else {
                                        %>
                                        <option value="CarteBlanche">Carte Blanche
                                        <%
                                            }
                                            if ("AmEx".equals (creditCardVO.getCcType())) {
                                        %>
                                        <option value="AmEx" selected>AmEx
                                        <%
                                            } else {
                                        %>
                                        <option value="AmEx">AmEx
                                        <%
                                            }
                                            if ("Discover".equals (creditCardVO.getCcType())) {
                                        %>
                                        <option value="Discover" selected>Discover
                                        <%
                                            } else {
                                        %>
                                        <option value="Discover">Discover
                                        <%
                                            }
                                            if ("JCB".equals (creditCardVO.getCcType())) {
                                        %>
                                        <option value="JCB" selected>JCB
                                        <%
                                            } else {
                                        %>
                                        <option value="JCB">JCB
                                        <%
                                            }
                                            if ("Enroute".equals (creditCardVO.getCcType())) {
                                        %>
                                        <option value="Enroute" selected>Enroute
                                        <%
                                            } else {
                                        %>
                                        <option value="Enroute">Enroute
                                        <%
                                            }
                                        %>
                                    </select>
                                    <%
                                        Object cCardTypeErrObj = errorsMap.get("ccardtype");
                                        if (cCardTypeErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)cCardTypeErrObj+"</font>");
                                        }
                                     %>                                                                                   
                                </td>
                            </tr>
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="ccardnbr">Credit Card Nbr&nbsp;</label><label for="ccardnbr" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="ccardnbr" class="edittextfield" value="<%=creditCardVO.getCcNbr()%>">
                                    <%
                                        Object cCardNbrErrObj = errorsMap.get("ccardnbr");
                                        if (cCardNbrErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)cCardNbrErrObj+"</font>");
                                        }
                                     %>                                        
                                </td>
                            </tr>
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="cvvnbr">CVV Nbr&nbsp;</label><label for="cvvnbr" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="cvvnbr" class="edittextfield" value="<%=creditCardVO.getCvvNbr()%>">
                                    <%
                                        Object cvvNbrErrObj = errorsMap.get("cvvnbr");
                                        if (cvvNbrErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)cvvNbrErrObj+"</font>");
                                        }
                                     %>            
                                </td>
                            </tr>
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="ccardexpmon">Expiry Month&nbsp;</label><label for="ccardexpmon" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <select name="ccardexpmon" class="edittextfield">
                                        <%
                                            if ("1".equals (creditCardVO.getExpMon())) {
                                        %>
                                        <option value="1" selected>January
                                        <%
                                            } else {
                                        %>
                                        <option value="1">January
                                        <%
                                            }
                                            if ("2".equals (creditCardVO.getExpMon())) {
                                       %>
                                        <option value="2" selected>February
                                        <%
                                            } else {
                                        %>                                   
                                        <option value="2">February
                                        <%
                                            }
                                            if ("3".equals (creditCardVO.getExpMon())) {
                                       %>
                                        <option value="3" selected>March
                                        <%
                                            } else {
                                        %>                                        
                                        <option value="3">March
                                        <%
                                            }
                                            if ("4".equals (creditCardVO.getExpMon())) {
                                       %>                                        
                                        <option value="4" selected>April
                                        <%
                                            } else {
                                        %>
                                        <option value="4">April
                                        <%
                                            }
                                            if ("5".equals (creditCardVO.getExpMon())) {
                                       %>                                        
                                        <option value="5" selected>May
                                        <%
                                            } else {
                                        %>                                        
                                        <option value="5">May
                                        <%
                                            }
                                            if ("6".equals (creditCardVO.getExpMon())) {
                                       %>                                        
                                       <option value="6" selected>June
                                        <%
                                            } else {
                                        %>
                                        <option value="6">June
                                        <%
                                            }
                                            if ("7".equals (creditCardVO.getExpMon())) {
                                       %>                                        
                                        <option value="7" selected>July
                                        <%
                                            } else {
                                        %>
                                        <option value="7">July
                                        <%
                                            }
                                            if ("8".equals (creditCardVO.getExpMon())) {
                                       %>                                        
                                        <option value="8" selected>August
                                        <%
                                            } else {
                                        %>
                                        <option value="8">August
                                        <%
                                            }
                                            if ("9".equals (creditCardVO.getExpMon())) {
                                       %>                                        
                                        <option value="9" selected>September
                                        <%
                                            } else {
                                        %>
                                        <option value="9">September
                                        <%
                                            }
                                            if ("10".equals (creditCardVO.getExpMon())) {
                                       %>                                        
                                       <option value="10" selected>October
                                        <%
                                            } else {
                                        %>
                                        <option value="10">October
                                        <%
                                            }
                                            if ("11".equals (creditCardVO.getExpMon())) {
                                       %>                                        
                                        <option value="11" selected>November
                                        <%
                                            } else {
                                        %>
                                        <option value="11">November
                                        <%
                                            }
                                            if ("12".equals (creditCardVO.getExpMon())) {
                                       %>                                        
                                       <option value="12" selected>December
                                        <%
                                            } else {
                                        %>
                                        <option value="12">December
                                        <%
                                            }
                                       %>                                        
                                    </select>
                                    <%
                                        Object expMonErrObj = errorsMap.get("ccardexpmon");
                                        if (expMonErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)expMonErrObj+"</font>");
                                        }
                                     %>                                              
                                </td>
                            </tr>
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="ccardexpyear">Expiry Year&nbsp;</label><label for="ccardexpyear" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <%
                                        int curryear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
                                    %>
                                    <select name="ccardexpyear" class="edittextfield">
                                        <%
                                            for (int cnt = 0; cnt < 10; cnt++) {
                                                int year = curryear + cnt;
                                                if (String.valueOf (year).equals (creditCardVO.getExpYear())) { 
                                        %>
                                        <option value="<%=new Integer (year)%>" selected><%=new Integer (year)%>
                                        <%
                                                } else {
                                        %>
                                        <option value="<%=new Integer (year)%>"><%=new Integer (year)%>
                                        <%
                                                }
                                            }
                                        %>
                                    </select>
                                    <%
                                        Object expYearErrObj = errorsMap.get("ccardexpyear");
                                        if (expYearErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)expYearErrObj+"</font>");
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