<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%-- 
    Document   : addproduct
    Created on : Mar 23, 2008, 8:02:37 PM
    Author     : Chander Singh
--%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.myappsecurity.sga.vo.ProductVO"%>
<%@ include file="../common/header.jsp"%>
<html>
    <head>
        <% 
            String action = actionprefix + "/productsub";
            HashMap errorsMap = new HashMap ();
            Object errorObj = request.getAttribute("ERRORS");
            if (errorObj != null) {
                errorsMap = (HashMap) errorObj;
            }               
            ProductVO productVO =  new ProductVO();
            Object productVOObj = request.getAttribute ("PRODUCT");
            if (productVOObj != null) {
                productVO = (ProductVO) productVOObj;
            }
            
            
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Add Product Page</title>
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
        <form enctype="multipart/form-data" action="<%=action%>" method="post">                
            <table class="normalTable" cellpadding="0" cellspacing="4">
                <tr class="normalTR">
                    <td class="fillerTD">
                        &nbsp;
                    </td>
                    <td align="center">
                        <table class="headerTable" cellpadding="2" cellspacing="1">
                            <tr>
                                <td align="left">
                                    <b>Product Details</b>
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
                                    <label for="productName">Product Name&nbsp;</label><label for="productName" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="hidden" name="categoryId" value="<%=String.valueOf (productVO.getCategoryId())%>">
                                    <input type="text" name="productName" class="edittextfield"  value="<%=productVO.getProductName()%>">
                                     <%
                                        Object productNameErrObj = errorsMap.get("productName");
                                        if (productNameErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)productNameErrObj+"</font>");
                                        }
                                     %>   
                                </td>
                            </tr>
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="productPrice">Price&nbsp;</label><label for="productPrice" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="productPrice" class="edittextfield" value="<%=String.valueOf(productVO.getProductPrice())%>">
                                     <%
                                        Object productPriceErrObj = errorsMap.get("productPrice");
                                        if (productPriceErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)productPriceErrObj+"</font>");
                                        }
                                     %>   
                                </td>
                            </tr>
                            <tr class="dataGridRowLight">
                                <td align="right">
                                    <label for="description">Description&nbsp;</label><label for="description" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="text" name="description" class="edittextfield"  value="<%=productVO.getDescription()%>">
                                     <%
                                        Object descriptionErrObj = errorsMap.get("description");
                                        if (descriptionErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)descriptionErrObj+"</font>");
                                        }
                                     %>   
                                </td>
                            </tr>
                            <tr class="dataGridRowDark">
                                <td align="right">
                                    <label for="productImg">Image&nbsp;</label><label for="productImg" class="mandatory">*&nbsp;</label>
                                </td>
                                <td align="left">
                                    <input type="file" name="productImg" class="edittextfield">
                                     <%
                                        Object productImgErrObj = errorsMap.get("productImg");
                                        if (productImgErrObj != null) {
                                            out.println ("<font color=\"red\">"+(String)productImgErrObj+"</font>");
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
                                    <input type="submit" value="Add" class="btn">
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
