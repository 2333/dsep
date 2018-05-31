<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 该注释为JSP注释，查看页面源码看不见
ruleDetail的英文名
detail1  每组专家数：sameDisciplineSumLimit
detail2  同单位专家数：sameUnitSumLimit
detail3  专家评价学科数：disciplineNumber
detail4  不设置专家评价学科数：notSetDisciplineNumber
detail5  仅在参评单位中选：onlyAttend
detail6  在参评单位及未参评单位中选：attendAndAccredit
detail7  不在参评单位中选：notAttend
detail8  在全部学科授予单位中选：allUnits
detail9  年龄：age
detail10  管理专家：manageExpert
detail11  管理专家所占百分比：manageExpertPCT
detail12  高水平专家：highLevelExpert
detail13  博士生导师：doctoralSupervisor
detail14  硕士生导师：masterSupervisor
detail15  985高校：985Expert
detail16  研究生院高校：graduateSchoolExpert
detail17  211高校：211Expert
detail18  国家重点学科（含培育学科）专家：nationalKeyDiscipline
detail19  博士一级学科专家优先：doctoralFirstLevelDiscipline
 --%>

<%-- 这行非常重要，它标志了datail的个数并传到后台！ --%>
<input type="hidden" value="19" id="detailSize">
<div id="addRuleInfo_div" class="form">
	<form id="rule">
		<table id="add_rule_tb_dialog" class="fr_table">
			<tr>
				<td width='160'><span class="TextBold">遴选规则名称：</span></td>
				<td><input type="text" name="rule.ruleName" /></td>
			</tr>
		</table>
	</form>
	
	<form id="details">
		<table id="add_Rule_tb_dialog" class="fr_table">
		
			<tr>
				<td><span class="TextBold">优先条件：</span></td>
			</tr>
			
			<tr>
				<td width='165'>
				<span class="TextBold">每组专家数</span>
				</td>
				<td>
					<input type="hidden" name="detail1.operator" value="between">
					<input type="hidden" name="detail1.itemName" value="sameDisciplineSumLimit">
					<input type="text" size="1" name="detail1.restrict1" value="${detail1.restrict1}" />~~
					<input type="text" size="1" name="detail1.restrict2" value="${detail1.restrict2}" />
				</td>
			</tr>
			<tr>
				<td width='165'>
				<span class="TextBold">同单位专家数</span>
				</td>
				<td>
					<input type="hidden" name="detail2.operator" value="between">
					<input type="hidden" name="detail2.itemName" value="sameUnitSumLimit">
					<input type="text" size="1" name="detail2.restrict1" value="${detail2.restrict1}" />~~
					<input type="text" size="1" name="detail2.restrict2" value="${detail2.restrict2}" />
				</td>
			</tr>
			<tr>
				<td><span class="TextBold">专家评价学科数</span></td>
				<td>
				<input type="hidden" name="detail3.operator" value="is">
				<input type="hidden" name="detail3.itemName" value="disciplineNumber">
				<input type="text" size="2" name="detail3.restrict1" value="${detail3.restrict1}" />
				</td>
			</tr>
			<tr>
				<td>
					<input type="hidden" name="detail4.sequ" value="4">
					<input type="checkbox" name="detail4.check" checked="checked" />
					不设置专家评价学科数
					<input type="hidden" name="detail4.operator" value="check">
					<input type="hidden" name="detail4.itemName" value="notSetDisciplineNumber">
				</td>
			</tr>
			
			
			
			<tr class="fr_left">
				<td colspan=2>
					<input type="hidden" name="detail5.sequ" value="5">
					<input type="radio" name="detail5.check" <c:if test="${detail5.conditionChecked}">checked</c:if> />仅在参评单位中选<br />
					<input type="hidden" name="detail5.operator" value="check">
					<input type="hidden" name="detail5.itemName" value="onlyAttend">
				</td>
			</tr>
			<tr class="fr_left">
				<td colspan=2>
					<input type="hidden" name="detail6.sequ" value="6">
					<input type="radio" name="detail6.check" <c:if test="${detail6.conditionChecked}">checked</c:if> />在参评单位及未参评单位中选<br />
					<input type="hidden" name="detail6.operator" value="check">
					<input type="hidden" name="detail6.itemName" value="attendAndAccredit">
				</td>
			</tr>
			<tr class="fr_left">
				<td colspan=2>
					<input type="hidden" name="detail7.sequ" value="7">
					<input type="radio" name="detail7.check" <c:if test="${detail7.conditionChecked}">checked</c:if> />不在参评单位中选<br />
					<input type="hidden" name="detail7.operator" value="check">
					<input type="hidden" name="detail7.itemName" value="notAttend">
				</td>
			</tr>
			<tr class="fr_left">
				<td colspan=2>
					<input type="hidden" name="detail8.sequ" value="8">
					<input type="radio" name="detail8.check" <c:if test="${detail8.conditionChecked}">checked</c:if> />在全部学科授予单位中选<br />
					<input type="hidden" name="detail8.operator" value="check">
					<input type="hidden" name="detail8.itemName" value="allUnits">
				</td>
			</tr>
			<tr>
				<td>
					<span class="TextBold">年龄</span>
				</td>
				<td>
					<input type="hidden" name="detail9.operator" value="between">
					<input type="hidden" name="detail9.itemName" value="age">
					<input type="text" size="1" name="detail9.restrict1" value="${detail9.restrict1}" />~~
					<input type="text" size="1" name="detail9.restrict2" value="${detail9.restrict2}" />
				</td>
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="detail10.check" <c:if test="${detail10.conditionChecked}">checked</c:if>/>
					管理专家
					<input type="hidden" name="detail10.operator" value="check">
					<input type="hidden" name="detail10.itemName" value="manageExpert">
				</td>
			</tr>
			<tr>
				<td><span class="TextBold">管理专家所占百分比</span></td>
				<td>
				<input type="hidden" name="detail11.operator" value="is">
				<input type="hidden" name="detail11.itemName" value="manageExpertPCT">
				<input type="text" size="2" name="detail11.restrict1" value="${detail11.restrict1}" />
				</td>
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="detail12.check" <c:if test="${detail12.conditionChecked}">checked</c:if>/>
					高水平专家
					<input type="hidden" name="detail12.operator" value="check">
					<input type="hidden" name="detail12.itemName" value="highLevelExpert">
				</td>
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="detail13.check" <c:if test="${detail13.conditionChecked}">checked</c:if>/>
					博士生导师
					<input type="hidden" name="detail13.operator" value="check">
					<input type="hidden" name="detail13.itemName" value="doctoralSupervisor">
				</td>
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="detail14.check" <c:if test="${detail14.conditionChecked}">checked</c:if>/>
					硕士生导师
					<input type="hidden" name="detail14.operator" value="check">
					<input type="hidden" name="detail14.itemName" value="masterSupervisor">
				</td>
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="detail15.check" <c:if test="${detail15.conditionChecked}">checked</c:if>/>
					985高校
					<input type="hidden" name="detail15.operator" value="check">
					<input type="hidden" name="detail15.itemName" value="985Expert">
				</td>
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="detail16.check" <c:if test="${detail16.conditionChecked}">checked</c:if>/>
					研究生院高校
					<input type="hidden" name="detail16.operator" value="check">
					<input type="hidden" name="detail16.itemName" value="graduateSchoolExpert">
				</td>
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="detail17.check" <c:if test="${detail17.conditionChecked}">checked</c:if>/>
					211高校
					<input type="hidden" name="detail17.operator" value="check">
					<input type="hidden" name="detail17.itemName" value="211Expert">
				</td>
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="detail18.check" <c:if test="${detail18.conditionChecked}">checked</c:if>/>
					国家重点学科（含培育学科）专家
					<input type="hidden" name="detail18.operator" value="check">
					<input type="hidden" name="detail18.itemName" value="nationalKeyDiscipline">
				</td>
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="detail19.check" <c:if test="${detail19.conditionChecked}">checked</c:if>/>
					博士一级学科专家优先
					<input type="hidden" name="detail19.operator" value="check">
					<input type="hidden" name="detail19.itemName" value="doctoralFirstLevelDiscipline">
				</td>
			</tr>
 			
		</table>
	</form>
		<form id="comment">
		<table id="add_rule_tb_dialog" class="fr_table">
			<tr><td><span class="TextBold">关于该遴选规则的备注</span></td></tr>
			<tr>
				<td><textarea rows="5" cols="45" name="rule.commentForRule">${rule.commentForRule }</textarea></td>
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