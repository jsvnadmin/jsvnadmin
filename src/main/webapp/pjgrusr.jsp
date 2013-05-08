<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.svnadmin.util.I18N"%>
<%@include file="header.jsp"%>
<span style="color:green;font-weight:bold;"><a href="<%=ctx%>/pj"><%=I18N.getLbl(request,"pj.title","项目管理") %>(<%=request.getParameter("pj")%>)</a> --> <a href="<%=ctx%>/pjgr?pj=<%=request.getParameter("pj")%>"><%=I18N.getLbl(request,"pjgr.title","用户组管理") %>(<%=request.getParameter("gr")%>)</a>--><%=I18N.getLbl(request,"pjgrusr.title","项目组用户管理") %></span><br><br>

<script>
$(function(){
    //移到右边
    $('#add').click(function() {
           //获取选中的选项，删除并追加给对方
           $('#select1 option:selected').appendTo('#select2');
    });
    //移到左边
    $('#remove').click(function() {
           $('#select2 option:selected').appendTo('#select1');
    });
    //全部移到右边
    $('#add_all').click(function() {
           //获取全部的选项,删除并追加给对方
           $('#select1 option').appendTo('#select2');
    });
    //全部移到左边
    $('#remove_all').click(function() {
           $('#select2 option').appendTo('#select1');
    });
    //双击选项
    $('#select1').dblclick(function(){     //绑定双击事件
           //获取全部的选项,删除并追加给对方
           $("option:selected",this).appendTo('#select2');     //追加给对方
    });
    //双击选项
    $('#select2').dblclick(function(){
           $("option:selected",this).appendTo('#select1');
    });
});

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
	
	<table>
	<tr>
		<td>
			<select multiple="multiple" id="select1" style="height: 150px;width: 180px;">
			<%
				java.util.List<org.svnadmin.entity.Usr> usrlist = (java.util.List<org.svnadmin.entity.Usr>)request.getAttribute("usrList");
				if(usrlist!=null){	
				for(int i = 0;i<usrlist.size();i++){
					org.svnadmin.entity.Usr usr = usrlist.get(i);
				%>
				<option value="<%=usr.getUsr()%>"><%=usr.getName()==null?usr.getUsr():usr.getName()+"("+usr.getUsr()+")"%></option>
				<%}}%>
				</select>
		</td>
		<td>
			<input id="add" type="button" value=">" style="width:30px;"><br>
			<input id="add_all" type="button" value=">>" style="width:30px;"><br><br>
			<input id="remove" type="button" value="<" style="width:30px;"><br>
			<input id="remove_all" type="button" value="<<" style="width:30px;"><br><br>
			
		</td>
		<td>
			<select id="select2" name="usrs" multiple="multiple" style="height: 150px;width: 180px;"></select>
		</td>
	</tr>
	<tr>
		<td colspan="3" align="center">
			<input type="submit" value="<%=I18N.getLbl(request,"pjgrusr.op.submit","增加用户") %>">
		</td>
	</tr>
	</table>
</form>

<table class="sortable thinborder">
	<thead>
		<td><%=I18N.getLbl(request,"sys.lbl.no","NO.") %></td>
		<td><%=I18N.getLbl(request,"pj.pj","项目") %></td>
		<td><%=I18N.getLbl(request,"pj_gr.gr","项目组") %></td>
		<td><%=I18N.getLbl(request,"usr.usr","用户") %></td>
		<td><%=I18N.getLbl(request,"usr.name","姓名") %></td>
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
		<td><%=pjGrUsr.getUsrName()==null?"":pjGrUsr.getUsrName() %></td>
		<td><a href="javascript:if(confirm('<%=I18N.getLbl(request,"pjgrusr.op.delete.confirm","确认删除?") %>')){del('<%=ctx%>/pjgrusr?&pj=<%=pjGrUsr.getPj()%>&gr=<%=pjGrUsr.getGr()%>&usr=<%=pjGrUsr.getUsr()%>')}"><%=I18N.getLbl(request,"pjgrusr.op.delete","删除") %></a></td>
	</tr>
		<%	
	}}
	%>
</table>