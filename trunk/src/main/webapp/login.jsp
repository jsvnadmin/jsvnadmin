<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.svnadmin.util.SpringUtils"%>
<%@page import="org.svnadmin.service.UsrService"%>
<%@page import="java.io.*"%>
<title>SVN ADMIN 登录</title>
<%
response.setHeader("Cache-Control", "no-cache, post-check=0, pre-check=0");
response.setHeader("Pragma", "no-cache");
response.setHeader("Expires", "Thu, 01 Dec 1970 16:00:00 GMT");

String ctx = request.getContextPath();
UsrService usrService = SpringUtils.getBean(UsrService.BEAN_NAME);
try{
	usrService.validatConnection();//验证是否连接上数据库 @see Issue 12
}catch(Exception e){
	StringWriter sWriter = new StringWriter();
	PrintWriter pWriter = new PrintWriter(sWriter);
	e.printStackTrace(pWriter);
	out.println("连接数据库失败!请确认数据库已经正确建立,并正确配置WEB-INF/jdbc.properties连接参数。<br><br><div style='color:red;'>" +sWriter.toString()+"</div>");
	return;
}
int usrCount = usrService.getCount();
if(usrCount == 0){
%>
<div style="color:blue">欢迎使用SVN ADMIN,第一次使用请设置管理员帐号和密码.</div>
<%
}

String errorMsg = (String)request.getAttribute(org.svnadmin.Constants.ERROR);
if(errorMsg != null){
%>
<div style="color:red;">错误：<%=errorMsg%></div>
<%
}
%>
<script type="text/javascript">
window.onload=function(){
	document.forms[0].usr.focus();
}
</script>
<form name="login" action="<%=ctx%>/login" method="post">
<table>

	<tr>
		<td>帐号</td>
		<td><input type="text" name="usr" value="<%=request.getParameter("usr")==null?"":request.getParameter("usr")%>"></td>
	</tr>
	<tr>
		<td>密码</td>
		<td><input type="password" name="psw" value="<%=request.getParameter("psw")==null?"":request.getParameter("psw")%>"></td>
	</tr>
	<tr>
		<td><input type="submit" value="登录"></td>
	</tr>
</table>

</form>