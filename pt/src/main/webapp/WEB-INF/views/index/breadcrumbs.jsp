<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>导航</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 

	<link href="${ctx}/static/styles/main.css" type="text/css" rel="stylesheet" />

  </head>
  
  <body>
  <div class="north_inner">
  </div>
    <div class="breadcrumbs" >
	    <div class="breadcrumbs_inner">
	    	<ul class="menu_breadcrumbs">
	        	<li><a href="index.html">首页</a></li>
	            <li></li>
	            <li></li>
	        </ul>
	    </div>
	</div><!--end layout east-->
  </body>
</html>