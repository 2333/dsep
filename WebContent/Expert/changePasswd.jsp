<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="cxt" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>欢迎登录学科自检平台</title>
</head>
<script type="text/javascript">
	function countDown(secs) {
		document.getElementById("jump").innerHTML = secs;
		if (--secs > 0) {
			setTimeout("countDown(" + secs + " )", 3000);
		} else {
			location.href = "${cxt}/DSEP";
		}
	}
</script>
<body>
<body style="overflow: auto;" onload="javascript:countDown(10);">
	<p>
		页面将在<span id="jump"></span>秒后自动跳转......
	</p>
</body>
</html>