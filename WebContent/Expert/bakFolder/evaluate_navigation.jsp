<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="selectbar" style="overflow: hidden">
	<table class="layout_table right">
		<tbody>
			<tr>
				<td width="135" style='<c:if test="${empty prevQuestionRoute}">visibility:hidden;</c:if>'>
					<a class="button" href="#" onclick="openLink('${prevQuestionRoute}');return false;">
						<span class="icon icon-left"></span>
						上一项打分
					</a>
				</td>
				<td width="135">
					<a class="button" href="#" onclick="openLink('${homeRoute}');return false;">
						<span class="icon icon-home"></span>
						返回流程页
					</a>
				</td>
				<td width="135">
					<a class="button nextQuestionRoute" href="#" onclick="openLink('${nextQuestionRoute}');return false;">
						<c:if test="${not empty nextQuestionRoute}">
						<span class="icon icon-right "></span>
						下一项打分
						</c:if>
						<c:if test="${empty nextQuestionRoute}">
						<span class="icon icon-del "></span>
						该页未完成
						</c:if>
					</a>
				</td>
			</tr>
		</tbody>
	</table>
</div>