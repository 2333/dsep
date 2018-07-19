<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${ContextPath}/js/expert.common.js"></script>
<table class="layout_table">
<c:forEach var="item" items="${batchIdAndNames}">  
	<tr>
		<td>
			<a class="button ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="#" onclick="chooseBatch(this);return false;">
				<span class="ui-button-text">
				${item.value}<span class="icon icon-edit"></span>
				</span>
				<input type="hidden" value="${item.key}" class="batchId">
			</a>
		</td>
	</tr>
</c:forEach>  
</table>
<script type="text/javascript">
chooseBatch = function(sel) {
	$("#dialog").dialog("close");
	$('#dialog').empty();
	var that = $(sel);
	var currentBatchId = that.children("input.batchId").val();
	var url = "${ContextPath}/evaluation/progressInitCurrentBatch/"+currentBatchId;
	$.post(url, function(data){
		  $( "#content" ).empty();
		  $( "#content" ).append( data );
	  }, 'html');
};
</script>