$(document).ready(function() {
	$("input[type=submit], a.button , button").button();
	$("#achievement_content").tabs();
	$(document).ready(function() {
		var subData = [ 
		    {
				id : "1",
				journal : "Science",
				paper : "XXXXXXXX",
				paperType : "Article",
				leadAuthor : "张强",
				correspondingAuthor : "张强",
				seriesNo : "479(7372)",
				includeType : "SCI",
				impactFactor : "32.1",
				remark : "通讯单位及第一署名单位"
			}, {
				id : "2",
				journal : "Nature",
				paper : "XXXXXXXX",
				paperType : "Article",
				leadAuthor : "李明",
				correspondingAuthor : "王浩",
				seriesNo : "334(6062)",
				includeType : "SCI",
				impactFactor : "30.8",
				remark : "第一署名单位"
			}];
							
		$("#achievement_list").jqGrid(
		{
			datatype : "local",
			colNames : ['学科', '学校', '参考数据项','评价'],
			colModel : [
			{name : 'discipline',index : 'discipline',width : 100,align : "center"}, 
			{name : 'college',index : 'college',width : 100,align : "center"}, 
			{name : 'dataItem',index : 'dataItem',width : 100,align : "center"}, 
			{name : 'evaluate',index : 'evaluate',width : 100,align : "center"} 
			],
			cmTemplate : {sortable : false},
			height : '100%',
			autowidth : true,
			rowNum : 10,
			rowList : [ 10, 20, 30 ],
			viewrecords : true,
			sortorder : "desc",
			pager : "#paper_pager",
			caption : "高水平学术论文评估",
			rownumbers: true,
						
			gridComplete: function(){
				//$("#paper_list").expandSubGridRow("1");
				var ids = jQuery("#achievement_list").jqGrid('getDataIDs');
				var showData;
				var evaluateText;
				for(var i=1;i <= ids.length;i++){
					showData = "<a class='showData' href='#' onclick=\"$('#achievement_list').toggleSubGridRow('"+i+"');\">显示/隐藏数据项</a>"; 
					evaluateText = "<select style='width:100px'> <option>-</option> <option>A</option> <option>B</option> <option>C</option> <option>D</option> </select>";
					jQuery("#achievement_list").jqGrid('setRowData',ids[i-1],{dataItem:showData});
					jQuery("#achievement_list").jqGrid('setRowData',ids[i-1],{evaluate:evaluateText});
				}	
			},
					
			/* http://www.trirand.com/blog/jqgrid/jqgrid.html  Advanced/Grid as Subgrid*/
			subGrid: true,
			subGridRowExpanded: function(subgrid_id, row_id) {
				//subgrid_id:subgridpaper_list_1
				//row_id:subgrid1
				// we pass two parameters
				// subgrid_id is a id of the div tag created whitin a table data
				// the id of this elemenet is a combination of the "sg_" + id of the row
				// the row_id is the id of the row
				// If we wan to pass additinal parameters to the url we can use
				// a method getRowData(row_id) - which returns associative array in type name-value
				// here we can easy construct the flowing
				var subgrid_table_id, pager_id;
				subgrid_table_id = subgrid_id+"_t";
				pager_id = "p_"+subgrid_table_id;
				//console.log("subgrid"+row_id);
				$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
				jQuery("#"+subgrid_table_id).jqGrid({
					//url:"subgrid.php?q=2&id="+row_id,
					//datatype: "xml",
					datatype : "local",
					data : subData,
					colNames: ['序号','发表刊物名称','论文名称','论文类型','第一作者','通讯作者',
					           '发表刊次（卷期）','收录类型','影响因子','备注'],
					colModel: [
						{name:"id",					index:"id",					width:30,			align:"center", 	key:true},
						{name:"journal",			index:"journal",			width:100,			align:"center"},
						{name:"achievement",		index:"achievement",		width:150,			align:"center"},
						{name:"paperType",			index:"paperType",			width:70,			align:"center"},
						{name:"leadAuthor",			index:"leadAuthor",			width:70,			align:"center"},
						{name:"correspondingAuthor",index:"correspondingAuthor",width:70,			align:"center"},
						{name:"seriesNo",			index:"seriesNo",			width:70,			align:"center"},
						{name:"includeType",		index:"includeType",		width:70,			align:"center"},
						{name:"impactFactor",		index:"impactFactor",		width:70,			align:"center"},
						{name:"remark",				index:"remark",				width:100,			align:"center"}
					],
					autowidth : true,
				   	rowNum:20,
				   	pager: pager_id,
				   	//sortname: 'num',
				    //sortorder: "asc",
				    cmTemplate : {sortable : false},
				    height: '100%'
				});
						//jQuery("#"+subgrid_table_id).jqGrid('navGrid',"#"+pager_id,{edit:false,add:false,del:false})
			},
				subGridRowColapsed: function(subgrid_id, row_id) {
					// this function is called before removing the data
					//var subgrid_table_id;
					//subgrid_table_id = subgrid_id+"_t";
					//jQuery("#"+subgrid_table_id).remove();
				}
			});

				var mydata = [ {
					id : "1",
					discipline : "0835-软件工程",
					college : "北京航空航天大学",
					paper : "",
					student : "",
					introduction: ""
				}, {
					id : "2",
					discipline : "0835-软件工程",
					college : "北京大学",
					paper : "",
					student : "",
					introduction: ""
				}, {
					id : "3",
					discipline : "0835-软件工程",
					college : "清华大学",
					paper : "",
					student : "",
					introduction: ""
				}, {
					id : "4",
					discipline : "0835-软件工程",
					college : "北京交通大学",
					paper : "",
					student : "",
					introduction: ""
				}, {
					id : "5",
					discipline : "0835-软件工程",
					college : "北京理工大学",
					paper : "",
					student : "",
					introduction: ""
				}, {
					id : "6",
					discipline : "0835-软件工程",
					college : "北京科技大学",
					paper : "",
					student : "",
					introduction: ""
				}, {
					id : "7",
					discipline : "0835-软件工程",
					college : "北京邮电大学",
					paper : "",
					student : "",
					introduction: ""
				} ];

				for ( var i = 0; i <= mydata.length; i++)
					jQuery("#achievement_list").jqGrid('addRowData', i + 1, mydata[i]);

			});
});