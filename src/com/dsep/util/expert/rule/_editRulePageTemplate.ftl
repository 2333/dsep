<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 该注释为JSP注释，查看页面源码看不见
ruleDetail的英文名
<#list list as detail>
detail${detail.sequ}  ${detail.chName}：${detail.itemName}
</#list>
 --%>

<%-- 这行非常重要，它标志了datail的个数并传到后台！ --%>
<input type="hidden" value="${list?size}" id="detailSize">
<div id="addRuleInfo_div" class="form">
	<form id="rule">
		<table id="add_rule_tb_dialog" class="fr_table">
			<tr>
				<td width='160'><span class="TextBold">遴选规则名称：</span></td>
				<td><input type="text" name="rule.ruleName" value="${'$'}{rule.ruleName}" /></td>
				<td><input type="hidden" name="rule.id" value="${'$'}{rule.id}" /></td>
			</tr>
		</table>
	</form>
	
	<form id="details">
		<table id="add_Rule_tb_dialog" class="fr_table">
		
			<tr>
				<td><span class="TextBold">必要条件：</span></td>
			</tr>
			<tr>
				<td><span class="TextBold">优先条件：</span></td>
			</tr>
			<tr>
				<td><span class="TextBold">遴选范围：</span></td>
			</tr>
			
		<#list list as detail>
			<#if detail.prior=="0" && detail.operator == "between">
			<tr>
				<td width='165'>
				<span class="">${detail.chName}</span>
				</td>
				<td>
					<input type="hidden" name="detail${detail.sequ}.operator" value="${detail.operator}">
					<input type="hidden" name="detail${detail.sequ}.itemName" value="${detail.itemName}">
					<input type="text" size="1" name="detail${detail.sequ}.restrict1" value="${'$'}{detail${detail.sequ}.restrict1}" />~~
					<input type="text" size="1" name="detail${detail.sequ}.restrict2" value="${'$'}{detail${detail.sequ}.restrict2}" />
					<input type="hidden" name="detail${detail.sequ}.sequ" value="${detail.sequ}">
				</td>
			</tr>
			<#elseif detail.itemName == "onlyAttend" || detail.itemName == "attendAndAccredit" || detail.itemName == "notAttend" || detail.itemName == "allUnits">
			<tr class="fr_left">
				<td colspan=2>
					<input type="hidden" name="detail${detail.sequ}.sequ" value="${detail.sequ}">
					<input type="radio" name="detail${detail.sequ}.check" <c:if test="${'$'}{detail${detail.sequ}.conditionChecked}">checked</c:if> />${detail.chName}<br />
					<input type="hidden" name="detail${detail.sequ}.operator" value="${detail.operator}">
					<input type="hidden" name="detail${detail.sequ}.itemName" value="${detail.itemName}">
				</td>
			</tr>
			<#elseif detail.operator == "is">		
			<tr>
				<td><span class="">${detail.chName}</span></td>
				<td>
				<input type="hidden" name="detail${detail.sequ}.operator" value="${detail.operator}">
				<input type="hidden" name="detail${detail.sequ}.itemName" value="${detail.itemName}">
				<input type="text" size="2" name="detail${detail.sequ}.restrict1" value="${'$'}{detail${detail.sequ}.restrict1}" />
				<input type="hidden" name="detail${detail.sequ}.sequ" value="${detail.sequ}">
				</td>
			</tr>
			<#elseif detail.itemName == "notSetDisciplineNumber">
			<tr>
				<td>
					<input type="hidden" name="detail${detail.sequ}.sequ" value="${detail.sequ}">
					<input type="checkbox" name="detail${detail.sequ}.check" <c:if test="${'$'}{detail${detail.sequ}.conditionChecked}">checked</c:if> />
					${detail.chName}
					<input type="hidden" name="detail${detail.sequ}.operator" value="${detail.operator}">
					<input type="hidden" name="detail${detail.sequ}.itemName" value="${detail.itemName}">
				</td>
			</tr>
			
			
			
			<#elseif detail.prior=="1" && detail.operator == "between">
			<tr>
				<td>
					<span class="">${detail.chName}</span>
				</td>
				<td>
					<input type="hidden" name="detail${detail.sequ}.operator" value="${detail.operator}">
					<input type="hidden" name="detail${detail.sequ}.itemName" value="${detail.itemName}">
					<input type="text" size="1" name="detail${detail.sequ}.restrict1" value="${'$'}{detail${detail.sequ}.restrict1}" />~~
					<input type="text" size="1" name="detail${detail.sequ}.restrict2" value="${'$'}{detail${detail.sequ}.restrict2}" />
					<input type="hidden" name="detail${detail.sequ}.sequ" value="${detail.sequ}">
				</td>
			</tr>
			<#elseif detail.prior == "1" && detail.operator == "check">
			<tr>
				<td>
					<input type="checkbox" name="detail${detail.sequ}.check" <c:if test="${'$'}{detail${detail.sequ}.conditionChecked}">checked</c:if>/>
					${detail.chName}
					<input type="hidden" name="detail${detail.sequ}.operator" value="${detail.operator}">
					<input type="hidden" name="detail${detail.sequ}.itemName" value="${detail.itemName}">
					<input type="hidden" name="detail${detail.sequ}.sequ" value="${detail.sequ}">
				</td>
			</tr>
			</#if>
 			</#list>
 			
		</table>
	</form>
		<form id="comment">
		<table id="add_rule_tb_dialog" class="fr_table">
			<tr><td><span class="TextBold">关于该遴选规则的备注</span></td></tr>
			<tr>
				<td><textarea rows="5" cols="45" name="rule.commentForRule">${'$'}{rule.commentForRule }</textarea></td>
			</tr>
		</table>
	</form>
	
</div>
<script type="text/javascript">
$(document).ready(function() {
    $("input[type='radio']").click(function() {
        $("input[type='radio']").removeAttr('checked');
    	$(this).prop('checked', true);
    });
});
</script>