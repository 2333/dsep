<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<td><span id="span_unit" class="icon icon-search"></span></td>
<td>
	<span id="font_unit" class="TextFont">学校:</span> 
	<select id="ddl_join_unit">
		<c:if test="${!empty joinUnitMap}">
			<c:forEach items="${joinUnitMap}" var="unitItem">
				<option value="${unitItem.key}">
					${unitItem.value}
				</option>
			</c:forEach>
		</c:if>
	</select>
	&nbsp;&nbsp;&nbsp;
</td>


<script type="text/javascript">
	//设置学校下拉框的名称，如异议汇总页面为“被异议学校”，默认名为“学校”
	function setUnitText(textContent) {
		$("#font_unit").text(textContent);
	}
</script>