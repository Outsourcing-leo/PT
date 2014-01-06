<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.tnt.pt.util.PTPARAMETERS"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>New PT-Discount Confirmed</title>
<link href="${ctx}/static/styles/main.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
</head>
<script language="JavaScript">
	function previous(obj){
		if(confirm("Set discount profile ?")){
			addContent(obj);
		}else if(confirm("Set  hw rate profile ?")){
		}else {
		}
	}
</script>
<body>
<form action="#${ctx}/ptCreate/disConfirm" method="post" id="disConfirm">
	<div style="padding:5px;">
  <h4 class="title">New PT-Discount Confirmed</h4>
<table class="table_A">
    <tr>
    <%--***************隐藏域********** --%>
    <input type="hidden" value="${business.customerId}" name="business.customerId">
    <input type="hidden" value="${business.id}" name="business.id">
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
      </tr><tr>
      <th>Zone type:</th>
      <td colspan="3">
	  <select name="business.zoneType" id="zoneType">
	   <c:forEach items="${zoneTypeList}" var="zoneType" begin="0">
			<option value="${zoneType.zoneType}" <c:if test="${zoneType.zoneType eq zoneType_}">selected</c:if> >${zoneType.zoneType}</option>
		</c:forEach>
	  </select></td>
    </tr>
  </table>
  </div>
<div style="padding:0 5px 5px 5px;">
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
        <tbody id="data">
            <c:forEach items="${productList}" var="product">
				<tr>
					<td>${product.product}</td>
					<c:forEach items="${zoneGroupList}" var="zoneGroup" begin="0">
							<c:set var="key">${product.id}_${zoneGroup.id}</c:set>
						     <td><input id="zg_${key}_${business.id}" name="zg_${key}_${business.id}" value="${discountDefaultMap[key]}" size="4"/></td>
				   </c:forEach>
			   </tr>
			</c:forEach>
        </tbody>
    </table>
  <hr />
   <br>
  <div style="text-align: center">
  <input type="button" value="Previous" class="cls-button" id="Previous"/> 
   	&nbsp;&nbsp;&nbsp;<input type="button" value="Next" class="cls-button" id="next" />
   	 &nbsp;&nbsp;&nbsp;<input type="button" value="Close" class="cls-button" id="index.html" onclick="window.location.href='index.html';"/>
   </div>
   <input type="hidden" id="isFollow" value="${isFollow}" name="isFollow">
   <input type="hidden" id="payment" value="${payment}" name="payment">
</form>
</div>
<script type="text/javascript">
	$(function(){
		$("#zoneType").change(function(){
			$("#disConfirm").submit();
		});
	});
	
	$(function(){
		 $("#Previous").click(function(){
			 if($('#isFollow').val()=='NO'&&$('#payment').val()=='<%=PTPARAMETERS.PAYMENT[1]%>'){
         		 $('#payment').val('');
         		 $("#disConfirm").attr('action',"${ctx}/ptModify/disConfirm");
     			 $("#disConfirm").submit();
         	}else{
         		 window.location.href = "${ctx}/ptModify/updateCustomer/${business.id}";
         	}
		});
	});
	

    $(function(){
        $("#next").click(function(){
            //序列化表单元素，返回json数据
            var params = $("#data").find("input").serializeArray();
            var jsonString = O2String(params);
            $.ajax({
                type:"POST",
                url:"${ctx}/ptCreate/addDiscount/${payment}",
                dataType:"json",      
                contentType:"application/json",   
                data:jsonString,
                success:function(data){
                	if($('#isFollow').val()=='NO'&&$('#payment').val()=='<%=PTPARAMETERS.PAYMENT[0]%>'){
                		$('#payment').val('<%=PTPARAMETERS.PAYMENT[1]%>');
                		$("#disConfirm").attr('action',"${ctx}/ptCreate/disConfirm");
                		$("#disConfirm").submit();
                	}else{
                		$('#payment').val('');
                		$("#disConfirm").attr('action',"${ctx}/ptCreate/disConProfile");
                		$("#disConfirm").submit();
                	}
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
