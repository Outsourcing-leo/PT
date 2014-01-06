<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>New PT-Discount Profile</title>
<link href="${ctx}/static/styles/main.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
</head>
		<title>Set Specific Countries </title>
		<style type="text/css">
			<!--
				table {
					width:100%;
					border-collapse:collapse;
					border:1px solid #ccc;
				}
				th, td {
					border:1px solid #ccc;
					height:25px;
					line-height:25px;
					padding:0 0px;
				}
				.cls-button{
   				    background-color:#F16E1E;
					height:20px;
					border:1px solid #aa3535;
					font-size:12px;
					color:#000;
					margin:0 10px;
					padding:0 3px;
					cursor: pointer;
					text-align:center;	
				}
			-->
		</style>
	</head>
<body style="background-color:#F6F4F6">
<br>
<div>
	<span style="background-color:#F16E1E;">
		Set Specific Countries
	</span>
</div>
<table style="border:1px solid #ccc " width="100%">
<tr>
	<td>
		<B>PRODUCT</B>
		</td>
	<td>
		${product.product}
		</td>
	<td>
		<b>Zone</b>
		</td>
	<td>
		${zoneGroup.zone}
		</td>
	</tr>	
</table>
<br>
<table width="100%">
	<thead style="background-color:#F5C07B;">
		<th>
			<input type="checkbox" />
			</th>
			<th>
				Country Code
			</th>
			<th>
				Country Name
			</th>
		</thead>
		<tbody>
			<c:forEach items="${countryList}" var="country" begin="0">
				 <tr>
				   <td> <input type="checkbox" name="${businessId}_${product.id}_${zoneGroup.id}_${country.id}" id="${country.id}"/> </td>
				   <td>${country.countryCode}</td>
				   <td>${country.countryName}</td>
				 </tr>
			</c:forEach>
		</tbody>
</table>
<br>
<div style="text-align:center">
    	<input type="button" value="Confirm" class="cls-button" id="next" onclick="save()"//>
    	<input type="button" value="Close" class="cls-button" onclick="window.close();"/>
    	<input type="hidden" value="${checked}" id="checked"/>
</div>

<script type="text/javascript">
	//让已经被选过的数据checked
	$(function(){
		var checkedArr = new Array();
		checkedArr = $("#checked").val().split(";");
		for(var i=0;i<checkedArr.length;i++){
			$("#"+checkedArr[i]).attr("checked",true);
		}
	});
	
    function save(){
    	var params = $("tbody").find("input:checked").serializeArray();
        var jsonString = O2String(params);
        if(jsonString.length<=2){
			alert("no checked country!");
        }else{
	        $.ajax({
	            type:"POST",
	            url:"${ctx}/ptCreate/addspecificCountry/${payment}",
	            dataType:"json",      
	            contentType:"application/json",   
	            data:jsonString,
	            success:function(data){
	            	window.close();
	            },
	            error:function(e) {
	                alert("error："+e);
	            }
	        });
        }
    }

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