<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table class="layout_table left">
<tr>
	<td><span class="icon icon-see"></span></td>
	<td><span class="TextFont">公示批次:</span> <select
		id="ddl_publicity_round">
			<c:choose>
				<c:when test="${!empty publicityRoundMap}">
					<c:forEach items="${publicityRoundMap}" var="publicityrounditem">
						<option value="${publicityrounditem.key}">
							${publicityrounditem.value}</option>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<option value="0">暂无</option>
				</c:otherwise>
			</c:choose>
	</select></td>
</tr>
</table>
<table id="tb_message" class="layout_table right">
	<tr>
		<td><span id="sp_status" class="TextFont">公示状态：</span></td>
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
	function showPublicityMessage() {
		var obj = document.getElementById('ddl_publicity_round');
		var index = obj.selectedIndex; //序号，取当前选中选项的序号
		var val = obj.options[index].value;
		$.ajax({
			type : 'POST',
			async : false,
			url : '${ContextPath}/publicity/viewPub_getPublicityMessage',
			data : "publicity_round_id=" + val,
			dataType : 'json',
			success : function(data) {
				if (data != null) {
					$("label#disstatus").html(data.openStatus + "  ");
					$("label#beginTime").html(data.beginTime + "  ");
					$("label#endTime").html(data.endTime + "  ");
					backupVersionId = data.versionId;
					if( data.openStatus == "进行中")
						ddl_open_status = '1';
					else
						ddl_open_status = '0';
				} else {
					$("#tb_message").children().children().hide();
				}
			}
		});
	}
</script>