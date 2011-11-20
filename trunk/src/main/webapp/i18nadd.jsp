<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.svnadmin.Constants"%>
<%@page import="org.svnadmin.util.I18N"%>
<%@page import="java.util.*"%>
<%@page import="java.text.DateFormat"%>
<%@include file="header.jsp"%>
<span style="color:green;font-weight:bold;"><a href="<%=ctx%>/i18n"><%=I18N.getLbl(request,"i18n.title","语言管理") %></a> --> <%=I18N.getLbl(request,"i18n.add.title","增加语言") %></span><br><br>

<%
org.svnadmin.entity.I18n entity = (org.svnadmin.entity.I18n)request.getAttribute("entity");
String defaultLang = I18N.getDefaultLang(request);
Locale locales[] = DateFormat.getAvailableLocales();
if(entity==null){
	entity=new org.svnadmin.entity.I18n();
	entity.setLang(request.getLocale().toString());
	for(Locale loc : locales){
		if(entity.getLang().equals(loc.toString())){
			entity.setLbl(loc.getDisplayLanguage());
			break;
		}
	}
}
%>
<script>
function checkForm(f){
	if(f.elements["lang"].value==""){
		alert("<%=I18N.getLbl(request,"i18n.error.lang","语言不可以为空") %>");
		f.elements["lang"].focus();
		return false;
	}
	if(f.elements["lbl"].value==""){
		alert("<%=I18N.getLbl(request,"i18n.error.lbl","标签不可以为空") %>");
		f.elements["lbl"].focus();
		return false;
	}
	return true;
}
function selectLocale(sel){
	sel.form.lang.value=sel.options[sel.selectedIndex].value;
	sel.form.lbl.value=sel.options[sel.selectedIndex].text;
}
</script>

	<%=I18N.getLbl(request, "i18n.current", "当前语言")%>: 
	<%=defaultLang %> - <%=I18N.getLbl(request, defaultLang, defaultLang)%><br><br>
	
<form name="i18nadd" action="<%=ctx%>/i18nadd" method="post" onsubmit="return checkForm(this);">
	<input type="hidden" name="act" value="save">
	<table class="thinborder">
		<tr>
			<td colspan="4">
				<select onchange="selectLocale(this)">
					<%
					for(Locale loc : locales){
					%>
					<option value="<%=loc.toString()%>"><%=loc.getDisplayLanguage()%></option>
					<%} %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="lbl">
				<%=I18N.getLbl(request,"i18n.lang","语言") %>
			</td>
			<td>
				<input type="text" name="lang" value="<%=entity.getLang()==null?"":entity.getLang()%>" 
				onkeyup="value=value.replace(/[^_\-A-Za-z0-9]/g,'')">
				<span style="color:red;">*</span>
			</td>
			
			<td class="lbl"><%=I18N.getLbl(request,"i18n.lbl","标签") %></td>
			<td>
				<input type="text" name="lbl" value="<%=entity.getLbl()==null?"":entity.getLbl()%>" >
				<span style="color:red;">*</span>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<input type="submit" value="<%=I18N.getLbl(request,"i18n.op.submit","提交") %>">
			</td>
		</tr>
	</table>
</form>

<table class="sortable thinborder">

	<thead>
		<td><%=I18N.getLbl(request,"sys.lbl.no","NO.") %></td>
		<td><%=I18N.getLbl(request,"i18n.lang","语言") %></td>
		<td><%=I18N.getLbl(request,"i18n.lbl","标签") %></td>
	</thead>
	<%
	List<String> langs = (List<String>)request.getAttribute("langs");

	if(langs!=null){
		for(int i=0;i<langs.size();i++){
		%>
		<tr>
		<td><%=i+1%></td>
		<td><%=langs.get(i)%></td>
		<td><%=I18N.getLbl(request, langs.get(i),langs.get(i))%></td>
	</tr>
		<%	
	}}
	%>
</table>