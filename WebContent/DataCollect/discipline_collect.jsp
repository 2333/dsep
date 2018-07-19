<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header inner_table_holder">
	<h3 class="layout_table left">
		<span class="icon icon-file"></span>${textConfiguration.discInfoFill}
	</h3>
	<table class="layout_table right">
		<tr>
			<td>
				<span class="TextFont">数据状态：</span>
			</td>
			<td>
				<label id="collect_state" for="collect_state" style="font-size:14px;font-weight:bold;color:#2e6e9e;"></label>
			</td>
		</tr>
	</table>
</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">学校编号：</span>
			</td>
			<td>
				<input type="text" id="unit_Id" value='${unitId}' disabled/>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
				<span class="TextFont">${textConfiguration.discNumber}：</span>
			</td>
			<td>
				<input type="text" id="disc_Id" value='${discId} ' disabled/>
			</td>
			 <!-- <td>
				<a id="search_collection" class="button" href="#"><span class="icon icon-search"></span>查询</a>
			</td> -->
		</tr>
	</table>
	<table class="layout_table right">
		<tr>
 			<!--  <td>
				<a  id="download_brief_report_btn" class="button" href="#"><span class="icon icon-download"></span>下载简况表</a>
			 </td>	 -->	
           <!--   <td>
				<a id="submit2Unit" class="button" href="#"><span class="icon icon-commit"></span>提交</a>
			 </td> -->
		</tr>
</table>
</div>
<div class="layout_holder">
	<jsp:include page="/CollectMeta/collect_tree.jsp"></jsp:include>
	<jsp:include page="/CollectMeta/collect_view.jsp"></jsp:include>
	<div id = "jq_collect_notice" class="selectbar right_block">
<div class="comment">
	<span class="ui-icon ui-icon-info" style="float: left; margin-right: .10em;position:absolute"></span> 
	<p style="margin-left:0px;">提示:如需添加信息，请选择左侧列表中相应类别！</p>
</div>
</div>
</div>
<div id="messageBox_dv" class="table" hidden="true">
	<form id="messageBox_fm">
	<div id="message">
		
	</div>
	</form>
</div>
<script src="${ContextPath}/js/jquery.common.js"></script>
<script src="${ContextPath}/js/download/briefsheet.js"></script>
<script type="text/javascript">
	/**
	 * 学科数据提交至学校
	 * @param unitId
	 * @param discId
	 */
var domainId = "${configurations.domainId}";
	function submit2Unit(unitId,discId,isConfirm){
		$.commonRequest({
			url:'${ContextPath}/FlowActions/disc2Unit/'+unitId+'/'+discId+'/'+isConfirm,
			dataType:'json',
			success:function(result){
				if(result.message=='success')
				{
					collectState(unitId,discId);
					isEditable_fun(unitId,discId);
					var editor = CKEDITOR.instances.disc_editor;
					console.log(editor);
					editor.setReadOnly(true);
					$("#jqGrid_collect_tb").jqGrid('setGridParam').trigger("reloadGrid");
					$("#submit2Unit").hide();
					alert_dialog('提交成功！');	
					
				}else{
					var errorMessage = result;
					if(errorMessage.passed==false){
						console.log(errorMessage.data);
						$( "#message" ).empty();
						$.each(errorMessage.data,function(i,item){
							if(item.typeName=='错误'){
								$( "#message" ).append("<div class='message'><span class='red'>"+item.typeName+"</span>"+
										" : \""+item.entityName+"\" , "+item.conclusion+"</div>");
							}else{
								$( "#message" ).append("<div class='message'><span class='yellow'>"+item.typeName+"</span>"+
										" : \""+item.entityName+"\" , "+item.conclusion+"</div>");
							}				
						});
						$("#message").append("<div><span class='TextFont'>您确定提交吗？</span></div>");
						$(".red").css({"color":"red"});
						$(".yellow").css({"color":"#EEC591"});
						$(".message").css({"font-size":"18px"});
						$( "#messageBox_dv" ).dialog({
							  width:500,
				      	      height:450,
				      	      buttons: {
				      	        "确定": function() {
				      	        	submit2Unit(unitId,discId,1);
				      	        	$( this ).dialog( "close" );
				      	        },
				      	        "取消": function() {
				      	            $( this ).dialog( "close" );
				      	          }
				      	        }
				      	  });
					}else if(errorMessage.passed!=false){
						alert_dialog(result.message);
					}	
				}
			}
		});
	}
	function collectState(unitId,dicsId)
	{
		if(unitId!=""&&discId!=""){
			$.commonRequest({
				url:'${ContextPath}/Collect/toCollect/collectState/'+unitId+'/'+dicsId,
				dataType:'text',
				success:function(data){
					data=convertStatus(data,domainId);
					var state = data;
					console.log(state);
					$("#collect_state").html(data);
					}
				});
		}	
	}
	$(document).ready(function(){
		$("#tAchieveToDisc").show();//教师导入学科的按钮在collect_oper_banner.jsp中
		collectState(collegeId,disciplineId);
		$("#submit2Unit").click(function(){
			$( "#dialog-confirm" ).empty().append("<p>你确定要提交吗？</p>");
	  		  $( "#dialog-confirm" ).dialog({
	      	      height:150,
	      	      buttons: {
	      	        "确定": function() {
	      	        	submit2Unit(unitId,discId,0);
	      	        	$( this ).dialog( "close" );
	      	        },
	      	        "取消": function() {
	      	            $( this ).dialog( "close" );
	      	          }
	      	        }
	      	  });
		});
	});
</script>