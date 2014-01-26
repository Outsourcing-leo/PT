<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>PT Query</title>
<link href="${ctx}/static/styles/main.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/js/calendar.js"></script>
</head>
<body>
<script type="text/javascript">
$(document).ready(function(){

	/*$(".datepicker").datePicker({
		inline:true,
		selectMultiple:false
	});
	
	$("#datepicker").datePicker({
		clickInput:true
	});*/
	
});
</script>
<form action="${ctx}/login/changeConfirm" method="post" id="confirmForm">

<div style="padding:5px;">
  <h4 class="title">Role List</h4>
</div>
<br/>
<div style="padding:0 5px 5px 5px;">
<div class="clearboth"></div>
	<div style="height:200px;width:96%;overflow:auto;">
	<table class="table_B" width="100%">
        <thead>
			<tr>
                <th>Selected</th>
                <th>Role Name</th>
            </tr>
        </thead>
        <tbody>
             <c:forEach items="${roleList}" var="role">
				<tr>
					<td><input type="radio" name="radio" id="role" value="${role}"/></td>
					<td>${role}</td>
			   </tr>
			</c:forEach>
        </tbody>
    </table>
    </div>
    <br>  
    <div style="text-align:center;">
  			<input type="button" id="Confirm" value="Confirm"  class="cls-button" "/> 
   			&nbsp;&nbsp;&nbsp;<input type="button" value="Close" id="Close" class="cls-button" onclick="window.close();"/>
   </div>
 </div>
</form>
<script type="text/javascript">
    $(function(){
        $("#Confirm").click(function(){
        	var checkValue = $('input[name=radio]:checked').val();
         	window.parent.location.href='${ctx}/login/changeConfirm?roleName='+checkValue;
         	window.close();
        });
    });

</script>
</body>
</html>