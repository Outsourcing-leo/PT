<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.tnt.pt.dmsentity.User"%>
<%@ page import="org.tnt.pt.util.PTPARAMETERS"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<% 
	//User user = (User)getSessionUserMananger(request);
	User user = (User) request.getSession().getAttribute("user");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>菜单页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<link href="${ctx}/static/styles/main.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/static/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/js/fun.js" type="text/javascript" ></script>
	<!--[if lt IE 9]>
		<script type="text/javascript" src="${ctx}/static/js/ie9.js"></script>
	<![endif]-->
	<script>
	//展开折叠按钮
$(function(){ 
	$('.btn_all_show').click(function(){
		$('.menu_sub dd').show();
		$('.menu_sub dt').css({background:'url(${ctx}/static/images/icon.png) no-repeat  10px -70px'});
	});
	$('.btn_all_hide').click(function(){
		$('.menu_sub dd').hide();
		$('.menu_sub dt').css({background:'url(${ctx}/static/images/icon.png) no-repeat  10px -44px'});
	});
});
</script>
  </head>
  
  <body>
    <div class="show_hide">
        	<a href="#" class="btn_show_hide">Hide</a>
            <a href="#" class="btn_all_show">Open</a>
            <a href="#" class="btn_all_hide">Close</a>
    </div>
    <div class="west">
    <div class="west_inner">
    	<div class="menu_sub">
        	<dl>
            	<dt onclick="addMenuSub(this);">Base info</dt>
				<dd><a href="${ctx}/ShowReport.wx?PAGEID=productlistpage" target="main">Product</a></dd>
                <dd><a href="${ctx}/ShowReport.wx?PAGEID=chargeWeightbandlistpage" target="main">Chargeable Weight Band</a></dd>
                <dd><a href="${ctx}/ShowReport.wx?PAGEID=WeightbandGrouplistpage" target="main">Weight Band Group</a></dd>
				<dd><a href="${ctx}/ShowReport.wx?PAGEID=highWeightBandlistpage" target="main">High Weight Band</a></dd>
                <dd><a href="${ctx}/ShowReport.wx?PAGEID=Countrylistpage" target="main">Country Maintenance</a></dd>
				<dd><a href="${ctx}/ShowReport.wx?PAGEID=ZoneGrouplistpage" target="main">ZoneGroup</a></dd>
				<dd><a href="${ctx}/ShowReport.wx?PAGEID=ZoneTypelistpage" target="main">ZoneType</a></dd>
                <dd><a href="${ctx}/ShowReport.wx?PAGEID=CountryGeoZonelistpage" target="main">Country-GEO Zone</a></dd>
                <dd><a href="${ctx}/ShowReport.wx?PAGEID=CountryZonelistpage" target="main">Country-Zone</a></dd>
                <dd><a href="${ctx}/ShowReport.wx?PAGEID=DepotBSMlistpage" target="main">Depot-BSM</a></dd>
                <dd><a href="${ctx}/ShowReport.wx?PAGEID=DepotRGMlistpage" target="main">Depot-RSM</a></dd>
                <dd><a href="${ctx}/discount/init" target="main" >Discount Rate</a></dd>
                <dd><a href="${ctx}/traiff/init" target="main" >Full Tariff</a></dd>
            </dl>
           <dl>
            	<dt onclick="addMenuSub(this);">PT Process</dt>
                <%if(user.getRole_name()!=null&&user.getRole_name().equals(PTPARAMETERS.ROLE_NAME[4])){//sales%>
                	<dd><a href="${ctx}/ptCreate/addCustomer" target="main">Apply a New PT</a></dd>
                    <dd><a href="${ctx}/ptQuery/ptModifyInit" target="main">Modify a PT</a></dd>
                    <dd><a href="${ctx}/ptQuery/confirmRateInit" target="main">Confirm Rates</a></dd>
                <%
        		}else if(user.getRole_name()!=null&&user.getRole_name().equals(PTPARAMETERS.ROLE_NAME[0])){//BSM  RSM%>
        			<dd><a href="${ctx}/ptQuery/ptApprove" target="main">Approve a PT</a></dd>
        		<%}else if(user.getRole_name()!=null&&user.getRole_name().equals(PTPARAMETERS.ROLE_NAME[2])){//Commercial %>
        			<dd><a href="${ctx}/ptQuery/analysePT" target="main">Analyse a PT</a></dd>
        			<dd><a href="${ctx}/ptQuery/ptModifyInit" target="main">Modify a PT</a></dd>
        		<%}else if(user.getRole_name()!=null&&user.getRole_name().equals(PTPARAMETERS.ROLE_NAME[1])){//Commercial %>
        			<dd><a href="${ctx}/ptQuery/analysePT" target="main">Analyse a PT</a></dd>
        			<dd><a href="${ctx}/ptQuery/ptModifyInit" target="main">Modify a PT</a></dd>
        		<}else if(user.getRole_name()!=null&&user.getRole_name().equals(PTPARAMETERS.ROLE_NAME[3])){//Billing %>
        			<dd><a href="${ctx}/ptQuery/PTSLoadInit" target="main">New PTs Load application</a></dd>
        		<%} %>
				<!-- <dd><a href="${ctx}/ptQuery/confirmToBillingInit" target="main">Confirm  a  PT ( to  Billing )</a></dd> -->
                
                <dd><a href="${ctx}/ptQuery/queryPTInit" target="main">PT Query</a></dd>
				<dd><a href="${ctx}/ptQuery/hisPTQueryInit" target="main">History PT Query</a></dd>
				<dd><a href="${ctx}/ptQuery/getZoneTableInit" target="main">Zone Table</a></dd>
				<!--<dd><a id="content/Query/filePlace.html" href="#" onclick="addContent_bc(this)">File Place</a></dd>-->
            </dl>
        </div>
    </div>
</div>
  </body>
</html>