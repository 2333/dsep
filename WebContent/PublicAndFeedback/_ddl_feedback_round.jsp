<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table class="layout_table left">
	<tr>
		<td><span class="icon icon-see"></span></td>
		<td>
			<span class="TextFont">反馈批次:</span> 
			<select id="ddl_feedback_round">
				<c:choose>
					<c:when test="${!empty feedbackRoundMap}">
						<c:forEach items="${feedbackRoundMap}" var="feedbackrounditem">
							<option value="${feedbackrounditem.key}">
								${feedbackrounditem.value}</option>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<option value="0">暂无</option>
					</c:otherwise>
				</c:choose>
			</select>
		</td>
	</tr>
</table>
<table id="tb_message" class="layout_table right">
	<tr>
		<td><span id="sp_status" class="TextFont">反馈状态：</span></td>
		<td><label id="disstatus" for="disstatus" class="TextFont">
		</label>&nbsp;&nbsp;</td>
		<td><span id="sp_beginTime" class="TextFont">开始时间：</span></td>
		<td><label id="beginTime" for="disstatus" class="TextFont">
		</label>&nbsp;&nbsp;</td>
		<td><span id="sp_endTime" class="TextFont">结束时间：</span></td>
		<td><label id="endTime" for="disstatus" class="TextFont">
		</label>&nbsp;&nbsp;</td>
	</tr>
</table>

<script type="text/javascript">
	var ddl_open_status = "";
	var backupVersionId = "";
	function showFeedbackMessage(theUrl) {
		var obj = document.getElementById('ddl_feedback_round');
		var index = obj.selectedIndex; //序号，取当前选中选项的序号
		var val = obj.options[index].value;
		$.ajax({
			type : 'POST',
			async : false,
			url : theUrl,
			data : "feedback_round_id=" + val,
			dataType : 'json',
			success : function(data) {
				if (data != null) {
					$("label#disstatus").html(data.openStatus + "");
					$("label#beginTime").html(data.beginTime + "");
					$("label#endTime").html(data.endTime + "");
					if( $("label#beginTime").html() == ""){
						$("label#beginTime").html("暂无");
					}
					if( $("label#endTime").html() == ""){
						$("label#endTime").html("暂无");
					}
					if( data.openStatus == "进行中")
						ddl_open_status = '1';
					else
						ddl_open_status = '0';
					backupVersionId = data.backupVersionId;
					console.log(backupVersionId);
				} else {
					$("#tb_message").children().children().hide();
				}
			}
		});
	}
</script>