<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<td style="vertical-align:text-top">
	<span class="TextFont">可查看${textConfiguration.disc}:</span>&nbsp;
</td>
<td>
	<select style="min-width:200px;height:600px;" id="lb_join_discipline" size="20">
			<%-- <c:if test="${!empty joinDisciplineMap}">
				<c:forEach items="${joinDisciplineMap}" var="discItem">
					<option value="${discItem.key}">
						${discItem.value}</option>
				</c:forEach>
			</c:if> --%>
	</select>&nbsp;&nbsp;
</td>