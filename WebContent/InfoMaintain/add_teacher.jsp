<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<div class="form">
	<form id="teacherAdd_fm" class="fr_form" action="" method="post">
		<jsp:include page="/InfoMaintain/teacher_info.jsp"></jsp:include>
	</form>
</div>
 
<script type="text/javascript">
	$(document).ready(function(){
		$(".fr_table td:nth-child(even)").addClass("fr_left");
		$(".fr_table td:nth-child(odd)").addClass("fr_right");
		
	});
</script>