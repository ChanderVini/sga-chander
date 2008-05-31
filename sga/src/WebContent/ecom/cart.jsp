<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%-- 
    Document   : shoppingcart
    Created on : Jan 27, 2008, 10:10:05 AM
    Author     : Chander Singh
--%>
<%@ page import="com.myappsecurity.sga.vo.ShoppingCartVO" %>
<%@ page import="com.myappsecurity.sga.vo.CartVO" %>
<%@ include file="../common/header.jsp"%>
<% 
    ShoppingCartVO shoppingCartVO = new ShoppingCartVO();
    Object shoppingCartObj = session.getAttribute ("SHOPPINGCART");
    if (shoppingCartObj != null) {
        shoppingCartVO = (ShoppingCartVO) shoppingCartObj;
    }
%>
<html>
    <head>
        <link href="../css/styles.css" rel="stylesheet" type="text/css"/>        
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Shopping Cart</title>
    </head>
    <body align="center">
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
            if (shoppingCartVO == null || shoppingCartVO.getCartVOs().length < 1) {
        %>
            Your shopping cart is empty.
        <%
            } else {
                String checkoutaction = actionprefix + "/shipdet";
                String continueaction = actionprefix + "/category";
                String updateaction = actionprefix + "/updcart";
                String saveaction = actionprefix + "/savecart";                
                CartVO[] cartVOs = shoppingCartVO.getCartVOs ();
        %>
        <form action="<%=checkoutaction%>" method="post">
        <table class="normalTable" cellpadding="0" cellspacing="4">
            <tr class="normalTR">
                <td class="fillerTD">
                    &nbsp;
                </td>
                <td align="center">
                    <table class="headerTable" cellpadding="2" cellspacing="1">
                        <tr>
                            <td align="left">
                                <b>Shopping Cart</b>
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
                                <th class="dataGridHeader">
                                    #
                                </th>                                
                                <th class="dataGridHeader" align="left">
                                    Product Name
                                </th>
                                <th class="dataGridHeader" align="left">
                                    Price ($)
                                </th>
                                <th class="dataGridHeader" align="left">
                                    Quantity
                                </th>
                                <th class="dataGridHeader" align="left">
                                    Amount ($)
                                </th>
                            </tr>
                            <%
                                double totamt = 0;
                                for (int cnt = 0; cnt < cartVOs.length; cnt++) {
                                    totamt += cartVOs[cnt].getProductPrice () * cartVOs[cnt].getQuantity ();
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
                                <td class="dataGridHeader" align="center">
                                    <%=new Integer(cnt + 1)%>
                                </td>
                                <td>
                                    <input type="hidden" name="productId" value="<%=new Long (cartVOs[cnt].getProductId())%>">
                                    <%=cartVOs[cnt].getProductName ()%>
                                </td>
                                <td>
                                    <%=new Double (cartVOs[cnt].getProductPrice ())%>
                                </td>
                                <td>
                                    <%int qty = cartVOs[cnt].getQuantity ();%>
                                    <select name="qty" class="edittextfield">
                                        <%
                                            for (int prodcnt = 0; prodcnt <= 10; prodcnt++) {
                                                if (qty == prodcnt) {
                                        %>
                                                <option value="<%=new Integer (prodcnt)%>" selected><%=new Integer (prodcnt)%>
                                        <%
                                                } else {
                                        %>
                                                <option value="<%=new Integer (prodcnt)%>" ><%=new Integer (prodcnt)%>
                                        <%
                                                }
                                           }
                                        %>
                                    </select>                        
                                </td>
                                <td>
                                    <%=new Double (cartVOs[cnt].getProductPrice () * cartVOs[cnt].getQuantity ())%>
                                </td>
                            </tr>
                            <%
                                }
                            %>
                            <tr class="rowDark">
                                <td colspan="4" align="right">
                                    <b>Total:</b>
                                </td>
                                <td>
                                    <%=new Double (totamt)%>
                                </td>
                            </tr>
                            <tr class="rowDark">
                                <td colspan="5" align="center">
                                    <input type="submit" name="checkout" value="Checkout" onclick="document.forms[0].action='<%=checkoutaction%>'" class="btn">
                                    <input type="submit" name="update" value="Update" onclick="document.forms[0].action='<%=updateaction%>'" class="btn">
                                    <input type="submit" name="continue" value="Continue" onclick="document.forms[0].action='<%=continueaction%>'" class="btn">
                                    <input type="submit" name="save" value="Save" onclick="document.forms[0].action='<%=saveaction%>'" class="btn">
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </form>
        <%
            }
        %>
    </body>
</html>