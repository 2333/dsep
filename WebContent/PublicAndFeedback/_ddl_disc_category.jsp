<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<td><span class="icon icon-web"></span></td>
<td>
	<span class="TextFont">公示数据项配置-学科门类:</span> 
	<select
		id="ddl_disc_category">
			<c:if test="${!empty discCategoryMap}">
				<c:forEach items="${discCategoryMap}" var="categoryitem">
					<option value="${categoryitem.key}">
						${categoryitem.value}</option>
				</c:forEach>
			</c:if>
	</select>
</td>
<script type="text/javascript">
	
	
</script>
