<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.svnadmin.util.EncryptUtil"%>
<%@include file="inc.jsp"%>
<span style="color:green;font-weight:bold;"><a href="pj">项目管理(<%=request.getParameter("pj")%>)</a> --> <a href="pjgr?pj=<%=request.getParameter("pj")%>">用户组管理(<%=request.getParameter("gr")%>)</a>-->设置用户</span><br><br>

<script>
function checkForm(f){
	if(f.elements["usrs"].value==""){
		alert("用户不可以为空");
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
	
	<input type="submit" value="增加用户">
</form>

<table class="sortable">
	<thead>
		<td>NO.</td>
		<td>项目</td>
		<td>组</td>
		<td>用户</td>
		<td>删除</td>
	</thead>
	<%
	java.util.List<org.svnadmin.entity.PjGrUsr> list = (java.util.List)request.getAttribute("list");

	if(list!=null){
	  for(int i = 0;i<list.size();i++){
		  org.svnadmin.entity.PjGrUsr pjGrUsr = list.get(i);
		%>
		<tr>
		<td><%=(i+1) %></td>
		<td><%=pjGrUsr.getPj() %></td>
		<td><%=pjGrUsr.getGr() %></td>
		<td><%=pjGrUsr.getUsr() %></td>
		<td><a href="javascript:if(confirm('确认删除?')){del('<%=ctx%>/pjgrusr?&pj=<%=pjGrUsr.getPj()%>&gr=<%=pjGrUsr.getGr()%>&usr=<%=pjGrUsr.getUsr()%>')}">删除</a></td>
	</tr>
		<%	
	}}
	%>
</table>