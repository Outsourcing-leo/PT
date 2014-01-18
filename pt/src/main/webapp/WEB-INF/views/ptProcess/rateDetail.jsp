<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/static/styles/main.css" type="text/css" rel="stylesheet" />
<title>New PT-Summary Info</title>
</head>
<style type="text/css">	
		.tab tr td {font-size:12px;
					align:left;
			}	
	
		</style>

<body>
<form action="#">
<div style="padding:5px;">
  <h4 class="title">PT Tariff Info</h4>
  
  <table class="tab">
    <tr>
	  <td width="20%">Account # :</td>
      <td width="30%">${customer.account}</td>
      <td width="20%">Customer Name :</td>
      <td width="30%">${customer.cusName}</td>
    </tr>
    <tr>
      <td>PT Application Reference# :</td>
      <td>${business.applicationReference}</td>
      <td>Terms of Payment : </td>
	  <td>${customer.payment}</td>
    </tr>
  </table>
 </div>
 <br />
 <div style="padding:5px;">
<div style="padding:0 5px 5px 5px;">
<div class="clearboth"> </div>
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="15" style="text-align:left;">48N</th>
            </tr>
            <tr>
                <th>Weightband</th>
                <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th ondblclick="setSpecificCountry();">${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
             <c:forEach items="${economyList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
					    <c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
						<td>${rateMap[key]}</td>	
				   </c:forEach>
			   </tr>
			</c:forEach>
	</tbody>
</table>
<br />
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="15" style="text-align:left;">Discounts Profile-15D/12D/10D/09D</th>
            </tr>
            <tr>
                <th>Weightband</th>
                <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th ondblclick="setSpecificCountry();">${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${documentList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
					    <c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
						<td>${rateMap[key]}</td>	
				   </c:forEach>
			   </tr>
			</c:forEach>
          </tbody>
</table>
<br />
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="15" style="text-align:left;">Discounts Profile-15N/12N/10N/09N</th>
            </tr>
            <tr>
                <th>Weightband</th>
                 <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th ondblclick="setSpecificCountry();">${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
             <c:forEach items="${ndocumentList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
					    <c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
						<td>${rateMap[key]}</td>	
				   </c:forEach>
			   </tr>
			</c:forEach>
          </tbody>
</table>
<br>
<table class="table_B" width="50%" align="left">
        <thead>
			<tr align="center">
                <th colspan="6" style="text-align:left;">Global Express - Non Documents (rate per kg) SenderPays</th>
            </tr>
			<tr align="center">
                <th>Country</th><th>21-30kg</th><th>31-50kg</th>
				<th>51-100kg</th><th>101-300kg</th><th>300kg+</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>BEï¼Zoneï¼</td>
                <td>50</td><td>51</td><td>52</td>
				<td>53</td><td>54</td>
            </tr>
			<tr style="border-left-style:none; border-right-style:none; border-bottom-style:none;"><td colspan="6"></td></tr>
          </tbody>
</table>

   <br />
  <div style="text-align: center">
  <input type="button" value="Close" class="cls-button" onclick="window.location.href='./index.html';"/>
   </div>
</form>
</div>
</body>
</html>
