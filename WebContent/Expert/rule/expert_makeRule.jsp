<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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
				<!-- <a id="addBatch" class="button" href="#">
					<span class="icon icon-add"></span>添加批次
				</a> -->
				
			</td>
		</tr>
	</table>
</div>	

<div class="con_header">
	<h3><span class="icon icon-web"></span>专家遴选规则设定</h3>
</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tbody>
			<tr>
				<td>
					<a id="addRule" class="button" href="#"> 
						<span class="icon icon-add"></span>
						新建规则
					</a>
				</td>
				<td>
					<a id="modifyRule" class="button" href="#"> 
						<span class="icon icon-edit"></span>
						编辑规则
					</a>
				</td>
				<td>
					<a id="viewRule" class="button" href="#"> 
						<span class="icon icon-report"></span>
						查看规则
					</a>
				</td>
				<td>
					<a id="delRule" class="button" href="#"> 
						<span class="icon icon-del"></span>
						删除规则
					</a>
					<div id="dialog-confirm" title="警告"></div>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<div class="cur">
	<table id="rule_list"></table>
	<div id="pager"></div>
</div>

<div class="con_header">
	<h3><span class="icon icon-web"></span>通过规则遴选专家</h3>
</div>
<div class="selectbar">
	<table class="layout_table">
		<tr>
			<td>
				
				<span class="TextFont">选择规则：</span>
				<label for="select"></label> 
				<select name="select" id="select">
					<option>请选择遴选规则</option> 
				</select>
				<a id="search" class="button" href="#">
					<span class="icon icon-filter"></span>开始遴选
				</a>
				<a id="review" class="button" href="#">
					<span class="icon icon-filter"></span>查看结果
				</a>
			</td>
		</tr>
	</table>
</div>
<div class="selectbar layout_holder">
	<table id="expert_list"></table>
	<div id="expert_list_pager"></div>
</div>
<div class="process_dialog">
	<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> 正在遴选，请稍候……
</div>

<div class="process_dialog2">
	<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> 正在加载批次信息，请稍候……
</div>

<script type="text/javascript">
	var batchId = "";
	var batchName = "";
	var currentBatchId = "";
	$("input[type=submit], a.button , button").button();
	// 先调用loadRuleList加载规则列表，完成时调用loadExpertList加载专家列表
	// 专家列表完成时调用loadRuleNameAndId加载规则下拉框
	$(document).ready(function() {
		var initLoadRuleList = true;
		var initLoadExpertList = true;
		
		$(".process_dialog").hide();
		$(".process_dialog2").hide();
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
						loadRuleList();
						initLoadRuleList = false;
					} else {
						$("#selectBatch").append("<option value='0'>-点击下拉框选择批次信息-</option>");
						for(var i = 0; i < batchIdAndName.length; i++) {
							// 后台传来的是rule的Id(32位)和Name拼接在一起的
							// 形如 {AF2a..F3k4L规则名称1}
							// 则batchId获得'AF2a..F3k4L'的32位Id
							// batchName获得'规则名称1'
							batchId = batchIdAndName[i].substring(0, 32);
							batchName = batchIdAndName[i].substring(32);
							$("#selectBatch").append("<option value='" + ( i + 1 ) + "' batchId='"+ batchId +"' name='"+batchName+"'>"+ batchName +"</option>");
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
			currentBatchName = e.options[e.selectedIndex].getAttribute("name");
			console.log(currentBatchName);
			if ('0' == optionValue) {
				//alert_dialog("请先选择批次!");
			} else {
				if (initLoadRuleList) {
					loadRuleList();
					initLoadRuleList = false;
				} else {
					$('#rule_list').setGridParam({url:'${ContextPath}/rule/makeRuleGetRuleList/'+currentBatchId}).trigger("reloadGrid");
				}
				
			}
		});
		
		$("#select").click(function() {
			var e = document.getElementById("selectBatch");
			var optionValue = e.options[e.selectedIndex].value;
			if ('0' == optionValue) {
				alert_dialog("请先选择批次!");
			}
		});
		
		function loadRuleNameAndId() {
			// 在载入页面的时候，加载所有规则的名称和ID
			$.ajax({
				type: "GET",
				// ★★填充下拉框的规则名称和ID
				// 访问后台com.dsep.controller.expert.rule
				// .RuleController的selectEveryRuleId注解方法
				url: '${ContextPath}/expert/selectExpertSelectEveryRuleId/'+currentBatchId,
				success: function(data) {
					var ruleIdAndName = eval(data);
					if (null == ruleIdAndName) {
						ruleIdAndName = [];
					}
					//alert(ruleIdAndName.length + "||" + currentBatchId);
					//$("#select option").remove();
					$('#select option[value!="0"]').remove();
					$("#select").append("<option name='0'>-请点击下拉框选择-</option>");
					for(var i = 0; i < ruleIdAndName.length; i++) {
						// 后台传来的是rule的Id(32位)和Name拼接在一起的
						// 形如 {AF2a..F3k4L规则名称1}
						// 则ruleId获得'AF2a..F3k4L'的32位Id
						// ruleName获得'规则名称1'
						var ruleId = ruleIdAndName[i].substring(0, 32);
						var ruleName = ruleIdAndName[i].substring(32);
						$("#select").append("<option name='"+ ruleId +"'>"+ ruleName +"</option>");
						
						/* $(".process_dialog2").dialog("destroy");
						$(".process_dialog2").hide(); */
					}
				}
			});// 115行 $.ajax()方法结束
		};
		
		// 遴选规则的查询
		/********************  jqGrid ***********************/
		function loadRuleList() {
			$("#rule_list").jqGrid({
				// ★★对应com.dsep.controller.expert_rule.RuleController里的路由
				url: '${ContextPath}/rule/makeRuleGetRuleList/' + currentBatchId,
				datatype : "json",
				mtype: 'GET',
				colNames : ['规则id(隐藏列)','规则名称','创建时间', '最后修改时间'],
				colModel : [ 
					{name : 'expertSelectionRule.id',		index : 'expertSelectionRule.id',		hidden:true}, 
					{name : 'expertSelectionRule.ruleName', index : 'expertSelectionRule.ruleName', width : 100, align : "center"},
					{name : 'createTime',					index : 'createTime',					width : 100, align : "center"},
					{name : 'modifyTime',					index : 'modifyTime',					width : 100, align : "center"}  
					],
				height : '100%',
				autowidth : true,
				rowNum : 5,
				rowList : [ 5, 10, 15 ],
				viewrecords : true,
				sortorder : "desc",
				pager: '#pager',
						
				caption : "专家遴选规则",
				jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
	                   root: "rows",  //包含实际数据的数组  
	                   page: "pageIndex",  //当前页  
	                   total: "totalPage",//总页数  
	                   records:"totalCount", //查询出的记录数  
	                   repeatitems : false,
	               },
	            gridComplete : function() {
	            	//
	            	if (initLoadExpertList) {
	            		loadExpertList();
	            		initLoadExpertList = false;
	            	} else {
	            		$('#expert_list').setGridParam({url:'${ContextPath}/expert/selectExpertData/' + currentBatchId}).trigger("reloadGrid");
	            	} 
	            }
		    }).navGrid('#pager',{edit:false,add:false,del:false});
			//$('#rule_list').setGridParam({url:'${ContextPath}/rule/makeRuleGetRuleList/'+currentBatchId}).trigger("reloadGrid");
			//loadRuleNameAndId(true);
		}// function loadGqrid()结束
		function openWaitingPanel() {
			
		}	
		function closeWaitingPanel() {
			
		}
		
		function loadExpertList() {
			$("#expert_list").jqGrid({
				// ★★后台调用显示专家的page方法
				url: '${ContextPath}/expert/selectExpertData/' + currentBatchId,
				datatype : "json",
				mtype: 'GET',
				colNames : ['id', '姓名', '评价学科','所在单位','电话', '电子邮箱', '专家类别' ],
				colModel : [ 
					{name : 'id',      		  index : 'id',	  		  width : 200, align : "center", hidden : true}, 
				    {name : 'expertName',     index : 'name',  	 	  width : 80,  align : "center"}, 
				    {name : 'disciplineName', index : 'real_disc_id,unit_id', width : 150, align : "center"}, 
				    {name : 'collegeName',    index : 'unit_id,real_disc_id', width : 150, align : "center"}, 
				    {name : 'phone',		  index : 'officePhone',  width : 150, align : "center"}, 
				    {name : 'email',		  index : 'email',		  width : 200, align : "center"}, 
				    {name : 'expertTypeName', index : 'expertType',	  width : 100, align : "center"}], 
				height : '100%',
				autowidth : true,
				rowNum : 20,
				rowList : [ 20, 30, 40 ],
				viewrecords : true,
				sortorder : "asc",
				sortname : "id",
				pager : '#expert_list_pager',
				//multiselect : true,
				multibodyonly : true,
				caption : "已选专家预览      (专家的详细工作进度和信息请跳转至“专家互动”页面)",
				rownumbers: true,
				jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
			        root: "rows",  //包含实际数据的数组  
			        page: "pageIndex",  //当前页  
			        total: "totalPage",//总页数  
			        records:"totalCount", //查询出的记录数  
			        repeatitems : false,
			    },
			    gridComplete : function () {
			    	loadRuleNameAndId();
			    }
			});// 128行jqGrid()方法结束
		}
		
		$("#review").click(function() {
			//url = '${ContextPath}/expert/selectExpertReview/'+currentBatchId;
			url = '${ContextPath}/expert/selectExpertGetReviewPage/';
			$.post(url, function(data) {
					$('#dialog').empty();
					$("#dialog").append(data);
					$('#dialog').dialog({
						title : "统计信息",
						autoheight: true,
						width: window.screen.availWidth*0.92,			
						position : 'top',
						modal : true,			
						draggable : true,
						hide : 'fade',			
						show : 'fade',
						autoOpen : true,
						buttons : {
							"关闭" : function() {
								$("#dialog").dialog("close");
								$('#dialog').empty();
							}
						}
					});
				}, 'html');
				/* obj = eval(data);
				alert(obj[0]);
				alert(obj[1]);
				alert(obj[2]);
				alert(obj[3]); */
		});		
		
		// 点击遴选时，执行选择专家的后台操作
		$("#search").click(function() {
			var optionValue = $("#select").find("option:selected").attr("name");
			if (null == optionValue || 0 == optionValue) {
				alert_dialog("请先选择一个规则!");
				
			} else {
			//清空警告框内文本并添加新的警告文本
  		    $( "#dialog-confirm" ).empty().append("<p>遴选过程将会清空原数据，是否继续？</p>");
  		    $( "#dialog-confirm" ).dialog({
      	        height:150,
      	        buttons: {
      	            "确定": function() {
      	            	$(this).dialog("close");
      	           		// 获取下拉框的规则Id
      	  				var ruleId = $("#select").find("option:selected").attr("name");
      	  				// ★★通过规则Id号选择专家
      	  				// 访问后台com.dsep.controller.expert.
      	  				// ExpertSelectionController的selectByRule注解方法
      	  				url = '${ContextPath}/expert/selectExpertSelectByRule?ruleId='+ruleId;
	      	  			$(".process_dialog").show();
	    				$('.process_dialog').dialog({
	    					position : 'center',
	    					modal:true,
	    					autoOpen : true,
	    				});
      	  				$.post(url, function(data) {
      	  					
      	  					// 需要重新加载参评学校查询条件
      	  					//needToLoadAttendUnits = true;
      	  					// 选择完毕之后，重新加载jqgrid
      	  					if (data == "success") {
	      	  					$("#expert_list").setGridParam({url:'${ContextPath}/expert/selectExpertData/' + currentBatchId}).trigger("reloadGrid");
	  	  						$(".process_dialog").dialog("destroy");
								$(".process_dialog").hide();
								alert_dialog("遴选完成！");
      	  					} else {
	      	  					$("#expert_list").setGridParam({url:'${ContextPath}/expert/selectExpertData/' + currentBatchId}).trigger("reloadGrid");
	  	  						$(".process_dialog").dialog("destroy");
								$(".process_dialog").hide();
								alert_dialog("遴选失败！");
      	  					}
      	  					
      	  				});
	      	        },
      	        	"取消": function() {
      	           		 $( this ).dialog( "close" );
      	          		}
      	        	}
      	  });
			}
		});	
		
		// 获得增加遴选规则的对话框
		$('#addRule').click(function() {
			var e = document.getElementById("selectBatch");
			var optionValue = e.options[e.selectedIndex].value;
			//currentBatchId = e.options[e.selectedIndex].getAttribute("batchId");
			if ('0' == optionValue) {
				alert_dialog("请先选择批次!");
			} else {
				//alert(currentBatchId);
				// ★★对应RuleController里的路由
				addRuleDialog("${ContextPath}/rule/makeRuleGetAddRuleDialog");
			}
		});
		// 增加遴选规则对应的对话框
		function addRuleDialog(url) {
			$.post(url, function(data) {
				$('#dialog2').empty();
				$("#dialog2").append(data);
				$('#dialog2').dialog({
					title : "新建规则",
					autoheight : true,
					width : '800',
					position : 'center',
					modal : true,
					draggable : true,
					hide : 'fade',
					show : 'fade',
					autoOpen : true,
					buttons : {
						/* "从别的批次导入":function(){
							var url = "${ContextPath}/rule/makeRuleGetImportRuleDialog";
							$.post(url,function(data){
								$("#dialog2").empty();
								$("#dialog2").append(data);
								$("#dialog2").dialog({
									title : "导入规则",
									autoheight : true,
									width : '800',
									position : 'top',
									modal : true,
									draggable : true,
									hide : 'fade',
									show : 'fade',
									autoOpen : true,
									buttons:{
										
									},
								});
							});
						}, */
						"保存" : function() {
							if (($("#checkSameDisciplineSumLimitHint").length 
							+ $("#checkSameUnitSumLimitHint").length
							+ $("#checkAgeLimitHint").length
							+ $("#checkDisciplineNumberHint").length
							+ $("#checkManageExpertPCTHint").length) > 0) {
								alert_dialog("参数有错误，请重新检查");
								return;
							}  
							var rule = $("#rule").serialize();
							var ruleName = $("input[name='rule.ruleName']").val();
							//ruleName = encodeURI(encodeURI(ruleName));
							var ids = $("#rule_list").jqGrid('getDataIDs');
							var isSameName = true;
							for(var i=0;i<ids.length;i++){
								var rowData = $("#rule_list").jqGrid('getRowData',ids[i]);
								var ruleNameCheck = rowData["expertSelectionRule.ruleName"];
								if(ruleName==ruleNameCheck)
									isSameName =false;
							}
							var ruleDetails = $("#details").serialize();

							var ruleComment = $("#comment textarea").val();
							//ruleComment = encodeURI(encodeURI(ruleComment));
							// 该detailCounter指的是上面一个有多少个details,freemarker生成页面时会根据数据库自动填写
							var	detailCounter = $("#detailSize").val();					
							var postData = {"ruleName" : ruleName, "ruleComment" : ruleComment};
							//alert(detailCounter);
							// urlDataPartition指的是向后退传的数据，因为这里要传递一个rule数据
							// 和n个ruleDetail数据，不能以list的形式传递，所以只能用原始办法传递字符串
							// 第一个参数是detail的个数
							// 第二个参数是rule的数据
							// 后面的参数是个details
							// 注意！增加一个查询条件，detailCounter就必须加1
							// 注意！增加一个查询条件，urlDataPartition后面就必须加新的detail
							if(ruleName!=""&&ruleName!=null&&isSameName){
								var urlDataPartition = '?detailCounter=' + detailCounter + "&currentBatchId=" + currentBatchId + "&" + rule + "&" + ruleDetails;
								$.ajax({
									headers: { 
								        'Accept': 'application/json',
								        'Content-Type': 'application/json' 
								    },
									type: "POST",
									// ★★对应RuleController里的路由
									url: '${ContextPath}/rule/makeRuleAddRule' + urlDataPartition,
									data : JSON.stringify(postData), 
									dataType : 'json',
									success: function(data){
										if (initLoadRuleList) {
											loadRuleList();
											initLoadRuleList = false;
										} else {
											$('#rule_list').setGridParam({url:'${ContextPath}/rule/makeRuleGetRuleList/'+currentBatchId}).trigger("reloadGrid");
										}
										dialog("新建成功!");
									}
								});
							}else{
								alert_dialog("新建名称不能重复或者为空");
								return;
							}
							
							$("#dialog2").dialog("close");
							$('#dialog2').empty();
						},
						"取消" : function() {
							$("#dialog2").dialog("close");
							$('#dialog2').empty();
						},
					},
				});
			}, 'html');
		};
		
		// 遴选规则的修改
		$('#modifyRule').click(function() {
			// 选择jqgrid一行数据
			var gr = $('#rule_list').jqGrid('getGridParam','selrow');
			if(gr != null) {
			    var rowData = $('#rule_list').jqGrid("getRowData", gr);
			    var id=rowData["expertSelectionRule.id"];
			    var e = document.getElementById("selectBatch");
				var optionValue = e.options[e.selectedIndex].value;
				if('0'!=optionValue){
				    modifyRuleDialog("${ContextPath}/rule/makeRuleGetModifyRuleDialog?id="+id);
				}else{
					alert_dialog("请选择批次！");
				}
			}
			else {
				alert_dialog("请先选择一行进行编辑");
			}
		});
		
		// 修改遴选规则对应的对话框
		function modifyRuleDialog(url) {
			$.post(url, function(data) {
				$('#dialog2').empty();
				$("#dialog2").append(data);
				$('#dialog2').dialog({
					title : "编辑规则",
					autoheight : true,
					width : '800',
					position : 'center',
					modal : true,
					draggable : true,
					hide : 'fade',
					show : 'fade',
					autoOpen : true,
					buttons : {
						"保存" : function() {
							if (($("#checkSameDisciplineSumLimitHint").length 
									+ $("#checkSameUnitSumLimitHint").length
									+ $("#checkAgeLimitHint").length
									+ $("#checkDisciplineNumberHint").length
									+ $("#checkManageExpertPCTHint").length) > 0) {
									alert_dialog("参数有错误，请重新检查");
									return;
							}
							var rule = $("#rule").serialize();
							var id = $("input[name='rule.id']").val();
							var ruleName = $("input[name='rule.ruleName']").val();
							var ids = $("#rule_list").jqGrid('getDataIDs');
							var isSameName = true;
							for(var i=0;i<ids.length;i++){
								var rowData = $("#rule_list").jqGrid('getRowData',ids[i]);
								var idCheck = rowData["expertSelectionRule.id"];
								var ruleNameCheck = rowData["expertSelectionRule.ruleName"];
								if(ruleName==ruleNameCheck&&id!=idCheck)
									isSameName =false;
							}
							var ruleDetails = $("#details").serialize();

							var ruleComment = $("#comment textarea").val();
							// 该detailCounter指的是上面一个有多少个details,freemarker生成页面时会根据数据库自动填写
							var	detailCounter = $("#detailSize").val();					
							//alert(detailCounter);
							// urlDataPartition指的是向后退传的数据，因为这里要传递一个rule数据
							// 和n个ruleDetail数据，不能以list的形式传递，所以只能用原始办法传递字符串
							// 第一个参数是detail的个数
							// 第二个参数是rule的数据
							// 后面的参数是个details
							// details的个数是根据freemarker生成的界面确定的
							var postData = {"ruleName" : ruleName, "ruleComment" : ruleComment};
							
							if(ruleName!=null&&ruleName!=""&&isSameName){
								var urlDataPartition = '?detailCounter=' + detailCounter + "&currentBatchId=" + currentBatchId + "&" + rule + "&" + ruleDetails;
								$.ajax({
									headers: { 
								        'Accept': 'application/json',
								        'Content-Type': 'application/json' 
								    },
									type: "POST",
									// ★★对应RuleController里的路由
									url: '${ContextPath}/rule/makeRuleModifyRule' + urlDataPartition,
									data : JSON.stringify(postData),
									dataType: 'json',
									success: function(data){
										if (initLoadRuleList) {
											loadRuleList();
											initLoadRuleList = false;
										} else {
											$('#rule_list').setGridParam({url:'${ContextPath}/rule/makeRuleGetRuleList/'+currentBatchId}).trigger("reloadGrid");
										}
										alert_dialog("修改成功");
									}
								});
							}else{
								alert_dialog("规则名称不能重复或者为空");
								return;
							}
							
							$("#dialog2").dialog("close");
							$('#dialog2').empty();
						},
						"取消" : function() {
							$("#dialog2").dialog("close");
							$('#dialog2').empty();
						},
					},
				});
			}, 'html');
		};
		
		// 遴选规则的修改
		$('#viewRule').click(function() {
			// 选择jqgrid一行数据
			var gr = $('#rule_list').jqGrid('getGridParam','selrow');
			if(gr != null) {
			    var rowData = $('#rule_list').jqGrid("getRowData", gr);
			    var id=rowData["expertSelectionRule.id"];
			    var e = document.getElementById("selectBatch");
				var optionValue = e.options[e.selectedIndex].value;
				if('0'!=optionValue){
				    viewRuleDialog("${ContextPath}/rule/makeRuleGetViewRuleDialog?id="+id);
				}else{
					alert_dialog("请选择批次！");
				}
			}
			else {
				alert_dialog("请先选择一行进行编辑");
			}
		});
		
		// 修改遴选规则对应的对话框
		function viewRuleDialog(url) {
			$.post(url, function(data) {
				$('#dialog2').empty();
				$("#dialog2").append(data);
				$('#dialog2').dialog({
					title : "查看规则",
					autoheight : true,
					width : '800',
					position : 'center',
					modal : true,
					draggable : true,
					hide : 'fade',
					show : 'fade',
					autoOpen : true,
					buttons : {
						"取消" : function() {
							$("#dialog2").dialog("close");
							$('#dialog2').empty();
						},
					},
				});
			}, 'html');
		};
		
		$('#delRule').click(function() {
			var gr = $('#rule_list').jqGrid('getGridParam','selrow'); 
	 		if (gr == null) {
				alert_dialog("请选择一名需要删除的规则！");
				return false;
			} else {
			    //清空警告框内文本并添加新的警告文本
	  		    $( "#dialog-confirm" ).empty().append("<p>确定删除规则？</p>");
	  		    $( "#dialog-confirm" ).dialog({
	      	        height:150,
	      	        buttons: {
	      	            "确定": function() {
	      	            	$( this ).dialog( "close" );
	      	            	var rowData = $('#rule_list').jqGrid("getRowData", gr);
	    				    var id = rowData["expertSelectionRule.id"];
	    				    $.ajax({
	    						type: "POST",
	    						// ★★对应RuleController里的路由
	    						url: "${ContextPath}/rule/makeRuleDeleteRule?id="+id,
	    						success: function(data){
	    							if (data) {
	    								$('#rule_list').setGridParam({url:'${ContextPath}/rule/makeRuleGetRuleList/'+currentBatchId}).trigger("reloadGrid");
	    							} else {
	    								alert_dialog("已经由该规则选出专家，如果删除该规则，专家补选无法按照原标准进行，故不能删除！");
	    							}
	    							
	    						}
	    					});
						},
	      	        	"取消": function() {
	      	           		 $( this ).dialog(
	      	           				 "close");
	      	          		}
	      	        	}
	      	  });
			}});
				
				    
	   
	
	//跳转到专家遴选界面
	function viewExpertSelection() {
		var reUrl = "${ContextPath}/expert/selectExpert";

		$.post(reUrl, function(data) {
			$("#content").empty();
			$("#content").append(data);
		}, 'html');
		event.preventDefault();

	};
	});

	 $("#addBatch").click(function(){
    	addBatchDialog("${ContextPath}/rule/makeRuleAddBatchDialog"); 
     });
     
     function addBatchDialog(url){
    	 $.post(url,function(data){
    		 $("#dialog2").empty();
    		 $("#dialog2").append(data);
    		 $("#dialog2").dialog({
    			 title : "添加批次",
			     autoheight : true,
				 width : '700',
				 position : 'center',
				 modal : true,
				 draggable : true,
				 hide : 'fade',
				 show : 'fade',
				 autoOpen : true,
				 buttons:{
					 /* "确认" : function() {
						     var batchNum = $("#batchNum").val();
						    //$.post("${ContextPath}/rule/makeBatchAddBatch");
						     var items = [];
					         for(var i=1;i<6;i++){
					        	 if($("#item"+i+"").prop("checked")){
					        		 var item = $("#item"+i+"").attr("name");
					        	     items.push(item);
					        	 }	 
					         }
					        console.log(items);
					         if(items.length==0){
					        	 alert_dialog("请至少选择一项打分项");
					         }	 
					         var list = {"num":batchNum,"items":items};
					         $.ajax({ 
				                   type : "POST", 
				                   url : "${ContextPath}/rule/makeRuleAddBatch", 
				                   dataType : "json",      
				                   contentType : "application/json",               
				                   data : JSON.stringify(list), 
				                   success:function(data){
				                   	
				                   } 
				                   error:function(){
				                	 alert(arguments[1]);
				                   }
				                });
					         $("#dialog2").dialog("close");
								$('#dialog2').empty();
					    }, */
					 "关闭" : function() {
							$("#dialog2").dialog("close");
							$('#dialog2').empty();
						},
				 },
    		 });
    	 },'html');
     }
	
	
	
	
</script>