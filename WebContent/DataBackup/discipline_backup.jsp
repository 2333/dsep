<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="disc_backup_dv" class="table">
	<form id="disc_backup_fm">
	<div>
		<table>
		</table>
	</div>
		<div class="layout_holder">
			<jsp:include page="/BackupMeta/backup_tree.jsp"></jsp:include>
			<jsp:include page="/BackupMeta/backup_view.jsp"></jsp:include>
		</div>
	</form>
</div>
<script type="text/javascript">
	function backup_dialog(unitId,discId,versionId){
		setBKVersionId(versionId);//backup_view.jsp页面中的函数
		requestCollectTree(unitId,discId);
		$('#disc_backup_dv').dialog({
  		    title:"备份数据",
  		    height:'800',
  			width:'90%',
  			position:'center',
  			modal:false,
  			draggable:true,
  		    hide:'fade',
  			show:'fade',
  		    autoOpen:true,
  		    close:function(){
  		    	tr_flag=true;
  		    	$("#hide_tree").unbind('click');
  		    },
  		    buttons:{  
 	            "关闭":function(){
 	            	$('#disc_backup_dv').dialog('close');
 	            }
  		    }}); 
	}
</script>