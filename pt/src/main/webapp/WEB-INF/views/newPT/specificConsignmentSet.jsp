<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Consignment Profile by Country</title>
<link href="${ctx}/static/styles/main.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
</head>
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
				.cls-button{/*æé®æ ·å¼*/
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
<script language="JavaScript">
	function previous(obj){
		if(confirm("Set discount profile ?")){
			
			}else if(confirm("Set  hw rate profile ?")){
				
				}else {
						
					}
		
	}
	
</script>

<body>
<form action="#">
	<div style="padding:5px;">
  <span style="background-color:#F16E1E;">Consignment Profile by Country</span>
  <table class="table_A">
    <tr>
      <th>PRODUCT:</th>
      <td>${product.product}</td>
      <th>Zone:</th>
      <td>${zoneGroup.zone}</td>
      </tr>
  </table>
  </div>
  <div style="text-align:left;">
  	<span style="color:blue;font-size:16">* Add Consignments in blanks.(Countries highlighted with blue are those countries applied HW rates)</span>
  </div>
<div style="padding:0 5px 5px 5px;">
<input type="button" value="add" class="cls-button"  id="add" onclick="addCountry()"/>
</div>
<div class="clearboth"> </div>

<table class="table_B" width="100%">
        <thead>
            <tr style="background-color:#F16E1E;" id="trhead">
                <th width="20%">Weightband</th>
                	 <c:forEach items="${countryListinSpec}" var="country" begin="0">
							<th id="${country.id}">${country.countryName}</th>
					</c:forEach>
            </tr>
        </thead>
        <tbody>
           <c:forEach items="${weightBandList}" var="weightBand">
				<tr id="trData_${weightBand.id}">
					  <td>${weightBand.name}</td>
					  <c:forEach items="${countryListinSpec}" var="country" begin="0">
					 	    <c:set var="key">${businessId}_${product.id}_${zoneGroup.id}_${weightBand.id}_${country.id}</c:set>
							<td id="${weightBand.id}_${country.id}"><input name="${businessId}_${product.id}_${zoneGroup.id}_${weightBand.id}_${country.id}"  value="${scMap[key]}" size="4"/></td>
				 	  </c:forEach> 
			   </tr>
			  </c:forEach>
			  <tr id="trOpea">
			  		<td>operate</td>
			  		<c:forEach items="${countryListinSpec}" var="country" begin="0">
							<td id="${country.id}_"><a href='#' onclick="deltr_(${country.id})">delete</a></td>
				 	</c:forEach>
			  </tr>
       </tbody>
</table>
   <br>
  <div style="text-align: center">
  	<input type="button" value="Confirm" class="cls-button"  id="confirm"/>
   	 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="Close" class="cls-button" onclick="window.close();"/>
  </div>
</form>
</div>
<script type="text/javascript">

function addCountry(){
    var weightBands=jQuery.parseJSON('${weightBandLists}');
    var countrys= jQuery.parseJSON('${countryLists}');
    //增加<tr/>
    if(countrys.length==0){
    	alert("this zone has no country");
    	return;
    }
	var appendTr = "<select onchange='jsFunction(this)' id='select0'>";
	appendTr += "<option value=''>---select---</option>";
    for(var m=0; m < countrys.length; m++){
		appendTr +="<option value='"+countrys[m].id+"'>"+countrys[m].countryCode+"</option>";
	}
    appendTr +="</select>";
	$("#trhead").append("<th id='th0'>"+appendTr+"</th>");
	
	for(var i=0; i <= weightBands.length; i++){
		if(i==weightBands.length){
			$("#trOpea").append("<td id='"+i+"'><a href='#' onclick='deltr("+i+")'>delete</a></td>");
		}
		$("#trData_"+weightBands[i].id).append("<td id='"+weightBands[i].id+"_"+i+"'></td>");
	}
}

	function jsFunction(choose){
	  var countryid = choose.options[choose.selectedIndex].value;
	  var countryname =  choose.options[choose.selectedIndex].text;
	  if($("#"+countryid).html()!=null){
		  alert(countryname+" is exist,please select another Country！");
		  return;
	  }
	  var myDiv = $("#th0");//找到初始select
	  myDiv.html('');//清空原有的html
	  myDiv.attr("id",countryid);//替换myDiv的id
	  myDiv.append(countryname);//替换myDiv的内容
	  
	  var weightBands=jQuery.parseJSON('${weightBandLists}');
	  for(var i=0; i <= weightBands.length; i++){
		    if(i==weightBands.length){
		    	 var myDiv1 = $("#"+i);//找到初始select
		    	 myDiv1.html('');//清空原有的html
		    	 myDiv1.attr("id",countryid+"_");//替换myDiv的id
		    	 myDiv1.append("<a href='#' onclick='deltr_("+countryid+")'>delete</a>");//替换myDiv的内容
				//$("#trOpea").append("<td id='"+countryid+"'>删除</td>");
			}
		     var myDiv2 = $("#"+weightBands[i].id+"_"+i);//找到初始select
	    	 myDiv2.html('');//清空原有的html
	    	 myDiv2.attr("id",weightBands[i].id+"_"+countryid);//替换myDiv的id
	    	 myDiv2.append("<input name='"+${businessId}+"_"+${product.id}+"_"+${zoneGroup.id}+"_"+weightBands[i].id+"_"+countryid+"'  value='' size='4'/>");//替换myDiv的内容
			//$("#trData_"+weightBands[i].id).append("<td id='"+weightBands[i].id+"_"+countryid+"'><input name='"+${businessId}+"_"+${product.id}+"_"+${zoneGroup.id}+"_"+weightBands[i].id+"_"+countryid+"'  value='' size='4'/></td>");
	  }
	}

	 //删除<tr/>
    function deltr(i){
        var weightBands=jQuery.parseJSON('${weightBandLists}');
        
        $("th[id='th0']").remove();
  	  	for(var i=0; i <= weightBands.length; i++){
  		    if(i==weightBands.length){
  		    	$("td[id='"+i+"']").remove();
  			}
  		    $("td[id='"+weightBands[i].id+"_"+i+"']").remove();
  	   }
        
    }
    
	 //删除<tr/>
    function deltr_(countryid){
        var weightBands=jQuery.parseJSON('${weightBandLists}');
        
        $("th[id='"+countryid+"']").remove();
  	  	for(var i=0; i <= weightBands.length; i++){
  		    if(i==weightBands.length){
  		    	$("td[id='"+countryid+"_']").remove();
  			}
  		    $("td[id='"+weightBands[i].id+"_"+countryid+"']").remove();
  	   }
        
    }
    
	
    $(function(){
        $("#confirm").click(function(){
            //序列化表单元素，返回json数据
            var params = $("tbody").find("input").serializeArray();
            var jsonString = O2String(params);
            if(jsonString.length<=2){
            	alert("can not save cause no data!");
            	return;
            }
            $.ajax({
                type:"POST",
                url:"${ctx}/ptCreate/addSpecificConsignmentSet/${payment}",
                dataType:"text",      
                contentType:"application/json",   
                data:jsonString,
                success:function(data){
                	window.returnValue = data;
                	window.close();
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
