<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>内容页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<link href="${ctx}/static/styles/main.css" type="text/css" rel="stylesheet" />
	
  </head>
  <body>
<div class="north">
    <div>
    	<h1 id="logo" style="text-indent:-9999px;text-align:center; ">PT - TNT Personalized Tariff System</h1>
        <div id="login_bar">Hello，Welcome<strong>Adam</strong>Logon<a href="#" onclick="window.parent.location.href='${ctx}/login/logout'">Log out</a></div>
    </div>
</div><!--end layout north-->
</body>