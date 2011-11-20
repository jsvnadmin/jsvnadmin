<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.svnadmin.Constants"%>
<%@page import="org.svnadmin.util.I18N"%>
<%@include file="header.jsp"%>
<span style="color:green;font-weight:bold;"><a href="<%=ctx%>/pj"><%=I18N.getLbl(request,"pj.title","项目管理") %>(<%=request.getParameter("pj")%>)</a> --> <%=I18N.getLbl(request,"pjgr.title","用户组管理") %></span><br><br>
<%
org.svnadmin.entity.PjGr entity = (org.svnadmin.entity.PjGr)request.getAttribute("entity");
if(entity==null)entity=new org.svnadmin.entity.PjGr();
%>
<script>
function checkForm(f){
	if(f.elements["pj"].value==""){
		alert("<%=I18N.getLbl(request,"pjgr.error.pj","项目不可以为空") %>");
		f.elements["pj"].focus();
		return false;
	}
	if(f.elements["gr"].value==""){
		alert("<%=I18N.getLbl(request,"pjgr.error.gr","组号不可以为空") %>");
		f.elements["gr"].focus();
		return false;
	}
	return true;
}
</script>
<form name="pjgr" action="<%=ctx%>/pjgr" method="post" onsubmit="return checkForm(this);">
	<input type="hidden" name="act" value="save">
	<table class="thinborder">
		<tr>
			<td class="lbl"><%=I18N.getLbl(request,"pj.pj","项目") %></td>
			<td><input type="hidden" name="pj" value="<%=request.getParameter("pj")%>"><%=request.getParameter("pj")%></td>
			<td class="lbl"><%=I18N.getLbl(request,"pj_gr.gr","组号") %></td>
			<td><input type="text" name="gr" value="<%=entity.getGr()==null?"":entity.getGr()%>"  onkeyup="value=value.replace(/[^_\-A-Za-z0-9]/g,'')"><span style="color:red;">*</span></td>
			<td class="lbl"><%=I18N.getLbl(request,"pj_gr.des","描述") %></td>
			<td><input type="text" name="des" value="<%=entity.getDes()==null?"":entity.getDes()%>"></td>
			<td>
				<input type="submit" value="<%=I18N.getLbl(request,"pjgr.btn.submit","提交") %>">
			</td>
		</tr>
	</table>
</form>

<table class="sortable thinborder">

	<thead>
		<td><%=I18N.getLbl(request,"sys.lbl.no","NO.") %></td>
		<td><%=I18N.getLbl(request,"pj.pj","项目") %></td>
		<td><%=I18N.getLbl(request,"pj_gr.gr","组号") %></td>
		<td><%=I18N.getLbl(request,"pj_gr.des","描述") %></td>
		<td><%=I18N.getLbl(request,"pjgr.op.setuser","设置用户") %></td>
		<td><%=I18N.getLbl(request,"pjgr.op.delete","删除") %></td>
	</thead>
	<%
	java.util.List<org.svnadmin.entity.PjGr> list = (java.util.List<org.svnadmin.entity.PjGr>)request.getAttribute("list");

	if(list!=null){
	  for(int i = 0;i<list.size();i++){
		  org.svnadmin.entity.PjGr pjGr = list.get(i);
		%>
		<tr>
		<td><%=(i+1) %></td>
		<td>
			<%=pjGr.getPj() %>
		</td>
		
		<td>
			<%if((pjGr.getPj()+"_"+Constants.GROUP_MANAGER).equals(pjGr.getGr()) || Constants.GROUP_MANAGER.equals(pjGr.getGr())){%><%=pjGr.getGr() %><%}else{%>
			<a href="<%=ctx%>/pjgr?act=get&pj=<%=pjGr.getPj()%>&gr=<%=pjGr.getGr()%>"><%=pjGr.getGr() %></a>
			<%}%>
		</td>
		<td><%=pjGr.getDes() %></td>
		<td><a href="<%=ctx%>/pjgrusr?pj=<%=pjGr.getPj()%>&gr=<%=pjGr.getGr()%>"><%=I18N.getLbl(request,"pjgr.op.setuser","设置用户") %></a></td>
		<td>
			<%if((pjGr.getPj()+"_"+Constants.GROUP_MANAGER).equals(pjGr.getGr()) || Constants.GROUP_MANAGER.equals(pjGr.getGr())){%>&nbsp;<%}else{%>
			<a href="javascript:if(confirm('<%=I18N.getLbl(request,"pjgr.op.delete.confirm","确认删除?") %>')){del('<%=ctx%>/pjgr?pj=<%=pjGr.getPj()%>&gr=<%=pjGr.getGr()%>')}"><%=I18N.getLbl(request,"pjgr.op.delete","删除") %></a>
			<%}%>
		</td>
	</tr>
		<%	
	}}
	%>
</table>