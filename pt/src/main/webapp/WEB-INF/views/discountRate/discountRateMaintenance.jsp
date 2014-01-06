<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>New PT-Discount Confirmed</title>
<link href="${ctx}/static/styles/main.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
</head>
<body>
<form action="${ctx}/discount/query" method="post" id="disForm">
	<div style="padding:5px;">
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
<h4 style="float:left">Discount rate List</h4>
<div class="clearboth"></div>
<table class="table_B" width="100%">
        <thead>
						<tr align="center">
                <th colspan="14" style="text-align:center;">Discounts Requested (% off full tariff)</th>
            </tr>
            <tr>
                <th>PRODUCT</th>
                <c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
						<th>${zoneGroup.zone}</th>
				</c:forEach>
            </tr>
        </thead>
        <tbody>
             <c:forEach items="${productList}" var="product">
				<tr>
					<td>${product.product}</td>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
							<c:set var="key">${product.id}_${zoneGroup.id}</c:set>
						     <td><input id="zg_${key}" name="zg_${key}" value="${discountDefaultMap[key]}" size="4"/></td>
				   </c:forEach>
			   </tr>
			</c:forEach>
        </tbody>
    </table>
  <br>    
    <div align="right" >
    <input type="button" value="Save" class="cls-button" onclick="save();" id="save"/>
</form>
</div>
<script type="text/javascript">
    $(function(){
        $("#save").click(function(){
            //序列化表单元素，返回json数据
            var params = $("tbody").find("input").serializeArray();
            var jsonString = O2String(params);
            $.ajax({
                type:"POST",
                url:"${ctx}/discount/add",
                dataType:"json",      
                contentType:"application/json",   
                data:jsonString,
                success:function(data){
                    alert("success");
                    $("#disForm").submit();
                    //window.location.href="${ctx}/discount/init";
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
