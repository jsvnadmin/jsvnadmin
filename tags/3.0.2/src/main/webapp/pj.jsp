<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.svnadmin.util.I18N"%>
<%@page import="org.svnadmin.entity.*"%>
<%@page import="org.svnadmin.Constants"%>
<%@include file="header.jsp"%>
<span style="color:green;font-weight:bold;"><%=I18N.getLbl(request,"pj.title","项目管理") %></span><br><br>

<%
boolean hasAdminRight = (Boolean)request.getAttribute("hasAdminRight");
boolean hasManagerRight = (Boolean)request.getAttribute("hasManagerRight");
%>

<%
if(hasManagerRight){
Pj entity = (Pj)request.getAttribute("entity");
if(entity==null)entity=new Pj();
%>
<script>
function checkForm(f){
	if(f.elements["pj"].value==""){
		alert("<%=I18N.getLbl(request,"pj.error.pj","项目不可以为空") %>");
		f.elements["pj"].focus();
		return false;
	}
	if(f.elements["path"].value==""){
		alert("<%=I18N.getLbl(request,"pj.error.path","路径不可以为空") %>");
		f.elements["path"].focus();
		return false;
	}
	if(f.elements["url"].value==""){
		alert("<%=I18N.getLbl(request,"pj.error.url","URL不可以为空") %>");
		f.elements["url"].focus();
		return false;
	}
	return true;
}
</script>
<form name="pj" action="<%=ctx%>/pj" method="post" onsubmit="return checkForm(this);">
	<input type="hidden" name="act" value="save">
	<table class="thinborder">
		<tr>
			<td class="lbl"><%=I18N.getLbl(request,"pj.pj","项目") %></td>
			<td>
			<%if(hasAdminRight){ %>
			<input type="text" name="pj" value="<%=entity.getPj()==null?"":entity.getPj()%>" onkeyup="value=value.replace(/[^_\-A-Za-z0-9]/g,'')"><span style="color:red;">*</span>
			<%}else{ %>
			<input type="hidden" name="pj" value="<%=entity.getPj()==null?"":entity.getPj()%>">
			<%=entity.getPj()==null?"":entity.getPj()%>
			<%} %>
			</td>
			<td class="lbl"><%=I18N.getLbl(request,"pj.type","类型") %></td>
			<td>
				<select name="type">
					<option value="<%=Constants.SVN%>" <%=Constants.SVN.equals(entity.getType())?"selected='selected'":""%> ><%=I18N.getLbl(request,"pj.type.svn","svn") %></option>
					<option value="<%=Constants.HTTP%>" <%=Constants.HTTP.equals(entity.getType())?"selected='selected'":""%> ><%=I18N.getLbl(request,"pj.type.http","http") %></option>
					<option value="<%=Constants.HTTP_MUTIL%>" <%=Constants.HTTP_MUTIL.equals(entity.getType())?"selected='selected'":""%> ><%=I18N.getLbl(request,"pj.type.http.mutil","http(多库)") %></option>
				</select>
				<span style="color:red;">*</span>
			</td>
			
		</tr>
		<tr id="path_tr">
			<td class="lbl"><%=I18N.getLbl(request,"pj.path","路径") %></td>
			<td colspan="5"><input type="text" name="path" value="<%=entity.getPath()==null?"":entity.getPath()%>" style="width:400px;"><span style="color:red;">*</span></td>
		</tr>
		<tr id="url_tr">
			<td class="lbl"><%=I18N.getLbl(request,"pj.url","URL") %></td>
			<td colspan="5"><input type="text" name="url" value="<%=entity.getUrl()==null?"":entity.getUrl()%>" style="width:400px;"><span style="color:red;">*</span></td>
		</tr>
		<tr>
			<td class="lbl"><%=I18N.getLbl(request,"pj.des","描述") %></td>
			<td colspan="5"><input type="text" name="des" value="<%=entity.getDes()==null?"":entity.getDes()%>" style="width:100%;"></td>
		</tr>
		<tr>
			<td colspan="6" align="center">
				<input type="submit" value="<%=I18N.getLbl(request,"pj.btn.submit","提交") %>">
			</td>
		</tr>
	</table>
</form>
<%}%>


<table class="sortable thinborder">

	<thead>
		<td><%=I18N.getLbl(request,"sys.lbl.no","NO.") %></td>
		<td><%=I18N.getLbl(request,"pj.pj","项目") %></td>
		<td><%=I18N.getLbl(request,"pj.path","路径") %></td>
		<td><%=I18N.getLbl(request,"pj.url","URL") %></td>
		<td><%=I18N.getLbl(request,"pj.type","类型") %></td>
		<td><%=I18N.getLbl(request,"pj.des","描述") %></td>
		<td><%=I18N.getLbl(request,"pj.op.setuser","设置用户") %></td>
		<td><%=I18N.getLbl(request,"pj.op.setgr","设置用户组") %></td>
		<td><%=I18N.getLbl(request,"pj.op.setauth","设置权限") %></td>
		<td><%=I18N.getLbl(request,"pj.op.delete","删除") %></td>
	</thead>
	<%
	java.util.List<Pj> list = (java.util.List<Pj>)request.getAttribute("list");

	if(list!=null){
	  for(int i = 0;i<list.size();i++){
		  Pj pj = list.get(i);
		%>
		<tr>
		<td><%=(i+1) %></td>
		<td>
			<%if(hasAdminRight || pj.isManager()){%><a href="<%=ctx%>/pj?act=get&pj=<%=pj.getPj()%>"><%=pj.getPj() %></a><%}else{%><%=pj.getPj() %><%}%>
		</td>
		<td title="<%=pj.getPj() %>">
			<%if(hasAdminRight || pj.isManager()){%><%=pj.getPath() %><%}else{%>&nbsp;<%}%>
		</td>
		<td title="<%=pj.getPj() %>">
			<%if(hasAdminRight || pj.isManager()){%>
				<a href="<%=ctx%>/rep?pj=<%=pj.getPj()%>"><%=pj.getUrl() %></a>
			<%}else{%>
				<%=pj.getUrl() %>
			<%}%>
		</td>
		<td title="<%=pj.getPj() %>"><%=pj.getType()%></td>
		<td title="<%=pj.getPj() %>"><%=pj.getDes() %></td>
		<td title="<%=pj.getPj() %>">
		<%if(Constants.SVN.equals(pj.getType()) || Constants.HTTP.equals(pj.getType())){%><a href="<%=ctx%>/pjusr?pj=<%=pj.getPj()%>"><%=I18N.getLbl(request,"pj.op.setuser","设置用户") %></a><%}else{%>&nbsp;<%}%>
		</td>
		
		<%if(hasAdminRight || pj.isManager()){%>
		<td title="<%=pj.getPj() %>">
			<a href="<%=ctx%>/pjgr?pj=<%=pj.getPj()%>"><%=I18N.getLbl(request,"pj.op.setgr","设置用户组") %></a>
		</td>
		<td title="<%=pj.getPj() %>">
		<a href="<%=ctx%>/rep?pj=<%=pj.getPj()%>"><%=I18N.getLbl(request,"pj.op.setauth","设置权限") %></a>
		</td>
		
		
		<td title="<%=pj.getPj() %>">
		<a href="javascript:if(confirm('<%=I18N.getLbl(request,"pj.op.delete.confirm","确认删除?") %>')){del('<%=ctx%>/pj?pj=<%=pj.getPj()%>')}"><%=I18N.getLbl(request,"pj.op.delete","删除") %></a>
		</td>
		<%}else{%>
		<td title="<%=pj.getPj() %>">&nbsp;</td>
		<td title="<%=pj.getPj() %>">&nbsp;</td>
		<td title="<%=pj.getPj() %>">&nbsp;</td>
		<%} %>
	</tr>
	<%}}%>
</table>