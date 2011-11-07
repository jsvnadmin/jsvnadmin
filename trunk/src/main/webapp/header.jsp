<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp"%>
<%@page import="org.svnadmin.entity.Usr"%>
<%@page import="org.svnadmin.util.I18N"%>
<%@page import="org.svnadmin.servlet.BaseServlet"%>
<%
response.setHeader("Cache-Control", "no-cache, post-check=0, pre-check=0");
response.setHeader("Pragma", "no-cache");
response.setHeader("Expires", "Thu, 01 Dec 1970 16:00:00 GMT");

String ctx = request.getContextPath();

Usr _usr = BaseServlet.getUsrFromSession(request);
%>

<title><%=I18N.getLbl(request,"sys.title","SVN ADMIN")%></title>
<head>

<%if(_usr == null){%>
<script>
	alert("<%=I18N.getLbl(request,"sys.timeout","超时或未登录")%>");	
	top.location="login.jsp";
</script>
<%
return;
}%>
<script src="<%=ctx%>/resources/jquery-1.7.min.js" type="text/javascript"></script>
<script src="<%=ctx%>/resources/sorttable.js"></script>
<style type="text/css" media="screen">
<!--
table {
	border-collapse:collapse;
	border:solid #999;
	border-width:1px 0 0 1px;
}
table caption {font-size:14px;font-weight:bolder;}
table th,table td {
	border:solid #999;
	border-width:0 1px 1px 0;
	padding:2px;
	word-break : keep-all;
	white-space:nowrap;
}
tfoot td {text-align:center;background: #ECF20F;}
thead td {text-align:center;background: #ECF20F;}

tr.odd{background: #ECFCCC;}
tr.even{background: #FFFFFF;}
tr.over{background: #ECF2AF;}
-->
</style>

</head>
<%-- 选择语言 --%>
<div style="float:right">
	<%@include file="chagelang.jsp"%>
</div>
<%-- 导航 --%>
<table width="100%" style="border-width:0px 0 0 0px;">
	<tr style="border-width:0px 0 0 0px;">
		<td align="center" style="font-size:20;font-weight:bold;border-width:0px 0 0 0px;">
			<%=I18N.getLbl(request,"sys.title","SVN ADMIN")%>
			<a style="text-decoration:none;" target="_blank" title="56099823@qq.com" href="http://code.google.com/p/jsvnadmin/"><sub style="font-size: 10px;font-weight:normal;">V ${project.version}</sub></a>
		</td>
	</tr>
	<tr style="border-width:0px 0 0 0px;">
		<td align="right" style="border-width:0px 0 0 0px;">
			 <a href="<%=ctx%>/usr"><%=I18N.getLbl(request,"main.link.user","用户")%></a> 
			 <a href="<%=ctx%>/pj"><%=I18N.getLbl(request,"main.link.pj","项目")%></a> 
			 <%if(BaseServlet.hasAdminRight(request)){ %>
			 <a href="<%=ctx%>/i18n"><%=I18N.getLbl(request,"main.link.i18n","语言")%></a> 
			 <%} %>
			 <%=_usr.getUsr()%>
			 <a href="<%=ctx%>/login?act=logout"><%=I18N.getLbl(request,"main.link.logout","退出")%></a>
		</td>
	</tr>
</table>
<hr size="1">
<%-- error message --%>
<%
String errorMsg = (String)request.getAttribute(org.svnadmin.Constants.ERROR);
if(errorMsg != null){
%>
	<div style="color:red;"><%=I18N.getLbl(request,"sys.error","错误") %> <%=errorMsg%></div>
<%}%>

<script>
function del(act){
	var fm =document.getElementById("delForm");
	//clear
	var eles = fm.elements;
	if(eles != null && eles.length > 0){
		for(var i=0;i<eles.length;i++){
			if(eles[i].name=="act"){
				continue;
			}
			alert("remove "+eles[i].name);
			fm.removeChild(eles[i]);
			i--;
		}
	}
	//action?a=1&b=2
	var ind = act.indexOf("?");
	if(ind == -1){
		fm.action = act;
	}else{
		fm.action = act.substring(0,ind);
		var parms = act.substring(ind+1);
		if(parms !=null && parms.length > 0){
			var arr=parms.split("&");
			if(arr!=null && arr.length>0){
				for(var i=0;i<arr.length;i++){
					if(arr[i] == null || arr[i].length == 0){
						continue;
					}
					var pv = arr[i].split("=");
					if(pv == null || pv.length==0 || pv[0] == null){
						continue;
					}
					var inp = document.createElement("input");
					inp.setAttribute("type","hidden");
					inp.setAttribute("name",pv[0]);
					if(pv.length == 2){
						inp.setAttribute("value",pv[1]);
					}
					fm.appendChild(inp);
				}
			}
		}
	}
	//submit
	fm.submit();
}
</script>
<form id="delForm" name="delForm" action="#" method="post">
		<input type="hidden" name="act" value="del">
</form>