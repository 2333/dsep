<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="con_header">
	<h3><span class="icon icon-web"></span>批次和规则信息</h3>
</div>
<div class="selectbar">
    <table class="layout_table">
        <tr>
            <td>
                <span class="TextFont">遴选批次</span>
                <label for="select"></label> 
                <select name="select" id="evalBatchForImport">
                    <option>请选择批次</option>
                </select>
                <span class="TextFont">遴选规则</span>
                <label for="select"></label> 
                <select name="select" id="evalRuleForImport">
                    <option>请先选择批次</option>
                </select>
            </td>
        </tr>
    </table>
</div>
<div id="ruleDetailContent">
</div>

<div class="selectbar" id="confirmContent" style="display:none">
    <table class="layout_table">
        <tr>
            <td><a class="button" href="#" id="confirmImport">确认导入</a></td>
        </tr>
    </table>
</div>

<script type="text/javascript">
$(document).ready(function(){
	
	$("input[type=submit], a.button , button").button();

	function loadBatchInformation(){
		$(".process_dialog2").show();
		$('.process_dialog2').dialog({
			position : 'center',
			modal:true,
			autoOpen : true,
		});
		var url = '${ContextPath}/expert/selectExpertSelectEveryBatchId/';
		$.post(url,function(data){
			var batchIdAndName = eval(data);
			$("#evalBatchForImport option").remove();
			console.log(batchIdAndName);
			if (batchIdAndName.length == 1) {
				batchId = batchIdAndName[0].substring(0, 32);
				batchName = batchIdAndName[0].substring(32);
				$("#evaBatchForImport").append("<option value='" +  1  + "' batchId='"+ batchId +"'>"+ batchName +"</option>");
				$(".process_dialog2").dialog("destroy");
				$(".process_dialog2").hide();
			} else {
				$("#evalBatchForImport").append("<option value='0'>-点击下拉框选择批次信息-</option>");
				for(var i = 0; i < batchIdAndName.length; i++) {
					// 后台传来的是rule的Id(32位)和Name拼接在一起的
					// 形如 {AF2a..F3k4L规则名称1}
					// 则batchId获得'AF2a..F3k4L'的32位Id
					// batchName获得'规则名称1'
					batchId = batchIdAndName[i].substring(0, 32);
					batchName = batchIdAndName[i].substring(32);
					if(batchId!=currentBatchId)
						$("#evalBatchForImport").append("<option value='" + ( i + 1 ) + "' batchId='"+ batchId +"' name='"+batchName+"'>"+ batchName +"</option>");
				}
				$(".process_dialog2").dialog("destroy");
				$(".process_dialog2").hide();
			}
		});
	}
	
	loadBatchInformation();
	
	$("#evalBatchForImport").change(function(){
		var e = document.getElementById("evalBatchForImport");
		var optionValue = e.options[e.selectedIndex].value;
		batchIdForImport = e.options[e.selectedIndex].getAttribute("batchId");
		var prevBatchId = $("#batchId").val();
		if(batchIdForImport!=prevBatchId){
			$("#ruleDetailContent").empty();
			document.getElementById("confirmContent").style.display="none";
		}	
		batchNameForImport = e.options[e.selectedIndex].getAttribute("name");
		if ('0' == optionValue) {
			//alert_dialog("请先选择批次!");
		} else {
            for(var i=0;i<e.options.length;i++){
            	if(e.options[i].value=='0')
            		e.remove(i);
            }
			$.post("${ContextPath}/rule/makeRuleGetRuleInfo/"+batchIdForImport,function(data){
				var ruleIdAndName = eval(data);
				$("#evalRuleForImport option").remove();
				if (ruleIdAndName.length == 1) {
					ruleId = ruleIdAndName[0].substring(0, 32);
					ruleName = ruleIdAndName[0].substring(32);
					$("#evalRuleForImport").append("<option value='" +  1  + "' ruleId='"+ ruleId +"'>"+ ruleName +"</option>");
				} else {
					$("#evalRuleForImport").append("<option value='0'>-点击下拉框选择规则信息-</option>");
					for(var i = 0; i < ruleIdAndName.length; i++) {
						// 后台传来的是rule的Id(32位)和Name拼接在一起的
						// 形如 {AF2a..F3k4L规则名称1}
						// 则batchId获得'AF2a..F3k4L'的32位Id
						// batchName获得'规则名称1'
						ruleId = ruleIdAndName[i].substring(0, 32);
						ruleName = ruleIdAndName[i].substring(32);
						$("#evalRuleForImport").append("<option value='" + ( i + 1 ) + "' ruleId='"+ ruleId +"' name='"+ruleName+"'>"+ ruleName +"</option>");
					}
				}
			
			});
		}
	});
	
	$("#evalRuleForImport").change(function(){
		var e = document.getElementById("evalRuleForImport");
		var optionValue = e.options[e.selectedIndex].value;
		var ruleIdForImport = e.options[e.selectedIndex].getAttribute("ruleId");
		if('0'==optionValue){
			
		}else{
			for(var i=0;i<e.options.length;i++){
            	if(e.options[i].value=='0')
            		e.remove(i);
            }
			$.post("${ContextPath}/rule/makeRuleGetImportRuleDetailDialog?id="+ruleIdForImport,function(data){
				$("#ruleDetailContent").empty();
				$("#ruleDetailContent").append(data);
				document.getElementById("confirmContent").style.display="";
			});
		}
	});
	
	$("#confirmImport").click(function(){
		var rule = $("#rule").serialize();
		console.log(rule);
		var ruleDetails = $("#details").serialize();var ruleComment = $("#comment").serialize();
		console.log(ruleDetails);
		var	detailCounter = $("#detailSize").val();	
		var urlDataPartition = '?detailCounter=' + detailCounter + "&currentBatchId=" + currentBatchId + "&" + rule + "&" + ruleDetails + "&" + ruleComment;
		$.ajax({
			type: "POST",
			// ★★对应RuleController里的路由
			url: '${ContextPath}/rule/makeRuleAddRule' + urlDataPartition,
			success: function(data){
				$('#rule_list').setGridParam({url:'${ContextPath}/rule/makeRuleGetRuleList/'+currentBatchId}).trigger("reloadGrid");
			}
		}); 
	});
});
</script>