<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>product query</title>
	<link href="${ctx}/static/styles/main.css" type="text/css" rel="stylesheet" />
</head>

<body>
<div style="padding:5px;">
    <table class="table_A">
			<tr>
                <td >Product Type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	<select name="1">
                		<option value="D">
                			15D
                			</option>
                			<option value="N">
                			15N
                			</option>
                			<option value="48N">
                			48N
                			</option>
                		</select>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="query" class="cls-button" /></td>
            </tr>
    </table>
</div>
<div style="padding:0 5px 5px 5px;">
<h4 style="float:left">product List</h4>
<span style="float:right"><input type="button" value="add" class="cls-button" onclick="javascript:jQuery.dialog.open('content/Product/productAdd.html',{id:'memdiv',width :740,height :350,title:'productAdd'})"/></span>
<div class="clearboth"></div>
<table class="table_B">
        <thead>
            <tr>
                <th width="40"></th>
                <th>Product</th>
				<th>Product Type</th>
                <th>Name</th>
                <th>Remark</th>
                <th>Is Available</th>
            
            </tr>
        </thead>
        <tbody>
           
            <tr id="content/product/productAdd.html" ondblclick="addContent(this)">
            	
                <td></td>
                <td>15D/12D/10D/09D</td>
				<td>Document</td>
                <td>13Zone/15D</td>
                <td>WeightBand for</td>
                <td><input name="" type="checkbox" checked="true" /></td>
            </tr>
            <tr id="content/product/productAdd.html" ondblclick="addContent(this)">
            	<td></td>
                <td>15D/12D/10D/09D</td>
				<td>Non Document</td>
                <td>11</td>
                <td>WeightBand for</td>
                <td><input name="" type="checkbox"/></td>
            </tr>
            <tr id="content/product/productAdd.html" ondblclick="addContent(this)">
            	<td></td>
                <td>15D/12D/10D/09D</td>
				<td>Economy</td>
                <td>13</td>
                <td></td>
                <td><input name="" type="checkbox"  checked="true"/></td>
            </tr>
        </tbody>
    </table>

</div>
</body>
</html>