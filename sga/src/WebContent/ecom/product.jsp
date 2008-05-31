<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%-- 
    Document   : product
    Created on : Jan 23, 2008, 7:27:32 AM
    Author     : Chander Singh
--%>
<%@ page import="com.myappsecurity.sga.vo.ProductVO" %>
<%@ include file="../common/header.jsp"%>
<% 
    ProductVO[] productVOs =  (ProductVO[])session.getAttribute ("PRODUCTS");
    String action = actionprefix + "/productdesc?productId=";    
    String updateaction = actionprefix + "/updateproduct?productId=";    
%>
<html>
    <head>
        <link href="../css/styles.css" rel="stylesheet" type="text/css" />        
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Products List</title>
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
                                <b>Products List</b>
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
                            String searchinput = null;
                            Object searchinputObj = request.getAttribute("SEARCH");
                            if (searchinputObj != null && (productVOs == null || productVOs.length < 1)) {
                        %>
                        <tr>
                            <td align="left">
                                No result found for your query "<%=(String)searchinputObj%>
                            </td>
                        </tr>
                        <%
                            } else {
                                if (searchinputObj != null && (productVOs != null && productVOs.length > 0)) {
                                    
                        %>
                        <tr>
                            <td align="left">
                                Result for your query <b>"<%=(String) searchinputObj%>"</b>:
                            </td>
                        </tr>
                        <%
                                }
                        %>
                    </table>
                    <table class="contentTable" cellpadding="2" cellspacing="1">
                        <thead>
                            <tr>
                                <th class="dataGridHeader" align="center">
                                    #
                                </th>
                                <th class="dataGridHeader" align="left">
                                    Category Name
                                </th>  
                                <th class="dataGridHeader" align="left">
                                    Product Name
                                </th>  
                                <th class="dataGridHeader" align="left">
                                    Operation
                                </th>  
                            </tr>
                        </thead>
                                <%
                                    for (int cnt = 0; cnt < productVOs.length; cnt++) {
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
                                <td align="center" class="dataGridHeader"><%=new Integer (cnt+1)%></td>
                                <td align="left"><%=productVOs[cnt].getCategoryName()%></td>
                                <td align="left">
                                    <a href="<%=action + productVOs[cnt].getProductId()%>"><%=productVOs[cnt].getProductName()%></a>
                                </td>
                                <td align="left">
                                    <a href="<%=updateaction + productVOs[cnt].getProductId()%>">Update</a>
                                </td>
                            </tr>
                        <%
                                }
                            }
                        %>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>

