<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<div id="editBatchInfo_div" class="form">
	<form id="batchEdit">
		<table id="edit_batch_tb_dialog" class="fr_table">
			<tr>
			    <td width='90'><span class="TextBold">遴选批次ID：</span></td>
				<td><input type="text" id="batchIdEdit" value="${batch.id}" type="hidden"/></td>
			</tr>
			<tr>
				<td width='90'><span class="TextBold">遴选批次号：</span></td>
				<td><input type="text" id="batchNumEdit" value="${batch.batchNum}" /></td>
			</tr>
			<tr>
				<td width='90'><span class="TextBold">遴选批次名称：</span></td>
				<td><input type="text" id="batchChNameEdit" value="${batch.batchChName}" /></td>
			</tr>
		</table>
	</form>
	
	<form id="batchContentEditForIndustrial">
	    <table id="edit_batch_content_dialog_1" class="fr_table">
	        <tr>
	            <td><input type="checkbox" id="itemEdit1_1" checked value="" name="indicIdx">指标体系</td>
	        </tr>
	        <tr>
	            <td><input type="checkbox" id="itemEdit1_2" checked value="" name="indicWt">权重</td>
	        </tr>
	        <tr>
	            <td><input type="checkbox" id="itemEdit1_3" checked value="" name="achv">成果</td>
	        </tr>
	        <tr>
	            <td><input type="checkbox" id="itemEdit1_4" checked value="" name="repu">声誉</td>
	        </tr>
	        <tr>
	            <td><input type="checkbox" id="itemEdit1_5" checked value="" name="rank">排名</td>
	        </tr>  
	        <!-- <tr>
	          <td><a id="saveEditBatch" class="button" href="#" style="float:right;margin-right: .3em;">
			  <span class="icon icon-edit"></span>确认</a></td>
			</tr> -->
	    </table>
	</form>
	<form id="batchContentEditForAcademic">
	    <table id="edit_batch_content_dialog_2" class="fr_table">
	        <tr>
	            <td><input type="checkbox" id="itemEdit2_1" checked value="" name="indicIdx">指标体系</td>
	        </tr>
	        <tr>
	            <td><input type="checkbox" id="itemEdit2_2" checked value="" name="indicWt">权重</td>
	        </tr>
	        <tr>
	            <td><input type="checkbox" id="itemEdit2_3" checked value="" name="achv">成果</td>
	        </tr>
	        <tr>
	            <td><input type="checkbox" id="itemEdit2_4" checked value="" name="repu">声誉</td>
	        </tr>
	        <tr>
	            <td><input type="checkbox" id="itemEdit2_5" checked value="" name="rank">排名</td>
	        </tr>   
	    </table>
	</form>
	
	<a id="saveEditBatch" class="button" href="#" style="float:right;margin-right: .3em;">
			  <span class="icon icon-edit"></span>确认</a>
	
	<!-- <a id="addRule" class="button" href="#"> 新建规则</a> -->
	
	</div>
	
	<script type="text/javascript">
	$(document).ready(function(){
		
		$("input[type=submit], a.button , button").button();
		
	    $("input[type='checkbox']").click(function() {
	        if($(this).prop('checked')==true){
	        	$(this).attr('checked');
	        }	
	    });
	    
	    $("#saveEditBatch").click(function(){
       	     console.log("hahaha");
       	     var batchId = $("#batchIdEdit").val();
			 var batchNum = $("#batchNumEdit").val();
			 var batchChName = $("#batchChNameEdit").val();
			 var industrialItems = [];
			 var academicItems = [];
			 for(var i=0;i<6;i++){
				 if($("#itemEdit1_"+i+"").prop("checked")){
					 var item = $("#itemEdit1_"+i+"").attr("name");
			   	     industrialItems.push(item); 
				 }
				 if($("#itemEdit2_"+i+"").prop("checked")){
					 var item = $("#itemEdit2_"+i+"").attr("name");
			   	     academicItems.push(item); 
				 }
			 }
			 if(industrialItems.length!=0 && academicItems.length!=0){
				 var addItem = {"num":batchNum,"batchName":batchChName,
				    		"industrialItems":industrialItems,"academicItems":academicItems};
				 $.ajax({
					 type:'POST',
				     dataType:'json',
				     url:"${ContextPath}/rule/makeRuleSaveEditBatch?id="+batchId,
				     contentType : "application/json",               
		             data : JSON.stringify(addItem), 
		             success:function(){
		            	 $('#dialog').dialog("close");
		            	 $('#dialog').empty();
		            	 $("#batch_list").setGridParam({url:"${ContextPath}/rule/makeRuleGetBatchData"}).trigger('reloadGrid');
		             }
				 });
			 }else alert_dialog("请保留至少一个打分项");
		 });


	});

         
	</script>