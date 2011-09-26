<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.svnadmin.Constants"%>
<%@page import="org.svnadmin.util.EncryptUtil"%>
<%@include file="header.jsp"%>
<span style="color:green;font-weight:bold;">用户管理</span><br><br>
<%
boolean hasAdminRight = (Boolean)request.getAttribute("hasAdminRight");
%>

<%
org.svnadmin.entity.Usr entity = (org.svnadmin.entity.Usr)request.getAttribute("entity");
if(entity==null)entity=new org.svnadmin.entity.Usr();
%>
<script>
function checkForm(f){
	if(f.elements["usr"].value==""){
		alert("用户不可以为空");
		f.elements["usr"].focus();
		return false;
	}
	if(f.elements["psw"].value==""  && f.elements["newPsw"]!=null && f.elements["newPsw"].value==""){
		alert("密码不可以为空");
		f.elements["psw"].focus();
		return false;
	}
	return true;
}
</script>
<form name="usr" action="<%=ctx%>/usr" method="post" onsubmit="return checkForm(this);">
	<input type="hidden" name="act" value="save">
	<table>
		<tr>
			<td>用户</td>
			<td>
				<%if(hasAdminRight){ %>
					<input type="text" name="usr" value="<%=entity.getUsr()==null?"":entity.getUsr()%>" 
					onkeyup="value=value.replace(/[^_\-A-Za-z0-9*]/g,'')">
					<span style="color:red;">*</span>
				<%}else{ %>
				   	<input type="hidden" name="usr" value="<%=entity.getUsr()==null?"":entity.getUsr()%>" >
				   	<%=entity.getUsr()==null?"":entity.getUsr()%>
				<%} %>				
			</td>
			
			<td>新密码</td>
			<td>
			<input type="password" name="newPsw" value="">
			<input type="hidden" name="psw" value="<%=entity.getPsw()==null?"":entity.getPsw()%>">
			</td>
			
			<%if(hasAdminRight){ %>
			<td>角色</td>
			<td>
				<select name="role">
					<option value="">选择角色</option>
					<option value="<%=Constants.USR_ROLE_ADMIN%>" <%=Constants.USR_ROLE_ADMIN.equals(entity.getRole())?"selected='selected'":""%>>管理员</option>
				</select>
			</td>
			<%} %>
			<td>
				<input type="submit" value="提交">
			</td>
		</tr>
	</table>
</form>

<%if(hasAdminRight){ %>

<table class="sortable">

	<thead>
		<td>NO.</td>
		<td>用户</td>
		<td>密码</td>
		<td>角色</td>
		<td>删除</td>
	</thead>
	<%
	java.util.List<org.svnadmin.entity.Usr> list = (java.util.List<org.svnadmin.entity.Usr>)request.getAttribute("list");

	if(list!=null){
		int no = 1;	  
		for(int i = 0;i<list.size();i++){
		  org.svnadmin.entity.Usr usr = list.get(i);
		  if("*".equals(usr.getUsr())){
			  continue;
		  }
		%>
		<tr>
		<td><%=(no++) %></td>
		<td>
			<a href="<%=ctx%>/usr?act=get&usr=<%=usr.getUsr()%>"><%=usr.getUsr() %></a>
		</td>
		<td><%=usr.getPsw() %></td>
		<td><%=usr.getRole()==null?"":usr.getRole() %></td>
		<td><a href="javascript:if(confirm('确认删除?')){del('<%=ctx%>/usr?usr=<%=usr.getUsr()%>')}">删除</a></td>
	</tr>
		<%	
	}}
	%>
</table>
<%} %>