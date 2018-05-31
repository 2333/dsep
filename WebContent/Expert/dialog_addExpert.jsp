<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="addExpertInfo_div" class="form">
	<form id="expertInfo_fm" class="fr_form">
		<table id="rule_tb_dialog" class="" style="width:800px">
			<tr>
				<td width='100'>
					<span class="TextBold">姓名</span>
				</td>
				<td width='200'>
					<input type="text" id="expertName"/>
				</td> 
				<td class="">
				    <a id="searchExpertByName" class="button" href="#">
				    <span class="icon icon-search"></span>查找</a>
				</td>
			</tr>
			<tr>
				<td width='100'>
					<span class="TextBold">专家编号</span>
				</td>
				<td width='200'>
					<input type="text" id="expertNumber"/>
				</td> 
				<td class="">
				    <a id="searchExpertByNumber" class="button" href="#">
				    <span class="icon icon-search"></span>查找</a>
				</td>
			</tr>
			<tr>
			    <td width='100'><span class="TextBold">学校编号</span></td>
				<td><input type="text" id="unitID"/></td>
				<td width='100'><span class="TextBold">学科编号</span></td>
				<td><input type="text" id="disciplineID"/></td>
				<td class="fr_right">
					<a id="searchExpertByDiscOrUnitID" class="button" href="#">
					<span class="icon icon-search"></span>根据学校学科查找
					</a>
				</td>
			<tr>
		</table>
	</form>
</div>
<div>
	<table id="expert_list2"></table>
	<div id="pager2"></div>
</div>

<script type="text/javascript">  
		$("input[type=submit], a.button , button").button();
		
		/* 表格化表单布局*/
		$(".fr_table td:nth-child(even)").addClass("fr_left");
		$(".fr_table td:nth-child(odd)").addClass("fr_right");
		
		$("#expert_list2").jqGrid({
			// ★★后台调用显示专家的page方法
			//url: '${ContextPath}/expert/selectExpertGetExpertByName?name=',
			//datatype : "json",
			datatype : "local",
			mtype: 'GET',
			colNames : ['专家编号(隐藏列)', '专家姓名' ,'所在单位', '所属学科' 
			            ,'指定专家评审学科', '指定一级学科' ,'是一级学科' , '办公电话', '电子邮箱', '专家类型', 'discId', 'discId2'],
			colModel : [ 
				{name : 'expertNumber',   		index : 'zjbh',  	 	  width : 100, align : "center", hidden: true},
				{name : 'expertName',     		index : 'name',  	 	  width : 100, align : "center"}, 
				{name : 'collegeName',    		index : 'college',	      width : 100, align : "center"}, 
				{name : 'disciplineName', 		index : 'discipline',	  width : 150, align : "center"}, 
				{name : 'edit',           		index : 'edit',           width:80,    align:"center",   sortable:false},
				{name : 'chooseDiscipline',  	align : "center",         width:100,   sortable:false,   formatter:"select",  edittype:"select",  editoptions:{value:{"true":'第一学科',"false":'第二学科'}},  editable:true},
				{name : 'isYJXKM1',           	index : 'isYJXKM1',       width:80,    align:"center",   sortable:false, hidden: true},
				{name : 'phone',		  		index : 'officePhone',    width : 120, align : "center"}, 
				{name : 'email',		  		index : 'email',		  width : 180, align : "center", hidden : true}, 
				{name : 'expertTypeName', 		index : 'zjfl',	  	 	  width : 100, align : "center"},
				{name : 'discId', index:'discId', hidden: true},
				{name : 'discId2', index:'discId2', hidden: true}
				],
			height : '100%',
			//autowidth : true,
			rowNum : 10,
			rowList : [ 10, 20, 30 ],
			viewrecords : true,
			sortorder : "asc",
			//sortname : "id",
			pager : '#pager2',
			multiselect : true,
			multibodyonly : true,
			caption : "专家检索结果",
			rownumbers: true,
			jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
		        root: "rows",  //包含实际数据的数组  
		        page: "pageIndex",  //当前页  
		        total: "totalPage",//总页数  
		        records:"totalCount", //查询出的记录数  
		        repeatitems : false,
		    },
			loadComplete: function() {
				var ids = jQuery("#expert_list2").jqGrid('getDataIDs');
				var remarkCheck;
				for(var i = 0; i < ids.length; i++) {
					//alert(ids[i]);
// 					remarkCheck = "<a href='#' onclick='openRemarkDialog()'>[查看]</a>"; 
// 					$("#expert_list2").jqGrid('setRowData',ids[i],{remark:remarkCheck});
					
					var rowData = $('#expert_list2').jqGrid("getRowData",ids[i]);
					var discipline = rowData["disciplineName"];
					// 有第二学科
					if (discipline.indexOf("|") > -1) {
						var edit="<a class='check_detail' href='#' onclick='editDisciplineInfo("+ids[i]+")'>编辑</a>";
						jQuery("#expert_list2").jqGrid('setRowData',ids[i],{edit:edit});
					}
				}	
			}
		});
			
	
	    $('#searchExpertByName').click(function(){
	    	var name = $('#expertName').val();
	    	if(name != null) {
	    		name = encodeURI(encodeURI(name));
	   			$("#expert_list2").setGridParam({url:'${ContextPath}/expert/selectExpertGetExpertByName?name='
	   					+ name, datatype : 'json'})
	   					.trigger("reloadGrid");
	    	}
	    });
	    
	    $('#searchExpertByDiscOrUnitID').click(function(){
	    	var unitId = $('#unitID').val().trim();
	    	var discId = $('#disciplineID').val().trim();
	    	if(!(discId == "" && unitId == "")) {
	    		//alert(unitId + discId);
	   			$("#expert_list2").setGridParam({url:"${ContextPath}/expert/selectExpertGetExpertByDiscIDAndUnitId?discId="
	   					+ discId + "&unitId="+unitId, datatype : 'json'})
	   					.trigger("reloadGrid");
	    	}
	    	else {
	    		alert_dialog("请输入学科编号或者学校编号");
	    	}
	    });
	
		$('#searchExpertByNumber').click(function() {
			var number = $('#expertNumber').val().trim();
	    	if(number != null || number != "") {
	    		number = encodeURI(encodeURI(number));
	   			$("#expert_list2").setGridParam({url:'${ContextPath}/expert/selectExpertGetExpertByNumber?number='
	   					+ number, datatype : 'json'})
	   					.trigger("reloadGrid");
	    	}
			//$("#expert_list2").setGridParam({url:'${ContextPath}/batchAddExpert/getExperts/'}).trigger("reloadGrid");
			
		});
			
		/**
		 * 编辑学科信息
		 */
		function editDisciplineInfo(id)
		{
			jQuery("#expert_list2").jqGrid('editRow',id);
			var cancel = "<a class='' href='#' onclick='cancelDisciplineInfo("+id+")'>取消</a>"; 
			var save = "<a class='' href='#' onclick='saveDiscInfo("+id+")'>保存</a>"; 
			jQuery("#expert_list2").jqGrid('setRowData',id,{edit:save+" | "+cancel});	
			//bindSelect(id);
		}
		
		function cancelDisciplineInfo(id)
		{
			jQuery("#expert_list2").jqGrid('restoreRow',id);	
			var modify = "<a class='' href='#' onclick='editDisciplineInfo("+id+")'>编辑</a>"; 
			jQuery("#expert_list2").jqGrid('setRowData',id,{edit :modify});
		}
		
		function saveDiscInfo(id)
		{
			jQuery("#expert_list2").saveRow(id, false, '');
			
// 			var rowData = $('#expert_list2').jqGrid("getRowData",id);
// 			var chooseDiscipline = rowData["chooseDiscipline"];
// 			if (chooseDiscipline == "false") {
					
// 			}
			var modify = "<a class='' href='#' onclick='editDisciplineInfo("+id+")'>编辑</a>"; 
			jQuery("#expert_list2").jqGrid('setRowData',id,{edit :modify});	
		}
</script>