<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.svnadmin.Constants"%>
<%@page import="org.svnadmin.util.I18N"%>
<%@include file="header.jsp"%>
<span style="color:green;font-weight:bold;"><a href="<%=ctx%>/pj"><%=I18N.getLbl(request,"pj.title","项目管理") %>(<%=request.getParameter("pj")%>)</a> --> <%=I18N.getLbl(request,"pjauth.title","权限管理") %></span><br><br>

<script type="text/javascript" src="<%=ctx%>/resources/treeview/treeview.js"></script>
<link type="text/css" rel="stylesheet" href="<%=ctx%>/resources/treeview/treeview.css"></link>
<script type="text/javascript">
<!--
AjaxTreeView.config.onclick=function(o,a){
	var p=o.getAttribute("param");if(p==null)p="";
	var url="<%=ctx%>/pjauth";
	if(url!=""){
	  if(p!=""){
		  if(url.indexOf("?")>0){
		  	url=url+"&"+p;
		  }else{
		  	url=url+"?"+p;
		  }
	   }
	   //alert(url);
	   go(url,"pjauthWindow");
	   return false;
	}
};
$(document).ready(function (){
	AjaxTreeView.toggle(document.getElementById("svnroot"));
});
function freshTree(){
	var $p = $("#path");
	var p = $p.val();
	if(p==""){
		p="/";
		$p.val(p);
	}else if(p.substring(0,1)!="/"){
		p = "/"+p;
		$p.val(p);
	}
	var $r = $("#svnroot");
	$r.children("ul").first().remove();
	AjaxTreeView.close($r[0]);
	$r.attr("param","pj=<%=request.getParameter("pj")%>&path="+p);
	$r[0].loading = false;
	$r[0].loaded = false;
	AjaxTreeView.open($r[0]);
}
//-->
</script>

<table style="width:100%;height:80%;">
	<tr>
		<td valign="top" style="width:300px;">
			<input type="text" id="path" value="<%=request.getAttribute("path")%>"><input onclick="freshTree();" type="button" value="<%=I18N.getLbl(request, "rep.btn.go", "刷新")%>">
			<div class="tree filetree treeview" style="width:300px;height:500px;overflow: auto;">
				<ul>
					<li id="svnroot" class="expandable lastExpandable" treeId="rep" param="pj=<%=request.getParameter("pj")%>&path=<%=request.getAttribute("path")%>">
						<div class="hitarea expandable-hitarea lastExpandable-hitarea" onclick='$att(this.parentNode);'></div>
						<span class="folder" onclick='$att(this.parentNode);'>
						<a href='javascript:void(0);' onclick='$atc(this)'><%=request.getAttribute("root")%></a>
						</span>
					</li>
				</ul>
			</div>

		</td>
		<td valign="top">
			<iframe height="100%" width="100%" style="border:0px;" name="pjauthWindow" src="<%=ctx%>/pjauth?pj=<%=request.getParameter("pj")%>&path=<%=request.getAttribute("path")%>"></iframe>
		</td>
	</tr>
</table>