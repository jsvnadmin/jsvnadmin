<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.svnadmin.service.I18nService"%>
<%@page import="org.svnadmin.servlet.BaseServlet"%>
<%@page import="org.svnadmin.util.SpringUtils"%>
<%@page import="org.svnadmin.util.I18N"%>
<%@page import="java.util.*"%>
<%
I18nService lang_i18nService = SpringUtils.getBean(I18nService.BEAN_NAME);
List<String> lang_langs = lang_i18nService.getLangs();
if(lang_langs != null && !lang_langs.isEmpty()){
%>
	<form name="changLangForm" action="<%=request.getContextPath()%>/changelang" method="post">
		<select name="lang" onchange="this.form.submit()">
			<%
			for(String lang:lang_langs){
			%>
			<option value="<%=lang%>" <%=I18N.getDefaultLang(request).equals(lang)?"selected='selected'":""%>><%=I18N.getLbl(request, lang,lang)%></option>
			<%} %>
		</select>
	</form>
<%} %>
