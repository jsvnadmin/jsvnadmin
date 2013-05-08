<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.svnadmin.Constants"%>
<%@page import="org.svnadmin.util.I18N"%>
<%
String ctx = request.getContextPath();
%>
<html>
	<head>
		<script src="<%=ctx%>/resources/jquery-1.7.min.js" type="text/javascript"></script>
		<script src="<%=ctx%>/resources/sorttable.js"></script>
		<script src="<%=ctx%>/resources/svnadmin.js"></script>
		<link rel="stylesheet" href="<%=ctx%>/resources/svnadmin.css" />
		<script>
		$(function(){
		    //移到用户组右边
		    $('#group_add').click(function() {
		           //获取选中的选项，删除并追加给对方
		           $('#select3 option:selected').appendTo('#select4');
		    });
		    //移到用户组左边
		    $('#group_del').click(function() {
		           $('#select4 option:selected').appendTo('#select3');
		    });
		    //全部移到用户组右边
		    $('#group_add_all').click(function() {
		           //获取全部的选项,删除并追加给对方
		           $('#select3 option').appendTo('#select4');
		    });
		    //全部移到用户组左边
		    $('#group_del_all').click(function() {
		           $('#select4 option').appendTo('#select3');
		    });
		    
		    //移到用户右边
		    $('#user_add').click(function() {
		           //获取选中的选项，删除并追加给对方
		           $('#select1 option:selected').appendTo('#select2');
		    });
		    //移到用户左边
		    $('#user_del').click(function() {
		           $('#select2 option:selected').appendTo('#select1');
		    });
		    //全部移到用户右边
		    $('#user_add_all').click(function() {
		           //获取全部的选项,删除并追加给对方
		           $('#select1 option').appendTo('#select2');
		    });
		    //全部移到用户左边
		    $('#user_del_all').click(function() {
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
	</head>
<body style="margin: 0px;">
	<%
	org.svnadmin.entity.PjAuth entity = (org.svnadmin.entity.PjAuth)request.getAttribute("entity");
	if(entity==null){
		entity=new org.svnadmin.entity.PjAuth();
	}
	%>
	<%-- error message --%>
	<%
	String errorMsg = (String)request.getAttribute(org.svnadmin.Constants.ERROR);
	if(errorMsg != null){
	%>
		<div style="color:red;"><%=I18N.getLbl(request,"sys.error","错误") %> <%=errorMsg%></div>
	<%}%>
<form name="pjauth" action="<%=ctx%>/pjauth" method="post" onsubmit="return checkForm(this);">
	<input type="hidden" name="act" value="save">
	<input type="hidden" name="pj" value="<%=request.getParameter("pj")%>">
	<table class="thinborder">
		<tr>
			<td class="lbl"><%=I18N.getLbl(request,"pjauth.res","资源") %></td>
			<td colspan="3">
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
			<td class="lbl"><%=I18N.getLbl(request,"pj_gr.gr","用户组") %></td>
			<td valign="top">
				<table>
					<tr>
						<td style="border:0px;">
							<select id="select3" multiple="multiple" style="height: 150px;width: 180px;">
								<%
								java.util.List<org.svnadmin.entity.PjGr> pjgrlist = (java.util.List<org.svnadmin.entity.PjGr>)request.getAttribute("pjgrlist");
								if(pjgrlist!=null){	
								for(int i = 0;i<pjgrlist.size();i++){
									org.svnadmin.entity.PjGr pjGr = pjgrlist.get(i);
								%>
								<option value="<%=pjGr.getGr()%>"><%=pjGr.getGr()%></option>
								<%}}%>
							</select>
						</td>
						<td style="border:0px;">
							<input id="group_add" type="button" style="width:30px;" value=">"><br>
							<input id="group_add_all" type="button" style="width:30px;" value=">>"><br><br>
							<input id="group_del" type="button" style="width:30px;" value="<"><br>
							<input id="group_del_all" type="button" style="width:30px;" value="<<"><br><br>
						</td>
						<td style="border:0px;">
							<select id="select4" name="grs" multiple="multiple" style="height: 150px;width: 180px;">
							</select>
						</td>
					</tr>
				</table>
			</td>
			
			<td class="lbl"><%=I18N.getLbl(request,"usr.usr","用户") %></td>
			<td valign="top">
				<table>
					<tr>
						<td style="border:0px;">
							<select id="select1" multiple="multiple" style="height: 150px;width: 180px;">
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
						<td style="border:0px;">
							<input id="user_add" type="button" style="width:30px;" value=">"><br>
							<input id="user_add_all" type="button" style="width:30px;" value=">>"><br><br>
							<input id="user_del" type="button" style="width:30px;" value="<"><br>
							<input id="user_del_all" type="button" style="width:30px;" value="<<"><br><br>
						</td>
						<td style="border:0px;">
							<select id="select2" name="usrs" multiple="multiple" style="height: 150px;width: 180px;">
							</select>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="lbl"><%=I18N.getLbl(request,"pjauth.rw","权限") %></td>
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

<table class="sortable thinborder">

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
		<td><%=pjAuth.getGr()==null?"":pjAuth.getGr() %><%=pjAuth.getUsr()==null?"":( (pjAuth.getUsrName()==null || pjAuth.getUsrName().trim().length()==0) ?pjAuth.getUsr():pjAuth.getUsrName()+"("+pjAuth.getUsr()+")") %></td>
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
</body>
</html>