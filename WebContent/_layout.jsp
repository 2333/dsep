<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>信息网</title>
<link rel="stylesheet" type="text/css" href="${ContextPath}/css/index.css" />
<link rel="stylesheet" type="text/css" href="${ContextPath}/css/layout.css" />
<link rel="stylesheet" type="text/css" href="${ContextPath}/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="${ContextPath}/css/icons.css"/>
<link rel="stylesheet" type="text/css" href="${ContextPath}/css/Form.css"/>
<link rel="stylesheet" type="text/css" href="${ContextPath}/css/Font.css"/>
<link rel="stylesheet" type="text/css" href="${ContextPath}/css/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="${ContextPath}/css/survey.css">
<link rel="stylesheet" href="${ContextPath}/ueditor/themes/default/css/ueditor.css">
<link href="${ContextPath}/css/redmond/jquery-ui-1.10.3.custom.css" rel="stylesheet">
<link href="${ContextPath}/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
<script src="${ContextPath}/js/jquery-1.9.1.js"></script>
<script src="${ContextPath}/js/jquery-ui-1.10.3.custom.js"></script>
<script src="${ContextPath}/js/jquery.jqGrid.min.js"></script>
<script src="${ContextPath}/js/grid.locale-cn.js"></script>
<script src="${ContextPath}/js/jqDnR.js"></script>
<script src="${ContextPath}/js/jqModal.js"></script>
<script src="${ContextPath}/js/common.js"></script>
<script src="${ContextPath}/js/index.jquery.js"></script>
<script src="${ContextPath}/js/jquery.ztree.core-3.5.min.js"></script>
<script src="${ContextPath}/js/jquery.ztree.excheck-3.5.min.js"></script>
<script src="${ContextPath}/js/jquery.validate.min.js"></script>
<script src="${ContextPath}/js/query.info.js"></script>
<script src="${ContextPath}/js/common_validate.js"></script>
<script src="${ContextPath}/js/uploadify/jquery.uploadify.js"></script>
<script src="${ContextPath}/js/highcharts.js"></script>
<script src="${ContextPath}/js/base64.js"></script>
<script src="${ContextPath}/js/jquery.ajax.common.js"></script>
<link rel="stylesheet" type="text/css" href="${ContextPath}/js/uploadify/uploadify.css">


<script  src="${ContextPath}/ueditor/ueditor.config.js"></script>
<script  src="${ContextPath}/ueditor/ueditor.all.js"></script>
</head>
<body>
<c:if test="${DomainId== 'D201301'}">
	<jsp:include page="_header.jsp"/>
</c:if>
<c:if test="${DomainId== 'D201401'}">
	<jsp:include page="_headerZ.jsp"/>
</c:if>
<%-- <c:choose>
	<c:when test="${DomainId == D201301}">
		<jsp:include page="_header.jsp"/>
	</c:when>
	<c:when test="${DomainId == D201401}">
		<jsp:include page="_headerZ.jsp"/>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose> --%>
    <%-- <jsp:include page="_header.jsp"/> --%>
    <jsp:include page="_hori_menu.jsp"/>
    <jsp:include page="_mainbody.jsp"/>
	<jsp:include page="_footer.jsp"/>
</body>
</html>