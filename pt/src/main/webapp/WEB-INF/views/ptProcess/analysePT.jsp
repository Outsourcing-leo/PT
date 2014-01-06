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
</head>
<body>
<form action="${ctx}/ptQuery/ptApprove" method="post" id="ptQueryForm">
	
<div>
<div style="padding:0 5px 5px 5px;">
<h4 class="title">Analyse a PT</h4>
<br/>
<h4 style="float:left">PT List</h4>
<div class="clearboth"></div>
<table class="table_B" width="100%">
        <thead>
        <tr>
			<th colspan="4">Commercial PT Pool </th>
			</tr>
			<tr>
                <th>Depot</th>
                <th>New PTs</th>
                <th>Total PTs</th>
            </tr>
        </thead>
        <tbody>
             <c:forEach items="${loadList}" var="loads">
				<tr>
					<td>${loads.depotcode}</td>
					<td><a id="newsNum" href="#" onclick="showNewNum('${loads.depotcode}')">${loads.newsNum}</a></td>
					<td>${loads.totalNum}</td>
			   </tr>
			</c:forEach>
        </tbody>
    </table>
  <br>    
</form>
</div>
<script type="text/javascript">
    $(function(){
        $("#query").click(function(){
         $("#ptQueryForm").submit();
        });
       
    });

	function showNewNum(val){
		document.forms[0].action="${ctx}/ptQuery/analyseDetailPool?depot="+val;
		document.forms[0].submit();
	}
	
</script>
</body>
</html>