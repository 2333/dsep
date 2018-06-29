<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<td><span id="span_unit" class="icon icon-search"></span></td>
<td>
	<span id="font_unit" class="TextFont">学校处理意见:</span> 
	<select id="ddl_response_type">
		<c:if test="${!empty responseTypeMap}">
			<c:forEach items="${responseTypeMap}" var="responseTypeItem">
				<option value="${responseTypeItem.key}">
					${responseTypeItem.value}
				</option>
			</c:forEach>
		</c:if>
	</select>
</td>
