<%@ page language="java" pageEncoding="UTF-8"%>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<%
	String path1 = request.getContextPath();
	String basePath1 = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path1 + "/";
	
	org.tnt.pt.vo.BaseVO model =  (org.tnt.pt.vo.BaseVO)request.getAttribute("model");
	//com.opensymphony.xwork2.util.ValueStack vs = (com.opensymphony.xwork2.util.ValueStack)request.getAttribute("struts.valueStack");
    org.tnt.pt.util.Navigate navigate = (org.tnt.pt.util.Navigate)model.getNavigate();
    int left,right,prev,next;

    int pageId = 0;
        pageId=navigate.getPageId();
    int pageCount =0;
        pageCount= navigate.getPageCount();
    int rowCount =0;
        rowCount= navigate.getRowCount();
    int curPageSize =0;
        curPageSize= rowCount - navigate.getPageOffset();

    if(curPageSize > navigate.getPageSize()) {
        curPageSize = navigate.getPageSize();
    }
    else if(curPageSize<0) {
        curPageSize = 0;
    }
    if(pageCount == 0) {
        pageCount = 1;
    }

    left  = (pageId - 3 > 0) ? (pageId - 3):1;
    right = (pageId + 3 < pageCount)?(pageId + 3):pageCount;
    prev  = (pageId - 1 > 0)?(pageId - 1):1;
    next  = (pageId + 1 < pageCount)?(pageId + 1):pageCount;
%>
<script language="javascript">

	function doNavigate(inPageId) { 
		$("input[name='navigate.pageId']").val(inPageId);
        document.forms[0].submit();
    }
	function getRowCount(){
		var rowCount = "<%=rowCount%>";
		return rowCount;
	}
	function toPage(){
		var pageId = document.getElementById("pageId").value;
		var pageCount = "<%=pageCount%>";
		
		if (pageId.length > 1) {
			if (pageId.substring(0, 1) == '0') {
				alert("输入的页数不合法.");
				return;
			}
		}
		
		if (pageId!=null&&(pageId<=0 || pageId!=Number(pageId))) {
			alert("输入的页数不合法.");
			return;
		}
		
		if (Number(pageId) > pageCount) {
			alert("输入的页数大于总页数.");
			return;
		} else {
			doNavigate(pageId);
		}
	}
</script>
<input type="hidden" name="navigate.pageId" value="${model.navigate.pageId}">
<input type="hidden" name="navigate.orderField" value="${model.navigate.orderField}">
<input type="hidden" name="navigate.orderDirection" value="${model.navigate.orderDirection}">
<table border="0"  cellspacing="0" cellpadding="0" align="right" height="35" width="100%">
  <tr>
    <!--  <td width="29%" align="left" valign="middle">&nbsp; 
    </td>-->
    <td width="70%" align="right" ><font style="font-weight: bold" size="2">Number&nbsp;<%=pageId%>&nbsp;page/&nbsp;total&nbsp;<%=pageCount%>&nbsp;page;&nbsp;CurPage&nbsp;<%=curPageSize%>&nbsp;count/&nbsp;total&nbsp;<%=rowCount%>&nbsp;count</font>
    &nbsp;&nbsp;&nbsp;
<a href="javascript:doNavigate(1)" class="icon-fast-backward"></a>
<a href="javascript:doNavigate(<%=prev%>)" class="icon-chevron-left"></a>

	<%
    for(int i = left ; i <= right ; i++)  {
        if(i == pageId)
            out.println("<B><A HREF=\"javascript:doNavigate(" + i + ")\" title=\""+i+"\"><FONT COLOR=\"#FF0000\">" + i + "</FONT></A></B>");
        else
            out.println("<B><A HREF=\"javascript:doNavigate(" + i + ")\" title=\""+i+"\">" + i + "</A></B>");
    }
%>
<a href="javascript:doNavigate(<%=next%>)" class="icon-chevron-right"></a>
<a href="javascript:doNavigate(<%=pageCount%>)" class=" icon-fast-forward"></a> &nbsp;&nbsp;&nbsp;
    </td>
    <td width="20%" align="right" ><font style="font-weight: bold">go to Number&nbsp;<input type="text" id="pageId" size=2 class="input-mini">&nbsp;page</font>
    </td> 
    <td width="2%" align="right" ><a href="javascript:toPage()" class="btn btn-large">GO</a></td>
  </tr>
</table>