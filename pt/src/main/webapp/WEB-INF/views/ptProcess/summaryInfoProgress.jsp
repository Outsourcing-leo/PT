<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>New PT-Summary Info</title>
<link href="${ctx}/static/styles/main.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/styles/tab.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctx}/uploadify/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="${ctx}/uploadify/jquery.uploadify-3.1.min.js"></script>
</head>

<style type="text/css">
<!--
	.modify tr th, .modify tr td {
	text-align:left;
	font-size:14px;
}
	.modify tr th {
	background:#EFEFEF;
}
-->
</style>
<body>
<form action="#">
	<div style="padding:5px;">
  <h4 class="title">New PT-Summary Info</h4>
  <input type="hidden" value="${business.customerId}" name="business.customerId">
    <input type="hidden" id="hiddenID" value="${business.id}" name="business.id">
    <input type="hidden" value="${business.zoneType}" name="business.zoneType">
  <table class="modify">
    <tr>
      <th>Application Date:</th>
      <td><fmt:formatDate value="${business.applicationDate}" pattern="yyyy-MM-dd" /></td>
      <th>Depot:</th>
      <td>${business.depotCode}</td>
    </tr>
    <tr>
      <th>A new PT Customer?</th>
      <td>${business.isNewCus}</td>
      <th>Territory:</th>
      <td>${business.territory}</td>
    </tr>
    <tr>
	  <th>PT Application Reference #: </th>
      <td>${business.applicationReference}</td>
      <th>Account #:</th>
      <td>${customer.account}</td>
    </tr>
    <tr>
      <th>Customer Name</th>
      <td>${customer.cusName}</td>
      <th>Channel:</th>
      <td>${customer.channel}</td>
    </tr>
    <tr>
      <th>Industry</th>
      <td colspan="3">${customer.industry}</td>
        
    </tr>
    <tr>
      <th>Current Service Provider</th>
      <td colspan="3">${customer.serviceProvider}</td>
    </tr>
   
  </table>
  <table class="modify">
    <tr>
      <th width="45%">Is the customer on fuel surcharge exemption or deduction now?</th>
      <td>${customer.isFuleDeduction }</td>
      <th width="20%">the Current fuel surcharge</th>
      <td>${customer.fuelSurcharge }</td>
    </tr>
    <tr>
      <th width="45%">Are you requesting for fuel surcharge exemption or deduction now?</th>
      <td>${customer.isReq}</td>
      <th width="20%">the fuel surcharge requested</th>
      <td>${customer.reqFuelSurcharge}</td>
    </tr>
    </tr>
    
  </table>
  <table class="modify">
    <tr>
      <th>Term of Payment:</th>
      <td colspan="3">${customer.payment}</td>
    </tr>
    <tr>
      <th>Is customer a mainly document sender?</th>
      <td>${business.isDocumentSender}</td>
      <th width="15%">Cons/Stop</th>
      <td>${business.consStop}</td>
    </tr>
     <tr>
      <th width="30%">Prdouct Description(eg:digital cameral)</th>
      <td colspan="3">${business.description}</td>
      
    </tr>
     <tr>
      <th width="20%">Reason for the PT:</th>
      <td colspan="3">${business.reson}</td>
    </tr>
  </table>
  </div>
 <br />
 <div style="padding:5px;">
  <c:if test="${flag=='both'}">
	<table class="tabTitlesContainer">
		<tr id="tabTitles">
			<td class="tabTitleSelected" onclick="tabPageControl(0)">SenderPay</td>
			<td class="tabTitleUnSelected" onclick="tabPageControl(1)">Recieve Pay</td>
		</tr>
	</table>
</c:if>
<div class="tabPageSelected" id="sedDIV">
 <table>
 <tr>
 <td style="background-color:#F5C07B">Summary </td><td style="background-color:#EFEFEF">Rev(CNY)</td><td>${zoneSummary.revM}/M</td>
 <td>${zoneSummary.revY}/Y</td><td style="background-color:#EFEFEF">Cons</td><td>${zoneSummary.consM}/M</td><td>${zoneSummary.consY}/Y</td>
  <td style="background-color:#EFEFEF">Kilo(kg) </td><td>${zoneSummary.kiloM}/M</td><td>${zoneSummary.kiloY}/Y</td>
 </tr>
 </table>
 <br />
<div style="padding:0 5px 5px 5px;">
<div class="clearboth"> </div>
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="7" style="text-align:center;">Summary by GEO Zone</th>
            </tr>
			<tr align="center" >
                <th rowspan="2" style="text-align:center;">GEO Zone</th>
				<th colspan="2" style="text-align:center;">Rev(CNY)</th>
				<th colspan="2" style="text-align:center;">Cons</th>
				<th colspan="2" style="text-align:center;">Kilo(kg)</th>
            </tr>
            <tr>
                <th>Per Month</th>
                <th>Per Year</th>
                <th>Per Month</th>
                <th>Per Year</th>
				<th>Per Month</th>
                <th>Per Year</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${geoSummaryList}" var="geoSummary">
				<tr>
					<td>${geoSummary.geoZone}</td>
					<td>${geoSummary.revM}</td>
					<td>${geoSummary.revY}</td>
					<td>${geoSummary.consM}</td>
					<td>${geoSummary.consY}</td>
					<td>${geoSummary.kiloM}</td>
					<td>${geoSummary.kiloY}</td>
			   </tr>
		 </c:forEach>
          </tbody>
</table>
<br>
<table class="table_B" width="100%">
       <thead>
			<tr align="center">
                <th colspan="7" style="text-align:center;">Summary by PT Zone</th>
            </tr>
			<tr align="center" >
                <th rowspan="2" style="text-align:center;">PT Zone</th>
				<th colspan="2" style="text-align:center;">Rev(CNY)</th>
				<th colspan="2" style="text-align:center;">Cons</th>
				<th colspan="2" style="text-align:center;">Kilo(kg)</th>
            </tr>
            <tr>
                <th>Per Month</th>
                <th>Per Year</th>
                <th>Per Month</th>
                <th>Per Year</th>
				<th>Per Month</th>
                <th>Per Year</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${zoneSummaryList}" var="zoneSummary">
				<tr>
					<td>${zoneSummary.zoneType}</td>
					<td>${zoneSummary.revM}</td>
					<td>${zoneSummary.revY}</td>
					<td>${zoneSummary.consM}</td>
					<td>${zoneSummary.consY}</td>
					<td>${zoneSummary.kiloM}</td>
					<td>${zoneSummary.kiloY}</td>
			   </tr>
		 </c:forEach>
          </tbody>
</table>
<br>
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="15" style="text-align:left;">Discounts Requested</th>
            </tr>
            <tr>
                <th>PRODUCT</th>
				<th width="8%"> Chargeable Weight(kg)</th>
                <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th>${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${documentList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<td>${weightBand.chargeableWeight}</td>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
						<td>${discountMap[key]}</td>
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
				<th  width="8%"> Chargeable Weight(kg)</th>
                 <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th>${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${ndocumentList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<td>${weightBand.chargeableWeight}</td>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
						<td>${discountMap[key]}</td>
				   </c:forEach>
			   </tr>
			  </c:forEach>
          </tbody>
</table>
<br />
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="15" style="text-align:left;">Discounts Profile-48N</th>
            </tr>
            <tr>
                <th>Weightband</th>
				<th  width="8%"> Chargeable Weight(kg)</th>
                <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th>${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${eonomyList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<td>${weightBand.chargeableWeight}</td>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
						<td>${discountMap[key]}</td>
				   </c:forEach>
			   </tr>
			  </c:forEach>
          </tbody>
</table>
</div>
</div>
<div class="tabPageUnSelected" id="recDIV">
 <table>
 <tr>
 <td style="background-color:#F5C07B">Summary </td><td style="background-color:#EFEFEF">Rev(CNY)</td><td>${recZoneSummary.revM}/M</td>
 <td>${recZoneSummary.revY}/Y</td><td style="background-color:#EFEFEF">Cons</td><td>${recZoneSummary.consM}/M</td><td>${recZoneSummary.consY}/Y</td>
  <td style="background-color:#EFEFEF">Kilo(kg) </td><td>${recZoneSummary.kiloM}/M</td><td>${recZoneSummary.kiloY}/Y</td>
 </tr>
 </table>
 <br />
<div style="padding:0 5px 5px 5px;">
<div class="clearboth"> </div>
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="7" style="text-align:center;">Summary by GEO Zone</th>
            </tr>
			<tr align="center" >
                <th rowspan="2" style="text-align:center;">GEO Zone</th>
				<th colspan="2" style="text-align:center;">Rev(CNY)</th>
				<th colspan="2" style="text-align:center;">Cons</th>
				<th colspan="2" style="text-align:center;">Kilo(kg)</th>
            </tr>
            <tr>
                <th>Per Month</th>
                <th>Per Year</th>
                <th>Per Month</th>
                <th>Per Year</th>
				<th>Per Month</th>
                <th>Per Year</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${recGeoSummaryList}" var="geoSummary">
				<tr>
					<td>${geoSummary.geoZone}</td>
					<td>${geoSummary.revM}</td>
					<td>${geoSummary.revY}</td>
					<td>${geoSummary.consM}</td>
					<td>${geoSummary.consY}</td>
					<td>${geoSummary.kiloM}</td>
					<td>${geoSummary.kiloY}</td>
			   </tr>
		 </c:forEach>
          </tbody>
</table>
<br>
<table class="table_B" width="100%">
       <thead>
			<tr align="center">
                <th colspan="7" style="text-align:center;">Summary by PT Zone</th>
            </tr>
			<tr align="center" >
                <th rowspan="2" style="text-align:center;">PT Zone</th>
				<th colspan="2" style="text-align:center;">Rev(CNY)</th>
				<th colspan="2" style="text-align:center;">Cons</th>
				<th colspan="2" style="text-align:center;">Kilo(kg)</th>
            </tr>
            <tr>
                <th>Per Month</th>
                <th>Per Year</th>
                <th>Per Month</th>
                <th>Per Year</th>
				<th>Per Month</th>
                <th>Per Year</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${recZoneSummaryList}" var="zoneSummary">
				<tr>
					<td>${zoneSummary.zoneType}</td>
					<td>${zoneSummary.revM}</td>
					<td>${zoneSummary.revY}</td>
					<td>${zoneSummary.consM}</td>
					<td>${zoneSummary.consY}</td>
					<td>${zoneSummary.kiloM}</td>
					<td>${zoneSummary.kiloY}</td>
			   </tr>
		 </c:forEach>
          </tbody>
</table>
<br>
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="15" style="text-align:left;">Discounts Requested</th>
            </tr>
            <tr>
                <th>PRODUCT</th>
				<th width="8%"> Chargeable Weight(kg)</th>
                <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th>${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${documentList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<td>${weightBand.chargeableWeight}</td>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
						<td>${recDiscountMap[key]}</td>
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
				<th  width="8%"> Chargeable Weight(kg)</th>
                 <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th>${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${ndocumentList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<td>${weightBand.chargeableWeight}</td>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
						<td>${recDiscountMap[key]}</td>
				   </c:forEach>
			   </tr>
			  </c:forEach>
          </tbody>
</table>
<br />
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="15" style="text-align:left;">Discounts Profile-48N</th>
            </tr>
            <tr>
                <th>Weightband</th>
				<th  width="8%"> Chargeable Weight(kg)</th>
                <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th>${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${eonomyList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<td>${weightBand.chargeableWeight}</td>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
						<td>${recDiscountMap[key]}</td>
				   </c:forEach>
			   </tr>
			  </c:forEach>
          </tbody>
</table>
</div>
</div>
<br>
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="2" style="text-align:left;">Reason For The PT</th>
            </tr>
			
        </thead>
        <tbody>
            <tr>
                <td width="20%">Reason For The PT</td>
                <td>${business.reson}</td>
            </tr>
          </tbody>
</table>
<br />
  <div style="text-align: center">
  <br>
<table>
	<tr>
		<td style="background-color:#EFEFEF;width:20%">Attach Supporting Documents for Approval or Rejection</td>
		<td width="10%" rowspan="2" align="center" id="fileName"></td>
		<td style="background-color:#EFEFEF;width:15%; text-align:center;">Operation</td>
		<td width="10%" rowspan="2"></td>
		<td rowspan="2"style="background-color:#EFEFEF;width:10%">Reason for Approval or Rejection</td>
		<td rowspan="2" width="35%">
		<div>
			<textarea id="examOppion" style="width:100%; height:100px"></textarea>
		</div>
		</td>
	</tr>
	<tr>
		<td align="center"> </td>

		<td align="center"><a href="#" id="deleteFile">Delete</a></td>
	</tr>
</table>
<br />
<div align="left">
	<input type="file" name="Browser" id="file_upload" />
	<a href="javascript:$('#file_upload').uploadify('upload','*')">upload</a>&nbsp;   
        <a href="javascript:$('#file_upload').uploadify('cancel', '*')">uploadCancel</a> 
</div>
<br />
  <div style="text-align: center">
  <input type="button" value="Approve" class="cls-button" id="Approve"  /> 
   	&nbsp;&nbsp;&nbsp;<input type="button" id="Reject" value="Reject" class="cls-button" />
   	 &nbsp;&nbsp;&nbsp;<input type="button" value="Return to PT List" class="cls-button" id="Return" onclick="javascript:history.go(-1);"/>
   </div>
</form>
</div>
<script type="text/javascript">

    $(function(){
        $("#Approve").click(function(){
        	var examOppion = document.getElementById("examOppion").value;
            $.ajax({
                type:"POST",
                url:"${ctx}/ptApprove/salesAdminApprove",
                data:"id="+document.getElementById("hiddenID").value+"&examOppion="+examOppion,
                success:function(data){
                	alert('Approve Success！');
                },error:function(e) {
                    alert("error："+e);
                }
            });
        });
        
        $("#deleteFile").click(function(){
        	var fileName = document.getElementById("fileName").innerText;
            $.ajax({
                type:"POST",
                url:"${ctx}/ptApprove/deleteFile",
                data:"id="+document.getElementById("hiddenID").value+"&fileName="+fileName,
                success:function(data){
                	alert("delete success!");
                	document.getElementById("fileName").innerText="";
                },error:function(e) {
                    alert("error："+e);
                }
            });
        });
    });

	$(function() {
    	var businessId = document.getElementById("hiddenID").value;
	    $("#file_upload").uploadify({
	    	'height'        : 27, 
	    	'width'         : 80,  
	    	'buttonText'    : 'browse',
	        'swf'           : '<%=path%>/uploadify/uploadify.swf',
	        'uploader'      : '${ctx}/ptApprove/uploadFile/'+businessId,
	        'auto'          : false,
	        //'fileTypeExts'  : '*.xls',
	        'formData'      : {'userName':'','qq':''},
	        'onUploadSuccess': function(file, data)
	        {		
	        	document.getElementById("fileName").innerText=file.name;
	        }
	    });
	});
    
	function tabPageControl(n) {
		for ( var i = 0; i < tabTitles.cells.length; i++) {
			tabTitles.cells[i].className = "tabTitleUnSelected";
		}
		tabTitles.cells[n].className = "tabTitleSelected";
		if(n==1){
			document.getElementById("recDIV").style.cssText = "display:block;";
			document.getElementById("sedDIV").style.cssText = "display:none;";
		}else{
			document.getElementById("sedDIV").style.cssText = "display:block;";
			document.getElementById("recDIV").style.cssText = "display:none;";
		}
		
	}
	
</script>
</body>
</html>
