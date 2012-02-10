<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<html>
<head>
<title>错误</title>
</head>
<body>
<div>
发生错误，请联系 QQ 56099823 或 <a target="_blank" href="http://code.google.com/p/jsvnadmin/">报告一个Issue</a>，以下是错误信息:<br><br>
<%
StringWriter sWriter = new StringWriter();
PrintWriter pWriter = new PrintWriter(sWriter);
exception.printStackTrace(pWriter);
%>
<code style="color:red;">
<%=sWriter.toString()%>
</code>
</div>
</body>
</html>