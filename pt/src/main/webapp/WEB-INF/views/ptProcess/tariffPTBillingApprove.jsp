<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(new Date());
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>New PT-Summary Info</title>
<link href="${ctx}/static/styles/main.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/styles/tab.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/js/calendar.js"></script>
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
  <table class="table_A">
  <tr>
  <td width="20%" align="left"><b>PT Approval Ref NO. &nbsp; :</b></td>
  <td align="left"> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;${business.applicationReference}</td>
  </tr>
  <tr></tr>
  <tr> <td colspan="2"><b>Customer & Shipping Profile</b></td></tr>
  </table>
  <table class="tab">
    <tr>
      <td>Customer Name :</td>
      <td width="40%">${customer.cusName}</td>
	  <td width="20%">Account # :</td>
      <td>${customer.account}</td>
    </tr>
    <tr>
      <td>Origin Depot :</td>
      <td>SHA	</td>
      <td>Average Weight per Consignment :</td>
      <td></td>
    </tr>
    <tr>
	  <td width="20%">Projected Monthly Revenue in CNY: </td>
      <td colspan="3">220</td>
    </tr>
  </table>
  <b>Loading Details</b>
  <table>
    <tr>
      <td width="20%">Division :</td>
      <td width="40%">G</td>
      <td width="15%">RateCategory :</td>
      <td>07PTO</td>
    </tr>
    <tr>
      <td>Approval Date :</td>
      <td>30-Oct-08</td>
      <td>Effective Date :</td>
      <td>${business.effectiveDate}</td>
    </tr>
    <tr>
      <td>End Date :</td>
      <td>Open</td>
      <td>Review Date :</td>
      <td>Open</td>
    </tr>
    <tr>
      <td>Product :</td>
      <td>15D/15N/48N</td>
      <td>Terms of Payment :</td>
      <td>${customer.payment}</td>
    </tr>
	<tr>
      <td>Option :</td>
      <td>No discount for potions!</td>
      <td>Currency :</td>
      <td>CNY</td>
    </tr>
    <tr>
      <td>Zoning :</td>
      <td>Set Default</td>
      <td>Fuel Surchange :</td>
      <td>FSI according to public fuel surchange</td>
    </tr>
  </div>
 <br />
 <c:if test="${flag=='Both'}">
	<table class="tabTitlesContainer">
		<tr id="tabTitles">
			<td class="tabTitleSelected" onclick="tabPageControl(0)" style="cursor:pointer">Sender Pay</td>
			<td class="tabTitleUnSelected" onclick="tabPageControl(1)" style="cursor:pointer">Recieve Pay</td>
		</tr>
	</table>
</c:if>
<div style="padding:0 5px 5px 5px;" id="tabPagesContainer">
<div class="tabPageSelected" id="sedDIV">
 <table>
 <tr>
 <td style="background-color:#F5C07B">Summary </td><td style="background-color:#EFEFEF">Rev(CNY)</td><td>${zoneSummary.revM}/M</td>
 <td>${zoneSummary.revY}/Y</td><td style="background-color:#EFEFEF">Cons</td><td>${zoneSummary.consM}/M</td><td>${zoneSummary.consY}/Y</td>
  <td style="background-color:#EFEFEF">Kilo(kg) </td><td>${zoneSummary.kiloM}/M</td><td>${zoneSummary.kiloY}/Y</td>
 </tr>
 </table>
<br/>

<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="25" style="text-align:left;">Discounts Profile-15D/12D/10D/09D</th>
            </tr>
            <tr>
                <th rowspan="2">Weightband</th>
                <th rowspan="2">Inc</th>
                <th rowspan="2">Add On</th>
               <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th colspan="2">${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
            <tr>
               <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th>Base</th>
						<th>Int</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${documentList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<td>${weightBand.addOnWeightInt}kg</td>
					<c:if test="${weightBand.type=='base'}">
						<td>N</td>
					</c:if>
					<c:if test="${weightBand.type=='add'}">
						<td>Y</td>
					</c:if>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
					    <c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
						<c:if test="${weightBand.type=='base'}">
							<td>${discountMap[key]}%</td>
						</c:if>
						<c:if test="${weightBand.type=='add'}">
							<td></td>
						</c:if>
						<td>${discountMap[key]}%</td>	
				   </c:forEach>
			   </tr>
			</c:forEach>
          </tbody>
</table>
<br />
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="25" style="text-align:left;">Discounts Profile-15N/12N/10N/09N</th>
            </tr>
            <tr>
                <th rowspan="2">Weightband</th>
                <th rowspan="2">Inc</th>
                <th rowspan="2">Add On</th>
               <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th colspan="2">${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
            <tr>
               <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th>Base</th>
						<th>Int</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${ndocumentList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<td>${weightBand.addOnWeightInt}kg</td>
					<c:if test="${weightBand.type=='base'}">
						<td>N</td>
					</c:if>
					<c:if test="${weightBand.type=='add'}">
						<td>Y</td>
					</c:if>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
					    <c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
						<c:if test="${weightBand.type=='base'}">
							<td>${discountMap[key]}%</td>
						</c:if>
						<c:if test="${weightBand.type=='add'}">
							<td></td>
						</c:if>
						<td>${discountMap[key]}%</td>	
				   </c:forEach>
			   </tr>
			</c:forEach>
          </tbody>
</table>
<br>
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="25" style="text-align:left;">Discounts Profile-48N</th>
            </tr>
            <tr>
                <th rowspan="2">Weightband</th>
                <th rowspan="2">Inc</th>
                <th rowspan="2">Add On</th>
               <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th colspan="2">${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
            <tr>
               <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th>Base</th>
						<th>Int</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
           <c:forEach items="${economyList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<td>${weightBand.addOnWeightInt}kg</td>
					<c:if test="${weightBand.type=='base'}">
						<td>N</td>
					</c:if>
					<c:if test="${weightBand.type=='add'}">
						<td>Y</td>
					</c:if>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
					    <c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
						<c:if test="${weightBand.type=='base'}">
							<td>${discountMap[key]}%</td>
						</c:if>
						<c:if test="${weightBand.type=='add'}">
							<td></td>
						</c:if>
						<td>${discountMap[key]}%</td>	
				   </c:forEach>
			   </tr>
			</c:forEach>
        </tbody>
    </table>
	<br />
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
                <td>BE(Zone)</td>
                <td>50</td><td>51</td><td>52</td>
				<td>53</td><td>54</td>
            </tr>
			<tr style="border-left-style:none; border-right-style:none; border-bottom-style:none;"><td colspan="6"></td></tr>
          </tbody>
</table>
</div>

<div class="tabPageUnSelected" id="recDIV">
 <table>
 <tr>
 <td style="background-color:#F5C07B">Summary </td><td style="background-color:#EFEFEF">Rev(CNY)</td><td>${recZoneSummary.revM}/M</td>
 <td>${recZoneSummary.revY}/Y</td><td style="background-color:#EFEFEF">Cons</td><td>${recZoneSummary.consM}/M</td><td>${recZoneSummary.consY}/Y</td>
  <td style="background-color:#EFEFEF">Kilo(kg) </td><td>${recZoneSummary.kiloM}/M</td><td>${recZoneSummary.kiloY}/Y</td>
 </tr>
 </table>
<br/>
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="25" style="text-align:left;">Discounts Profile-15D/12D/10D/09D</th>
            </tr>
            <tr>
                <th rowspan="2">Weightband</th>
                <th rowspan="2">Inc</th>
                <th rowspan="2">Add On</th>
               <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th colspan="2">${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
            <tr>
               <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th>Base</th>
						<th>Int</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${documentList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<td>${weightBand.addOnWeightInt}kg</td>
					<c:if test="${weightBand.type=='base'}">
						<td>N</td>
					</c:if>
					<c:if test="${weightBand.type=='add'}">
						<td>Y</td>
					</c:if>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
					    <c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
						<c:if test="${weightBand.type=='base'}">
							<td>${recDiscountMap[key]}%</td>
						</c:if>
						<c:if test="${weightBand.type=='add'}">
							<td></td>
						</c:if>
						<td>${recDiscountMap[key]}%</td>	
				   </c:forEach>
			   </tr>
			</c:forEach>
          </tbody>
</table>
<br />
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="25" style="text-align:left;">Discounts Profile-15N/12N/10N/09N</th>
            </tr>
            <tr>
                <th rowspan="2">Weightband</th>
                <th rowspan="2">Inc</th>
                <th rowspan="2">Add On</th>
               <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th colspan="2">${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
            <tr>
               <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th>Base</th>
						<th>Int</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${ndocumentList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<td>${weightBand.addOnWeightInt}kg</td>
					<c:if test="${weightBand.type=='base'}">
						<td>N</td>
					</c:if>
					<c:if test="${weightBand.type=='add'}">
						<td>Y</td>
					</c:if>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
					    <c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
						<c:if test="${weightBand.type=='base'}">
							<td>${recDiscountMap[key]}%</td>
						</c:if>
						<c:if test="${weightBand.type=='add'}">
							<td></td>
						</c:if>
						<td>${recDiscountMap[key]}%</td>	
				   </c:forEach>
			   </tr>
			</c:forEach>
          </tbody>
</table>
<br>
<table class="table_B" width="100%">
        <thead>
			<tr align="center">
                <th colspan="25" style="text-align:left;">Discounts Profile-48N</th>
            </tr>
            <tr>
                <th rowspan="2">Weightband</th>
                <th rowspan="2">Inc</th>
                <th rowspan="2">Add On</th>
               <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th colspan="2">${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
            <tr>
               <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th>Base</th>
						<th>Int</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
           <c:forEach items="${economyList}" var="weightBand">
				<tr>
					<td>${weightBand.name}</td>
					<td>${weightBand.addOnWeightInt}kg</td>
					<c:if test="${weightBand.type=='base'}">
						<td>N</td>
					</c:if>
					<c:if test="${weightBand.type=='add'}">
						<td>Y</td>
					</c:if>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
					    <c:set var="key">${weightBand.id}_${zoneGroup.id}</c:set>
						<c:if test="${weightBand.type=='base'}">
							<td>${recDiscountMap[key]}%</td>
						</c:if>
						<c:if test="${weightBand.type=='add'}">
							<td></td>
						</c:if>
						<td>${recDiscountMap[key]}%</td>	
				   </c:forEach>
			   </tr>
			</c:forEach>
        </tbody>
    </table>
	<br />
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
                <td>BE(Zone)</td>
                <td>50</td><td>51</td><td>52</td>
				<td>53</td><td>54</td>
            </tr>
			<tr style="border-left-style:none; border-right-style:none; border-bottom-style:none;"><td colspan="6"></td></tr>
          </tbody>
</table>
</div>
<br /><br />
<div style="width:100%">
<br />
<span style="width:20%">Effective Date:  &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
<input type="text" onclick="new Calendar().show(this,this)" id="effectiveDate" value="<%=format.format(calendar.getTime()) %>"/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" value="Modify" id="Modify" class="cls-button" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

(Preferably the first date of current invoice cycle.)

</div>
<br />
<div style="width:100%">
<span style="width:20%">Account &nbsp;&nbsp;#: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
<input type="text" name="account" id="account" />
</div>
<br />
<div style="width:100%">
<span style="width:20%; float:inherit">
Reason for Rejection:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</span>
<textarea id="examOppion" style=" width:60%;height:80px;"></textarea>
</div>
<br />

  <div style="text-align:center">
  <input type="button" id="Approve" value="Approve"  class="cls-button" "/> 
   	&nbsp;&nbsp;&nbsp;<input type="button" value="Reject" id="Reject" class="cls-button"/>
	 &nbsp;&nbsp;&nbsp;<input type="button" value="Export" class="cls-button" onclick="downLoadPdf();"/>
	 &nbsp;&nbsp;&nbsp;<input type="button" value="Return" class="cls-button" onclick="javascript:history.go(-1)"/>
   </div>
   <br />
    <input type="hidden" id="hiddenID" value="${business.id}" name="business.id">
</form>
</div>
<script type="text/javascript">

    $(function(){
        $("#Approve").click(function(){
	        var examOppion = document.getElementById("examOppion").value;
	        var effectiveDate = document.getElementById("effectiveDate").value;
	        var account = document.getElementById("account").value;
	        if(examOppion==""){
	    		if(confirm("Are you sure approve without oppinon?"))
	    		{ 
	    			return true;
	    			document.getElementById("examOppion").focus();
	    		}else{
	    			
	    		}
	    		
	    	}
            $.ajax({
                type:"POST",
                url:"${ctx}/ptApprove/billingApprove",
                data:"id="+document.getElementById("hiddenID").value+"&examOppion="+examOppion+"&effectiveDate="+effectiveDate+"&account="+account,
                success:function(data){
                	alert('Approve success !');
                },error:function(e) {
                    alert("error："+e);
                }
            });
        });
        
        $("#Reject").click(function(){
	        var examOppion = document.getElementById("examOppion").value;
	        if(examOppion==""){
	    		if(confirm("Are you sure reject without oppinon?"))
	    		{ 
	    			return true;
	    			document.getElementById("examOppion").focus();
	    		}else{
	    			
	    		}
	    	}
           $.ajax({
               type:"POST",
               url:"${ctx}/ptApprove/reject",
               data:"id="+document.getElementById("hiddenID").value+"&state=billingFail"+"&examOppion="+examOppion,
               success:function(data){
               	alert('Reject success !');
               },error:function(e) {
                   alert("error："+e);
               }
           });
       });
        
        $("#Modify").click(function(){
	        var effectiveDate = document.getElementById("effectiveDate").value;
            $.ajax({
                type:"POST",
                url:"${ctx}/ptApprove/modifyEffectiveDate",
                data:"id="+document.getElementById("hiddenID").value+"&effectiveDate="+effectiveDate,
                success:function(data){
                	alert('Modify success !');
                },error:function(e) {
                    alert("error："+e);
                }
            });
        });
    });

    function downLoadPdf(){
    	var id = document.getElementById("hiddenID").value
        document.forms[0].action="${ctx}/documentDown/downDocument2/"+id;
		document.forms[0].submit();
    }
    
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
