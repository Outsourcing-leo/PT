<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.tnt.pt.util.PTPARAMETERS"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
response.setHeader("Pragma","No-cache");    
response.setHeader("Cache-Control","no-cache");    
response.setDateHeader("Expires", -10);   
%> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>New PT-hwRate Profile</title>
<link href="${ctx}/static/styles/main.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/js/country.js"></script>
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
	
	function setSpecificCountry(){
		window.showModalDialog("content/NewPT/specificCountry.html","123","dialogWidth=340px;dialogHeight=210px");
		
	}
	
	
	 function addtr(id,type){
	        var weightBandids;
	        var productid;
	        //增加<tr/>
	        var _len = $("#"+id+" tr").length;
	        if(id=='tb1'){
	        	weightBandids='${ndocumentIds}';
	        	productid = '${ndocument}';
	        }else{
	        	weightBandids ='${eonomyIds}';
	        	productid = '${eonomy}';
	        }
	        var inputArr = weightBandids.split(";");
	        var appendTr = '';
        	for(var m=0; m < inputArr.length-1; m++){
				appendTr +="<td><input type='text' name='"+id+"_"+inputArr[m]+"_${business.id}_"+productid+"_"+_len+"'  id='"+id+"_"+inputArr[m]+"_"+_len+"'/></td>"
			}
        	 $("#"+id).append("<tr id='"+id+"_"+_len+"' align='center'>"
					   +"<td><input type='text' name='"+id+"_country_name_"+_len+"'  id='"+id+"_country_name_"+_len+"' onkeyup=\"new Customer('"+id+"',"+_len+",'"+type+"').show(this)\"/></td>" //<input type='hidden' name='"+id+"_country_id_"+_len+"'  id='"+id+"_country_id_"+_len+"'/>
					   + appendTr
                     +"<td><a href=\'#\' onclick=\"deltr(\'"+id+"\',"+_len+")\">删除</a></td>"
              +"</tr>");    
    }
    
     //删除<tr/>
    var deltr =function(id,index){
    	var ids = id+"_"+index;
        $("tr[id='"+ids+"']").remove();
    }
</script>


<body>
<form action="#" method="post" id="hwRateProfile">
	<div style="padding:5px;">
  <h4 class="title">New PT-HW rate Profile</h4>
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
      <td>${payment}<input type="hidden" value="${customer.payment}" name="customer.payment"></td>
      </tr>
  </table>
  </div>
  <br />
<div style="padding:0 5px 5px 5px;">
<div class="clearboth"> </div>
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="14" style="text-align:left;">New PT-HW rate Profile-15N(RMB per kilo)</th>
            </tr>
            <tr>
                <th >Country-Depot</th>
                 <c:forEach items="${ndocumentList}" var="weightBand">
					<th>${weightBand.name}</th>
			     </c:forEach>
			    <th >operate</th>
            </tr>
        </thead>
        <tbody id="tb1">
        	<c:forEach items="${ndocumentCountrys}" var="country" varStatus="co">
				<tr id='tb1_${co.index}' align="center">
					<td>
					<input type='text' value="${country.countryCode}" name='tb1_country_name_${co.index}'  id='tb1_country_name_${co.index}' onclick="new Customer('tb1',${co.index},'15D').show(this)"/>
					</td>
					<c:forEach items="${ndocumentList}" var="weightBand" begin="0">
							<c:set var="key">${business.id}_${ndocument}_${weightBand.id}_${country.id}</c:set>
						    <td><input  name="tb1_${weightBand.id}_${business.id}_${ndocument}_${co.index}" value="${hwRateMap[key]}"/></td>
				   </c:forEach>
				    <td><a href='#' onclick="deltr('tb1',${co.index})">删除</a></td>
			   </tr>
			</c:forEach>
        </tbody>
</table>
<br>

  <div style="text-align: left">
    <input type="button" value="Add a New Row" class="cls-button" onclick="addtr('tb1','15D')"/> 
   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <%--<input type="button" value="Delete Selected Rows" class="cls-button" />
   --%></div>
   <br>
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="14" style="text-align:left;">New PT-HW rate Profile-48N(RMB per kilo)</th>
            </tr>
            <tr>
                <th >Country-Depot</th>
                 <c:forEach items="${eonomyList}" var="weightBand">
					<th>${weightBand.name}</th>
			     </c:forEach>
			     <th >operate</th>
            </tr>
        </thead>
        <tbody id="tb2">
        	<c:forEach items="${eonomyCountrys}" var="country" varStatus="co">
				<tr id='tb2_${co.index}' align="center">
					<td>
					<input type='text' value="${country.countryCode}" name='tb2_country_name_${co.index}'  id='tb2_country_name_${co.index}' onclick="new Customer('tb2',${co.index},'48N').show(this)"/>
					</td>
					<c:forEach items="${eonomyList}" var="weightBand" begin="0">
							<c:set var="key">${business.id}_${eonomy}_${weightBand.id}_${country.id}</c:set>
						    <td><input  name="tb2_${weightBand.id}_${business.id}_${eonomy}_${co.index}" value="${hwRateMap[key]}"/></td>
				   </c:forEach>
				    <td><a href='#' onclick="deltr('tb2',${co.index})">删除</a></td>
			   </tr>
			</c:forEach>
        </tbody>
</table>
 <br>
  <div style="text-align: left">
    <input type="button" value="Add a New Row" class="cls-button" onclick="addtr('tb2','48N')"/><%-- 
   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" value="Delete Selected Rows" class="cls-button" />
   --%></div>
   <br>
  <div style="text-align: center">
  <input type="button" value="Back" class="cls-button" id="Back"  /> 
   	&nbsp;&nbsp;&nbsp;<input type="button" value="Next" class="cls-button"  id="next"  onclick="addContent(this);"/>
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
           		$("#hwRateProfile").attr('action',"${ctx}/ptModify/hwRateProfile");
			    $("#hwRateProfile").submit();
           	}else{
           		$("#hwRateProfile").attr('action',"${ctx}/ptModify/disConProfile");
			 	$("#hwRateProfile").submit();
           	}
		});
	});

    $(function(){
        $("#next").click(function(){
        	//在提交之前还需要进行discount值的更新，做法如下：1、点击方框，则在隐藏域中加一个值。2、只更新修改的值 没有修改的值不更新
        	//$("#hwRateProfile").attr('action',"${ctx}/ptCreate/consProfile");
        	//$("#hwRateProfile").submit();
        	 //序列化表单元素，返回json数据
            var params = $(".table_B").find("input").serializeArray();
             var jsonString = O2String(params);
             if(jsonString.length>2){
	             $.ajax({
	                 type:"POST",
	                 url:"${ctx}/ptCreate/addHwRate/${payment}",
	                 dataType:"json",      
	                 contentType:"application/json",   
	                 data:jsonString,
	                 success:function(data){
	                	 if($('#isFollow').val()=='NO'&&$('#payment').val()=='<%=PTPARAMETERS.PAYMENT[0]%>'){
	                 		$("#hwRateProfile").attr('action',"${ctx}/ptCreate/hwRateProfile");
	                 		$("#hwRateProfile").submit();
	                 	}else{
	                 		$('#payment').val('');
		                 	$("#hwRateProfile").attr('action',"${ctx}/ptCreate/consProfile/hw");
		                 	$("#hwRateProfile").submit();
	                 	}
	                 },
	                 error:function(e) {
	                     alert("error："+e);
	                 }
	             });
             }else{
            	 if($('#isFollow').val()=='NO'&&$('#payment').val()=='<%=PTPARAMETERS.PAYMENT[0]%>'){
              		$("#hwRateProfile").attr('action',"${ctx}/ptCreate/hwRateProfile");
              		$("#hwRateProfile").submit();
              	}else{
              		$('#payment').val('');
	                $("#hwRateProfile").attr('action',"${ctx}/ptCreate/consProfile/hw");
	                $("#hwRateProfile").submit();
              	}
             }
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
