<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%-- 
    Document   : header
    Created on : Feb 18, 2008, 7:37:47 PM
    Author     : Chander Singh
--%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%
    String apptype = "";
    String appname = "";
    Object apptypeobj =  session.getAttribute("APPTYPE");
    Object appnameobj = session.getAttribute("APPNAME");
    if (apptypeobj != null) {
        apptype = (String) apptypeobj;
    }
    if (appnameobj != null) {
        appname = (String) appnameobj;
    }
    String actionprefix = "../" + apptype.toLowerCase() + "-" + appname.toLowerCase();
%>
<head>
    <link href="../css/styles.css" rel="stylesheet" type="text/css"/>              
</head>