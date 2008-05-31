<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%-- 
    Document   : category.jsp
    Created on : Jan 22, 2008, 6:41:39 PM
    Author     : Chander Singh
--%>
<%@ page import="com.myappsecurity.sga.vo.CategoryVO, com.myappsecurity.sga.vo.ShoppingCartVO" %>
<%@ include file="../common/header.jsp"%>
<% 
    CategoryVO[] categoryVOs =  new CategoryVO[0];
    Object categoryVOObj = session.getAttribute ("CATEGORIES");
    if (categoryVOObj != null) {
        categoryVOs = (CategoryVO[]) categoryVOObj;
    }
%>
<html>
    <head>
        <link href="../css/styles.css" rel="stylesheet" type="text/css" />
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Catgories List</title>
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
        <table class="normalTable" cellpadding="0" cellspacing="4">
            <tr class="normalTR">
                <td class="fillerTD">
                    &nbsp;
                </td>
                <td align="center">
                    <table class="headerTable" cellpadding="2" cellspacing="1">
                        <tr>
                            <td align="left">
                                <b>Categories List</b>
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
                                <th class="dataGridHeader" align="center">
                                    #
                                </th>
                                <th class="dataGridHeader" align="left">
                                    Name
                                </th>  
                                <th class="dataGridHeader" align="left">
                                    Description
                                </th>   
                                <th class="dataGridHeader" align="left">
                                    Operation
                                </th>                                   
                            </tr>
                            <% 
                                for (int cnt = 0; cnt < categoryVOs.length; cnt++) {
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
                                <td class="dataGridHeader" align="center"><%=new Integer (cnt+1)%></td>
                                <td align="left">
                                    <%String action = actionprefix + "/product?categoryId=";%>
                                    <a href="<%=action + categoryVOs[cnt].getCategoryId()%>"><%=categoryVOs[cnt].getCategoryName()%></a>
                                </td>
                                <td align="left">
                                    <%=categoryVOs[cnt].getDescription()%>
                                </td>                   
                                <td align="left">
                                    <%String addaction = actionprefix + "/addproduct?categoryId=";%>
                                    <a href="<%=addaction + categoryVOs[cnt].getCategoryId()%>">Add Product</a>
                                </td>                                                   
                            </tr>
                        <%}%>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>
