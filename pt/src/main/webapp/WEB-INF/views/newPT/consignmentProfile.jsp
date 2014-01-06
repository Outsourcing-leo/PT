<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.tnt.pt.util.PTPARAMETERS"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>New PT-consigmentProfile</title>
<link href="${ctx}/static/styles/main.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_en.js" type="text/javascript"></script>
<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" type="text/css" rel="stylesheet" />
<script>
		$(document).ready(function() {
			//为inputForm注册validate函数
			$("#consignment").validate();
		});
</script>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>New PT-HW rate Profile</title>
</head>
<script language="JavaScript">
	function previous(obj){
		if(confirm("Set discount profile ?")){
			
			}else if(confirm("Set  hw rate profile ?")){
				
				}else {
						
					}
		
	}
	
	function setSpecificConsignmentSet(zonegroupId,productId){
		var returnValue = window.showModalDialog("${ctx}/ptCreate/specificConsignmentSet/"+productId+"/"+zonegroupId+"?businessId=${business.id}&payment=${payment}","","dialogWidth=640px;dialogHeight=450px");
		var inputArr = returnValue.split(";");
		for(var i=0; i < inputArr.length-1; i++){
			var weightBandId = inputArr[i].split("_")[0];
			var id = "zg_"+weightBandId+"_"+zonegroupId+"_"+${business.id};
			var value = inputArr[i].split("_")[1];
			$("#"+id).val(value);
			$("#"+id).attr("disabled",true);
		}
	}
</script>
<body>
<form action="#" method="post" id="consignment">
	<div style="padding:5px;">
  <h4 class="title">New PT-Consignment Profile</h4>
<table class="table_A">
      <tr>
    <%--***************隐藏域********** --%>
    <input type="hidden" value="${business.customerId}" name="business.customerId">
    <input type="hidden" value="${business.id}" name="business.id">
    <input type="hidden" value="${business.zoneType}" name="business.zoneType">
    <%--***************隐藏域********** --%>
      <th>Account #:</th>
      <td>${customer.account}<input type="hidden" value="${customer.account}" name="customer.account"></td>
      <th>Customer Name:</th>
      <td>${customer.cusName}<input type="hidden" value="${customer.cusName}" name="customer.cusName"></td>
      </tr>
      <tr>
      <th>PT Application Reference #: </th>
      <td>${business.applicationReference}<input type="hidden" value="${business.applicationReference}" name="business.applicationReference"></td>
      <th>Terms of payments:</th>
      <td>${customer.payment}<input type="hidden" value="${customer.payment}" name="customer.payment"></td>
      </tr>
  </table>
  </div>
  <br />
  <div style="text-align:left;font-color:blue;">
  	<span style="color:blue;font-size:16">.Click <span style="background-color:#F16E1E;"> &nbsp;Zone &nbsp;</span> if you want to set specific countries.</span>
  	 	
  </div>
<div style="padding:0 5px 5px 5px;">
<div class="clearboth"> </div>
<table class="table_B" width="100%">
        <thead>
						<tr align="center">
                <th colspan="14" style="text-align:left;">Discounts Profile- 15D/12D/10D/09D(% off full tariff)</th>
            </tr>
            <tr>
                <th>Weightband</th>
                 <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th ondblclick="setSpecificConsignmentSet(${zoneGroup.id},${zoneType.document});">${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${documentList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
							<td><input id="zg_${key}_${business.id}" name="zg_${key}_${business.id}"  size="4" 
							value="<c:choose><c:when test="${consignmentMap[key] == null}">0</c:when><c:otherwise>${consignmentMap[key]}</c:otherwise></c:choose>" size="4"/></td>
				   </c:forEach>
			   </tr>
			</c:forEach>
          </tbody>
</table>
<br>
<table class="table_B" width="100%">
        <thead>
						<tr align="center">
                <th colspan="14" style="text-align:left;">Discounts Profile- 15N/12N/10N/09N(% off full tariff)</th>
            </tr>
            <tr>
                <th>Weightband</th>
                <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th ondblclick="setSpecificConsignmentSet(${zoneGroup.id},${zoneType.nonDocument});">${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
           <c:forEach items="${ndocumentList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
							<td><input id="zg_${key}_${business.id}" name="zg_${key}_${business.id}"  size="4" 
							value="<c:choose><c:when test="${consignmentMap[key] == null}">0</c:when><c:otherwise>${consignmentMap[key]}</c:otherwise></c:choose>" size="4"/></td>
				   </c:forEach>
			   </tr>
			  </c:forEach>
          </tbody>
</table>
<br>
<table class="table_B" width="100%">
        <thead>
						<tr align="center">
                <th colspan="14" style="text-align:left;">Discounts Profile- 48N(% off full tariff)</th>
            </tr>
            <tr>
                <th >Weightband</th>
                <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th ondblclick="setSpecificConsignmentSet(${zoneGroup.id},${zoneType.economy});">${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
             <c:forEach items="${eonomyList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
						<td><input id="zg_${key}_${business.id}" name="zg_${key}_${business.id}" 
value="<c:choose><c:when test="${consignmentMap[key] == null}">0</c:when><c:otherwise>${consignmentMap[key]}</c:otherwise></c:choose>" size="4"/></td>
				   </c:forEach>
			   </tr>
			  </c:forEach>
          </tbody>
</table>

<br />
<div>total Rev&nbsp;&nbsp; <input type="text" id="totalRev" name="business.totalRev" value="${business.totalRev}" class="required number"/><input type="button" value="refresh" class="cls-button" id="refresh"/> </div>
<br />
  <hr />
   
  <div style="text-align: center">
  <input type="button" value="Back" class="cls-button" id="Back"/> 
   	&nbsp;&nbsp;&nbsp;<input type="button" value="Next" class="cls-button"  id="next" />
   	 &nbsp;&nbsp;&nbsp;<input type="button" value="Close" class="cls-button" onclick="window.location.href='index.html';"/>
   </div>
  <input type="hidden" id="isFollow" value="${isFollow}" name="isFollow">
   <input type="hidden" id="payment" value="${payment}" name="payment">
</form>
</div>
<script type="text/javascript">
	$(function(){
		 $("#Back").click(function(){
			 if($('#isFollow').val()=='NO'&&$('#payment').val()=='<%=PTPARAMETERS.PAYMENT[1]%>'){
				 $('#payment').val('');
              	 $("#consignment").attr('action',"${ctx}/ptModify/consProfile");
              	 $("#consignment").submit();
              }else{
             	 $("#consignment").attr('action',"${ctx}/ptModify/hwRateProfile");
    			 $("#consignment").submit();
              }
		});
	});

	$(function(){
		 $("#next").click(function(){
			 	var _totalRev = $("#totalRev").val();
	            $.ajax({
	                type:"GET",
	                url:"${ctx}/ptCreate/addSummary/${payment}",
	                dataType:"text",      
	                contentType:"application/json",   
	                data:{businessId:'${business.id}',totalRev:_totalRev,zonetype:'${business.zoneType}'},
	                success:function(data){
	                	 if($('#isFollow').val()=='NO'&&$('#payment').val()=='<%=PTPARAMETERS.PAYMENT[0]%>'){
		                 	$("#consignment").attr('action',"${ctx}/ptCreate/consProfile");
		                 	$("#consignment").submit();
		                 }else{
		                	$('#payment').val('');
		                	$("#consignment").attr('action',"${ctx}/ptCreate/summaryInfo");
		                 	$("#consignment").submit();
		                 }
	                },
	                error:function(e) {
	                    alert("error："+e);
	                }
	            });
		});
	});
	
    $(function(){
        $("#refresh").click(function(){
            //序列化表单元素，返回json数据
            var params = $(".table_B").find("input").serializeArray();
            var jsonString = O2String(params);
            $.ajax({
                type:"POST",
                url:"${ctx}/ptCreate/addConsignment/${payment}",
                dataType:"text",      
                contentType:"application/json",   
                data:jsonString,
                success:function(data){
                	$("#totalRev").val(data);
                },
                error:function(e) {
                    alert("error："+e);
                }
            });
        });
    });

    var O2String = function (O) {
        //return JSON.stringify(jsonobj);
        var S = [];
        var J = "";
        if (Object.prototype.toString.apply(O) === '[object Array]') {
            for (var i = 0; i < O.length; i++)
                S.push(O2String(O[i]));
            J = '[' + S.join(',') + ']';
        }
        else if (Object.prototype.toString.apply(O) === '[object Date]') {
            J = "new Date(" + O.getTime() + ")";
        }
        else if (Object.prototype.toString.apply(O) === '[object RegExp]' || Object.prototype.toString.apply(O) === '[object Function]') {
            J = O.toString();
        }
        else if (Object.prototype.toString.apply(O) === '[object Object]') {
            for (var i in O) {
                O[i] = typeof (O[i]) == 'string' ? '"' + O[i] + '"' : (typeof (O[i]) === 'object' ? O2String(O[i]) : O[i]);
                S.push(i + ':' + O[i]);
            }
            J = '{' + S.join(',') + '}';
        }
        return J;
 };
</script>
</body>
</html>
