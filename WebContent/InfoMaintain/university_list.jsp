<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" deferredSyntaxAllowedAsLiteral="true" %>
    
<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>学校列表
	</h3>
</div>
<div class="selectbar">
	<div class="inner_table_holder">
		<table class="layout_table left">
			<tr>
				<%-- <td><a class="button" href="#" id="addDiscipline_btn"><span class="icon icon-add" ></span>添加${textConfiguration.disc}</a></td> --%>
			<td>
				<a class="button" href="#" id="editUniversity_bt"><span class="icon icon-edit" id="editUniversity"></span>编辑学校</a>
			</td>
				<%-- <td><a class="button" href="#" id="deleteDiscipline_btn"><span class="icon icon-del"></span>删除${textConfiguration.disc}</a> --%>
				<!-- </td> -->
				
	
			</tr>
		</table>
		
		<table style="padding-left:80px;">
	   		<tr>
	   			<td >
					<span class="TextFont">学校编号：</span>
				</td>
				<td >
					<input id="unitNumber" type='text' value=""/>
				</td>
				<td>&nbsp;&nbsp;</td>
	   			<td>
					<span class="TextFont">学校名称：</span>
				</td>
				<td >
					<input id="unitName" type="text" value="" />
				</td>
				<td hidden="true">
					<input id="unitId" type="text" value="${userSession.unitId}" />
				</td>
				<td>&nbsp;&nbsp;</td>
				<td><a  id="unit-search_btn" class="button" href="#"><span class="icon icon-search"></span>查询</a>
				</td>
	   		</tr>
	   	</table>
	</div>
</div>
<table id="universityList_tb"></table>
<div id="pager"></div>
<div id="dialog-confirm" title="警告">
</div>
<script type="text/javascript">
	//加载按钮
	//$(document).ready(function(){
		$( "input[type=submit], a.button , button" )
		  .button();
		//初始化表格
		$("#universityList_tb").jqGrid({
			url:'${ContextPath}/InfoMaintain/universityList/',
			datatype:'json',
			mtype:'GET',
			colNames:['用户ID','学校编号','学校名称','通讯电话','办公电话','电子邮箱',''],
			colModel:[
					{name:'user.id',index:'id', align:"center",width:"300", sorttype:"String",editable:true ,hidden:true},
					{name:'user.unitId',index:'unitId', align:"center",width:"300", sorttype:"String",editable:true },
					{name:'user.name',index:'name',align:"center", width:"400",sorttype:"String", editable:true },	
					{name:'user.cellPhone',index:'cellPhone',align:"center", width:"200",sorttype:"String", editable:true },
					{name:'user.officePhone',index:'officePhone',align:"center", width:"200",sorttype:"String", editable:true },
					{name:'user.email',index:'email',align:"center", width:"300",sorttype:"String", editable:true },
					{name:'showDetails',index:'showDetails',align:'center',width:'200'}
			          ],
			height:"100%",
			autowidth:true,
			pager: '#pager',
			rowNum:10, 
			rowList:[10,20,30],
			viewrecords: true,
			sortname: "unitId",
			sortorder: "desc",
			caption: "学校列表",
			gridComplete: function(){
		    	var ids = jQuery("#universityList_tb").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					
					var showdetails = "<a  href='#' onclick=showSchoolDetails('"+ids[i]+"')>查看</a>"; 
					jQuery("#universityList_tb").jqGrid('setRowData',ids[i],{showDetails :showdetails});
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
		

	//});	
	/*点击查看 学校详细信息   */
	function showSchoolDetails(rowId){
			var rowData = $('#universityList_tb').jqGrid("getRowData", rowId);
			var id=rowData["user.id"];
			showUniversityDialog("${ContextPath}/InfoMaintain/universityshow?id="+id);

	}
	function showUniversityDialog(url){
		$.post(url,function(data){
			$("#dialog").empty();
			$("#dialog").append(data);
			$("#dialog").dialog({
				title:"查看学校",
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
	
	$("#unit-search_btn").click(function(){
		jQuery("#universityList_tb").setGridParam({
			url:"${ContextPath}/InfoMaintain/universitySerach?"
			 	+"unitId="+$("#unitNumber").val()
			 	+"&name="+$("#unitName").val(),
			sortorder: "desc",
			page:1,
		}).trigger("reloadGrid");
		event.preventDefault();
	});
	//添加学校用户按钮事件
	$("#addUniversity_bt").click(function(event){
		addUniversityDialog("${ContextPath}/InfoMaintain/universityadd");
		//event.preventDefault();
	});
	function addUniversityDialog(url){
		$.post(url,function(data){
			$("#dialog").empty();
			$("#dialog").append(data);
			$("#dialog").dialog({
				title:"添加学校",
	  		    height:'530',
	  			width:'1040',
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  		    hide:'fade',
	  		    resizable:false,
	  			show:'fade',
	  		    autoOpen:true,
	  		    buttons:{
	  		    	"确定":function(){
	  		    		var schoolInfo = $('#universityAdd_fr').serialize();
	  		    		/* 确认提交时进行验证 */
	  		    		if($('#universityAdd_fr').valid()){
	  		    			$.ajax({
	    	  	      			type:'POST',
	    	  	      			url:'${ContextPath}/InfoMaintain/universitysaveadd',
	    	  	      			data:schoolInfo,
	    	  	      			success: function(data){
		    	  	      			if(data == true){
		    	  	      				$("#universityList_tb").setGridParam({url:'${ContextPath}/InfoMaintain/universityList/'}).trigger("reloadGrid");
	   	  	      	   					$("#dialog").dialog("close");
	   	  	      	   				}
	   	  	      	   				else{
			   	  	      	   			$("#dialog").dialog("close");
				  	      				alert_dialog("保存失败！");	
	   	  	      	   				}
	    	  	      			}
		  		    		});
	  		    		}
	  		    	},
	  		    	"取消":function(){
	  		    		$("#dialog").dialog("close");
	  		    	}
	  		    }});
		},'html');
	}
	
	//编辑用户事件
	$("#editUniversity_bt").click(function(event){
		var gr = $("#universityList_tb").jqGrid('getGridParam','selrow');
		if(gr != null){
			var rowData = $('#universityList_tb').jqGrid("getRowData", gr);
			var id=rowData["user.id"];
			editUniversityDialog("${ContextPath}/InfoMaintain/universityedit?id="+id);
		}
		else{
			alert_dialog("请选择要编辑的行！");
		}
	});
	
	function editUniversityDialog(url){
		$.post(url,function(data){
			$("#dialog").empty();
			$("#dialog").append(data);
			$("#dialog").dialog({
				title:"编辑学校",
	  		    height:'450',
	  			width:'980',
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  			resizable:true,
	  		    hide:'fade',
	  			show:'fade',
	  		    autoOpen:true,
 	  		    buttons:{
	  		    	"确定":function(){
	  		    		var schoolInfo = $('#universityEdit_fr').serialize();
	  		    		//alert(schoolInfo);
	  		    		/* 提交确认时验证  */
	  		    		if($('#universityEdit_fr').valid()){
	  		    			$.ajax({
    	  	      				type:'POST',
    	  	      				url:'${ContextPath}/InfoMaintain/universitysaveedit',
    	  	      				data:schoolInfo+"&roleid=t0003",
    	  	      				success: function(data){
	    	  	      				if(data == true){
    	  	      	   					$("#universityList_tb").setGridParam({url:'${ContextPath}/InfoMaintain/universityList'}).trigger("reloadGrid");
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
	  		    	},
	  		    	"取消":function(){
	  		    		$("#dialog").dialog("close");
	  		    	}
	  		    } 
	  		    });
		},'html');
	}
	
	$('#delUniversity_bt').click(function(event){
	      var gr = jQuery("#universityList_tb").jqGrid('getGridParam','selrow');
	  	  if(gr!=null)
	  	  {	
	  		  //清空警告框内文本并添加新的警告文本
	  		  $( "#dialog-confirm" ).empty().append("<p>你确定删除该用户吗？</p>");
	  		  $( "#dialog-confirm" ).dialog({
	      	      height:150,
	      	      buttons: {
	      	        "确定": function() {
	      	        	$( this ).dialog( "close" );
	      	        	var rowData = $("#universityList_tb").jqGrid("getRowData", gr);
	      	  			var id = rowData["user.id"];
	      	  			var name = rowData["user.name"];
	      	        	$.post('${ContextPath}/InfoMaintain/universitydel?id='+ id, function(data){
	      	    			  if(data){
	      	    				  alert_dialog("删除"+ name +"用户成功！");
	      	    				  $("#universityList_tb").setGridParam({url:'${ContextPath}/InfoMaintain/universitylist'}).trigger("reloadGrid");
	      	    			  }
	      	    			  else
	      	    			  {
	      	    				  alert_dialog("删除"+ name +"用户失败！");
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
			  alert_dialog("请选择要删除的行！");
		  }
	    });
</script>