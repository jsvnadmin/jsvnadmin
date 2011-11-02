<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.svnadmin.util.I18N"%>
<%@page import="java.util.*"%>
<html>
<%
response.setHeader("Cache-Control", "no-cache, post-check=0, pre-check=0");
response.setHeader("Pragma", "no-cache");
response.setHeader("Expires", "Thu, 01 Dec 1970 16:00:00 GMT");

String ctx = request.getContextPath();
String defaultLang = I18N.getDefaultLang(request);
%>
<head>
	<title><%=I18N.getLbl(request, "lang.title", "选择语言")%></title>
</head>
<body>
	<%-- error --%>
	<%
	String errorMsg = (String)request.getAttribute(org.svnadmin.Constants.ERROR);
	if(errorMsg != null){
	%>
	<div style="color:red;"><%=I18N.getLbl(request,"sys.error","错误") %> <%=errorMsg%></div>
	<%}%>
	
	<%=I18N.getLbl(request, "lang.current", "当前语言")%>: <%=defaultLang %> - 
	<%=I18N.getLbl(request, defaultLang, defaultLang)%><br><br>
	
	<%-- form --%>
	<form name="langForm" action="<%=ctx%>/lang" method="post">
		<input name="act" type="hidden" value="change">
		<%
		List<String> langs = (List<String>)request.getAttribute("langs");
		if(langs != null && !langs.isEmpty()){
		%>
			<select name="lang">
				<%
				for(String lang:langs){
				%>
				<option value="<%=lang%>" <%=defaultLang.equals(lang)?"selected='selected'":""%>><%=I18N.getLbl(request, lang,lang)%></option>
				<%} %>
			</select>
		<%} %>
		
		<input type="submit" value="<%=I18N.getLbl(request,"lang.btn.submit","确定") %>">
		
	</form>
</body>
</html>