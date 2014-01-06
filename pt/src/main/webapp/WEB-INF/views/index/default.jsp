<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title>个性化报价系统</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" /><%--

<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/styles/main.css" type="text/css" rel="stylesheet" />

<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js" type="text/javascript"></script>
<script src="${ctx}/static/js/fun.js" type="text/javascript" ></script>
<!--[if lt IE 9]>
<script type="text/javascript" src="${ctx}/static/js/ie9.js"></script>
<![endif]-->


--%></head>
<frameset rows="72,*,30" cols="*" frameborder="no" border="0" framespacing="0" >
  <frame src="${ctx}/login/header" name="topFrame" scrolling="no">
  <frameset cols="220,*" name="middleFrame" frameborder="NO" border="0" framespacing="0">
    <frame src="${ctx}/login/left" noresize name="menu" scrolling="NO">
     <frameset rows="25,*" cols="*" name="contentFrame" frameborder="NO" border="0" framespacing="0">
        <frame src="${ctx}/login/breadcrumbs" noresize name="breadcrumbs" scrolling="NO">
        <frame src="${ctx}/login/main" noresize name="main" scrolling="yes">
      </frameset>
  </frameset>
  <frame src="${ctx}/login/footer" name="bottomFrame" scrolling="no">
</frameset>
<noframes>
	<body>您的浏览器不支持框架！</body>
</noframes>
</html>