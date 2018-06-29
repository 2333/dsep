<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="con_header" style="overflow: hidden">
	<table class="layout_table left">
		<tr>
			<td><span class="icon icon-disciplinecollect"></span> <span
				class="TextFont">历史评价情况 - </span></td>
			<td width='200'><span class="TextFont">指标体系评价-当前</span></td>
		</tr>
	</table>
</div>

<div style="position: relative">
	<div class="expert_history_tree">
		<ul id="check_tree" class="ztree"></ul>
	</div>
	<div class="expert_history_tb">
		<div class="selectbar">
			<table>
				<tr>
					
					<td><span class="TextFont">选择学科：</span> <label for="select"></label>
						<select name="select" id="select">
							<option>-</option>
							<option>人文社科</option>
							<option>理工农医</option>
							<option>管理学</option>
							<option>艺术学</option>
							<option>建筑类</option>
							<option>计算机类</option>
							<option>体育学</option>
					</select></td>
				</tr>
			</table>
		</div>
		<div>
			<table id="history_list"></table>
			<div id="pager"></div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(
			function() {

				$("input[type=submit], a.button , button").button();
				
				/********************  jqGrid ***********************/

				$("#history_list").jqGrid(
						{
							datatype : "local",
							colNames : [ '序号', '指标名称', '参考值', '建议值', '指标名称',
									'参考值', '建议值', '指标名称', '参考值', '建议值' ],
							colModel : [ {
								name : 'id',
								width : 50,
								align : "center"
							}, {
								name : 'aName',
								index : 'aName',
								width : 100,
								align : "center"
							}, {
								name : 'aRefer',
								index : 'aRefer',
								width : 100,
								align : "center"
							}, {
								name : 'aValue',
								index : 'aValue',
								width : 100
							}, {
								name : 'bName',
								index : 'bName',
								width : 100,
								align : "center"
							}, {
								name : 'bRefer',
								index : 'bRefer',
								width : 100,
								align : "center"
							}, {
								name : 'bValue',
								index : 'bValue',
								width : 100
							}, {
								name : 'cName',
								index : 'cName',
								width : 200,
								align : "center"
							}, {
								name : 'cRefer',
								index : 'cRefer',
								width : 200,
								align : "center"
							}, {
								name : 'cValue',
								index : 'cValue',
								width : 200,
								editable : true
							} ],
							height : '100%',
							autowidth : true,
							rowNum : 10,
							rowList : [ 10, 20, 30 ],
							viewrecords : true,
							sortorder : "desc",
							pager : "#pager",
							caption : "学科声誉评价"
						});

				//合并表头
				$("#history_list").jqGrid('setGroupHeaders', {
					useColSpanStyle : true, // 没有表头的列是否与表头列位置的空单元格合并
					groupHeaders : [ {
						startColumnName : 'aName', // 对应colModel中的name
						numberOfColumns : 3, // 跨越的列数
						titleText : '一级指标'
					}, {
						startColumnName : 'bName', // 对应colModel中的name
						numberOfColumns : 3, // 跨越的列数
						titleText : '二级指标'
					}, {
						startColumnName : 'cName', // 对应colModel中的name
						numberOfColumns : 3, // 跨越的列数
						titleText : '三级指标（末级指标）'
					}, ]
				});

				var mydata = [ {
					id : "1",
					aName : "师资队伍与资源",
					aRefer : "23",
					aValue : "",
					bName : "专家团队",
					bRefer : "12",
					bValue : "",
					cName : "专家团队",
					cRefer : "12",
					cValue : ""
				}, {
					id : "2",
					aName : "师资队伍与资源",
					aRefer : "23",
					aValue : "",
					bName : "师生情况",
					bRefer : "4",
					bValue : "",
					cName : "生师比",
					cRefer : "2",
					cValue : ""
				}, {
					id : "3",
					aName : "师资队伍与资源",
					aRefer : "23",
					aValue : "",
					bName : "师生情况",
					bRefer : "4",
					bValue : "",
					cName : "专职教师总数",
					cRefer : "2",
					cValue : ""
				}, {
					id : "4",
					aName : "师资队伍与资源",
					aRefer : "23",
					aValue : "",
					bName : "学科资源",
					bRefer : "7",
					bValue : "",
					cName : "重点学科数",
					cRefer : "4",
					cValue : ""
				}, {
					id : "5",
					aName : "师资队伍与资源",
					aRefer : "23",
					aValue : "",
					bName : "学科资源",
					bRefer : "7",
					bValue : "",
					cName : "重点实验室、基地、中心数",
					cRefer : "3",
					cValue : ""
				}, {
					id : "6",
					aName : "科学研究水平",
					aRefer : "31",
					aValue : "",
					bName : "学术论文",
					bRefer : "11",
					bValue : "",
					cName : "国内论文他引次数和",
					cRefer : "2",
					cValue : ""
				}, {
					id : "7",
					aName : "科学研究水平",
					aRefer : "31",
					aValue : "",
					bName : "学术论文",
					bRefer : "11",
					bValue : "",
					cName : "国外论文他引次数和",
					cRefer : "1.5",
					cValue : ""
				}, {
					id : "8",
					aName : "科学研究水平",
					aRefer : "31",
					aValue : "",
					bName : "学术论文",
					bRefer : "11",
					bValue : "",
					cName : "ESI高被引论文及SCIENCE、NATURE论文数",
					cRefer : "0.5",
					cValue : ""
				}, {
					id : "9",
					aName : "科学研究水平",
					aRefer : "31",
					aValue : "",
					bName : "学术论文",
					bRefer : "11",
					bValue : "",
					cName : "高水平学术论文",
					cRefer : "4",
					cValue : ""
				}, {
					id : "10",
					aName : "科学研究水平",
					aRefer : "31",
					aValue : "",
					bName : "学术论文",
					bRefer : "11",
					bValue : "",
					cName : "人均发表论文数",
					cRefer : "3",
					cValue : ""
				}, {
					id : "11",
					aName : "科学研究水平",
					aRefer : "31",
					aValue : "",
					bName : "专著",
					bRefer : "11",
					bValue : "",
					cName : "出版学术专著数",
					cRefer : "4",
					cValue : ""
				}, {
					id : "12",
					aName : "科学研究水平",
					aRefer : "31",
					aValue : "",
					bName : "科研项目",
					bRefer : "11",
					bValue : "",
					cName : "国家级科研项目数",
					cRefer : "1.5",
					cValue : ""
				}, {
					id : "13",
					aName : "科学研究水平",
					aRefer : "31",
					aValue : "",
					bName : "科研项目",
					bRefer : "11",
					bValue : "",
					cName : "国家级科研项目经费",
					cRefer : "3",
					cValue : ""
				}, {
					id : "14",
					aName : "科学研究水平",
					aRefer : "31",
					aValue : "",
					bName : "科研项目",
					bRefer : "11",
					bValue : "",
					cName : "人均科研总经费",
					cRefer : "2.5",
					cValue : ""
				}, {
					id : "15",
					aName : "科学研究水平",
					aRefer : "31",
					aValue : "",
					bName : "科研获奖",
					bRefer : "11",
					bValue : "",
					cName : "国家与省部级科研获奖数",
					cRefer : "9",
					cValue : ""
				}, {
					id : "16",
					aName : "人才培养质量",
					aRefer : "26",
					aValue : "",
					bName : "学位论文质量",
					bRefer : "7",
					bValue : "",
					cName : "全国优博论文数",
					cRefer : "3.5",
					cValue : ""
				} ];

				for ( var i = 0; i <= mydata.length; i++)
					jQuery("#history_list").jqGrid('addRowData', i + 1,
							mydata[i]);
				/*树结构*/
				var nodes = [ {
					"name" : "指标体系评价",
					open : true,
					children : [ {
						"name" : "当前"
					}, {
						"name" : "2012年"
					}, {
						"name" : "2011年"
					}, {
						"name" : "2010年"
					}, {
						"name" : "2009年"
					} ]
				}, {
					"name" : "学科成果评价",
					open : true,
					children : [ {
						"name" : "当前"
					}, {
						"name" : "2012年"
					}, {
						"name" : "2011年"
					}, {
						"name" : "2010年"
					}, {
						"name" : "2009年"
					} ]

				}, {
					"name" : "学科声誉评价",
					open : true,
					children : [ {
						"name" : "当前"
					}, {
						"name" : "2012年"
					}, {
						"name" : "2011年"
					}, {
						"name" : "2010年"
					}, {
						"name" : "2009年"
					} ]

				} ],

				setting = {
					view : {
						selectedMulti : false
					}
				}

				$.fn.zTree.init($("#check_tree"), setting, nodes);

			});
</script>