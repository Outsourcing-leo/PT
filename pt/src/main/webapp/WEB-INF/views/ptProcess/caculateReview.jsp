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
<link href="${ctx}/static/styles/tab.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
</head>
<body>
<form action="${ctx}/ptQuery/ptApprove" method="post" id="ptQueryForm">
<c:if test="${flag=='both'}">
	<table class="tabTitlesContainer">
		<tr id="tabTitles">
			<td class="tabTitleSelected" onclick="tabPageControl(0)">Sender Pay</td>
			<td class="tabTitleUnSelected" onclick="tabPageControl(1)">Recieve Pay</td>
		</tr>
	</table>
</c:if>
<table class="table_B" width="100%" id="tabPagesContainer">
        <thead>
			<tr align="center" style="background-color: Orange;">
                <c:forEach items="${showList}" var="show">
					<th style="text-align:center;">${show}</th>
				</c:forEach>
				<th style="text-align:center;">Rev</th>
				<th style="text-align:center;">Cons</th>
				<th style="text-align:center;">Kilos</th>
				<th style="text-align:center;">RPC</th>
				<th style="text-align:center;">RPK</th>
				<th style="text-align:center;">WPC</th>
				<th style="text-align:center;">DM(%)</th>
				<th style="text-align:center;">FM(%)</th>
            </tr>
        </thead>
          <tbody >
			<c:forEach items="${sendReviewList}" var="review" varStatus="stat">
				<c:if test="${stat.last}">
			   	<tr align="center" >
	                <td colspan="${counter}">Total</td>
	                <td width="10%">${review.rev}</td>
	                <td width="10%">${review.cons}</td>
	                <td width="10%">${review.kilo}</td>
	                <td width="10%">${review.rpc}</td>
	                <td width="10%">${review.rpk}</td>
	                <td width="10%">${review.wpc}</td>
					<td width="10%">${review.dm}</td>
					<td width="10%">${review.fm}</td>
			   </tr>
			   <c:set var="isDoing" value="1"/>
			   </c:if>
			   <c:if test="${isDoing!=1}">
				<tr align="center" >
	                <c:forEach items="${showList}" var="show">
						<c:if test="${show=='Country'}">
							<td>${review.COUNTRYNAME}</td>
						</c:if>
						<c:if test="${show=='Product'}">
							<td>${review.PRODUCTNAME}</td>
						</c:if>
						<c:if test="${show=='PT Zone'}">
							<td>${review.zone}</td>
						</c:if>
						<c:if test="${show=='WeightBand'}">
							<td>${review.WEIGHTNAME}</td>
						</c:if>
					</c:forEach>
	                <td width="10%">${review.rev}</td>
	                <td width="10%">${review.cons}</td>
	                <td width="10%">${review.kilo}</td>
	                <td width="10%">${review.rpc}</td>
	                <td width="10%">${review.rpk}</td>
	                <td width="10%">${review.wpc}</td>
					<td width="10%">${review.dm}</td>
					<td width="10%">${review.fm}</td>
			   </tr>
			   </c:if> 
			</c:forEach>
		</tbody>
		<tbody style="display: none;">
			<c:forEach items="${recieveReviewList}" var="review" varStatus="stat">
				<c:if test="${stat.last}">
			   	<tr>
	                <td colspan="${counter}">Total</td>
	                <td>${review.rev}</td>
	                <td>${review.cons}</td>
	                <td>${review.kilo}</td>
	                <td>${review.rpc}</td>
	                <td>${review.rpk}</td>
	                <td>${review.wpc}</td>
					<td>${review.dm}</td>
					<td>${review.fm}</td>
			   </tr>
			   <c:set var="isDoing" value="1"/>
			   </c:if>
			   <c:if test="${isDoing!=1}">
				<tr>
	                <c:forEach items="${showList}" var="show">
						<c:if test="${show=='Country'}">
							<td>${review.COUNTRYNAME}</td>
						</c:if>
						<c:if test="${show=='Product'}">
							<td>${review.PRODUCTNAME}</td>
						</c:if>
						<c:if test="${show=='PT Zone'}">
							<td>${review.zone}</td>
						</c:if>
						<c:if test="${show=='WeightBand'}">
							<td>${review.WEIGHTNAME}</td>
						</c:if>
					</c:forEach>
	                <td>${review.rev}</td>
	                <td>${review.cons}</td>
	                <td>${review.kilo}</td>
	                <td>${review.rpc}</td>
	                <td>${review.rpk}</td>
	                <td>${review.wpc}</td>
					<td>${review.dm}</td>
					<td>${review.fm}</td>
			   </tr>
			   </c:if>
			</c:forEach>
		</tbody>
</table>
</form>
<script type="text/javascript">
function tabPageControl(n) {
	for ( var i = 0; i < tabTitles.cells.length; i++) {
		tabTitles.cells[i].className = "tabTitleUnSelected";
	}
	tabTitles.cells[n].className = "tabTitleSelected";
	for ( var i = 0; i < tabPagesContainer.tBodies.length; i++) {
		tabPagesContainer.tBodies[i].style.cssText = "display: none;";
	}
	tabPagesContainer.tBodies[n].style.cssText = "display: block;";
}
</script>
</body>
</html>