<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" deferredSyntaxAllowedAsLiteral="true"%>

<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>评估流程配置信息
	</h3>
</div>
<div class="selectbar"></div>
<table id="innerStateDetail_tb"></table>
<div id="pager"></div>
<div id="dialog-confirm" title="警告"></div>
<script src="${ContextPath}/js/jquery.common.js"></script>
<script src="${ContextPath}/js/jqGrid.datepicker.js"></script>
<script type="text/javascript">
//时间控件
function setDateCol(id){
	console.log(id);
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
 jQuery("[id$='detail.startTime']","#innerStateDetail_tb").datepicker({
	   yearRange:'1900:2020',
	   changeMonth: true,
	   changeYear: true,
	   showButtonPanel: true,
	   dateFormat: 'yy-mm-dd',
 });
 jQuery("[id$='detail.endTime']","#innerStateDetail_tb").datepicker({
	   yearRange:'1900:2020',
	   changeMonth: true,
	   changeYear: true,
	   showButtonPanel: true,
	   dateFormat: 'yy-mm-dd',  //日期格式，自己设置              buttonImage: 'calendar.gif',  //按钮的图片路径，自己设置              buttonImageOnly: true,  //Show an image trigger without any button.             showOn: 'both',//触发条件，both表示点击文本域和图片按钮都生效         yearRange: '1990:2008',//年份范围          clearText:'清除',//下面的就不用详细写注释了吧，呵呵，都是些文本设置         closeText:'关闭',         prevText:'前一月',         nextText:'后一月',         currentText:' ',          monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],  
});
}
function createLink(type,id){
	if(type=="edit"){
		return  "<a class='edit_detail' href='#' onclick='editInnerDetail("+id+")'>编辑</a>";
	}
	if(type=='save'){
		return "<a class='save_detail' href='#' onclick='saveInnerDetail("+id+")'>保存</a>";
	}
	if(type=='cancel'){
		return "<a class='cancel_detail' href='#' onclick='cancelInnerDetail("+id+")'>取消</a>"; 
	}
}
/**
 * 编辑功能
 */
function editInnerDetail(id){
	jQuery('#innerStateDetail_tb').jqGrid('editRow',id,false,setDateCol);
	var saveLink = createLink("save",id);
	var cancelLink = createLink("cancel",id);	
	jQuery("#innerStateDetail_tb").jqGrid('setRowData',id,{oper:saveLink+" | "+cancelLink});
	
	
}
//保存操作
function saveInnerDetail(id){
	var editLink = createLink("edit",id);
	jQuery("#innerStateDetail_tb").jqGrid('saveRow',id,
			  { 
				url:"${ContextPath}/InnerState/innerStateManage/editInnerStateDetail",
				aftersavefunc:function(data){
				if(data){
					jQuery("#innerStateDetail_tb").jqGrid('setRowData',id,{oper:editLink});
				}else
					alert_dialog('保存失败！');
				}});
}
//取消操作
function cancelInnerDetail(id){
	var editLink = createLink("edit",id);
	jQuery('#innerStateDetail_tb').jqGrid('restoreRow',id);
	jQuery("#innerStateDetail_tb").jqGrid('setRowData',id,{oper:editLink});
}
$(document).ready(function(){
	$( "input[type=submit], a.button , button" ).button();
	 $("#innerStateDetail_tb").jqGrid({
			datatype: "json",
	        url:"${ContextPath}/InnerState/innerStateManage/innerStateDetail",
			colNames:['','ID','流程名称','本轮评估名称','开始时间','结束时间','备注'],
			colModel:[
						{name:'oper',align:"center", width:50,hidden:false},
						{name:'detail.id',align:"center", width:100,hidden:true,editable:true},
						{name:'detail.innerName',align:"center", width:100},
						{name:'detail.domainName',align:"center", width:100},
						{name:'detail.startTime',align:"center", width:100,editable:true},
						{name:'detail.endTime',align:"center", width:100,editable:true},
						{name:'detail.memo',align:"center", width:200,edittype:"textarea",editable:true},
			],
			rownumbers:true,
			height:"100%",
			autowidth:true,
			pager: '#pager',
			rowNum:20,
			rowList:[20,30,40],
			viewrecords: true,
			sortorder: "asc",
			sortname: "innerState",
		    jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
             root: "rows",  //包含实际数据的数组  
             page: "pageIndex",  //当前页  
             total: "totalPage",//总页数  
             records:"totalCount", //查询出的记录数  
             repeatitems : false      
         	},
			gridComplete: function(){
				var ids = jQuery("#innerStateDetail_tb").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					var rowData = $("#innerStateDetail_tb").jqGrid("getRowData",ids[i]);
					console.log(rowData);
					var edit = "<a class='edit_detail' href='#' onclick='editInnerDetail("+ids[i]+")'>编辑</a>"; 
					jQuery("#innerStateDetail_tb").jqGrid('setRowData',ids[i],{oper:edit});
					//setDateCol("innerStateDetail_tb","metaInnerStateDetail.startTime",ids[i]);
					//setDateCol("innerStateDetail_tb","metaInnerStateDetail.endTime",ids[i]);
				}
			},
			caption: "评估流程配置信息"
		}).navGrid('#pager',{edit:false,add:false,del:false});
	
});
</script>