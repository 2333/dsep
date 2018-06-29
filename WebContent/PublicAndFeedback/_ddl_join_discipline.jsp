<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<td class="td_ddl_join_discipline"><span id="span_discipline" class="icon icon-search"></span></td>
<td class="td_ddl_join_discipline">
	<span class="TextFont" id="font_discipline">${textConfiguration.disc}:</span> 
	<select id="ddl_join_discipline">
			<c:if test="${!empty joinDisciplineMap}">
				<c:forEach items="${joinDisciplineMap}" var="categoryitem">
					<option value="${categoryitem.key}">
						${categoryitem.value}</option>
				</c:forEach>
			</c:if>
	</select>
</td>

<script type="text/javascript">
	//设置学科下拉框的名称，如异议汇总页面为“被异议学科”，默认名为“学科”
	function setDisciplineText(textContent) {
		$("#font_discipline").text(textContent);
	}
</script>