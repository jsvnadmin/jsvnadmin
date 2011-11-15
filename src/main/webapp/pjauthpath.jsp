<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.svnadmin.Constants"%>
<%@page import="org.svnadmin.util.I18N"%>
<%
String _ctx_path = request.getContextPath();
%>
<html>
	<head>
		<script src="<%=_ctx_path%>/resources/jquery-1.7.min.js" type="text/javascript"></script>
		<script src="<%=_ctx_path%>/resources/sorttable.js"></script>
		<script src="<%=_ctx_path%>/resources/svn.js"></script>
		<link rel="stylesheet" href="<%=_ctx_path%>/resources/sorttable.css" />
	</head>
	<%-- error message --%>
	<%
	String errorMsg = (String)request.getAttribute(org.svnadmin.Constants.ERROR);
	if(errorMsg != null){
	%>
		<div style="color:red;"><%=I18N.getLbl(request,"sys.error","错误") %> <%=errorMsg%></div>
	<%}%>
		
	<%@include file="pjauthinc.jsp"%>
</html>