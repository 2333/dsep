<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tr>
	<td>
		<label>异议类型：</label>
	<td>
	<td>
		<select id="ddl_object_type">
				<c:if test="${!empty objectTypeMap}">
					<c:forEach items="${objectTypeMap}" var="objectTypeItem">
						<option value="${objectTypeItem.key}">
							${objectTypeItem.value}</option>
					</c:forEach>
				</c:if>
		</select>
	</td>
</tr>