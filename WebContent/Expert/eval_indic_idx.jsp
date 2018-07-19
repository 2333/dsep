<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="${ContextPath}/js/expert.common.js"></script>

<style type='text/css'>
       .alertStyle{
       font-family: Lucida Grande,Lucida Sans,Arial,sans-serif;
       font-size: 1.1em;
       font-weight: bold;
       color: red;
       text-align: center;
       }
       
       
       .noneAlertStyle{
       font-family: Lucida Grande,Lucida Sans,Arial,sans-serif;
       font-size: 1.1em;
       font-weight: bold;
       color: green;
       text-align: center;
       }
</style>
    
 <div class="con_header">
	<h3>
		<span class="icon icon-web"></span>指标体系评价
		<input class="sumPoints" style="float:right" disabled="disabled">
		<label style="float:right">打分值总和：  </label> 
	</h3>
</div>
<input id="nextQuestionRoute" type="hidden" value="${nextQuestionRoute}"/> 

<!-- 导航箭头 -->
<jsp:include page="eval_navi.jsp"/>

<div class="selectbar layout_holder" style="width:100%;">
	<table id="indic_idx_list"></table>
	<div id="pager"></div>
</div>

<div class="selectbar inner_table_holder" style="width:100%">
	<table class="layout_table right">
		<tbody>
			<tr>
				<td>
					<div class="con_header">
						<h3>
							<input class="sumPoints" style="float:right" disabled="disabled">
							<label style="float:right">打分值总和：  </label> 
						</h3>
					</div>
				</td>
				<td width="125">
					<a id="submitAllResults2" class="button" href="#">
					<span class="icon icon-store "></span>整体保存
					</a>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<!-- 导航箭头 -->
<jsp:include page="eval_navi.jsp"/>

<div class="process_dialog">
	<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> 正在保存……
</div>
<div id="dialog-confirm" title="警告"></div>

<script type="text/javascript">  
	/**
	 * tab_width是第一个tab的宽，赋值给第二个tab中的jqgrid的width
	 */
	var tab_width = $("#indicator_content div.ui-tabs-panel:not(.ui-tabs-hide)").width();
	tab_width -= 2;
		
	$(".process_dialog").hide();
	$("#dialog-confirm").hide();
	var currentBatchId = "";
	var expertId = "";
		/*
		 * ★★
		 * 之所以要把colModelndex中的index这个属性也加入进来，是为了方便构造JSON对象
			 * colModelndex中的index属性是为了合并单元格而设立的，
			 * EvalProgressRowVM内置的display属性为数字时表示合并单元格的个数，为"none"时表明此时这项不显示
			 * 原来是colModelndex中的name属性承担这项显示工作，但是这样给后台构建JSON带来困难
			 * 现在加入index属性，name属性只要设置为String即可，
			 * 而index属性设置为一个类，内部维护String类型display的显示信息
		 */
		attrSetting = function (rowId, val, rawObject, cm) {
			var attr = rawObject.attr[cm.index], result = '';
			//alert(attr.aId);
			if (attr.display != "none") {
				result = ' rowspan=' + '"' + attr.display + '"';
			} else {
				result = ' style="display:' + attr.display + '"';
			}
			return result;
		};
 
	   $("input[type=submit], a.button, button").button();    	      	      	   	       	   
    	   
            jQuery("#indic_idx_list").jqGrid({  
            	url:'${ContextPath}/evaluation/progress/indicIdxGetResults',
                datatype: 'json', 
                mtype : 'GET',
            
                colNames:['一级指标ID','一级指标名称','参考值','实际值',
                          '二级指标ID','二级指标名称','参考值','实际值', 'bToA',
                          '末级指标ID','末级指标名称','参考值', '实际值','cToB',
           				  'OLD打分','打分','状态','结果ID','题目ID', "请您评价"],  
                colModel:[
                         {name : 'aId',			hidden: true}, 
 						 {name : 'aName', 		index : 'aNameIndex', 	width : 130,	align : "center",	cellattr : attrSetting}, 
 						 {name : 'aVal',  		index : 'aValIndex',  	width : 45,		align : "center",	cellattr : attrSetting}, 
 						 {name : 'aActualVal',  index : 'aValIndex',  	width : 45,		align : "center",	cellattr : attrSetting}, 
 						 {name : 'bId',	  		hidden: true}, 
 						 {name : 'bName', 		index : 'bNameIndex', 	width : 130,	align : "center",	cellattr : attrSetting},
 						 {name : 'bVal',  		index : 'bValIndex',	width : 45,		align : "center",   cellattr : attrSetting},
	 					 {name : 'bActualVal',  index : 'bValIndex',  	width : 45,		align : "center",	cellattr : attrSetting}, 
 						 {name : 'bToAAnchor',	hidden: true},
 						 {name : 'cId',	  		hidden: true},
 						 {name : 'cName',		index : 'cNameIndex',  	width : 220,	align : "left", 	cellattr : attrSetting},
 						 {name : 'cVal', 		index : 'cValIndex', 	width : 45,		align : "center",	cellattr : attrSetting},
 						 {name : 'cActualVal',  index : 'cValIndex',  	width : 45,		align : "center",	cellattr : attrSetting}, 
 						 {name : 'cToBAnchor',	hidden: true},
 						 {name : 'oldScore', 	hidden: true},
 						 {name : 'score', 		hidden: true},
 						 {name : 'state', 		hidden: true},
 						 {name : 'questionId',	hidden: true},
 						 {name : 'resultId',	hidden: true},
 						 {name : 'evaluate',	index : 'evaluate',		width : 90}
                ],                                                          
               
                jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
        			root : "rows", //包含实际数据的数组  
        			page : "pageIndex", //当前页  
        			total : "totalPage",//总页数  
        			records : "totalCount", //查询出的记录数  
        			repeatitems : false
        		},
        		beforeSelectRow: function(rowid, e) {
        		    return false;
        		},
        		gridComplete: function(){
        			var grid = jQuery("#indic_idx_list");
        			var ids = grid.jqGrid('getDataIDs');
        			var evaluateText;
        			var sum = 0;
        			for (var i = 0; i < ids.length; i++) {
        				var rowId = ids[i];
        				var rowdata = grid.jqGrid('getRowData',rowId);
        			    var score = rowdata.score; 
        			    
        			    if (score != "") {
        			    	sum += parseFloat(score);	
        			    }
        			    
        			    if (score == "0") score = "";
        			    
        			    evaluateText = "<input type='text' id='evaluateText" + rowId + "' class='scoreinput' value='" + score +"' onblur='changeScore(this);return false;'/>";
        			    grid.jqGrid('setRowData', rowId, {evaluate:evaluateText});
        				
        			    if (rowdata.aActualVal != rowdata.aVal) {
    				    	grid.jqGrid('setCell',rowId, 'aActualVal', '', 'alertStyle');
    				    } else {
    				    	grid.jqGrid('setCell',rowId, 'aActualVal', '', 'noneAlertStyle');
    				    }
        			    
        			    if (rowdata.bActualVal != rowdata.bVal) {
    				    	grid.jqGrid('setCell',rowId, 'bActualVal', '', 'alertStyle');
    				    } else {
    				    	grid.jqGrid('setCell',rowId, 'bActualVal', '', 'noneAlertStyle');
    				    }
        			    
        			    //alert(rowdata.cActualVal + " == " + rowdata.cVal + " " + (rowdata.cActualVal == rowdata.cVal));
        			    if (rowdata.cActualVal != rowdata.cVal) {
    				    	grid.jqGrid('setCell',rowId, 'cActualVal', '', 'alertStyle');
    				    } else {
    				    	grid.jqGrid('setCell',rowId, 'cActualVal', '', 'noneAlertStyle');
    				    }
        			}
        			
        			
        			$(".sumPoints").val(sum.toFixed(2));
        			if (100.00 == parseFloat($(".sumPoints").val())) {
        				$(".sumPoints").removeClass("noneAlertStyle");
        				$(".sumPoints").removeClass("alertStyle");
        				
        				$(".sumPoints").addClass("noneAlertStyle");
        			} else {
        				$(".sumPoints").removeClass("noneAlertStyle");
        				$(".sumPoints").removeClass("alertStyle");
        				
        				$(".sumPoints").addClass("alertStyle");
        			}
        		},
                caption: "指标体系",             
                pager : '#pager',
                height:"100%",
                autowidth : true,
                viewrecords:true,    // 设为具体数值则会根据实际记录数出现垂直滚动条  
                sortorder : "desc",
        		sortname : "level1Name",
        		rowNum: 60
            }).navGrid('#pager', {edit : true,add : false,del : false});  
            
          
         	// 将鼠标离开input框的blur事件的this传入
         	// 这个方法很长，但几个循环的原理都一样
         	// 该方法主要完成的事情
         	// 1、获取当前的输入值
         	// 2、更改cActualVal，如果和cVal不一样，显示红色
         	// 3、更改总分，如果不为100.00，显示红色
         	// 4、找到该指标的父指标，也就是二级指标，更改bActualVal，如果和bVal不一样，显示红色
         	// 5、找到该指标的grand指标，也就是一级指标，更改aActualVal，如果和aVal不一样，显示红色
            changeScore = function(sel){	
            	var grid = $('#indic_idx_list');
		    	// 将this封装成jquery对象，即input框的query对象
            	var that = $(sel);
		     	var inputVal = that.val().trim();
		     	if (inputVal != "") {
		     		if (!validate(inputVal)) {
		     			alert_dialog("输入数字不合法，请输入小于100的非负整数、1位小数或者2位小数");
		     			inputVal = "0";
		     		}
		     		
		     		// 获得激活行rowId
			     	var rowId = that.closest('tr')[0].id;
			     	var rowdata = grid.jqGrid('getRowData',rowId);
			     	
			     	var preCActualVal = rowdata.cActualVal;
			     	rowdata.cActualVal = parseFloat(inputVal).toFixed(2);
			     	
			     	var diffVal = (parseFloat(inputVal) - parseFloat(preCActualVal)).toFixed(2);
			     	$(".sumPoints").val((parseFloat($(".sumPoints").val()) + parseFloat(diffVal)).toFixed(2));
        			if (100.00 == parseFloat($(".sumPoints").val())) {
        				$(".sumPoints").removeClass("noneAlertStyle");
        				$(".sumPoints").removeClass("alertStyle");
        				
        				$(".sumPoints").addClass("noneAlertStyle");
        			} else {
        				$(".sumPoints").removeClass("noneAlertStyle");
        				$(".sumPoints").removeClass("alertStyle");
        				
        				$(".sumPoints").addClass("alertStyle");
        			}
			     	
			     	if (rowdata.cActualVal != rowdata.cVal) {
				    	// 以下4行是为了removeClass(noneAlertStyle)
				    	var iCol = getColumnIndexByName(grid,"cActualVal");
				        tr = grid[0].rows.namedItem(rowId); // grid is defined as grid=$("#grid_id")
				        td = tr.cells[iCol];
				   		$(td).removeClass("noneAlertStyle");
				   		
				    	grid.jqGrid('setCell',rowId, 'cActualVal', '', 'alertStyle');
				    } else {
				    	// 以下4行是为了removeClass(alertStyle)
				    	var iCol = getColumnIndexByName(grid,"cActualVal");
				        tr = grid[0].rows.namedItem(rowId); // grid is defined as grid=$("#grid_id")
				        td = tr.cells[iCol];
				   		$(td).removeClass("alertStyle");
				   		
				    	grid.jqGrid('setCell',rowId, 'cActualVal', '', 'noneAlertStyle');
				    }
			     	
			     	$('#indic_idx_list').jqGrid('setRowData', rowId, rowdata);
				    evaluateText = "<input type='text' id='evaluateText" + rowId + "' class='scoreinput' value='" + inputVal +"' onblur='changeScore(this);return false;'/>";
				    grid.jqGrid('setRowData', rowId, {evaluate:evaluateText});
				    
				    // 获得b到a的索引，快速定位第一个a的位置
				    var bToAAnchor = rowdata.bToAAnchor;
				    var ids = grid.jqGrid('getDataIDs');
	    			var findFirstARowId = "";
				    for (var i = 0; i < ids.length; i++) {
				    	findFirstARowId = ids[i];
	    				var aRowdata = grid.jqGrid('getRowData',findFirstARowId);
	    			    var aId = aRowdata.aId; 
	    				// 通过索引找了了aId
	    			    if (aId == bToAAnchor) break;
	    			}	
				    
				    // 更改aId所在行的值
				    var firstARow = grid.jqGrid('getRowData',findFirstARowId);
				    firstARow.aActualVal = (parseFloat(firstARow.aActualVal) + parseFloat(diffVal)).toFixed(2);
				    if (firstARow.aActualVal != firstARow.aVal) {
				    	// 以下4行是为了removeClass(noneAlertStyle)
				    	var iCol = getColumnIndexByName(grid,"aActualVal");
				        tr = grid[0].rows.namedItem(findFirstARowId); // grid is defined as grid=$("#grid_id")
				        td = tr.cells[iCol];
				   		$(td).removeClass("noneAlertStyle");
				   		
				    	grid.jqGrid('setCell',findFirstARowId, 'aActualVal', '', 'alertStyle');
				    } else {
				    	// 以下4行是为了removeClass(alertStyle)
				    	var iCol = getColumnIndexByName(grid,"aActualVal");
				        tr = grid[0].rows.namedItem(findFirstARowId); // grid is defined as grid=$("#grid_id")
				        td = tr.cells[iCol];
				   		$(td).removeClass("alertStyle");
				   		
				    	grid.jqGrid('setCell',findFirstARowId, 'aActualVal', '', 'noneAlertStyle');
				    }
				    
				    grid.jqGrid('setRowData', findFirstARowId, firstARow);
				   
				    
				    // 原理同上一个for循环，获得c到b的索引，快速定位第一个b的位置
				    var cToBAnchor = rowdata.cToBAnchor;
	    			var findFirstBRowId = "";
				    for (var i = 0; i < ids.length; i++) {
				    	findFirstBRowId = ids[i];
	    				var bRowdata = grid.jqGrid('getRowData',findFirstBRowId);
	    			    var bId = bRowdata.bId;
	    			 // 通过索引找了了bId
	    			    if (bId == cToBAnchor) break;
	    			}
				    
				 	// 更改bId所在行的值
				    var firstBRow = grid.jqGrid('getRowData',findFirstBRowId);
				    firstBRow.bActualVal = (parseFloat(firstBRow.bActualVal) + parseFloat(diffVal)).toFixed(2);
				    //alert(firstBRow.bActualVal + " " + firstBRow.bVal + " " + (firstBRow.bActualVal == firstBRow.bVal));
				    
				    if (firstBRow.bActualVal != firstBRow.bVal) {
				    	// 以下4行是为了removeClass(noneAlertStyle)
				    	var iCol = getColumnIndexByName(grid,"bActualVal");
				        tr = grid[0].rows.namedItem(findFirstBRowId); // grid is defined as grid=$("#grid_id")
				        td = tr.cells[iCol];
				   		$(td).removeClass("noneAlertStyle");
				    	
				   		grid.jqGrid('setCell',findFirstBRowId, 'bActualVal', '', 'alertStyle');
				    } else {
				    	// 以下4行是为了removeClass(alertStyle)
				    	var iCol = getColumnIndexByName(grid,"bActualVal");
				        tr = grid[0].rows.namedItem(findFirstBRowId); // grid is defined as grid=$("#grid_id")
				        td = tr.cells[iCol];
				   		$(td).removeClass("alertStyle");
				   		
				   		grid.jqGrid('setCell',findFirstBRowId, 'bActualVal', '', 'noneAlertStyle');
				    }
				    grid.jqGrid('setRowData', findFirstBRowId, firstBRow);
		     	}
		     	
            };
            
            var getColumnIndexByName = function(grid,columnName) {
                var cm = grid.jqGrid('getGridParam','colModel');
                for (var i=0,l=cm.length; i<l; i++) {
                    if (cm[i].name===columnName) {
                        return i; // return the index
                    }
                }
                return -1;
            };
       
            function validate(num) {
                var reg = new RegExp("^[0-9]{1,2}(.[0-9]{1,2})?$");
              	return reg.test(num);
            }
            
       $("#submitAllResults1").click(function(){
    	   if ((!allFinished()) || (100.00 != parseFloat($(".sumPoints").val()))) {
				$( "#dialog-confirm" ).empty().append("<p>您有项目没有评价或者总分不为100.00，可以保存但最后不能提交，是否保存？</p>");
	  		    $( "#dialog-confirm" ).dialog({
	      	        height:170,
	      	    	width: 400,			
					position : 'top',
					modal : true,	
	      	        buttons: {
	      	            "确定": function() {
	      	            	$(this).dialog("close");
	      	            	wholeSave(true, null);    
		      	        },
	      	        	"取消": function() {
	      	           		 $( this ).dialog( "close" );
	      	          		}
	      	        	}
	      	  });
			} else {
				wholeSave(true, null);    
			}
       });
       $("#submitAllResults2").click(function(){
    	   if ((!allFinished()) || (100.00 != parseFloat($(".sumPoints").val()))) {
				$( "#dialog-confirm" ).empty().append("<p>您有项目没有评价或者总分不为100.00，可以保存但最后不能提交，是否保存？</p>");
	  		    $( "#dialog-confirm" ).dialog({
	      	        height:170,
	      	      	width: 400,			
					position : 'top',
					modal : true,	
	      	        buttons: {
	      	            "确定": function() {
	      	            	$(this).dialog("close");
	      	            	wholeSave(true, null);    
		      	        },
	      	        	"取消": function() {
	      	           		 $( this ).dialog( "close" );
	      	          		}
	      	        	}
	      	  });
			} else {
				wholeSave(true, null);    
			}
       });
       
       function allFinished() {
    	   var grid = jQuery("#indic_idx_list");
			var ids = grid.jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var rowId = ids[i];
				var score = $('#evaluateText' + rowId).val();
				console.log(score.trim());
				if (score.trim() == "") return false;
			}
			return true;
       }
      	 function openLink(url) {
           wholeSave(false, url);
		};
       
		function wholeSave(clickFromSaveButton, url) {
			$(".process_dialog").show();
			$('.process_dialog').dialog({
				position : 'center',
				modal : true,
				autoOpen : true,
			});
			
			
    	   	var results = [];
    	   	var ids = $("#indic_idx_list").jqGrid('getDataIDs');
    	   	for (var i = 0; i < ids.length; i++) {
    		   	var rowId = ids[i];
    		   	var rowData = $('#indic_idx_list').jqGrid("getRowData",rowId);
    		   	var questionId = rowData.questionId;
    		   	var resultId = rowData.resultId;
    		   	var score = $('#evaluateText' + rowId).val().trim();
    		   	//alert("#evaluateText"+rowId+"="+score);
    		   	var oldScore = rowData.oldScore.trim();
    		   	//if (score == "") score = "0";
    		   	//if (oldScore == "") score = "0";
    		   	if (score != "") {
    		   		score = parseFloat(score).toFixed(2);
    		   	}
    		   	if (oldScore != "") {
    		   		oldScore = parseFloat(oldScore).toFixed(2);
    		   	}
			   	console.log(score + " " + oldScore + " " + (score == oldScore));    		   
 		   	   	// 表明这次专家改变了评价，需要保存
    		   	if (score != oldScore) {
    		   		//alert(score);
           			evalResultFromJSP = {
           				"resultId" : resultId, 
           				"value" : score, 
               			"questionId" : questionId
               		};
               		results.push(evalResultFromJSP);
           		}
       		}
    	    $.ajax({ 
               type : "POST", 
               url : "${ContextPath}/evaluation/progress/indicIdxSaveOrUpdateResults", 
               dataType : "json",      
               contentType : "application/json",               
               data : JSON.stringify(results), 
               success:function(backData){
            	   if (clickFromSaveButton) {
            		   $("#indic_idx_list")
                   			.setGridParam({url:'${ContextPath}/evaluation/progress/indicIdxGetResults/'})
                   			.trigger("reloadGrid");
   	        			$(".process_dialog").dialog("destroy");
   						$(".process_dialog").hide();
   	        		} else {
   	        			$(".process_dialog").dialog("destroy");
   						$(".process_dialog").hide();
   	        			if (url.trim() == "") return;
   						$.post(url, function(postData){
   							$( "#content" ).empty();
   						  	$( "#content" ).append( postData );
   						}, 'html');
   	        	}
               
               }
        });
		}
       
    </script>  
