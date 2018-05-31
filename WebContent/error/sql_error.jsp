<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>异常信息</title>
</head>
<body>
	<%-- <% Exception ex = (Exception)request.getAttribute("exception"); %> 
<H2>异常信息: <%= ex.getMessage()%></H2>  --%>
<h2>数据操作异常:</h2>
<h2>异常信息：${message}</h2>
</body>
</html>