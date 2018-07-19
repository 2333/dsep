<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cxt" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	</head>
	
<body> 

<% 
 response.setHeader("refresh","0.01;url=/DSEP_ZYPG");//定时跳转 
 session.invalidate();//注销 
%>

<%--  <center><h3>你好,你已经退出本系统,两秒后跳转至登录页面</h3> </center>
<center><h3>如没有跳转,请按<a href="${ContextPath}/">这里</a> </h3></center> --%>

</body> 
</html>