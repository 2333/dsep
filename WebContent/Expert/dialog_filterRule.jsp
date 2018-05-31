<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="editRuleInfo_div" class="form">
	<form id="ruleInfo_fm" class="fr_form">
		<table id="rule_tb_dialog" class="fr_table">
			<tr>
				<td><span class="TextBold">筛选专家所在的学校或学科</span></td>
			</tr>
			<tr>
				<td><input type="checkbox" checked="checked" /> <span
					class="TextBold">反馈率低于<input type="text" size=2/>%的学校
				</span></td>
			</tr>
			<tr>
				<td><input type="checkbox" /> <span class="TextBold">反馈率低于<input
						type="text" size=2/>%的学科
				</span></td>
			</tr>
			<tr>
				<td><input type="checkbox" /> <span class="TextBold">专家数量低于<input
						type="text" size=2/>%的学科
				</span></td>
			</tr>
			<tr>
				<td><input type="checkbox" /> <span class="TextBold">985院校</span>
				</td>
			</tr>
			<tr>
				<td><input type="checkbox" /> <span class="TextBold">211院校</span>
				</td>
			</tr>
		</table>
	</form>
</div>
