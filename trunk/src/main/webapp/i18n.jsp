<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.svnadmin.entity.I18n"%>
<%@page import="org.svnadmin.util.I18N"%>
<%@include file="header.jsp"%>
<%
String defaultLang = I18N.getDefaultLang(request);
List<String> langs = (List<String>)request.getAttribute("langs");
List<I18n> ids = (List<I18n>)request.getAttribute("ids");
Map<String,I18n> entitys = (Map<String,I18n>)request.getAttribute("entitys");

%>
<span style="color:green;font-weight:bold;"><%=I18N.getLbl(request,"i18n.title","语言管理") %></span>
<%=I18N.getLbl(request, "i18n.current", "当前语言")%>: 
<%=defaultLang %> - <%=I18N.getLbl(request, defaultLang, defaultLang)%><br><br>

<script>
function checkForm(f){
	if(f.elements["id"].value==""){
		return false;
	}
	return true;
}
</script>
<table style="width:100%;" class="thinborder">
	<tr>
		<td style="width:20%;">
			<%
				for(I18n i18n : ids){
			%>
			<% if(i18n.getTotal()!=langs.size()){%>
			<img alt="" src="<%=ctx%>/resources/incomplete.gif">
			<%} %>
			<a href="<%=ctx%>/i18n?id=<%=i18n.getId()%>"><%=i18n.getId()%></a><br>
			<%} %>
		</td>
		<td valign="top">
			<form name="i18n" action="<%=ctx%>/i18n" method="post" onsubmit="return checkForm(this);">
				<input type="hidden" name="act" value="save">
				<table class="thinborder">
					<tr>
						<td class="lbl"><%=I18N.getLbl(request, "i18n.id", "键值")%></td>
						<td style="width:100%;">
						   	<%=request.getParameter("id")==null?"":request.getParameter("id")%>
						   	<input type="hidden" name="id" value="<%=request.getParameter("id")==null?"":request.getParameter("id")%>" >
						</td>
					</tr>
					<%
						for(int i=0;i<langs.size();i++){
							String lang = langs.get(i);
					%>
					<tr>
						<td class="lbl">
							<%=I18N.getLbl(request, lang, lang)%>(<%=lang%>)
							<input type="hidden" name="lang_<%=i%>" value="<%=lang%>" >
						</td>
						<td>
						   	<input style="width:100%;" type="text" name="lbl_<%=i%>" value="<%=(entitys==null || entitys.get(lang)==null)?"":entitys.get(lang).getLbl()%>" >
						</td>
					</tr>
					<%} %>
					<tr>
						<td colspan="2" align="center">
							<input type="submit" value="<%=I18N.getLbl(request,"i18n.op.submit","提交") %>">
							&nbsp;&nbsp;
							<a href="<%=ctx%>/i18nadd"><%=I18N.getLbl(request,"i18n.op.addlang","增加语言") %></a>
							&nbsp;&nbsp;
							<a href="<%=ctx%>/i18n?act=download"><%=I18N.getLbl(request,"i18n.op.export","导出多语言贡献给svnadmin项目组") %></a>
						</td>
					</tr>
				</table>
			</form>
		</td>
	</tr>
</table>
