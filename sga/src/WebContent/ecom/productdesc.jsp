<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%-- 
    Document   : productdesc
    Created on : Jan 23, 2008, 7:34:03 AM
    Author     : Chander Singh
--%>
<%@ page import="java.io.File" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.myappsecurity.sga.util.Constants, com.myappsecurity.sga.vo.ProductVO, com.myappsecurity.sga.vo.ReviewVO" %>
<%@ include file="../common/header.jsp"%>
<% 
    HashMap errorsMap = new HashMap ();
    Object errorObj = request.getAttribute("ERRORS");
    if (errorObj != null) {
        errorsMap = (HashMap) errorObj;
    }       
    ProductVO productVO =  (ProductVO)request.getAttribute ("PRODUCT");
    int qty = 1;
    Object qtyObj = request.getAttribute ("QTY");
    if (qtyObj != null) {
        qty = ((Integer)qtyObj).intValue();
    }
    ReviewVO reviewVO = new ReviewVO ();
    Object reviewObj  = request.getAttribute ("REVIEW");
    if (reviewObj != null) {
        reviewVO = (ReviewVO) reviewObj;
    }
    ReviewVO[] reviewVOs = new ReviewVO [0];
    Object reviewsObj  = request.getAttribute ("REVIEWS");
    if (reviewsObj != null) {
        reviewVOs = (ReviewVO[]) reviewsObj;
    }
    String action = actionprefix + "/addcart";
    String addreviewaction = actionprefix + "/addreview";
    String addwishaction = actionprefix + "/wishlist";
%>
<html>
    <head>
        <link href="../css/styles.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../js/common.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Product Description</title>
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
                                <b>Product Description</b>
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
                    <form action="<%=action%>" method="POST">        
                        <table class="contentTable" cellpadding="2" cellspacing="1">
                            <tr class="rowLight">
                                <td>
                                    <img src="../<%=productVO.getUploaddir().concat(productVO.getProductImg())%>" width="150" height="150">
                                    <input type="hidden" name="productId" value="<%=new Long (productVO.getProductId())%>">
                                </td>
                                <td colspan="2" rowspan="2" valign="top">
                                    <b><%=productVO.getProductName()%></b><br>
                                    <%=productVO.getDescription()%>
                                </td>
                            </tr>
                            <tr class="rowLight">
                                <td>
                                    <b>Price:</b> $<%=new Double (productVO.getProductPrice())%>
                                </td>                    
                            </tr>
                            <tr class="rowLight">
                                <td colspan="3" align="center">
                                    <b>Quantity: </b>
                                    <select name="qty" class="edittextfield">
                                        <%
                                            for (int cnt = 1; cnt <= 10; cnt++) {
                                                if (qty == cnt) {
                                        %>
                                                <option value="<%=new Integer (cnt)%>" selected><%=new Integer (cnt)%>
                                        <%
                                                } else {
                                        %>
                                                <option value="<%=new Integer (cnt)%>" ><%=new Integer (cnt)%>
                                        <%
                                                }
                                           }
                                        %>
                                    </select>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <input class="btn" type="submit" name="submit" value="Add to cart">
                                    <input class="btn" type="submit" name="addwishlist" value="Add to wishlist" onclick="document.forms[0].action='<%=addwishaction%>'">
                                </td>
                            </tr>
                        </table>
                    </form>
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
                                    if (reviewVOs == null || reviewVOs.length < 1) {
                                %>
                                No reviews available for this product
                                <%
                                    } else {
                                %>
                                <a href="javascript:viewreviews()">View reviews</a>                    
                                <%
                                    }
                                %>
                            </td>
                        </tr>
                    </table>                      
                </td>
            </tr>
            <tr class="normalTR" id="viewreviews" style="display:none">
                <td class="fillerTD">
                    &nbsp;
                </td>
                <td align="center">
                    <table class="contentTable" cellpadding="2" cellspacing="1">
                        <%
                            for (int cnt = 0; cnt < reviewVOs.length; cnt++) {
                                if (cnt % 2 == 0) {
                        %>
                        <tr class="dataGridRowLight">
                        <%
                                } else {
                        %>
                        <tr class="dataGridRowDark">
                        <%
                                }
                        %>
                            <td align="left">
                                <label for="username">User name</label>: <%=reviewVOs[cnt].getUsername()%><br>
                                <label for="rating">Rating</label>: <%=reviewVOs[cnt].getRating ()%><br>
                                <label for="comments">Comments</label>: <%=reviewVOs[cnt].getMessage ()%>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </table>
                </td>
            </tr>
            <%
                if (loggedin) {
                    UserVO userVO = (UserVO)session.getAttribute("USER");
            %>
            <tr class="normalTR">
                <td class="fillerTD">
                    &nbsp;
                </td>
                <td align="center">
                    <table class="headerTable" cellpadding="2" cellspacing="1">
                        <tr>
                            <td align="left">
                                <a href="javascript:addreview()">Add review</a>                    
                            </td>
                        </tr>
                    </table>                      
                </td>
            </tr>
            <tr class="normalTR" id="addreview" style="display:none">
                <td class="fillerTD">
                    &nbsp;
                </td>
                <td align="center">
                    <form action="<%=addreviewaction%>" method="POST">
                        <table class="contentTable" cellpadding="2" cellspacing="1" width="100%">
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="username">User Name&nbsp;</label><label for="username" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="hidden" name="productId" value="<%=String.valueOf (productVO.getProductId())%>">
                                    <input type="text" name="username" class="edittextfield" readonly value="<%=userVO.getUserName()%>">
                                </td>
                            </tr>
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="rating">Rating&nbsp;</label><label for="rating" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <select name="rating" class="edittextfield">
                                        <%
                                            if ("1".equals (reviewVO.getRating ())) {
                                        %>
                                        <option value="1" selected>1 - Poor
                                        <%
                                            } else {
                                        %>
                                        <option value="1">1 - Poor
                                        <%
                                            }
                                            if ("2".equals (reviewVO.getRating ())) {
                                        %>
                                        <option value="2" selected>2 - Fair
                                        <%
                                            } else {    
                                        %>
                                        <option value="2">2 - Fair
                                        <%
                                            }                                                        
                                            if ("3".equals (reviewVO.getRating ())) {
                                        %>
                                       <option value="3" selected>3 - Good
                                       <%
                                            } else {
                                       %>
                                       <option value="3">3 - Good
                                       <%
                                            } 
                                            if ("4".equals (reviewVO.getRating ())) {
                                        %>    
                                        <option value="4" selected>4 - Very Good
                                        <%
                                            } else {
                                        %>
                                        <option value="4">4 - Very Good
                                        <%
                                            }
                                            if ("5".equals (reviewVO.getRating ())) {
                                        %>
                                        <option value="5" selected>5 - Excellent
                                        <%
                                            } else {
                                        %>
                                        <option value="5">5 - Excellent
                                        <%
                                            }
                                        %>
                                    </select>
                                </td>
                            </tr>
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="message">Message&nbsp;</label><label for="message" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left" valign="middle">
                                    <textarea name="message" class="edittextfield" rows="15" cols="30"><%=reviewVO.getMessage()%></textarea>
                                     <%
                                        Object messageErrObj = errorsMap.get("message");
                                        if (messageErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)messageErrObj+"</font>");
                                        }
                                     %>   
                                </td>
                            </tr>
                            <tr class="normalTR">
                                <td class="dataGridRowLight" align="center" colspan="2">
                                    <input type="submit" value="Submit" class="btn">
                                </td>
                            </tr>
                        </table>
                    </form>
                </td>
            </tr>
            <%
                }
            %>
        </table>        
    </body>
</html>
<%
    if (errorsMap.get ("username") != null || errorsMap.get ("message") != null) {
%>     
<script>addreview();</script>
<%
    }
%>