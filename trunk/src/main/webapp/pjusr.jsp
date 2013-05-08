<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="org.svnadmin.util.I18N"%>
<%@include file="header.jsp"%>
<span style="color:green;font-weight:bold;"><a href="<%=ctx%>/pj"><%=I18N.getLbl(request,"pj.title","项目管理") %>(<%=request.getParameter("pj")%>)</a> --> <a href="<%=ctx%>/pjusr?pj=<%=request.getParameter("pj")%>"><%=I18N.getLbl(request,"pjusr.title","项目用户管理") %></a></span>
<em style="color:blue;"><%=I18N.getLbl(request,"pjusr.info","(注意:这里设置的用户密码只对这个项目有效)") %></em>
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
		alert("<%=I18N.getLbl(request,"pjusr.error.pj","项目不可以为空") %>");
		f.elements["pj"].focus();
		return false;
	}
	if(f.elements["usr"].value==""){
		alert("<%=I18N.getLbl(request,"pjusr.error.usr","用户不可以为空") %>");
		f.elements["usr"].focus();
		return false;
	}
	if(f.elements["psw"].value==""  && f.elements["newPsw"]!=null && f.elements["newPsw"].value==""){
		alert("<%=I18N.getLbl(request,"pjusr.error.psw","项目新密码不可以为空") %>");
		f.elements["psw"].focus();
		return false;
	}
	return true;
}
</script>

<form name="pjusr" action="<%=ctx%>/pjusr" method="post" onsubmit="return checkForm(this);">
	<input type="hidden" name="act" value="save">
	<table class="thinborder">
		<tr>
			<td class="lbl"><%=I18N.getLbl(request,"pj.pj","项目") %></td>
			<td><input type="hidden" name="pj" value="<%=request.getParameter("pj")%>"><%=request.getParameter("pj")%></td>
			<td class="lbl"><%=I18N.getLbl(request,"usr.usr","用户") %></td>
			<td>
			 <%if(hasManagerRight){ %>
				 <select name="usr">
				 <option value=""><%=I18N.getLbl(request,"pjusr.usr.select","选择用户") %></option>
				 <%
				 List<Usr> usrList = (List<Usr>)request.getAttribute("usrList");
				 for(int i=0;i<usrList.size();i++){
					 Usr usr = usrList.get(i);
				 %>
				 <option value="<%=usr.getUsr()%>" <%=(usr.getUsr().equals(entity.getUsr()))?"selected='selected'":"" %>><%=usr.getName()==null?usr.getUsr():usr.getName()+"("+usr.getUsr()+")"%></option>
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
			
			<td class="lbl"><%=I18N.getLbl(request,"pjusr.psw.psw","项目新密码") %></td>
			<td>
				<input type="password" name="newPsw" value="">
				<input type="hidden" name="psw" value="<%=entity.getPsw()==null?"":entity.getPsw()%>">
			<td>
				<input type="submit" value="<%=I18N.getLbl(request,"pjusr.op.submit","提交") %>">
			</td>
		</tr>
	</table>
</form>

<%if(hasManagerRight){ %>
<table class="sortable thinborder">

	<thead>
		<td>NO.</td>
		<td><%=I18N.getLbl(request,"pj.pj","项目") %></td>
		<td><%=I18N.getLbl(request,"usr.usr","用户") %></td>
		<td><%=I18N.getLbl(request,"usr.name","姓名") %></td>
		<td><%=I18N.getLbl(request,"pjusr.psw.psw","项目新密码") %></td>
		<td><%=I18N.getLbl(request,"pjusr.op.delete","删除") %></td>
	<%
	java.util.List<org.svnadmin.entity.PjUsr> list = (java.util.List<org.svnadmin.entity.PjUsr>)request.getAttribute("list");

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
		<td>
			<a href="<%=ctx%>/pjusr?act=get&pj=<%=pjUsr.getPj()%>&usr=<%=pjUsr.getUsr()%>"><%=pjUsr.getName()==null?"":pjUsr.getName() %></a>
		</td>
		<td><%=pjUsr.getPsw() %></td>
		<td>
		<a href="javascript:if(confirm('<%=I18N.getLbl(request,"pjusr.op.delete.confirm","确认删除?") %>')){del('<%=ctx%>/pjusr?&pj=<%=pjUsr.getPj()%>&usr=<%=pjUsr.getUsr()%>')}"><%=I18N.getLbl(request,"pjusr.op.delete","删除") %></a>
		</td>
	</tr>
		<%	
	}}
	%>
</table>
<%} %>