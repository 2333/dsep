<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div  style="overflow: hidden;">
<div class="left" style="width:60%">
<div class="con_header" style="overflow: hidden">
	<table class="layout_table left">
		<tr>
			<td><span class="icon icon-disciplinecollect"></span> <span
				class="TextFont">分组列表</span></td>
		</tr>
	</table>
</div>
 <div class="selectbar" style="overflow: hidden">
     	    <table>
     	    	<tr>
     	    		<td>
     	    		<span class="TextFont">分组名称：</span>
     	    		<input type="text">
     	    		<span class="TextFont">备注：</span>
     	    		<input type="text">
					<a id="search" class="button" href="#"><span class="icon icon-search"></span>新建</a>
     	    	</td>	
     	    	</tr>
     	</table>	
 </div>
 <div class="selectbar">
   		<table id="group_list_tb"></table>
   		<div id="group_pager_tb"></div>
 </div>
</div>
<div class="right" style="width:39%">
<div class="con_header" style="overflow: hidden">
	<table class="layout_table left">
		<tr>
			<td><span class="icon icon-disciplinecollect"></span> <span
				class="TextFont">“人文社科”分组详情</span></td>
		</tr>
	</table>
</div>	
<div class="selectbar">
     		<span class="TextFont">学科：</span>
     		<select>
     			<option> 软件工程</option>
     			<option> 系统工程</option>
     			<option> 金融</option>
     			<option> 中文</option>
     			<option> 计算机</option>
     		</select>
     		<a id="search" class="button" href="#"><span class="icon icon-search"></span>增加</a>
</div>
<div>
   	<table id="discipline_list_tb"></table>
	<div id="discipline_pager_tb"></div>
 </div>
 </div>
</div>
 <div id="detail_dialog">
 </div>
  <script type="text/javascript">
  $(document).ready(function(){
  common_Css();//button的样式
  $("#discipline_list_tb").jqGrid({
      datatype: 'local',
		colNames:['学科',''],
		colModel:[
			{name:'disciplinename',index:'disciplinename', width:60,align:"center",editable:true},	
			{name:'op',index:'op', width:60, align:"center",sorttype:"String",editable:false }
		],
		autowidth:true,
		pager: '#discipline_pager_tb',
		pgbuttons:true,
		rowNum:10,
		rowList:[10,20,30],
		viewrecords: true,
		sortname: 'disciplinename',
		sortorder: "desc",
		caption: "分组学科列表",
		modal:false,
      gridComplete: function(){
			var ids = jQuery("#discipline_list_tb").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var del = "<a class='check_detail' href='#'>删除</a>"; 
				jQuery("#discipline_list_tb").jqGrid('setRowData',ids[i],{op:del});
			}	
		}
	}).navGrid('#discipline_list_tb',{edit:false,add:false,del:false});
  var myfeedbackdata = [{disciplinename:"0101-哲学"}
  ,{disciplinename:"0201-理论经济学"},
  {disciplinename:"0202-应用经济学"},
  {disciplinename:"0301-法学"}
  ,{disciplinename:"0302-政治学"}
  ,{disciplinename:"0303-社会学"},
  {disciplinename:"0304-民族学"}
  ,{disciplinename:"0305-马克思主义理论"}
  ,{disciplinename:"0401-公安学"}
  ,{disciplinename:"0402-教育学"}
 ];
  for(var i=0;i<=myfeedbackdata.length;i++)
		jQuery("#discipline_list_tb").jqGrid('addRowData',i+1,myfeedbackdata[i]);
  jQuery("#discipline_list_tb").setGridHeight("230");
  //分组列表
  $("#group_list_tb").jqGrid({
      datatype: 'local',
		colNames:['组名','备注',''],
		colModel:[
			{name:'groupname',index:'groupname', width:100, sorttype:"String",align:"center",editable:true,edittype:"text"},
			{name:'memo',index:'memo', width:200, align:"center",sorttype:"String",editable:false },
			{name:'op',index:'op', width:60, align:"center",sorttype:"String",editable:false }
		],
		autowidth:true,
		pager: '#group_pager_tb',
		pgbuttons:true,
		rowNum:10,
		rowList:[10,20,30],
		viewrecords: true,
		sortname: 'groupname',
		sortorder: "desc",
		caption: "分组列表",
		modal:false,
        gridComplete: function(){
			var ids = jQuery("#group_list_tb").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var modify = "&nbsp;&nbsp;<a class='modify' href='#'>编辑</a>"; 
				var check = "&nbsp;&nbsp;<a class='check_detail' href='#'>详情</a>"; 
				jQuery("#group_list_tb").jqGrid('setRowData',ids[i],{op:modify + check});
			}	
		}
	}).navGrid('#group_list_tb',{edit:false,add:false,del:false});
  var myfeedbackdata = [
 {groupname:"理工农医",memo:"包括如下学科：“XX”、“XX”、“XX”、“XX”"},
 {groupname:"建筑",memo:"包括如下学科：“XX”、“XX”、“XX”、“XX”"},
  {groupname:"人文社科",memo:"包括如下学科：“XX”、“XX”、“XX”、“XX”"},
  {groupname:"计算机1",memo:"包括如下学科：“XX”、“XX”、“XX”、“XX”"},
  ,{groupname:"计算机2",memo:"包括如下学科：“XX”、“XX”、“XX”、“XX”"},
  ,{groupname:"计算机3",memo:"包括如下学科：“XX”、“XX”、“XX”、“XX”"}
  ,{groupname:"艺术",memo:"包括如下学科：“XX”、“XX”、“XX”、“XX”"}
  ,{groupname:"体育",memo:"包括如下学科：“XX”、“XX”、“XX”、“XX”"}
  ,{groupname:"软件工程1",memo:"包括如下学科：“XX”、“XX”、“XX”、“XX”"}
  ,{groupname:"软件工程2",memo:"包括如下学科：“XX”、“XX”、“XX”、“XX”"}
  ];
  for(var i=0;i<=myfeedbackdata.length;i++)
		jQuery("#group_list_tb").jqGrid('addRowData',i+1,myfeedbackdata[i]);
  $("#group_list_tb").setGridHeight("230");
  });
  </script>