<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <div class="con_header">
	<h3>
		<span class="icon icon-web"></span>${textConfiguration.discResultBackup}
	</h3>
</div>  
<div class="selectbar">
	<table class="layout_table">
		<tr>
			<td>
				<span class="TextFont">学校：</span>
			</td>
			<td>
				 <span class="TextFont"> <label id="lb_unit">${unitName}</label>   </span>
			</td>
			<td width = "20">
				 <span class="TextFont"> </span>
			</td>
			<td>
				<span class="TextFont">${textConfiguration.disc}：</span>
			</td>
			<td>
				<span class="TextFont"><label id="lb_discipline" >${discName}</label> </span>
			</td>
		</tr>
	</table>
</div>
<div class="selectbar">
	<table>
	     <tr>
	         <td>
	         	<a class="button" href="#" id="btn_backup">
	         		<span class="icon icon-backup"></span>备份当前数据</a>
	         	<a class="button" href="#" id="btn_restore">
	         		<span class="icon icon-undo"></span>还原数据</a>
	         	<a class="button" href="#" id="btn_delete">
	         		<span class="icon icon-del"></span>删除备份</a>
	        </td>
	     </tr>
    </table>
	<div class="table">
		<table id="version_list"></table>
		<div id="pager"></div>
	</div>
</div>
<div hidden="true">
	<div id="dialog-confirm" title="提示"></div>
</div>
<div hidden="true">
	<jsp:include page="/DataBackup/discipline_backup.jsp"></jsp:include>
</div>
<script src="${ContextPath}/js/edit_jqgrid.js"></script>
<script type="text/javascript">
	function viewDetail(id){
		var rowData = $('#version_list').jqGrid("getRowData", id);
    	var unitId = rowData['backup.unitId'];
    	var discId= rowData['backup.discId'];
    	var versionId = rowData['backup.versionId'];
    	backup_dialog(unitId,discId,versionId);
	}
	$(document).ready(function(){
		$( "input[type=submit], a.button , button" ).button();
		/********************  jqGrid ***********************/
		var lastsel; 
		
		$("#version_list").jqGrid({
			url:'${ContextPath}/databackup/disciplinebackup_getallversion',
			editurl:'${ContextPath}/databackup/disciplinebackup_saveremark',
			datatype: "json",
			colNames:['ID','学校ID','学科ID','数据版本号','备份时间','最近还原时间','备注','编辑备注','查看数据'],
			colModel:[
			    {name:'backup.id',index:'id',align:'center',editable:true,hidden:true},
			    {name:'backup.unitId',index:'unitId',editable:true,hidden:true},
				{name:'backup.discId',index:'discId',editable:true,hidden:true},
				{name:'backup.versionId',index:'versionId', width:80,align:'center'},
				{name:'backupTime',index:'backupTime', width:50, sorttype:"date",align:'center'},
				{name:'restoreTime',index:'restoreTime',width:50,align:'center'},
				{name:'remark',index:'remark', width:150,align:"center",editable:true},
				{name:'edit',index:'edit',width:20,align:'center',sortable:false},
				{name:'viewDetail',width:20,align:'center',sortable:false}
			],
			jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
	            root: "rows",  //包含实际数据的数组  
	            page: "pageIndex",  //当前页  
	            total: "totalPage",//总页数  
	            records:"totalCount", //查询出的记录数  
	            repeatitems : false,
	        },
			height:"100%",
			width:700,
			rownumbers:true,
			rownumWidth:15,
			pager: '#pager',
			rowNum:10,
			rowList:[10,20,30],
			sortname:'backupTime',
			autowidth:true,
			viewrecords: true,
			sortorder: "desc",
			onSelectRow: function(id){
			},
			caption: "版本管理",
			gridComplete:function(){
				var ids = $("#version_list").jqGrid('getDataIDs');
				for(var i=0; i < ids.length; i++){
					var ci = ids[i];
					var list_name = "#version_list";
					//editRow函数引自edit_jqgrid.js文件
					var editRow = "<a href='#' id='editGrid'  onclick=\"editRow('"+list_name+"','"+ci+"');\">编辑</a>";
					$('#version_list').setRowData(ids[i],{edit:editRow});
					var view = "<a href='#' onclick=\"viewDetail('"+ids[i]+"');\">查看</a>";
					$('#version_list').setRowData(ids[i],{viewDetail:view});
					
				}
			}
		}).navGrid('#pager',{edit:false,add:false,del:false});
	});
	
	function restoreData(){
		
	}
	
	$("#btn_restore").click(function(){
		var rowId = $("#version_list").jqGrid('getGridParam','selrow');
		if( !rowId ){
			alert_dialog('请选择要还原的版本');
		}
		else{
			/* if( confirm("确定还原吗?")){
				var discId = $("#version_list").getCell(rowId,'backup.discId');
				var unitId = $("#version_list").getCell(rowId,'backup.unitId');
				var id = $("#version_list").getCell(rowId,'backup.id');
				var versionId = $("#version_list").getCell(rowId,'backup.versionId');
				$.ajax({
					type:'POST',
					url:'${ContextPath}/databackup/disciplinebackup_restore',
					data:"discId="+discId+"&unitId="+unitId+"&id="+id+"&versionId="+versionId,
					success:function(data){
						if( data == true ){
							alert_dialog("还原成功");
							$("#version_list").setGridParam({url:'${ContextPath}/databackup/disciplinebackup_getallversion'}).trigger("reloadGrid");
						}
						else{
							alert_dialog("还原失败");
						}
					}
				});
			} */
			$( "#dialog-confirm" ).empty().append("<p>你确定要还原吗？</p>");
			$( "#dialog-confirm" ).dialog({
			      height:150,
			      buttons: {
			        "确定": function() {
			        	var discId = $("#version_list").getCell(rowId,'backup.discId');
						var unitId = $("#version_list").getCell(rowId,'backup.unitId');
						var id = $("#version_list").getCell(rowId,'backup.id');
						var versionId = $("#version_list").getCell(rowId,'backup.versionId');
						$.ajax({
							type:'POST',
							url:'${ContextPath}/databackup/disciplinebackup_restore',
							data:"discId="+discId+"&unitId="+unitId+"&id="+id+"&versionId="+versionId,
							success:function(data){
								if( data == true ){
									alert_dialog("还原成功");
									$("#version_list").setGridParam({url:'${ContextPath}/databackup/disciplinebackup_getallversion'}).trigger("reloadGrid");
								}
								else{
									alert_dialog("还原失败");
								}
							}
						});
			        	$( this ).dialog( "close" );
			        },
			        "取消": function() {
			            $( this ).dialog( "close" );
			          }
			        }
			  });
		} 
	});
	
	$("#btn_backup").click(function(){
		/* if( confirm("确定备份吗?") ){
			$.ajax({
				type:'POST',
				url:'${ContextPath}/databackup/disciplinebackup_add',
				success:function(data){
					if(data == true){
						alert_dialog("备份成功");
						$("#version_list").setGridParam({url:'${ContextPath}/databackup/disciplinebackup_getallversion'}).trigger("reloadGrid");
					}
					else
						alert_dialog("备份失败");
				}
			});
		} */
		$( "#dialog-confirm" ).empty().append("<p>你确定要备份吗？</p>");
		$( "#dialog-confirm" ).dialog({
			height:150,
		    buttons: {
		        "确定": function() {
		        	$.ajax({
						type:'POST',
						url:'${ContextPath}/databackup/disciplinebackup_add',
						success:function(data){
							if(data == true){
								alert_dialog("备份成功");
								$("#version_list").setGridParam({url:'${ContextPath}/databackup/disciplinebackup_getallversion'}).trigger("reloadGrid");
							}
							else
								alert_dialog("备份失败");
						}
					});
		        	$( this ).dialog( "close" );
		        },
		        "取消": function() {
		            $( this ).dialog( "close" );
		          }
		        }
		});
	});
	
	$("#btn_delete").click(function(){
		var rowId = $("#version_list").jqGrid('getGridParam','selrow');
		if( !rowId ){
			alert_dialog('请选择要删除的版本');
		}
		else{
			$( "#dialog-confirm" ).empty().append("<p>你确定要删除吗？</p>");
			$( "#dialog-confirm" ).dialog({
				height:150,
			    buttons: {
			        "确定": function() {
			        	var discId = $("#version_list").getCell(rowId,'backup.discId');
						var versionId = $("#version_list").getCell(rowId,'backup.versionId');
						$.ajax({
							type:'POST',
							url:'${ContextPath}/databackup/disciplinebackup_deletebackup',
							data:"versionId="+versionId+"&discId="+discId,
							success:function(data){
								if(data == true){
									alert_dialog("删除成功");
									$("#version_list").setGridParam({url:'${ContextPath}/databackup/disciplinebackup_getallversion'}).trigger("reloadGrid");
								}
								else
									alert_dialog("删除失败");
							}
						});
			        	$( this ).dialog( "close" );
			        },
			        "取消": function() {
			            $( this ).dialog( "close" );
			          }
			        }
			});
		} /* if( confirm("确定删除吗?")){
			var discId = $("#version_list").getCell(rowId,'backup.discId');
			var versionId = $("#version_list").getCell(rowId,'backup.versionId');
			$.ajax({
				type:'POST',
				url:'${ContextPath}/databackup/disciplinebackup_deletebackup',
				data:"versionId="+versionId+"&discId="+discId,
				success:function(data){
					if(data == true){
						alert_dialog("删除成功");
						$("#version_list").setGridParam({url:'${ContextPath}/databackup/disciplinebackup_getallversion'}).trigger("reloadGrid");
					}
					else
						alert_dialog("删除失败");
				}
			});
		} */
	});

</script>