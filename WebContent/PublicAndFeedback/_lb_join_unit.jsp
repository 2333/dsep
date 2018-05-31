<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<td style="vertical-align:text-top">
	<span class="TextFont">可查看学校:</span>&nbsp; 
</td>
<td>
	<select style="min-width:200px;height:600px;" id="lb_join_unit" size="20">
		<%-- <c:if test="${!empty joinUnitMap}">
			<c:forEach items="${joinUnitMap}" var="unitItem">
				<option value="${unitItem.key}">
					${unitItem.value}
				</option>
			</c:forEach>
		</c:if> --%>
	</select>&nbsp;&nbsp;
</td>