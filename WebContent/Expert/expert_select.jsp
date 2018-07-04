<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style type='text/css'>
       .redStyle{
       font-family: Lucida Grande,Lucida Sans,Arial,sans-serif;
       font-size: 1.1em;
       font-weight: bold;
       color: red;
       text-align: center;
       }
       
        .orangeStyle{
       font-family: Lucida Grande,Lucida Sans,Arial,sans-serif;
       font-size: 1.1em;
       font-weight: bold;
       color: orange;
       text-align: center;
       }
       
       .greenStyle{
       font-family: Lucida Grande,Lucida Sans,Arial,sans-serif;
       font-size: 14px;
       font-weight: bolder;
       color: green;
       text-align: center;
       }
</style>
<div class="con_header">
	<h3><span class="icon icon-web"></span>批次信息</h3>
</div>
<div class="selectbar">
	<table class="layout_table">
		<tr>
			<td>
				<span class="TextFont">选择批次：</span>
				<label for="select"></label> 
				<select name="select" id="selectBatch">
					<option>请选择批次</option> 
				</select>
			</td>
		</tr>
	</table>
</div>	
<div class="con_header">
	<h3><span class="icon icon-web"></span>专家互动</h3>
</div>
<div class="selectbar">
	<table class="layout_table">
		<tr >
			<td>
				<span class="TextFont">姓名：</span> 
					<input id="expertNameQuery" type="text" size="10" name="queryName"/>
					
				<span class="TextFont" style="margin-left:20px">专家编号：</span> 
						<input id="queryExpertNumber" type="text" size="10" name="queryExpertNumber"/> 
						 
				<!-- <span class="TextFont">学校：</span>
		     	<select name="discList" id="discList"> onclick="size=10;">
	   		     	<option value="-1">全部</option>
				</select> -->
		 		<span class="TextFont" style="margin-left:20px">进度状态：</span>
			    <select id="currentStatus" name="mailStatus">
		            <option value="-1">全部</option>
					<option value="0">未发送邮件</option>
					<option value="1">已发送邮件</option>
					<option value="2">已再次发送邮件</option>
					<option value="3">确认参评</option>
					<option value="4">拒绝参评</option>
					<option value="5">正在打分</option>
					<option value="6">完成打分</option>
		        </select>
		       	<!-- <span class="TextFont">打分进度：</span>
				    <select id="ConfirmStatus" name="ConfirmStatus">
                    	<option>全部</option>
						<option>确认参加</option>
						<option>拒绝参加</option>
						<option>未回应</option>
	                </select>	 -->				    	
          		 	<input type="checkbox" name="queryIs985" id="queryIs985"  style="margin-left:20px"/> <span class="TextFont">985高校</span>
		 			<input type="checkbox" name="queryIs211" id="queryIs211"  style="margin-left:20px"/> <span class="TextFont">211高校</span>
		 			<a id="query" class="button" href="#" style="margin-left:20px;">
		 			<span class="icon icon-search ">
		 			</span>查询</a >    
			</td>
		</tr>
	</table>
</div>

<div class="selectbar">
	<table class="layout_table">
		<tbody>
			<tr>
			<td>
				<a id="reSelectExpert" class="button" href="#">
					<span class="icon icon-usermanager"></span>
					补选专家
				</a>
			</td>
			<td>
				<a id="addExpert" class="button" href="#">
					<span class="icon icon-adduser"></span>
					添加专家
				</a>
			</td>
			<td>	
				<a id="deleteExpert" class="button" href="#">
					<span class="icon icon-deleteuser"></span>
					删除专家
				</a>
				<div id="dialog-confirm" title="警告"></div>
			</td>
			<td>
				<a id="replaceExpert" class="button" href="#">
					<span class="icon icon-replacexpert"></span>
					替换专家
				</a>
			</td>
			<td>
				<a id="mailInform" class="button" href="#">
					<span class="icon icon-email"></span>
					群发邮件通知
				</a>
			</td>
			</tr>
		</tbody>
	</table>
</div>
<div class="selectbar layout_holder">
	<table id="selected_expert_list"></table>
	<div id="pager"></div>
</div>

<div class="process_dialog2">
	<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> 正在加载批次信息，请稍候……
</div>

<div class="process_dialog3">
	<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> 后台正在处理，请稍候……
</div>

<script type="text/javascript">
		$(".process_dialog2").hide();
		$(".process_dialog3").hide();
		
		var initLoadRuleList = true;
		$("input[type=submit], a.button , button").button();
		
		var currentBatchId = "";
		function loadBatchNameAndId() {
			$(".process_dialog2").show();
			$('.process_dialog2').dialog({
				position : 'center',
				modal:true,
				autoOpen : true,
			});
			// 在载入页面的时候，加载所有规则的名称和ID
			$.ajax({
				type: "GET",
				// ★★填充下拉框的规则名称和ID
				// 访问后台com.dsep.controller.expert.rule
				// .RuleController的selectEveryRuleId注解方法
				url: '${ContextPath}/expert/selectExpertSelectEveryBatchId/',
				success: function(data) {
					var batchIdAndName = eval(data);
					$("#selectBatch option").remove();
					if (batchIdAndName.length == 1) {
						batchId = batchIdAndName[0].substring(0, 32);
						batchName = batchIdAndName[0].substring(32);
						$("#selectBatch").append("<option value='" +  1  + "' batchId='"+ batchId +"'>"+ batchName +"</option>");
						$(".process_dialog2").dialog("destroy");
						$(".process_dialog2").hide();
						currentBatchId = batchId;
						loadJqGrid();
					} else {
						$("#selectBatch").append("<option value='0'>-点击下拉框选择批次信息-</option>");
						for(var i = 0; i < batchIdAndName.length; i++) {
							// 后台传来的是rule的Id(32位)和Name拼接在一起的
							// 形如 {AF2a..F3k4L规则名称1}
							// 则batchId获得'AF2a..F3k4L'的32位Id
							// batchName获得'规则名称1'
							batchId = batchIdAndName[i].substring(0, 32);
							batchName = batchIdAndName[i].substring(32);
							$("#selectBatch").append("<option value='" + ( i + 1 ) + "' batchId='"+ batchId +"'>"+ batchName +"</option>");
						}
						$(".process_dialog2").dialog("destroy");
						$(".process_dialog2").hide();
					}
				}
			});// 93行 $.ajax()方法结束
		}
		
		// 载入页面后首先会执行这个方法，待到选择了批次之后才显示当前批次下的规则、专家等
		loadBatchNameAndId();
		
		$('#selectBatch').change(function() {
		    var e = document.getElementById("selectBatch");
			var optionValue = e.options[e.selectedIndex].value;
			currentBatchId = e.options[e.selectedIndex].getAttribute("batchId");
			if ('0' == optionValue) {
				//alert_dialog("请先选择批次!");
			} else {
				if (initLoadRuleList) {
					loadJqGrid();
					initLoadRuleList = false;
				} else {
					$('#selected_expert_list').setGridParam({url:'${ContextPath}/expert/selectExpertData/'+currentBatchId}).trigger("reloadGrid");
				}
				
			}
		});
			
		function sendSeparatelyFunc(expertId) {
			//alert(expertId);
			//清空警告框内文本并添加新的警告文本
  		    $( "#dialog-confirm" ).empty().append("<p>即将对选定的专家进行邮件通知，是否继续？</p>");
  		    $( "#dialog-confirm" ).dialog({
      	        height:150,
      	        buttons: {
      	            "确定": function() {
      	            	$( this ).dialog( "close" );
      	            	var addUrl = '${ContextPath}/expert/selectExpertEmailingSeparately/' + expertId;
      	  				$.get(addUrl, function(result){
      	  			    	if ("success" == result) {
      	  			    		alert_dialog("发送成功");
      	  			    	}
      	  			  	});
	      	        },
      	        	"取消": function() {
      	           		 $( this ).dialog( "close" );
      	          		}
      	        	}
      	  });
		}
		var needToLoadAttendUnits = true;
		
		
		$("#mailInform").click(function(){
			if (currentBatchId == "") {
				alert_dialog("请先选择一个批次！");
				return;
			}
			//清空警告框内文本并添加新的警告文本
  		    $( "#dialog-confirm" ).empty().append("<p>即将对数据库中所有未发送过邮件的专家进行邮件通知，是否继续？</p>");
  		    $( "#dialog-confirm" ).dialog({
      	        height:150,
      	        buttons: {
      	            "确定": function() {
      	            	$( this ).dialog( "close" );
      	            	var addUrl = '${ContextPath}/expert/selectExpertMassEmailing/' + currentBatchId;
      	  				$.get(addUrl, function(result){
      	  			    	if ("success" == result) {
      	  			    		alert_dialog("群发成功");
      	  			    	}
      	  			  	});
	      	        },
      	        	"取消": function() {
      	           		 $( this ).dialog( "close" );
      	          		}
      	        	}
      	  });
			/* var emailList = [];
			var selectedId = $("#selected_expert_list").jqGrid("getGridParam", "selarrrow"); 
			if(selectedId.length==0)
				alert_dialog("请选择需要发送邮件的专家");
			for (var i = 0; i < selectedId.length; i++) {
				var email = $("#selected_expert_list").jqGrid("getCell",selectedId[i],"email");
				console.log(email);
				emailList.push(email);
			} */
			
			/* $.ajax({ 
	            type : "POST", 
	            url : addUrl, 
	            dataType : "json",      
	            contentType : "application/json",               
	            //data:JSON.stringify(emailList), 
	            success:alert("发送成功"), 
	         });  */
		});
		
		$('#addExpert').click(function() {
			if (currentBatchId == "") {
				alert_dialog("请先选择一个批次！");
				return;
			}
			addExpertDialog("${ContextPath}/expert/selectExpertAddExpert");
		});
		
		$('#reSelectExpert').click(function() {
			if (currentBatchId == "") {
				alert_dialog("请先选择一个批次！");
				return;
			}
			//清空警告框内文本并添加新的警告文本
  		    $("#dialog-confirm").empty().append("<p>将依据上一次遴选的规则进行补选，是否继续？</p>");
  		    $("#dialog-confirm").dialog({
      	        height:150,
      	        buttons: {
      	            "确定": function() {
      	            	$( this ).dialog( "close" );
      	            	$(".process_dialog3").show();
	      	  			$('.process_dialog3').dialog({
	      	  				position : 'center',
	      	  				modal:true,
	      	  				autoOpen : true,
	      	  			});
						var reSelectUrl = '${ContextPath}/expert/selectExpertReSelectByRule?currentBatchId=' + currentBatchId;					
						$.post(reSelectUrl, function(data) {
							// 返回“专家遴选”的大页面
							var refreshUrl = '${ContextPath}/expert/selectExpert';
							$.post(refreshUrl, function(data) {
							$(".process_dialog3").dialog("destroy");
							$(".process_dialog3").hide();
							// 大页面中的jqgrid重新加载数据
							$("#selected_expert_list")
								.setGridParam({url:'${ContextPath}/expert/selectExpertData/' + currentBatchId})
								.trigger("reloadGrid");
							});
						});
					},
      	        	"取消": function() {
      	           		 $( this ).dialog( "close" );
      	          		}
      	        	}
      	  });
		});
		
		$('#deleteExpert').click(function() {
			if (currentBatchId == "") {
				alert_dialog("请先选择一个批次！");
				return;
			}
			var selectedId = $("#selected_expert_list").jqGrid("getGridParam", "selarrrow"); 
	 		if (selectedId.length == 0) {
				alert_dialog("请选择一名需要删除的专家！");
				return false;
			} else {
			    //清空警告框内文本并添加新的警告文本
	  		    $( "#dialog-confirm" ).empty().append("<p>确定删除专家？</p>");
	  		    $( "#dialog-confirm" ).dialog({
	      	        height:150,
	      	        buttons: {
	      	            "确定": function() {
	      	            	$( this ).dialog( "close" );
							var selectedIds = $("#selected_expert_list").jqGrid("getGridParam", "selarrrow"); 
							var delUrl = '${ContextPath}/expert/selectExpertBatchDelete?selectedIds='+selectedIds;					
							$.post(delUrl, function(data) {
								// 返回“专家遴选”的大页面
								var refreshUrl = '${ContextPath}/expert/selectExpert';
								$.post(refreshUrl, function(data) {
								// 大页面中的jqgrid重新加载数据
								$("#selected_expert_list")
								.setGridParam({url:'${ContextPath}/expert/selectExpertData/' + currentBatchId})
								.trigger("reloadGrid");
								});
							});
						},
	      	        	"取消": function() {
	      	           		 $( this ).dialog( "close" );
	      	          		}
	      	        	}
	      	  });
			}});
		
		function loadJqGrid() {
			$("#selected_expert_list").jqGrid({
				// ★★后台调用显示专家的page方法
				url: '${ContextPath}/expert/selectExpertData/' + currentBatchId,
				datatype : "json",
				mtype: 'GET',
				colNames : ['id', '姓名', '所在学科', '所在单位', 
				            '类别', '通知情况',  '拒绝', 
				            '拒评原因','电子邮箱','改邮箱', '发邮件',
				            '指标', '权重',  '成果',  '声誉'
				            , '电话'],
				colModel : [ 
					{name : 'id',      		  index : 'id',	  		  width : 200, align : "center", hidden : true}, 
				    {name : 'expertName',     index : 'name',  	 	  width : 80, align : "center"},
				    {name : 'disciplineName', index : 'real_disc_Id,unit_id', width : 100, align : "center"}, 
				    {name : 'collegeName',    index : 'unit_id,real_disc_id',	  width : 100, align : "center"}, 
				    {name : 'expertTypeName', index : 'expertType',	  width : 100, align : "center"}, 
				    /* {name : 'mailedName',     index : 'isMailed',	  width : 100, align : "center"}, 
				    {name : 'confirmName',    index : 'confirm',	  width : 100, align : "center"},  */
				    {name : 'currentStatus',  index : 'currentStatus',width : 120, align : "center"},
				    {name : 'isRefused',      index : 'isRefused',	  width : 200, align : "center", hidden : true}, 
				    {name : 'remark',         index : 'remark',		  width : 100, align : "center"}, 
				    {name : 'email',		  index : 'email',		  width : 180, align : "center", editable:true,
				    	editrules: {
	   		         		custom: true,
	   		        		custom_func: function (value, colName) {
	   		            		return validEmail(value);
	   		        	}}},
				    {name : 'oper',			  index:'oper',			  width:70,   align : "center"},
				    {name : 'send',			  index:'send',			  width:80,   align : "center"},
				    {name : 'indicatorName',  index : 'indicatorName',  width : 80,  align : "center"},
				    {name : 'indicatorWeightName',  index : 'indicatorWeightName',  width : 80,  align : "center"},
				    {name : 'achievementName',index : 'achievementName',width : 80,  align : "center"}, 
				    {name : 'reputationName', index : 'reputationName', width : 80,  align : "center"},
				    {name : 'phone',		  index : 'officePhone',  width : 90, align : "center", hidden : true}],
				height : '100%',
				autowidth : true,
				rowNum : 20,
				rowList : [ 20, 30, 40 ],
				viewrecords : true,
				sortorder : "asc",
				sortname : "id",
				pager : '#pager',
				multiselect : true,
				multibodyonly : true,
				caption : "已选专家",
				rownumbers: true,
				jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
			        root: "rows",  //包含实际数据的数组  
			        page: "pageIndex",  //当前页  
			        total: "totalPage",//总页数  
			        records:"totalCount", //查询出的记录数  
			        repeatitems : false,
			    },
				gridComplete: function() {
					var grid = $("#selected_expert_list");
					var ids = grid.jqGrid('getDataIDs');
					var remarkCheck;
					for(var i = 0; i < ids.length; i++) {
						var rowId = ids[i];
						var jqGridData = grid.jqGrid('getRowData',rowId);
						var isRefused = jqGridData.isRefused;
						if (isRefused == '1') {
							remarkCheck = "<a href='#' onclick='openRemarkDialog()'>[查看]</a>"; 
							grid.jqGrid('setRowData',ids[i],{remark:remarkCheck});
						}
						var currentStatus = jqGridData.currentStatus;
						if (currentStatus == "未发送邮件") {
							sendSeparately = "<a href='#' onclick='sendSeparatelyFunc(\""+ rowId +"\")'>单独发送</a>"; 
							grid.jqGrid('setRowData',ids[i],{send:sendSeparately});
						}
						
						var edit_link="<a class='' href='#' onclick='editEmail(\""+ rowId +"\")'>编辑</a>";
					   	grid.jqGrid('setRowData',ids[i],{oper : edit_link});
						
						var indicatorName = jqGridData.indicatorName;
						var indicatorWeightName = jqGridData.indicatorWeightName;
						var achievementName = jqGridData.achievementName;
						var reputationName = jqGridData.reputationName;
						if (indicatorName == "已提交") {
							grid.jqGrid('setCell',rowId, 'indicatorName', '', 'greenStyle');
					    } else if (indicatorName == "无任务") {
					    	// do nothing
					    } else {
					    	//grid.jqGrid('setCell',rowId, 'indicatorName', '', 'redStyle');
					    } 
						
						if (indicatorWeightName == "已提交") {
							grid.jqGrid('setCell',rowId, 'indicatorWeightName', '', 'greenStyle');
					    } else if (indicatorWeightName == "无任务") {
					    	// do nothing
					    } else {
					    	//grid.jqGrid('setCell',rowId, 'indicatorWeightName', '', 'redStyle');
					    }
						
						if (achievementName == "已提交") {
							grid.jqGrid('setCell',rowId, 'achievementName', '', 'greenStyle');
					    } else if (achievementName == "无任务") {
					    	// do nothing
					    } else {
					    	//grid.jqGrid('setCell',rowId, 'achievementName', '', 'redStyle');
					    }
						
						if (reputationName == "已提交") {
							grid.jqGrid('setCell',rowId, 'reputationName', '', 'greenStyle');
					    } else if (reputationName == "无任务") {
					    	// do nothing
					    } else {
					    	//grid.jqGrid('setCell',rowId, 'reputationName', '', 'redStyle');
					    }
					}	
				},
				loadComplete: function() {
					/* if (needToLoadAttendUnits) {
						$.ajax({ 
				            type : "POST", 
				            url : '${ContextPath}/expert/selectExpertGetQueryUnitsAndDiscs', 
				            dataType : "json",      
				            contentType : "application/json",               
				            success:function(selectValues){ 
				            	$.each(selectValues, function(key, value) {   
								     $('#discList')
								         .append($("<option></option>")
								         .attr("value",key)
								         .text(value)); 
								});            
				            	needToLoadAttendUnits = false;
				            } 
				         }); 
					} */
					
					
					
				}
			});// 128行jqGrid()方法结束
		}
		
		
		/**
   		 * 编辑
   		 */
   		 function editEmail(id)
   		 {
   				$("#selected_expert_list").jqGrid('editRow',id,{  
   		            keys : true,        //这里按[enter]保存  
   		            url: "${ContextPath}/expert/selectExpertAlertExpertEmail/" + id,  
   		            mtype : "POST",  
   		            restoreAfterError: true,  
   		            extraparam: {  
   		            },  
   		            oneditfunc: function(id){  
   		                //alert("${ContextPath}/expert/selectExpertModifyExpertEmail/" + id);  
   		            },  
   		            succesfunc: function(response){  
   		                //alert("save success");  
   		                return true;  
   		            },  
   		            errorfunc: function(rowid, res){  
   		                console.log(rowid);  
   		                console.log(res);  
   		            }  
   		        });
   				//var resure_link="<a class='' href='#' onclick='resureBatchItem("+id+")'>确认</a>";
   				var resure_link = "<a class='' href='#' onclick='resureEmail(\""+id+"\")'>保存</a>"; 
   				jQuery("#selected_expert_list").jqGrid('setRowData',id,{oper : resure_link});
   		 }
		
   		/**
    	 * 确认
    	*/
   		function resureEmail(id)
   		{
   			$(".process_dialog3").show();
			$('.process_dialog3').dialog({
				position : 'center',
				modal:true,
				autoOpen : true,
			});
			var newEmail = $("#" + id + "_email").val();
			// 把"."替换为一个少用的字符串，后台相应的做替换
			newEmail = newEmail.replace(/\./g, "S___d_B__l_");
			
			//alert(newEmail);
   			var result = $("#selected_expert_list").jqGrid('saveRow',id, {
   				url: "${ContextPath}/expert/selectExpertModifyExpertEmail/" + id + "/" + newEmail,  
		        mtype : "POST",  
		        aftersavefunc: function(response){  
		        	//alert(response);
		        	$(".process_dialog3").dialog("destroy");
					$(".process_dialog3").hide();
		        	if (response) {
		        		alert_dialog("修改成功！")
		        	} else {
		        		alert_dialog("系统错误，请重试！");
		        	}
		            return true;  
		        }
		        
   			});
   			if (result) {
   				var resure_link="<a class='' href='#' onclick='editEmail(\""+id+"\")'>编辑</a>";
   	   			$("#selected_expert_list").jqGrid('setRowData',id,{oper :resure_link});
   			}
   			
   		}
   		
   		function validEmail(email) {
   			var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
   		 	if (email.trim() == "") {
   		 		//alert_dialog("请输入合法的邮箱，系统会在学校检查时向负责人发送提醒邮件！");
   				return [false, "请输入合法的邮箱"];
   		 	} else if (!emailReg.test(email)) {
   		 		//alert_dialog("请输入合法的邮箱，系统会在学校检查时向负责人发送提醒邮件！");
   		 		return [false, "请输入合法的邮箱"];
   		 	}
   			return [true, ""];
   		}

		// 合并表头
// 		$("#selected_expert_list").jqGrid('setGroupHeaders', {
// 			useColSpanStyle : true, // 没有表头的列是否与表头列位置的空单元格合并
// 			groupHeaders : [ {
// 				startColumnName : 'indicatorName', // 对应colModel中的name
// 				numberOfColumns : 3, // 跨越的列数
// 				titleText : '评价完成状况'
// 			} ]
// 		});
	//});// 91行ready方法结束
	
	function openRemarkDialog(){
		url = "${ContextPath}/expert/selectExpertRemark";
		$.post(url, function(data) {
			$('#dialog').empty();
			$("#dialog").append(data);
			$('#dialog').dialog({
				title : "备注",
				autoheight: true,
				width: '700',
				position : 'center',
				modal : true,
				draggable : true,
				hide : 'fade',
				show : 'fade',
				autoOpen : true,
				buttons : {
					"确定" : function() {
						$("#dialog").dialog("close");
						$('#dialog').empty();
					},
					"取消" : function() {
						$("#dialog").dialog("close");
						$('#dialog').empty();
					}
				}
			});
		}, 'html');
	};
	
	
	
	
		/* else{}
		$("#dialog2").dialog({
			title : "是否删除所选专家？",
			resizable : false,
			height : 140,
			modal : true,
			buttons : {
				"确定" : function() {
					// 获取所有选中的教师对象的Ids  
					var selectedIds = $("#selected_expert_list").jqGrid("getGridParam", "selarrrow"); 
					
					var delUrl = '${ContextPath}/expert/delExpert?selectedIds='+selectedIds;					
					$.post(delUrl, function(data) {
						// 返回“专家遴选”的大页面
						var refreshUrl = '${ContextPath}/expert/selectExpert';
						$.post(refreshUrl, function(data) {
							// 大页面中的jqgrid重新加载数据，把利用教师选择出专家展示在页面上
							$("#selected_expert_list").setGridParam({url:'${ContextPath}/expert/expertSelectedData/'}).trigger("reloadGrid");
						});
					});
					$(this).dialog("close");
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			}
		}); */
	
	// 该URL是：${ContextPath}/expert/addExpert
	// 它会弹出Expert下的dialog_addExpert弹出窗口
	function addExpertDialog(url) {
		$.post(url, function(data) {
			$('#dialog').empty();
			$("#dialog").append(data);
			$('#dialog').dialog({
				title : "添加未被选择的专家",
				height : '550',
				width : '900',
				position : 'center',
				modal : true,
				draggable : true,
				hide : 'fade',
				show : 'fade',
				autoOpen : true,
				buttons : {
					// 弹出窗口下方的按钮
					"添加选中专家" : function() {
						// 获取所有选中的教师对象的Ids  
						var experts = [];
						
						var selectedIds = $("#expert_list2").jqGrid("getGridParam", "selarrrow"); 
						for (var i = 0; i < selectedIds.length; i++) {
							selectedId = selectedIds[i];
							//alert(selectedId);
		    				var rowdata = $("#expert_list2").jqGrid('getRowData',selectedId);
		    				//alert(rowdata);
	       					//var expertName = $("#expert_list2").jqGrid('getCell',i,'expertName');
	       					var expertName = rowdata.expertName;
	       					//var expertNumber = $("#expert_list2").jqGrid('getCell',i,'expertNumber');
	       					//var expertType = $("#expert_list2").jqGrid('getCell',i,'expertTypeName');
	       					//var discId = $("#expert_list2").jqGrid('getCell',i,'disciplineCode');
	       					var expertNumber = rowdata.expertNumber;
	       					var expertType = rowdata.expertTypeName;
	       					var discId = rowdata.discId;
	       					var discId2 = rowdata.discId2;
	       					var collegeName = rowdata.collegeName;
	       					//alert(expertName + " " + expertNumber + " " + expertType + " " + discId);
	       					var expert = {};
	       					// 选的是第二学科
	       					if ("false" == $("#expert_list2").jqGrid('getCell',selectedId,'chooseDiscipline')) {
	       						// 传一级学科码2
	       						expert = {
      								"expertName"  : expertName, 
      								"expertNumber": expertNumber, 
      								"expertType"  : expertType, 
      								"discId2"     : discId2, 
      								"unitId"      : collegeName
      							};
	       					} else {
	       						expert = {
	       							"expertName"  : expertName, 
	       							"expertNumber": expertNumber, 
	       							"expertType"  : expertType, 
	       							"discId"      : discId, 
	       							"unitId"      : collegeName
	       						};
	       					}
	       					
	       					experts.push(expert);
						}
						// 访问BatchAddExpertController，利用选择到的教师对象Ids，往已选专家表插入相关的数据
						var addUrl = '${ContextPath}/expert/selectExpertBatchAdd/' + currentBatchId;
						$.ajax({ 
				            type : "POST", 
				            url : addUrl, 
				            dataType : "json",      
				            contentType : "application/json",               
				            data:JSON.stringify(experts), 
				            success:function(data){ 
				            	// 返回“专家遴选”的大页面
				            	// 大页面中的jqgrid重新加载数据，把利用教师选择出专家展示在页面上
								$("#selected_expert_list")
				            		.setGridParam({url:'${ContextPath}/expert/selectExpertData/' + currentBatchId})
									.trigger("reloadGrid");
// 								var refreshUrl = '${ContextPath}/expert/selectExpertData';
// 								$.post(refreshUrl, function(data) {
									
// 								});                       
				            } 
				         }); 
										
						$("#dialog").dialog("close");
						$('#dialog').empty();
					},
					"取消" : function() {
						$("#dialog").dialog("close");
						$('#dialog').empty();
					}
				}
			});
		}, 'html');
	}
	
	$("#query").click(function() {
		if (currentBatchId == "") {
			alert_dialog("请先选择一个批次！");
			return;
		}
		var name = $('#expertNameQuery').val();
		var expertNumber = $("#queryExpertNumber").val();
		var disc = $("#discList option:selected").val();
		var currentStatus = $("#currentStatus option:selected").val();
		//if($("#isAgeSelected").is(':checked'))
		//alert(disc);
		//alert(currentStatus);
		//alert(name == null);
		
		var is985Checked = $('#queryIs985').attr('checked');
		var is211Checked = $('#queryIs211').attr('checked');
    	if((name == "") && (expertNumber == "") && (disc == -1) 
    			&& (currentStatus == -1) 
    			&& (!is985Checked) && (!is211Checked)) {
    		$("#selected_expert_list").setGridParam({url:'${ContextPath}/expert/selectExpertData/' + currentBatchId}).trigger("reloadGrid");
		} else {
			if (name != "") {
				name = encodeURI(encodeURI(name));
			}
			
			$("#selected_expert_list")
				.setGridParam({url:'${ContextPath}/expert/selectExpertQueryExperts?name='+name
						+"&queryExpertNumber="+expertNumber
						+"&queryIs985="+is985Checked
						+"&queryIs211="+is211Checked
						+"&currentStatus="+currentStatus+"&currentBatchId="+currentBatchId})
				.trigger("reloadGrid");
		}
	});
	
 	$("#replaceExpert").click(function() {
 		if (currentBatchId == "") {
			alert_dialog("请先选择一个批次！");
			return;
		}
 		var selectedId = $("#selected_expert_list").jqGrid("getGridParam", "selarrrow"); 
 		if (selectedId.length == 0) {
			alert_dialog("请选择一名需要替换的专家！");
			return false;
		}
		if (selectedId.length > 1) {
			alert_dialog("一次只能替换一名专家！");
			return false;
		}
		// 获取到需要替换的专家Id
		var oldExpertId = selectedId[0];
 		replaceExpertDialog("${ContextPath}/expert/selectExpertReplaceExpert?needReplaceExpertId="+ oldExpertId, oldExpertId);
	});
 	
 	// 该URL是：${ContextPath}/expert/replaceExpert
	// 它会弹出Expert下的dialog_addExpert弹出窗口
	function replaceExpertDialog(url, oldExpertId) {
		$.post(url, function(data) {
			$('#dialog').empty();
			$("#dialog").append(data);
			$('#dialog').dialog({
				title : "替换专家",
				height : '550',
				width : '900',
				position : 'center',
				modal : true,
				draggable : true,
				hide : 'fade',
				show : 'fade',
				autoOpen : true,
				buttons : {
					// 弹出窗口下方的按钮
					"替换专家" : function() {
						// 获取所有选中的教师对象的Id  
						var selectedId = $("#expert_list2").jqGrid("getGridParam", "selarrrow"); 
						console.log(selectedId[0]);
						console.log(selectedId[1]);
						// 访问BatchAddExpertController，利用选择到的教师对象Ids，往已选专家表插入相关的数据
						if (selectedId.length == 0) {
							alert_dialog("请选择一名专家！");
							return false;
						}
						if (selectedId.length > 1) {
							alert_dialog("只能替换一名专家！");
							return false;
						} 
						
						
						var newExpertNumber = $("#expert_list2").jqGrid("getCell", selectedId[0],'expertNumber');
						console.log(newExpertNumber);
						// 该路由对应BatchAddExpertController
						var  isSecond = "false";
						//alert("false" == $("#expert_list2").jqGrid('getCell',selectedId[0],'chooseDiscipline'));
						if ("false" == $("#expert_list2").jqGrid('getCell',selectedId[0],'chooseDiscipline')) {
							//alert("come in");
							isSecond = "true";
						}
						var addUrl = '${ContextPath}/expert/selectExpertBatchReplace?oldExpertId='
									+ oldExpertId + "&newExpertNumber=" + newExpertNumber + "&isSecond=" + isSecond;
						//var addUrl = '${ContextPath}/batchAddExpert/batchAdd?selectedIds='+selectedIds;					
						$.post(addUrl, function(data) {
							// 返回“专家遴选”的大页面
							var refreshUrl = '${ContextPath}/expert/selectExpert';
							$.post(refreshUrl, function(data) {
								//alert(1);
								// 大页面中的jqgrid重新加载数据，把利用教师选择出专家展示在页面上
								$("#selected_expert_list").setGridParam({url:'${ContextPath}/expert/selectExpertData/' + currentBatchId}).trigger("reloadGrid");
							});
						});
						$("#dialog").dialog("close");
						$('#dialog').empty();
					},
					"取消" : function() {
						$("#dialog").dialog("close");
						$('#dialog').empty();
					}
				}
			});
		}, 'html');
	}
</script>