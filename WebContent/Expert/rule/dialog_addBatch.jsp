<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<div id="addBatchInfo_div" class="form">
	<form id="batch">
		<table id="add_batch_tb_dialog" class="fr_table">
			<!-- <tr>
				<td width='90'><span class="TextBold">遴选批次号：</span></td>
				<td><input type="hidden" id="batchNum" /></td>
			</tr>  -->
			
			<tr>
				<td width='120'><span class="TextBold" style="float:right;">遴选批次名称：</span></td>
				<td>
					<input type="text" id="batchChName" />
					<input type="hidden" id="batchNum" />
				</td>
			</tr>
			<tr>
				<td width=''><span class="TextBold" style="float:right;">开始日期：</span></td>
				<td>
					<input type="text" id="beginDate" />
				</td>
				<td width='75'><span class="TextBold" style="float:right;">结束日期：</span></td>
				<td>
					<input type="text" id="endDate" />
				</td>
			</tr>
		</table>
	</form>
	
	
	<form id="batchContent">
	    <table id="add_batch_content_dialog" class="fr_table">
	    	<tr><td colspan="3">本批次<span class="TextBold">同行专家</span>的打分任务选择</td></tr>
	        <tr>
	            <td><input type="checkbox" id="item1_1" value="" name="indicIdx" style="margin:4px 4px 0 0">指标体系打分</td>
	            <td><input type="checkbox" id="item1_2" value="" name="indicWt" style="margin:4px 4px 0 0">指标权重打分</td>
	            <td><input type="checkbox" id="item1_3" value="" name="achv" style="margin:4px 4px 0 0">学科成果打分</td>
	        </tr>
	        <tr>
	            <td><input type="checkbox" id="item1_4" value="" name="repu" style="margin:4px 4px 0 0">学校声誉打分</td>
	            <td><input type="checkbox" id="item1_5" value="" name="rank" style="margin:4px 4px 0 0">学科排名打分</td>
	        </tr>
	    </table>
	</form>
	
	<form id="batchContent2">
	    <table id="add_batch_content_dialog2" class="fr_table">
	    	<tr><td colspan="3">本批次<span class="TextBold">学术专家</span>的打分任务选择</td></tr>
	        <tr>
	            <td><input type="checkbox" id="item2_1" value="" name="indicIdx" style="margin:4px 4px 0 0">指标体系打分</td>
	            <td><input type="checkbox" id="item2_2" value="" name="indicWt" style="margin:4px 4px 0 0">指标权重打分</td>
	            <td><input type="checkbox" id="item2_3" value="" name="achv" style="margin:4px 4px 0 0">学科成果打分</td>
	        </tr>
	        <tr>
	            <td><input type="checkbox" id="item2_4" value="" name="repu" style="margin:4px 4px 0 0">学校声誉打分</td>
	            <td><input type="checkbox" id="item2_5" value="" name="rank" style="margin:4px 4px 0 0">学科排名打分</td>
	        </tr>
	    </table>
	</form>
	
	<!-- <a id="addRule" class="button" href="#"> 新建规则</a> -->
	<a href="#" class="button" id="addBatchAction" style="float:right;margin-right: .3em;">
	            <span class="icon icon-add"></span>添加批次</a>
	</div>
	
	
	
	<!-- <div class="cur">
	<table id="batch_list"></table>
	<div id="pager"></div>
    </div>
    <div class="selectbar">
	<table class="layout_table">
	      <tr>
	          <td><a id="editBatch" class="button" href="#" style="float:right;margin-right: .3em;">
			  <span class="icon icon-edit"></span>编辑</a></td>
	          <td><a id="delBatch" class="button" href="#" style="float:right;margin-right: .3em;">
			  <span class="icon icon-del"></span>删除</a></td>
	      </tr>
	</table>
	</div> -->
	
<script type="text/javascript">
$(document).ready(function() {
	$("#addBatchAction").click(function(){
	    var batchNum = $("#batchNum").val().trim();
	    var batchChName = $("#batchChName").val().trim();
	    if (batchChName == "") {
	    	alert_dialog("请填写批次的名称！");
		   	return;
	    }
	    var beginDate = $("#beginDate").val();
	    var endDate = $("#endDate").val();
	    var beginDate2 = beginDate.replace(/-/g, "");
	    beginDate2 = parseInt(beginDate2);
	    
	    var endDate2 = endDate.replace(/-/g, "");
	    endDate2 = parseInt(endDate2);
	    
	    if (endDate2 <= beginDate2) {
	    	alert_dialog("结束日期应当大于开始日期！");
		   	return;
	    }
	    var industrialItems = [];
	    var academicItems = [];
	    for (var i = 1; i < 6; i++) {
	   	    if ($("#item1_" + i).prop("checked")) {
	   		    industrialItems.push($("#item1_" + i).attr("name"));
	   	    }
	   	    if ($("#item2_" + i).prop("checked")) {
	   	    	academicItems.push($("#item1_" + i).attr("name"));
	   	    }
	    }
	    if(industrialItems.length == 0 && academicItems.length == 0){
	   		alert_dialog("请至少选择一项打分项，否则本批次遴选的专家没有任何任务");
	   		return;
	    } 	 
	    
	    var addItem = {"num" : batchNum, "batchName" : batchChName,
	    			   "industrialItems" : industrialItems,
	    			   "academicItems" : academicItems,
	    			   "beginDate" : beginDate, "endDate" : endDate};
	    $.ajax({ 
	         type : "POST", 
	         url : "${ContextPath}/rule/makeRuleAddBatch", 
	         dataType : "json",      
	         contentType : "application/json",               
	         data : JSON.stringify(addItem), 
	         success:function(data){
	        	$("#dialog2").dialog("close");
				$('#dialog2').empty();
	         	$("#batch_list").setGridParam({url:'${ContextPath}/rule/makeRuleGetBatchData'}).trigger('reloadGrid');
	         }, 
	         error:function(){
	      	 alert(arguments[1]);
	         }
	      });

	});
	
	$("input[type=submit], a.button , button").button();
	
    $("input[type='checkbox']").click(function() {
        if($(this).prop('checked')==true){
        	$(this).attr('checked');
        }	
    });
    
    var d = new Date();
    var vYear = d.getFullYear();
    var vMon = d.getMonth() + 1;
    var vDay = d.getDate();
    var h = d.getHours(); 
    var m = d.getMinutes(); 
    var se = d.getSeconds(); 
    s = vYear+(vMon<10 ? "0" + vMon : vMon) + (vDay<10 ? "0"+ vDay : vDay)
    + (h<10 ? "0"+ h : h) + (m<10 ? "0" + m : m) + (se<10 ? "0" +se : se);
    $("#batchNum").val(s);
    
    $.datepicker.regional['zh-CN'] = {  
  	      clearText: '清除',  
  	      clearStatus: '清除已选日期',  
  	      closeText: '关闭',  
  	      closeStatus: '不改变当前选择',  
  	      prevText: '<上月',  
  	      prevStatus: '显示上月',  
  	      prevBigText: '<<',  
  	      prevBigStatus: '显示上一年',  
  	      nextText: '下月>',  
  	      nextStatus: '显示下月',  
  	      nextBigText: '>>',  
  	      nextBigStatus: '显示下一年',  
  	      currentText: '今天',  
  	      currentStatus: '显示本月',  
  	      monthNames: ['一月','二月','三月','四月','五月','六月', '七月','八月','九月','十月','十一月','十二月'],  
  	      monthNamesShort: ['一','二','三','四','五','六', '七','八','九','十','十一','十二'],  
  	      monthStatus: '选择月份',  
  	      yearStatus: '选择年份',  
  	      weekHeader: '周',  
  	      weekStatus: '年内周次',  
  	      dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
  	      dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
  	      dayNamesMin: ['日','一','二','三','四','五','六'],  
  	      dayStatus: '设置 DD 为一周起始',  
  	      dateStatus: '选择 m月 d日, DD',  
  	      dateFormat: 'yy-mm-dd-hh-mm',  
  	      firstDay: 1,  
  	      initStatus: '请选择日期',  
  	      isRTL: false  
  	    };
   $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
   jQuery("#beginDate").datepicker({
  	   yearRange:'1900:2020',
  	   changeMonth: true,
      changeYear: true,
      showButtonPanel: true,
  	   dateFormat: 'yy-mm-dd',  //日期格式，自己设置              buttonImage: 'calendar.gif',  //按钮的图片路径，自己设置              buttonImageOnly: true,  //Show an image trigger without any button.             showOn: 'both',//触发条件，both表示点击文本域和图片按钮都生效         yearRange: '1990:2008',//年份范围          clearText:'清除',//下面的就不用详细写注释了吧，呵呵，都是些文本设置         closeText:'关闭',         prevText:'前一月',         nextText:'后一月',         currentText:' ',          monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],  
   });
   jQuery("#endDate").datepicker({
  	   yearRange:'1900:2020',
  	   changeMonth: true,
      changeYear: true,
      showButtonPanel: true,
  	   dateFormat: 'yy-mm-dd',  //日期格式，自己设置              buttonImage: 'calendar.gif',  //按钮的图片路径，自己设置              buttonImageOnly: true,  //Show an image trigger without any button.             showOn: 'both',//触发条件，both表示点击文本域和图片按钮都生效         yearRange: '1990:2008',//年份范围          clearText:'清除',//下面的就不用详细写注释了吧，呵呵，都是些文本设置         closeText:'关闭',         prevText:'前一月',         nextText:'后一月',         currentText:' ',          monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],  
   });
    
    /* $("#batch_list").jqGrid({
		url: "${ContextPath}/rule/makeRuleGetBatchData",
	    datatype : "json",
		mtype: 'GET',
		colNames : ['批次ID','批次序号','批次名称'],
		colModel : [ 
                     {name :'id',index :'id',width: 100,align : "center"}, 
		             {name :'batchNum',index :'batchNum',width: 100,align : "center"},
		             {name :'batchChName',index :'batchChName',width: 100,align : "center"}
					],
					height : '100%',
					width: '470',
					rowNum : 5,
					rowList : [ 5, 10, 15 ],
					viewrecords : true,
					sortorder : "desc",
					pager: '#pager',
							
					caption : "专家遴选批次",
					jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
		                   root: "rows",  //包含实际数据的数组  
		                   page: "pageIndex",  //当前页  
		                   total: "totalPage",//总页数  
		                   records:"totalCount", //查询出的记录数  
		                   repeatitems : false,
		               },
		               
     }).navGrid('#pager',{edit:false,add:false,del:false}); */
    
});



/* $("#editBatch").click(function(){
	var rowId = $("#batch_list").jqGrid('getGridParam','selrow');
	console.log(rowId);
	if(rowId!=null){
		openEditBatchDialog("${ContextPath}/rule/makeRuleEditBatch?id="+rowId);
	}else{
		alert_dialog("请选择一行进行编辑");
	}
});

$("#delBatch").click(function(){
	var rowId = $("#batch_list").jqGrid('getGridParam','selrow');
	console.log(rowId);
	if(rowId!=null){
		$.post("${ContextPath}/rule/makeRuleDelBatch?id="+rowId,function(data){
			$("#batch_list").setGridParam({url:'${ContextPath}/rule/makeRuleGetBatchData'}).trigger('reloadGrid');
		},'json');
	}
	else{
		alert_dialog("请选择一行删除");
	}
});

function openEditBatchDialog(url){
	$.post(url,function(data){
		$('#dialog').empty();
		$('#dialog').append(data);
		$('#dialog').dialog({
			 title : "编辑批次",
		     autoheight : true,
			 width : '500',
			 position : 'center',
			 modal : true,
			 draggable : true,
			 hide : 'fade',
			 show : 'fade',
			 autoOpen : true,
		});
	});
}; */

</script>
	