<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.svnadmin.Constants"%>
<%@page import="org.svnadmin.util.I18N"%>
<%@include file="header.jsp"%>
<span style="color:green;font-weight:bold;"><a href="<%=ctx%>/pj"><%=I18N.getLbl(request,"pj.title","项目管理") %>(<%=request.getParameter("pj")%>)</a> --> <%=I18N.getLbl(request,"pjauth.title","权限管理") %></span><br><br>
<%
org.svnadmin.entity.PjAuth entity = (org.svnadmin.entity.PjAuth)request.getAttribute("entity");
if(entity==null)entity=new org.svnadmin.entity.PjAuth();
%>
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
  //双击选项
    $('#select3').dblclick(function(){     //绑定双击事件
           //获取全部的选项,删除并追加给对方
           $("option:selected",this).appendTo('#select4');     //追加给对方
    });
    //双击选项
    $('#select4').dblclick(function(){
           $("option:selected",this).appendTo('#select3');
    });
});

function checkForm(f){
	if(f.elements["pj"].value==""){
		alert("<%=I18N.getLbl(request,"pjauth.error.pj","项目不可以为空") %>");
		f.elements["pj"].focus();
		return false;
	}
	if(f.elements["res"].value==""){
		alert("<%=I18N.getLbl(request,"pjauth.error.res","资源不可以为空") %>");
		f.elements["res"].focus();
		return false;
	}
	if(f.elements["grs"].value=="" && f.elements["usrs"].value==""){
		alert("<%=I18N.getLbl(request,"pjauth.error.grusr","请选择用户组或用户") %>");
		f.elements["grs"].focus();
		return false;
	}
	return true;
}
</script>
<form name="pjauth" action="<%=ctx%>/pjauth" method="post" onsubmit="return checkForm(this);">
	<input type="hidden" name="act" value="save">
	<table>
		<tr>
			<td align="right"><%=I18N.getLbl(request,"pj.pj","项目") %></td>
			<td><input type="hidden" name="pj" value="<%=request.getParameter("pj")%>"><%=request.getParameter("pj")%></td>
			<td align="right"><%=I18N.getLbl(request,"pjauth.res","资源") %></td>
			<td>
				<input type="text" name="res" value="<%=entity.getRes()==null?"":entity.getRes()%>" style="width:400px;"><span style="color:red;">*</span>
				<select onchange="this.form.res.value=this.value">
				<option value=""><%=I18N.getLbl(request,"pjauth.res.select","选择资源") %></option>
				 <%
				 java.util.List<String> pjreslist = (java.util.List<String>)request.getAttribute("pjreslist");
				 for(int i=0;i<pjreslist.size();i++){
				 %>
				 <option value="<%=pjreslist.get(i)%>"><%=pjreslist.get(i)%></option>
				 <%
				 }
				 %>	
				 </select>
			</td>
		</tr>
		<tr>
			<td align="right"><%=I18N.getLbl(request,"pj_gr.gr","用户组") %></td>
			<td>
				<select id="select3" multiple="multiple" style="width: 100px;height:160px;">
					<%
					java.util.List<org.svnadmin.entity.PjGr> pjgrlist = (java.util.List<org.svnadmin.entity.PjGr>)request.getAttribute("pjgrlist");
					if(pjgrlist!=null){	
					for(int i = 0;i<pjgrlist.size();i++){
						org.svnadmin.entity.PjGr pjGr = pjgrlist.get(i);
					%>
					<option value="<%=pjGr.getGr()%>"><%=pjGr.getGr()%></option>
					<%}}%>
				</select>
			<td align="right"><%=I18N.getLbl(request,"pj_gr.gr.selected","已选中组") %></td>
			<td>
				<select id="select4" name="grs" multiple="multiple" style="width: 100px;height:160px;">
			</td>
		</tr>
				<tr>
			<td align="right"><%=I18N.getLbl(request,"usr.usr","用户") %></td>
			<td>
			<select id="select1" multiple="multiple" style="width: 100px;height:160px;">
					<%
					java.util.List<org.svnadmin.entity.Usr> usrlist = (java.util.List<org.svnadmin.entity.Usr>)request.getAttribute("usrList");
					if(usrlist!=null){	
					for(int i = 0;i<usrlist.size();i++){
						org.svnadmin.entity.Usr usr = usrlist.get(i);
					%>
					<option value="<%=usr.getUsr()%>"><%=usr.getUsr()%></option>
					<%}}%>
			</select>
			</td>
			<td align="right"><%=I18N.getLbl(request,"usr.usr.selected","已选中用户") %></td>
			<td>
			<select id="select2" name="usrs" multiple="multiple" style="width: 100px;height:160px;">
			</td>
		</tr>
		<tr>
			<td align="right"><%=I18N.getLbl(request,"pjauth.rw","权限") %></td>
			<td colspan="3">
			<select name="rw">
					<option value="" <%="".equals(entity.getRw())?"selected='selected'":""%> ><%=I18N.getLbl(request,"pjauth.rw.none","没有权限") %></option>
					<option value="r"<%="r".equals(entity.getRw())?"selected='selected'":""%>><%=I18N.getLbl(request,"pjauth.rw.r","可读") %></option>
					<option value="rw"<%="rw".equals(entity.getRw())?"selected='selected'":""%>><%=I18N.getLbl(request,"pjauth.rw.rw","可读可写") %></option>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<input type="submit" value="<%=I18N.getLbl(request,"pjauth.btn.submit","保存") %>">
			</td>
		</tr>
	</table>
</form>

<table class="sortable">

	<thead>
		<td><%=I18N.getLbl(request,"sys.lbl.no","NO.") %></td>
		<td><%=I18N.getLbl(request,"pj.pj","项目") %></td>
		<td><%=I18N.getLbl(request,"pjauth.res","资源") %></td>
		<td><%=I18N.getLbl(request,"pj_gr.gr","用户组") %>/<%=I18N.getLbl(request,"usr.usr","用户") %></td>
		<td><%=I18N.getLbl(request,"pjauth.rw","权限") %></td>
		<td><%=I18N.getLbl(request,"pjauth.op.delete","删除") %></td>
	</thead>
	<%
	java.util.List<org.svnadmin.entity.PjAuth> list = (java.util.List<org.svnadmin.entity.PjAuth>)request.getAttribute("list");

	if(list!=null){
	  for(int i = 0;i<list.size();i++){
		  org.svnadmin.entity.PjAuth pjAuth = list.get(i);
		%>
		<tr>
		<td><%=(i+1) %></td>
		<td><%=pjAuth.getPj() %></td>
		<td><%=pjAuth.getRes() %></td>
		<td><%=pjAuth.getGr()==null?"":pjAuth.getGr() %><%=pjAuth.getUsr()==null?"":pjAuth.getUsr() %></td>
		<td>
			<% if("r".equals(pjAuth.getRw())){ %>
				<%=I18N.getLbl(request,"pjauth.rw.r","可读") %>
			<%}else if("rw".equals(pjAuth.getRw())){%>
				<%=I18N.getLbl(request,"pjauth.rw.rw","可读可写") %>
			<%}else{%>
				<%=I18N.getLbl(request,"pjauth.rw.none","没有权限") %>
			<%}%>
		</td>
		<td>
		<a href="javascript:if(confirm('<%=I18N.getLbl(request,"pjauth.op.delete.confirm","确认删除?") %>')){del('<%=ctx%>/pjauth?pj=<%=pjAuth.getPj()%>&res=<%=pjAuth.getRes()%>&gr=<%=pjAuth.getGr()%>&usr=<%=pjAuth.getUsr()%>')}"><%=I18N.getLbl(request,"pjauth.op.delete","删除") %></a>
		</td>
	</tr>
		<%	
	}}
	%>
</table>