<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>登陆</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<link href="${ctx}/static/styles/login.css" type="text/css" rel="stylesheet" />
	
  </head>
<body>
<div class="top"></div>
<div class="container">
  <h1 class="welcome"> Welcome to TNT Express PT System </h1>
  <div class="content">
    <div class="left"></div>
    <div class="right">
      <form action="${ctx}/login/loginin" method="post">
        <table width="510" >
       
          <tr>
            <td width="150px">&nbsp;</td>
            <td width="80px"><label>username:</label></td>
            <td ><input type="text" style="height:27px;" name="userName" value="${model.userName}"/></td>
            <td>&nbsp;</td>
            
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td><label>password:</label></td>
            <td><input type="password" style="height:27px;" name="pwd"/></td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td colspan="2" ><input type="submit" id="sub" value="submit"/>
              &nbsp;&nbsp;
              <input type="reset" id="reset"  value="reset"/></td>
            <td>&nbsp;</td>
          </tr>
           <%
		String error = "";
        if(request.getAttribute("error")!=null){
        	error = (String)request.getAttribute("error");
        }
		if(error != null && !error.equals("")){
		%>
		<tr height="20">
			<td width="150px" height="20">&nbsp;</td>
            <td colspan="3"><span style="color: red;font-size:14;"><%=error %></span></td>
         </tr>
		<%
		}
		%>
        </table>
      </form>
    </div>
  </div>
  <div class="clearboth"></div>
  <div class="bottom">
    <h4><span></span></h4>
    <div class="copyright"> Intellectual and other property rights to the information contained in this site are held by TNT Holdings B.V. with all rights reserved Â© 2011 </div>
  </div>
</div>
</body>
</html>
