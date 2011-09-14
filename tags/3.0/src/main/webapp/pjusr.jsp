<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.svnadmin.util.EncryptUtil"%>
<%@page import="java.util.Calendar"%>
<%@include file="inc.jsp"%>
<span style="color:green;font-weight:bold;"><a href="pj">项目管理(<%=request.getParameter("pj")%>)</a> --> <a href="pjusr?pj=<%=request.getParameter("pj")%>">用户管理</a></span>
<em style="color:blue;">(注意:这里设置的用户密码只对这个项目有效)</em>
<br><br>

<%
boolean hasManagerRight = (Boolean)request.getAttribute("hasManagerRight");
%>
<%

%>
<%
org.svnadmin.entity.PjUsr entity = (org.svnadmin.entity.PjUsr)request.getAttribute("entity");
if(entity==null)entity=new org.svnadmin.entity.PjUsr();
%>
<script>
function checkForm(f){
	if(f.elements["pj"].value==""){
		alert("项目不可以为空");
		f.elements["pj"].focus();
		return false;
	}
	if(f.elements["usr"].value==""){
		alert("用户不可以为空");
		f.elements["usr"].focus();
		return false;
	}
	if(f.elements["psw"].value==""  && f.elements["newPsw"]!=null && f.elements["newPsw"].value==""){
		alert("项目新密码不可以为空");
		f.elements["psw"].focus();
		return false;
	}
	return true;
}
</script>

<form name="pjusr" action="<%=ctx%>/pjusr" method="post" onsubmit="return checkForm(this);">
	<input type="hidden" name="act" value="save">
	<table>
		<tr>
			<td>项目</td>
			<td><input type="hidden" name="pj" value="<%=request.getParameter("pj")%>"><%=request.getParameter("pj")%></td>
			<td>用户</td>
			<td>
			 <%if(hasManagerRight){ %>
				 <select name="usr">
				 <option value="">选择用户</option>
				 <%
				 List<Usr> usrList = (List<Usr>)request.getAttribute("usrList");
				 for(int i=0;i<usrList.size();i++){
					 Usr usr = usrList.get(i);
				 %>
				 <option value="<%=usr.getUsr()%>" <%=(usr.getUsr().equals(entity.getUsr()))?"selected='selected'":"" %>><%=usr.getUsr()%></option>
				 <%
				 }
				 %>	
				 </select>
				 <span style="color:red;">*</span>
			<%}else{ %>
				 <input type="hidden" name="usr" value="<%=entity.getUsr()==null?"":entity.getUsr()%>">
				 <%=entity.getUsr()==null?"":entity.getUsr()%>
			<%} %>

			 
			</td>
			
			<td>项目新密码</td>
			<td>
				<input type="password" name="newPsw" value="" onkeyup="value=value.replace(/[^_\-A-Za-z0-9]/g,'')">
				<input type="hidden" name="psw" value="<%=entity.getPsw()==null?"":entity.getPsw()%>">
			<td>
				<input type="submit" value="提交">
			</td>
		</tr>
	</table>
</form>

<%if(hasManagerRight){ %>
<table class="sortable">

	<thead>
		<td>NO.</td>
		<td>项目</td>
		<td>用户</td>
		<td>密码</td>
		<td>删除</td>
	<%
	java.util.List<org.svnadmin.entity.PjUsr> list = (java.util.List)request.getAttribute("list");

	if(list!=null){
	  for(int i = 0;i<list.size();i++){
		  org.svnadmin.entity.PjUsr pjUsr = list.get(i);
		%>
		<tr>
		<td><%=(i+1) %></td>
		<td>
			<%=pjUsr.getPj() %>
		</td>
		
		<td>
			<a href="<%=ctx%>/pjusr?act=get&pj=<%=pjUsr.getPj()%>&usr=<%=pjUsr.getUsr()%>"><%=pjUsr.getUsr() %></a>
		</td>
		<td><%=pjUsr.getPsw() %></td>
		<td>
		<a href="javascript:if(confirm('确认删除?')){del('<%=ctx%>/pjusr?&pj=<%=pjUsr.getPj()%>&usr=<%=pjUsr.getUsr()%>')}">删除</a>
		</td>
	</tr>
		<%	
	}}
	%>
</table>
<%} %>