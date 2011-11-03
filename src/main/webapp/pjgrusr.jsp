<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.svnadmin.util.I18N"%>
<%@include file="header.jsp"%>
<span style="color:green;font-weight:bold;"><a href="<%=ctx%>/pj"><%=I18N.getLbl(request,"pj.title","项目管理") %>(<%=request.getParameter("pj")%>)</a> --> <a href="<%=ctx%>/pjgr?pj=<%=request.getParameter("pj")%>"><%=I18N.getLbl(request,"pjgr.title","用户组管理") %>(<%=request.getParameter("gr")%>)</a>--><%=I18N.getLbl(request,"pjgrusr.title","项目组用户管理") %></span><br><br>

<script>
function checkForm(f){
	if(f.elements["usrs"].value==""){
		alert("<%=I18N.getLbl(request,"pjgrusr.error.usr","用户不可以为空") %>");
		f.elements["usrs"].focus();
		return false;
	}
	return true;
}
</script>

<form name="pjgrusr" action="<%=ctx%>/pjgrusr" method="post" onsubmit="return checkForm(this);">
	<input type="hidden" name="act" value="save">
	<input type="hidden" name="pj" value="<%=request.getParameter("pj")%>">
	<input type="hidden" name="gr" value="<%=request.getParameter("gr")%>">
	<select name="usrs" multiple="multiple">
		<%
		java.util.List<org.svnadmin.entity.Usr> usrlist = (java.util.List<org.svnadmin.entity.Usr>)request.getAttribute("usrList");
		if(usrlist!=null){	
		for(int i = 0;i<usrlist.size();i++){
			org.svnadmin.entity.Usr usr = usrlist.get(i);
		%>
		<option value="<%=usr.getUsr()%>"><%=usr.getUsr()%></option>
		<%}}%>
	</select>
	
	<input type="submit" value="<%=I18N.getLbl(request,"pjgrusr.op.submit","增加用户") %>">
</form>

<table class="sortable">
	<thead>
		<td><%=I18N.getLbl(request,"sys.lbl.no","NO.") %></td>
		<td><%=I18N.getLbl(request,"pj.pj","项目") %></td>
		<td><%=I18N.getLbl(request,"pj_gr.gr","项目组") %></td>
		<td><%=I18N.getLbl(request,"usr.usr","用户") %></td>
		<td><%=I18N.getLbl(request,"pjgrusr.op.delete","删除") %></td>
	</thead>
	<%
	java.util.List<org.svnadmin.entity.PjGrUsr> list = (java.util.List<org.svnadmin.entity.PjGrUsr>)request.getAttribute("list");

	if(list!=null){
	  for(int i = 0;i<list.size();i++){
		  org.svnadmin.entity.PjGrUsr pjGrUsr = list.get(i);
		%>
		<tr>
		<td><%=(i+1) %></td>
		<td><%=pjGrUsr.getPj() %></td>
		<td><%=pjGrUsr.getGr() %></td>
		<td><%=pjGrUsr.getUsr() %></td>
		<td><a href="javascript:if(confirm('<%=I18N.getLbl(request,"pjgrusr.op.delete.confirm","确认删除?") %>')){del('<%=ctx%>/pjgrusr?&pj=<%=pjGrUsr.getPj()%>&gr=<%=pjGrUsr.getGr()%>&usr=<%=pjGrUsr.getUsr()%>')}"><%=I18N.getLbl(request,"pjgrusr.op.delete","删除") %></a></td>
	</tr>
		<%	
	}}
	%>
</table>