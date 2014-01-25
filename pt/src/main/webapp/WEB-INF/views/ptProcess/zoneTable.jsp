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
<!-- <script type="text/javascript" src="${ctx}/static/datePicker/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/datePicker/js/jquery.datePicker-min.js"></script>
<link type="text/css" href="${ctx}/static/datePicker/css/datepicker.css" rel="stylesheet" /> -->
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
<form action="${ctx}/ptQuery/getZoneTable" method="post" id="ptQueryForm">

<div style="padding:5px;">
  <h4 class="title">Zone Table</h4>
<table class="table_A">
        <thead>
             <tr>
		      <td style="text-align:center;">Zone Type</td>
                <td>
                <select name="id" value="${id}">
                <c:forEach items="${zoneTypeList}" var="zoneType" begin="0">
						<option value="${zoneType.id}" <c:if test="${zoneType.id==id}">selected</c:if> >${zoneType.zoneType}</option>
				</c:forEach>
				</select>
                </td>
                <td><input type="submit" value="query" class="cls-button" /> </td>
		     </tr>
        </thead>
    </table>
    
</div>
<div>
<div style="padding:0 5px 5px 5px;">
<h4 style="float:left">Zone Table List</h4>
<div class="clearboth"></div>
<table class="table_B" width="100%">
        <thead>
			<tr>
                <th>Zone</th>
                <th>Country Code</th>
                <th>Country Name</th>
                <th>Depot</th>
                <th>48N Availability</th>
                <th>Default Country</th>
            </tr>
        </thead>
        <tbody>
             <c:forEach items="${zoneTableList}" var="zoneTable">
				<tr>
					<td>${zoneTable.zone}</td>
					<td>${zoneTable.countryCode}</td>
					<td>${zoneTable.countryName}</td>
					<td>${zoneTable.depot}</td>
					<c:choose>
					    <c:when test="${zoneTable.countryCode==null or zoneTable.countryCode==''}">
					      <td></td>
					      <td></td>
					    </c:when>
					   <c:otherwise>  
					      <td>Y</td>
					      <td>Y</td>
					   </c:otherwise>
					</c:choose>
			   </tr>
			</c:forEach>
        </tbody>
    </table>
    <%@ include file="/WEB-INF/tags/navigate.jsp"%>
  <br>    
</form>
</div>
<script type="text/javascript">
    $(function(){
        $("#query").click(function(){
         $("#ptQueryForm").submit();
        });
       
    });

	function showDetail(val){
		document.forms[0].action="${ctx}/ptQuery/tariffPT?id="+val;
		document.forms[0].submit();
	}
     
</script>
</body>
</html>