<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" deferredSyntaxAllowedAsLiteral="true" %>

<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>${textConfiguration.disc}列表
	</h3>
</div>
 
<!-- <div class="selectbar">
<table>
	<tr>
		<td><span class="TextFont">查找学科：</span></td>
		<td>

		<input type="text" name="seekDiscipline" value=""/>
		</td>
		<td ><a class="button" href="#"><span class="icon icon-search"></span>查找</a></td>
	</tr>
</table>
</div> -->
<div class="selectbar">
	<div class="inner_table_holder">
		<table class="layout_table left">
			<tr>
				<%-- <td><a class="button" href="#" id="addDiscipline_btn"><span class="icon icon-add" ></span>添加${textConfiguration.disc}</a></td> --%>
				<td><a class="button" href="#" id="editDiscipline_btn"><span class="icon icon-edit"></span>编辑${textConfiguration.disc}</a></td>
				<%-- <td><a class="button" href="#" id="deleteDiscipline_btn"><span class="icon icon-del"></span>删除${textConfiguration.disc}</a> --%>
				<!-- </td> -->
				
	
			</tr>
		</table>
		
		<table style="padding-left:80px;">
	   		<tr>
	   			<td >
					<span class="TextFont">${textConfiguration.discNumber}：</span>
				</td>
				<td >
					<input id="discNumber" type='text' value=""/>
				</td>
				<td>&nbsp;&nbsp;</td>
	   			<td>
					<span class="TextFont">${textConfiguration.discName}：</span>
				</td>
				<td >
					<input id="discName" type="text" value="" />
				</td>
				<td hidden="true">
					<input id="unitId" type="text" value="${userSession.unitId}" />
				</td>
				<td>&nbsp;&nbsp;</td>
				<td><a  id="disc-search_btn" class="button" href="#"><span class="icon icon-search"></span>查询</a>
				</td>
	   		</tr>
	   	</table>
	</div>
</div>

<table id="disciplineList_tb"></table>
<div id="pager"></div>

<div id="dialog-confirm" title="警告">
</div>

<script type="text/javascript">
	//加载按钮
	$(document).ready(function(){
		// 更改button样式
		$( "input[type=submit], a.button , button" )
		  .button();
		//显示当前学校下得所有学科信息
		$("#disciplineList_tb").jqGrid({
			url:'${ContextPath}/InfoMaintain/disciplinelist/',
			datatype:'json',
			mtype:'GET',
			colNames:['用户ID','${textConfiguration.discNumber}','${textConfiguration.discName}','办公电话','邮箱',''],
			colModel:[{name:'user.id',index:'id',align:"center", width:"100", hidden:true},
			          {name:'user.discId',index:'discId',align:"center", width:"100"},
			          {name:'user.name',index:'name',align:"center", width:"150"},
			          {name:'user.officePhone',index:'officePhone',align:"center", width:"50"},
			          {name:'user.email',index:'email',align:"center", width:"80"},
			          {name:'showDetails',index:'showDetails', align:'center',width:"40"}
			          ],
			height:"100%",
			autowidth:true,
			pager: '#pager',
			rowNum:10, 
			rowList:[10,20,30],
			viewrecords: true,
			sortname: "discId",
			sortorder: "desc",
			caption: "${textConfiguration.disc}列表",
		    gridComplete: function(){
		    	var ids = jQuery("#disciplineList_tb").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					
					var showdetails = "<a  href='#' onclick=showDetails('"+ids[i]+"')>查看</a>"; 
					jQuery("#disciplineList_tb").jqGrid('setRowData',ids[i],{showDetails :showdetails});
				}
		    },
			jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
                root: "rows",  //包含实际数据的数组  
                page: "pageIndex",  //当前页  
                total: "totalPage",//总页数  
                records:"totalCount", //查询出的记录数  
                repeatitems : false        
    			}
		}).navGrid('#pager',{edit:false,add:false,del:false});
		$(".fr_table td:nth-child(even)").addClass("fr_left");
		$(".fr_table td:nth-child(odd)").addClass("fr_right");
		
		$("#disc-search_btn").click(function(){
			jQuery("#disciplineList_tb").setGridParam({
				url:"${ContextPath}/InfoMaintain/disciplineSerach?"
					+"unitId="+$("#unitId").val()
				 	+"&discId="+$("#discNumber").val()
				 	+"&name="+$("#discName").val(),
				sortorder: "desc",
				page:1,
			}).trigger("reloadGrid");
			event.preventDefault();
		});
		$("#addDiscipline_btn").click(function(event){
			addDisciplineDialog("${ContextPath}/InfoMaintain/disciplineadd");
			//event.preventDefault();
		});
		
	});
	

	/*点击查看，查看学科用户详情   */
	function showDetails(rowId){
 		var rowData = $("#disciplineList_tb").jqGrid("getRowData", rowId);
 	    var id=rowData["user.id"];
     	showDisciplineDialog("${ContextPath}/InfoMaintain/disciplineshow?id="+id);	 
	}
	function showDisciplineDialog(url){
		$.post(url,function(data){
			$("#dialog").empty();
			$("#dialog").append(data);
			$("#dialog").dialog({
				title:"查看学科",
	  		    height:'530',
	  			width:'1040',
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  		    hide:'fade',
	  			show:'fade',
	  			resizable:false,
	  		    autoOpen:true,
	  		  	buttons:{
	  		    	"关闭":function(){
	  		    		$("#dialog").dialog("close");
	  		    		$("#dialog").empty();
	  		    	}
	  		    }
			});
		},'html');
	}
	//将add_discipline填充到添加学科的对话框
	function addDisciplineDialog(url){
		$.post(url,function(data){
			$("#dialog").empty();
			$("#dialog").append(data);
			$("#dialog").dialog({
				title:"添加学科",
	  		    height:'530',
	  			width:'1040',
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  		    hide:'fade',
	  			show:'fade',
	  			resizable:false,
	  		    autoOpen:true,
	  		    buttons:{
	  		    	"保存":function(){

	  	                var discinfo=$("#disciplineAdd_fr").serialize();
	  	                
	  	                //submit之前进行验证，若不通过则不能提交
	  	                if (!$("#disciplineAdd_fr").valid()) 
	  	                	return false;
	  	                else{
		  	         		$.ajax({
	    	  	      			type:'POST',
	    	  	      			url:'${ContextPath}/InfoMaintain/disciplinesaveadd',
	    	  	      			data:discinfo,
	    	  	      			success: function(data){
		    	  	      			if(data){
	    	  	      	   				$("#disciplineList_tb").setGridParam({url:'${ContextPath}/InfoMaintain/disciplinelist'}).trigger("reloadGrid");
		  	      	   		    		$("#dialog").dialog("close");
		    	  	      			}
		    	  	      			else
		    	  	      			{
			    	  	      			$("#dialog").dialog("close");
		    	  	      				alert_dialog("保存失败！");	
		    	  	      			}
	    	  	      			}
		  	      			});
	  	                }
	  	            },
	  		    	"取消":function(){
	  		    		$("#dialog").dialog("close");
	  		    		$("#dialog").empty();
	  		    	}
	  		    }
			});
		},'html');
	}
	
	//点击“编辑学科”按钮，弹出“编辑学科”对话框
	$("#editDiscipline_btn").click(function(event){
		var gr = jQuery("#disciplineList_tb").jqGrid('getGridParam','selrow');
  	  	if(gr!=null){
  	  		
  		  	var rowData = $("#disciplineList_tb").jqGrid("getRowData", gr);
  			var id=rowData["user.id"];
      	  	editDisciplineDialog("${ContextPath}/InfoMaintain/disciplineedit?id="+id);
  	  	}
  	  	else{
 		 
  			alert_dialog("请选择要编辑的行！");
 		}

		//event.preventDefault();
	});
	
	//将edit_discipline填充到编辑学科的对话框
	function editDisciplineDialog(url){
		$.post(url,function(data){
			$("#dialog").empty();
			$("#dialog").append(data);
			$("#dialog").dialog({
				title:"编辑学科",
	  		    height:'450',
	  			width:'980',
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  		    hide:'fade',
	  			show:'fade',
	  			resizable:false,
	  		    autoOpen:true,
	  		    buttons:{
	  		    	"保存":function(){
	  	                var discinfo=$("#disciplineEdit_fr").serialize();
	  	                //alert(discinfo);
	  	                if (!$("#disciplineEdit_fr").valid()) 
	  	                	return false;
	  	                else{
	  	         		$.ajax({
    	  	      			type:'POST',
    	  	      			url:'${ContextPath}/InfoMaintain/disciplinesaveedit',
    	  	      			data:discinfo+"&roleid=t0002",
    	  	      			success: function(data){
	    	  	      			if(data){
    	  	      	   				$("#disciplineList_tb").setGridParam({url:'${ContextPath}/InfoMaintain/disciplinelist'}).trigger("reloadGrid");
	  	      	   		    		$("#dialog").dialog("close");
	  	      	   		    		alert_dialog("保存成功！");
	    	  	      			}
	    	  	      			else
	    	  	      			{
		    	  	      			$("#dialog").dialog("close");
	    	  	      				alert_dialog("保存失败！");	
	    	  	      			}
    	  	      			}
	  	      			});
	  	                }
	  	         	
	  	         		$("#dialog").dialog("close");
	  		    		$("#dialog").empty();
	  	            },
	  		    	"取消":function(){
	  		    		$("#dialog").dialog("close");
	  		    		$("#dialog").empty();
	  		    	}
	  	            
	  		    }
			});
		},'html');
	}
	//删除学科
    $('#deleteDiscipline_btn').click(function(event){
  	  var gr = jQuery("#disciplineList_tb").jqGrid('getGridParam','selrow');

  	  if(gr!=null)
  	  {	
  		var rowData = $("#disciplineList_tb").jqGrid("getRowData", gr);
		var id=rowData["user.id"];
		//var name = rowData["user.discId"];
  		  //清空警告框内文本并添加新的警告文本
  		  
  		  $( "#dialog-confirm" ).empty().append("<p>你确定删除该学科吗？</p>");
  		  $( "#dialog-confirm" ).dialog({
      	      height:150,
      	      buttons: {
      	        "确定": function() {
      	        	$( this ).dialog( "close" );
      	        	$.post('${ContextPath}/InfoMaintain/disciplinedel?id=' + id, function(data){
      	    			  if(data){
      	    				  alert_dialog("删除"+"学科成功！");
      	    				  $("#disciplineList_tb").setGridParam({url:'${ContextPath}/InfoMaintain/disciplinelist'}).trigger("reloadGrid");
      	    			  }
      	    			  else
      	    			  {
      	    				  alert_dialog("删除" +"学科失败！");
      	   				  }
      	    		  });
      	        },
      	        "取消": function() {
      	          $( this ).dialog( "close" );
      	        }
      	      }
      	  });
  	  }else
 		  {
  		  alert_dialog("请选择删除行！");
 		  }
    });
	
</script>