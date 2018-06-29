<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
 <div class="con_header">
	<h3>
		<span class="icon icon-publiccontrast"></span>结果计算
	</h3>
 </div> 
   	<div class="selectbar"> <!-- class="layout_table" -->
        <table class="layout_table">
           <tr>
              <td>
                  <label for="discipline" class="TextFont">计算方式：</label>
                  <input type="radio" name="calType" value="discipline" checked="checked" hidden="true"/>
                  <label for="discipline" class="TextFont">按一级学科</label>
                  <input type="radio" name="calType" value="index"  hidden="true"/>
                  <label for="discipline" class="TextFont" hidden="true">按指标体系分类</label>
              </td> 
              <td>
                  <a id="weight_calculate" href="#" class="button"><span class="icon icon-tool"></span>权重、折算系数计算</a>
                  <a id="export_weight" href="#" class="button"><span class="icon icon-export"></span>导出指标权重</a>
                  <a id="export_index" href="#" class="button"><span class="icon icon-export"></span>导出观测点系数</a>
              </td>
           </tr>
           <tr>
           		<td>
           		<div>
     				<a id="data_calculate" href="#" class="button"><span class="icon icon-tool"></span>计算选中学科</a>
     			</div>
           		</td>
           		<td>
           			<div >
     				<label class="TextFont">类间距：</label>
     				<input type="text" id="clusterLimit"/>
     				<a id="cluster" href="#" class="button"><span class="icon icon-set"></span>聚类</a>
     			</div>
           		</td>
           </tr>
        </table>
     </div>
    
     <div class="selectbar layout_holder">
     	 
          <table id="cal_config_tb"></table>
	      <div id="cal_config_pager_tb"></div>
     </div>
     <div class="process_dialog">
		<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> 正在计算，请稍候！
	</div>
	<div class="inform_dialog">
		<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span>
	</div>

 <script type="text/javascript">
 var contextPath="${ContextPath}";
 
 	$(document).ready(function() {
 		
 		$("input[type=submit], a.button , button").button();
   		$(".process_dialog").hide();
   		$(".inform_dialog").hide();
   		
   		$("#export_weight").click(function(){//下载指标权重计算结果ECXEL表
   			var ids = $("#cal_config_tb").jqGrid("getGridParam", "selarrrow");
   		    if(ids.length>1)
   		    	alert_dialog("一次只能导出一门学科的末级指标项权重！");
   		    else if(ids.length==0)
   		    	alert_dialog("请选择要导出的学科！");
   		    else if(ids.length==1){
   		    	var id= ids[0];
   	   		    var rowData = $("#cal_config_tb").jqGrid('getRowData',id);
   	   		    var discId = rowData["discId"];
   	   		    var url = '${ContextPath}/calculate/calExportWeight/' + discId + '/';
   				outputJS(url);
   		    }
		});
   		
   		$("#export_index").click(function(){//下载折算系数计算结果EXCEL表
   			var ids = $("#cal_config_tb").jqGrid("getGridParam", "selarrrow");
   		    if(ids.length==0)
   		    	alert_dialog("请选择要导出的学科！");
   		    else if(ids.length>1)
   		    	alert_dialog("一次只能导出一门学科的观测点系数！");
   		    else if(ids.length==1){
   		    	var id= ids[0];
   	   		    var rowData = $("#cal_config_tb").jqGrid('getRowData',id);
   	   		    var discId = rowData["discId"];
   				var url = '${ContextPath}/calculate/calExportIndex/' + discId + '/';
   				outputJS(url);
   		    }
		});
   		
   		//开始计算指标权重平均值入口
    	$("#weight_calculate").click(function(){//下载指标权重计算结果EXCEL表
    		var calType = $("input[name='calType']:checked").val();
    	    var discIds = [];
    	    var ids = $("#cal_config_tb").jqGrid("getGridParam", "selarrrow");
    	    if(ids.length==0)
    	    	alert_dialog("请选择要计算的学科！");
    	    
    	    if(ids.length>0){
    	    	for(var i=0;i<ids.length;i++){
        	    	var rowData = $("#cal_config_tb").jqGrid("getRowData",ids[i]);
        	    	var discId = rowData["discId"];
        	    	discIds.push(discId);
        	    }
        	    
        		$(".process_dialog").show();
    			$('.process_dialog').dialog({
    				position : 'center',
    				modal:true,
    				autoOpen : true,
    			});
    			
        		$.ajax({
        			type:"Post",
        			url:'${ContextPath}/calculate/calWeightFactor?calType='+calType,
        			dataType:"json",
        			contentType : "application/json", 
                    data : JSON.stringify(discIds), 
                    success:function(data){
                    	$(".process_dialog").dialog("destroy");
                    	$(".process_dialog").hide();
                    	$(".inform_dialog").empty();
                    	$(".inform_dialog").show();
                    	$('.inform_dialog').dialog({
            				position : 'center',
            				modal:true,
            				autoOpen : true,
            			});
                    	$(".inform_dialog").append(data);
                    },
        		});
    	    }
		});
   		
   		$("#cluster").click(function(){
   			var limitStr=$("#clusterLimit").val();
   			if(isNumber(limitStr)==false){
   				alert_dialog("类间距输入错误，请输入一个正数！");
   				return;
   			}
   			var selectedIds=$("#cal_config_tb").jqGrid("getGridParam", "selarrrow");
   			if(selectedIds.length == 0){
    			alert_dialog("请选择需要聚类的学科!");
     		}else{
     			$(".process_dialog").show();
    			$('.process_dialog').dialog({
    				position : 'center',
    				modal:true,
    				autoOpen : true,
    			});
    			//获取需要计算的学科id
    			var discIds = new Array();
    			for(var i = 0;i<selectedIds.length;i++){
    				var discId =  $("#cal_config_tb").jqGrid("getCell", selectedIds[i], "discId");
    				discIds.push(discId);
    			}
    			
    			$.post('${ContextPath}/calculate/calCluste?discIds=' + discIds+'&limit='+limitStr,function(data){
    				
    				if(data==""){
    					$(".process_dialog").dialog("destroy");
						$(".process_dialog").hide();
						alert_dialog("聚类完成！");
						$("#cal_config_tb").setGridParam({url : '${ContextPath}/calculate/calConfigList/'}).trigger("reloadGrid");
    				}
    				else{
    					$(".process_dialog").dialog("destroy");
    					$(".process_dialog").hide();
    					alert(data);
    					alert_dialog(data);
    				} 
    			});	
     		}
     	});
   			
   		
    	$("#cal_config_tb").jqGrid({
    		url : '${ContextPath}/calculate/calConfigList/',
			datatype : 'json',
			mtype : 'GET',
    		colNames:['ID','学科ID','学科名称','计算状态','上次计算时间','操作人ID','聚类间距','显示计算结果'],
    		colModel:[
    			{name : 'id',index : 'id',width : 60,align : "center",hidden : true}, 
    			{name : 'discId',index : 'discId',width : 60,align : "center"},
    			{name : 'discName',index : 'discChsName',width : 100,align : "center"}, 
    			{name : 'calStatus',index : 'calStatus',width : 60,align : "center",formatter:function(cellvalue, options, row){
			     	if(cellvalue=="0"){
			        	 return "未计算";
			    	}else {
			        	return "已计算";
			   		}},
			 		cellattr: function(rowId, val, rawObject, cm, rdata){
						 if(val=="已计算"){
				        	return "style='color:green'";
				    	}else{
				    		return "style='color:red'";
				   		}}}, 
    			{name:'calTime',index:'calTime',width:80,align:'center',
    				formatter:"date",formatoptions: {srcformat:'u',newformat:'Y-m-d'}},
    			{name : 'userId',index : 'userId',width : 60,align : "center",hidden : true},
    			{name : 'distance',index : 'distance',width : 60,align : "center",cellattr: function(rowId, val, rawObject, cm, rdata){
					 if(val=="未聚类"){
				        	return "style='color:red'";
				    	}else{
				    		return "style='color:green'";
				   		}}},
    			{name : 'show',index : 'show',width : 60,align : "center",hidden:true}
    		],
    		height:"100%",
    		autowidth:true,
    		rowNum : 9999,
    		sortname : 'disc_id',
    		sortorder: "asc",
    		pager : '#cal_config_pager_tb',
    		multiselect: true,
    		multiboxonly: true,
    		caption: "选择计算学科",
    		gridComplete:function(){
    			var ids = jQuery("#cal_config_tb").jqGrid('getDataIDs');
    			var show = "<a class='showCalculateResults' href='#'>显示计算结果</a>";
    			for(var i=0;i<ids.length;i++){
    				jQuery("#cal_config_tb").jqGrid('setRowData',ids[i],{show:show});
    			}
    			
    			$(".showCalculateResults").click(function(){
    	    		var discId = $(this).closest("td").prev().prev().prev().prev().prev().text();
    	    		openShowResultsDialog(discId);
    	    	});
    		}
    		}).navGrid('#cal_config_pager_tb',{edit : false,add : false,del : false, search:false});
    	
    	
       
    	$("#data_calculate").click(function() {
    		
     		var selectedIds = $("#cal_config_tb").jqGrid("getGridParam", "selarrrow");
     		if(selectedIds.length == 0){
    			alert_dialog("请选择需要计算的学科!");
     		}else{
     			$(".process_dialog").show();
    			$('.process_dialog').dialog({
    				position : 'center',
    				modal:true,
    				autoOpen : true,
    			});
    			//获取需要计算的学科id
    			var discIds = new Array();
    			for(var i = 0;i<selectedIds.length;i++){
    				var discId =  $("#cal_config_tb").jqGrid("getCell", selectedIds[i], "discId");
    				discIds.push(discId);
    			}
    			
    			$.post('${ContextPath}/calculate/calStartCalculate?discIds=' + discIds,function(data){
    				
    				if(data==""){
    					$(".process_dialog").dialog("destroy");
						$(".process_dialog").hide();
						alert_dialog("计算完成！");
						$("#cal_config_tb").setGridParam({url : '${ContextPath}/calculate/calConfigList/'}).trigger("reloadGrid");
    				}
    				else{
    					$(".process_dialog").dialog("destroy");
    					$(".process_dialog").hide();
    					alert_dialog(data);
    				} 
    			});	
     		}
     	});
 	});
 	
 	function isNumber(s){
 		var result=false;
 		
 		var patrn = new RegExp("^(([0-9]+[\\.]?[0-9]+)|[1-9])$");
 		if(patrn.test(s))
 			result = true;
 		return result;
 	}
 	
 	function openShowResultsDialog(discId){
		var url = "${ContextPath}/calculate/calShowResultsDialog?discId=" + discId;
		$.post(url,function(data){
			$('#dialog').empty();
			$('#dialog').append(data);
			$('#dialog').dialog({
				title : "备注",
				autoheight: true,
				width: '750',
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
		});
	}
 </script>