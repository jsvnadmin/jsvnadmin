<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.svnadmin.util.SpringUtils"%>
<%@page import="org.svnadmin.util.I18N"%>
<%@page import="org.svnadmin.service.UsrService"%>
<%@page import="java.io.*"%>
<html>
<%
response.setHeader("Cache-Control", "no-cache, post-check=0, pre-check=0");
response.setHeader("Pragma", "no-cache");
response.setHeader("Expires", "Thu, 01 Dec 1970 16:00:00 GMT");

String ctx = request.getContextPath();
UsrService usrService = SpringUtils.getBean(UsrService.BEAN_NAME);
//验证是否连接上数据库 @see Issue 12
try{
	usrService.validatConnection();
}catch(Exception e){
	StringWriter sWriter = new StringWriter();
	PrintWriter pWriter = new PrintWriter(sWriter);
	e.printStackTrace(pWriter);
	out.println("Could not connect to database."
			+"<br>连接数据库失败!请确认数据库已经正确建立,并正确配置WEB-INF/jdbc.properties连接参数"
			+"<br><br><div style='color:red;'>" +sWriter.toString()+"</div>");
	return;
}
%>
<head>
	<title><%=I18N.getLbl(request, "login.title", "SVN ADMIN 登录")%></title>
	<script type="text/javascript">
	window.onload=function(){
		document.getElementById("usr").focus();
	}
	</script>
	<style>
		body{
			background-image: url('<%=ctx%>/resources/bg.png');
			background-repeat: no-repeat;
			-webkit-background-size: cover; /* Safari/Chrome */
			-moz-background-size: cover; /* Firefox */
			-o-background-size: cover; /* Opera */
			background-size: cover; /* IE10+ and other browsers */
			
			display: flex;
			align-items: center;
			justify-content: center;
		}
		.container {
			text-align: center;
			margin:auto;
			opacity: 0.8;
		}
		.login-box {
			width: 400px;
			height: 300px;
			background-color: #fff;
			border: 1px solid #ccc;
			border-radius: 5px;
			margin-top: 50px;
			padding: 20px;
		}
		
		input{
		    outline-style: none ;
		    border: 1px solid #ccc; 
		    border-radius: 3px;
		    padding: 13px 14px;
		    width: 200px;
		    font-size: 14px;
		    font-weight: 700;
		    font-family: "Microsoft soft";
		}
		input:focus {
		    border-color: #66afe9;
		    outline: 0;
		    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6);
		    box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6)
		}
		.login-btn {
			padding-left: 50px;
		}
	</style>
</head>
<body>
<%-- 选择语言 --%>
<div style="position:absolute ;top: 20px;right: 50px;">
	<%@include file="chagelang.jsp"%>
</div>

<%-- login form --%>
<div class="container">
	<div class="login-box">
		<h3><%=I18N.getLbl(request, "login.title", "SVN ADMIN 登录")%></h3>
		<form name="login" action="<%=ctx%>/login" method="post">
			<p><%=I18N.getLbl(request,"usr.usr","帐号") %>：<input type="text" placeholder="请输入账号" id="usr" name="usr" value="<%=request.getParameter("usr")==null?"":request.getParameter("usr")%>"></p>
			<p><%=I18N.getLbl(request,"usr.psw","密码") %>：<input type="password" placeholder="请输入密码" id="psw" name="psw" value="<%=request.getParameter("psw")==null?"":request.getParameter("psw")%>"></p>
			<p class="login-btn"><input type="submit" value="<%=I18N.getLbl(request,"login.btn.login","登录") %>"></p>
		</form>
		<%-- error --%>
		<%
		String errorMsg = (String)request.getAttribute(org.svnadmin.Constants.ERROR);
		if(errorMsg != null){
		%>
		<div style="color:red;"><%=I18N.getLbl(request,"sys.error","错误") %> <%=errorMsg%></div>
		<%}%>
		<%-- set administrator tip --%>
		<%
		int usrCount = usrService.getCount();
		if(usrCount == 0){
		%>
		<div style="color:blue"><%=I18N.getLbl(request,"login.info.setadmin","欢迎使用SVN ADMIN,第一次使用请设置管理员帐号和密码.") %></div>
		<%}%>
	</div>
	
</div>
</body>
</html>