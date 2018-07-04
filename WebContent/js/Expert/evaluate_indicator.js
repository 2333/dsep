$(document).ready(function() {
	$("input[type=submit], a.button , button").button();
	$("#indicator_content").tabs();
					
					
	/**
	 * tab_width是第一个tab的宽，赋值给第二个tab中的jqgrid的width
	 */
	var tab_width = $("#indicator_content div.ui-tabs-panel:not(.ui-tabs-hide)").width();
	tab_width -= 2;
	//console.log("tabwidth"+tab_width);
					
					
					var mydata1 = [ 
    {id : "1",aName : "师资队伍与资源",aRefer : "23",bName : "专家团队",bRefer : "12",cName : "专家团队",cRefer : "12",cValue : "",
	attr: {	aName: {rowspan: "5"},aRefer: {rowspan: "5"},bName: {rowspan: "1"},bRefer: {rowspan: "1"}}}, 
	{id : "2",aName : "师资队伍与资源",aRefer : "23",bName : "师生情况",bRefer : "4",cName : "生师比",cRefer : "2",cValue : "",
	attr: {	aName: {display: "none"},aRefer: {display: "none"},bName: {rowspan: "2"},bRefer: {rowspan: "2"}}}, 
	{id : "3",aName : "师资队伍与资源",aRefer : "23",bName : "师生情况",bRefer : "4",cName : "专职教师总数",cRefer : "2",cValue : "",
	attr:  {aName: {display: "none"},aRefer: {display: "none"},bName: {display: "none"},bRefer: {display: "none"}}}, 
	{id : "4",aName : "师资队伍与资源",aRefer : "23",bName : "学科资源",bRefer : "7",cName : "重点学科数",cRefer : "4",cValue : "",
	attr: {	aName: {display: "none"},aRefer: {display: "none"},bName: {rowspan: "2"},bRefer: {rowspan: "2"}}}, 
	{id : "5",aName : "师资队伍与资源",aRefer : "23",bName : "学科资源",bRefer : "7",cName : "重点实验室、基地、中心数",cRefer : "3",cValue : "",
	attr:  {aName: {display: "none"},aRefer: {display: "none"},bName: {display: "none"},bRefer: {display: "none"}}}, 
	
	
	
	
	
	{id : "6",aName : "科学研究水平",aRefer : "31",bName : "学术论文",bRefer : "11",cName : "国内论文他引次数和",cRefer : "2",cValue : "",
	attr:  {aName: {rowspan: "10"},aRefer: {rowspan: "10"},bName: {rowspan: "5"},bRefer: {rowspan: "5"}}}, 
	{id : "7",aName : "科学研究水平",
						aRefer : "31",
						// aValue : "",
						bName : "学术论文",
						bRefer : "11",
						// bValue : "",
						cName : "国外论文他引次数和",
						cRefer : "1.5",
						cValue : "",
						attr:  {aName: {display: "none"},
							aRefer: {display: "none"},
							bName: {display: "none"},
							bRefer: {display: "none"}
							}
					}, {
						id : "8",
						aName : "科学研究水平",
						aRefer : "31",
						// aValue : "",
						bName : "学术论文",
						bRefer : "11",
						// bValue : "",
						cName : "ESI高被引论文及SCIENCE、NATURE论文数",
						cRefer : "0.5",
						cValue : "",
						attr:  {aName: {display: "none"},
							aRefer: {display: "none"},
							bName: {display: "none"},
							bRefer: {display: "none"}
							}
					}, {
						id : "9",
						aName : "科学研究水平",
						aRefer : "31",
						// aValue : "",
						bName : "学术论文",
						bRefer : "11",
						// bValue : "",
						cName : "高水平学术论文",
						cRefer : "4",
						cValue : "",
						attr:  {aName: {display: "none"},
							aRefer: {display: "none"},
							bName: {display: "none"},
							bRefer: {display: "none"}
							}
					}, {
						id : "10",
						aName : "科学研究水平",
						aRefer : "31",
						// aValue : "",
						bName : "学术论文",
						bRefer : "11",
						// bValue : "",
						cName : "人均发表论文数",
						cRefer : "3",
						cValue : "",
						attr:  {aName: {display: "none"},
							aRefer: {display: "none"},
							bName: {display: "none"},
							bRefer: {display: "none"}
							}
					}, {
						id : "11",
						aName : "科学研究水平",
						aRefer : "31",
						// aValue : "",
						bName : "专著",
						bRefer : "4",
						// bValue : "",
						cName : "出版学术专著数",
						cRefer : "4",
						cValue : "",
						attr:  {aName: {display: "none"},
							aRefer: {display: "none"},
							bName: {rowspan: "1"},
							bRefer: {rowspan: "1"}
							}
					}, {
						id : "12",
						aName : "科学研究水平",
						aRefer : "31",
						// aValue : "",
						bName : "科研项目",
						bRefer : "7",
						// bValue : "",
						cName : "国家级科研项目数",
						cRefer : "1.5",
						cValue : "",
						attr:  {aName: {display: "none"},
							aRefer: {display: "none"},
							bName: {rowspan: "3"},
							bRefer: {rowspan: "3"}
							}
					}, {
						id : "13",
						aName : "科学研究水平",
						aRefer : "31",
						// aValue : "",
						bName : "科研项目",
						bRefer : "7",
						// bValue : "",
						cName : "国家级科研项目经费",
						cRefer : "3",
						cValue : "",
						attr:  {aName: {display: "none"},
							aRefer: {display: "none"},
							bName: {display: "none"},
							bRefer: {display: "none"}
							}
					}, {
						id : "14",
						aName : "科学研究水平",
						aRefer : "31",
						// aValue : "",
						bName : "科研项目",
						bRefer : "7",
						// bValue : "",
						cName : "人均科研总经费",
						cRefer : "2.5",
						cValue : "",
						attr:  {aName: {display: "none"},
							aRefer: {display: "none"},
							bName: {display: "none"},
							bRefer: {display: "none"}
							}
					}, {
						id : "15",
						aName : "科学研究水平",
						aRefer : "31",
						// aValue : "",
						bName : "科研获奖",
						bRefer : "9",
						// bValue : "",
						cName : "国家与省部级科研获奖数",
						cRefer : "9",
						cValue : "",
						attr:  {aName: {display: "none"},
							aRefer: {display: "none"},
							bName: {rowspan: "1"},
							bRefer: {rowspan: "1"}
							}
					}, {
						id : "16",
						aName : "人才培养质量",
						aRefer : "26",
						// aValue : "",
						bName : "学位论文质量",
						bRefer : "7",
						// bValue : "",
						cName : "全国优博论文数",
						cRefer : "3.5",
						cValue : "",
						attr:  {aName: {rowspan: "8"},
							aRefer: {rowspan: "8"},
							bName: {rowspan: "2"},
							bRefer: {rowspan: "2"}
							}
					}, {
						id : "17",
						aName : "人才培养质量",
						aRefer : "26",
						// aValue : "",
						bName : "学位论文质量",
						bRefer : "7",
						// bValue : "",
						cName : "博士学位论文抽检情况",
						cRefer : "3.5",
						cValue : "",
						attr:  {aName: {display: "none"},
							aRefer: {display: "none"},
							bName: {display: "none"},
							bRefer: {display: "none"}
							}
					}, {
						id : "18",
						aName : "人才培养质量",
						aRefer : "26",
						// aValue : "",
						bName : "学生国际交流",
						bRefer : "5",
						// bValue : "",
						cName : "学生境外交流人数",
						cRefer : "2.5",
						cValue : "",
						attr:  {aName: {display: "none"},
							aRefer: {display: "none"},
							bName: {rowspan: "2"},
							bRefer: {rowspan: "2"}
							}
					}, {
						id : "19",
						aName : "人才培养质量",
						aRefer : "26",
						// aValue : "",
						bName : "学生国际交流",
						bRefer : "5",
						// bValue : "",
						cName : "授予境外学生学位数",
						cRefer : "2.5",
						cValue : "",
						attr:  {aName: {display: "none"},
							aRefer: {display: "none"},
							bName: {display: "none"},
							bRefer: {display: "none"}
							}
					}, {
						id : "20",
						aName : "人才培养质量",
						aRefer : "26",
						// aValue : "",
						bName : "授予学位数",
						bRefer : "4",
						// bValue : "",
						cName : "授予博士/硕士学位数",
						cRefer : "4",
						cValue : "",
						attr:  {aName: {display: "none"},
							aRefer: {display: "none"},
							bName: {rowspan: "1"},
							bRefer: {rowspan: "1"}
							}
					}, {
						id : "21",
						aName : "人才培养质量",
						aRefer : "26",
						// aValue : "",
						bName : "教学与教材",
						bRefer : "5",
						// bValue : "",
						cName : "国家与省部级教学成果奖数",
						cRefer : "2.5",
						cValue : "",
						attr:  {aName: {display: "none"},
							aRefer: {display: "none"},
							bName: {rowspan: "2"},
							bRefer: {rowspan: "2"}
							}
					}, {
						id : "22",
						aName : "人才培养质量",
						aRefer : "26",
						// aValue : "",
						bName : "教学与教材",
						bRefer : "5",
						// bValue : "",
						cName : "国家级规划教材与精品教材数",
						cRefer : "2.5",
						cValue : "",
						attr:  {aName: {display: "none"},
							aRefer: {display: "none"},
							bName: {display: "none"},
							bRefer: {display: "none"}
							}
					}, {
						id : "23",
						aName : "人才培养质量",
						aRefer : "26",
						// aValue : "",
						bName : "优秀学生",
						bRefer : "5",
						// bValue : "",
						cName : "优秀在校生与毕业生",
						cRefer : "5",
						cValue : "",
						attr:  {aName: {display: "none"},
							aRefer: {display: "none"},
							bName: {rowspan: "1"},
							bRefer: {rowspan: "1"}
							}
					}, {
						id : "24",
						aName : "学科声誉",
						aRefer : "20",
						// aValue : "",
						bName : "学科声誉",
						bRefer : "20",
						// bValue : "",
						cName : "学科声誉",
						cRefer : "20",
						cValue : "",
						attr:  {aName: {rowspan: "1"},
							aRefer: {rowspan: "1"},
							bName: {rowspan: "1"},
							bRefer: {rowspan: "1"}
							}
					} ],
				    arrtSetting = function (rowId, val, rawObject, cm) {
				        var attr = rawObject.attr[cm.name], result;
				        if (attr.rowspan) {
				            result = ' rowspan=' + '"' + attr.rowspan + '"';
				        } else if (attr.display) {
				            result = ' style="display:' + attr.display + '"';
				        }
				        return result;
				    };
				    
				    var mydata2 = [
									{
										id : "1",
										indiType : "基础学科",
										indiName : "两院院士",
										refer : "10",
										value : "10"
									},
									{
										id : "2",
										indiType : "基础学科",
										indiName : "国家最高科技奖获得者、图灵奖获得者",
										refer : "15",
										value : "15"
									},
									{
										id : "3",
										indiType : "基础学科",
										indiName : "千人计划、军队领军人才、长江特聘、长江讲座、国家杰青、973首席、国家教学名师",
										refer : "5",
										value : "5"
									},
									{
										id : "4",
										indiType : "基础学科",
										indiName : "马工程首席专家、中宣部四个一批人才、学科评议组成员",
										refer : "5",
										value : "5"
									},
									{
										id : "5",
										indiType : "基础学科",
										indiName : "百千万人才工程国家级人选、高校青年教师奖获得者",
										refer : "4",
										value : "4"
									},
									{
										id : "6",
										indiType : "基础学科",
										indiName : "教育部跨世纪人才、中科院百人计划",
										refer : "3",
										value : "3"
									},
									{
										id : "7",
										indiType : "基础学科",
										indiName : "国家自然基金委创新群体",
										refer : "8",
										value : "1"
									},
									{
										id : "8",
										indiType : "基础学科",
										indiName : "教育部新世纪人才",
										refer : "1",
										value : "1"
									},
									{
										id : "9",
										indiType : "基础学科",
										indiName : "教育部创新团队",
										refer : "4",
										value : "4"
									},
									{
										id : "10",
										indiType : "艺术类学科",
										indiName : "中国音乐家、舞蹈家等协会主席/理事长",
										refer : "10",
										value : "10"
									},
									{
										id : "11",
										indiType : "艺术类学科",
										indiName : "中国音乐家、舞蹈家等协会副主席及所属艺委会主任",
										refer : "15",
										value : "15"
									},
									{
										id : "12",
										indiType : "艺术类学科",
										indiName : "艺术硕士专业学位教指委委员",
										refer : "5",
										value : "5"
									},
									{
										id : "13",
										indiType : "艺术类学科",
										indiName : "中国音乐家、舞蹈家等协会所属艺委会副主任",
										refer : "5",
										value : "5"
									},
									{
										id : "14",
										indiType : "体育类学科",
										indiName : "国际健将、国际比赛冠军教练",
										refer : "10",
										value : "10"
									},
									{
										id : "15",
										indiType : "体育类学科",
										indiName : "运动健将、国家级比赛冠军教练",
										refer : "15",
										value : "15"
									},
									{
										id : "16",
										indiType : "法学类学科",
										indiName : "十大杰出青年法学家",
										refer : "10",
										value : "10"
									},
									{
										id : "17",
										indiType : "计算机类学科",
										indiName : "ACM Fellow、IEEE Fellow",
										refer : "10",
										value : "10"
									},

									{
										id : "18",
										indiType : "重点实验室、基地、中心",
										indiName : "国家实验室",
										refer : "10",
										value : "10"
									},
									{
										id : "19",
										indiType : "重点实验室、基地、中心",
										indiName : "国家重点实验室",
										refer : "15",
										value : "15"
									},
									{
										id : "20",
										indiType : "重点实验室、基地、中心",
										indiName : "国家重大基础设施、国家工程技术研究中心、国家工程研究中心、国防科技重点实验室、国家重点实验室（筹）、省部共建国家重点实验室",
										refer : "5",
										value : "5"
									},
									{
										id : "21",
										indiType : "重点实验室、基地、中心",
										indiName : "教育部重点实验室、教育部人文社科基地",
										refer : "5",
										value : "5"
									},
									{
										id : "22",
										indiType : "重点实验室、基地、中心",
										indiName : "其他省部级重点实验室/基地/中心",
										refer : "4",
										value : "4"
									},

									{
										id : "23",
										indiType : "建筑学类学科",
										indiName : "国际建协专业奖（含：城市规划奖、建筑技术奖、建筑教育奖、改善人居质量奖）",
										refer : "10",
										value : "10"
									}, {
										id : "24",
										indiType : "建筑学类学科",
										indiName : "世界人居奖、联合国人居环境奖",
										refer : "15",
										value : "15"
									}, {
										id : "25",
										indiType : "建筑学类学科",
										indiName : "阿卡汗奖",
										refer : "5",
										value : "5"
									}, {
										id : "26",
										indiType : "建筑学类学科",
										indiName : "亚洲建协奖",
										refer : "5",
										value : "5"
									}, {
										id : "27",
										indiType : "建筑学类学科",
										indiName : "联合国教科文组织亚太地区文化保护奖",
										refer : "4",
										value : "4"
									}, {
										id : "28",
										indiType : "建筑学类学科",
										indiName : "国际照明设计奖项",
										refer : "10",
										value : "10"
									}, {
										id : "29",
										indiType : "建筑学类学科",
										indiName : "亚澳地区建筑遗产保护奖",
										refer : "15",
										value : "15"
									}, {
										id : "30",
										indiType : "建筑学类学科",
										indiName : "IFLA亚太地区年度奖、ASLA年度奖",
										refer : "5",
										value : "5"
									}, {
										id : "31",
										indiType : "建筑学类学科",
										indiName : "BALI英国国家景观奖年度奖、意大利托萨罗伦佐国际风景园林奖",
										refer : "5",
										value : "5"
									}, {
										id : "32",
										indiType : "建筑学类学科",
										indiName : "中国建筑学会建筑教育奖、建筑创作奖",
										refer : "4",
										value : "4"
									}, {
										id : "33",
										indiType : "建筑学类学科",
										indiName : "全国优秀勘察设计奖，全国优秀城乡规划设计奖",
										refer : "4",
										value : "4"
									}, {
										id : "34",
										indiType : "建筑学类学科",
										indiName : "国家文物局文物科技进步奖、优秀文物保护工程奖",
										refer : "10",
										value : "10"
									}, {
										id : "35",
										indiType : "建筑学类学科",
										indiName : "全国绿色建筑创新奖",
										refer : "15",
										value : "15"
									}, {
										id : "36",
										indiType : "建筑学类学科",
										indiName : "优秀风景园林规划设计奖",
										refer : "5",
										value : "5"
									} ];
					
				    createWeight();
					createConvert();
					
					function createWeight(){
						$("#weight_list").jqGrid(
								{
									datatype : "local",
									data : mydata1,
									colNames : ['指标名称', '参考值',
									// '建议值',
									'指标名称', '参考值',
									// '建议值',
									'指标名称', '参考值', '建议值' ],
									colModel : [ {
										name : 'aName',
										index : 'aName',
										width : 100,
										align : "center",
										cellattr : arrtSetting
									}, {
										name : 'aRefer',
										index : 'aRefer',
										width : 100,
										align : "center",
										cellattr : arrtSetting
									/*
									 * }, { name : 'aValue', index : 'aValue', width :
									 * 100
									 */
									}, {
										name : 'bName',
										index : 'bName',
										width : 100,
										align : "center",
										cellattr : arrtSetting
									}, {
										name : 'bRefer',
										index : 'bRefer',
										width : 100,
										align : "center",
										cellattr : arrtSetting
									/*
									 * }, { name : 'bValue', index : 'bValue', width :
									 * 100
									 */
									}, {
										name : 'cName',
										index : 'cName',
										width : 300,
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
										editable : true,
										align : "center"
									} ],
									cmTemplate : {
										sortable : false
									},
									height : '100%',
									autowidth : true,
									rowNum : 30,
									// rowList : [ 10, 20, 30 ],
									viewrecords : true,
									sortorder : "desc",
									// pager : "#pager",
									caption : "指标体系评价-指标比重",
									rownumbers: true,
									
									gridComplete : function() {
										var ids = jQuery("#weight_list").jqGrid(
												'getDataIDs');
										var input;
										for ( var i = 0; i < ids.length; i++) {
											input = "<input type='text' />";
											jQuery("#weight_list").jqGrid(
													'setRowData', ids[i], {
														cValue : input
													});
										}
									}
								});

						// 合并表头
						$("#weight_list").jqGrid('setGroupHeaders', {
							useColSpanStyle : true, // 没有表头的列是否与表头列位置的空单元格合并
							groupHeaders : [ {
								startColumnName : 'aName', // 对应colModel中的name
								numberOfColumns : 2, // 跨越的列数
								titleText : '一级指标'
							}, {
								startColumnName : 'bName', // 对应colModel中的name
								numberOfColumns : 2, // 跨越的列数
								titleText : '二级指标'
							}, {
								startColumnName : 'cName', // 对应colModel中的name
								numberOfColumns : 3, // 跨越的列数
								titleText : '三级指标（末级指标）'
							}, ]
						});
					};
					function createConvert(){
						$("#convert_list")
						.jqGrid(
								{
									datatype : "local",
									data : mydata2,
									colNames : [ '序号', '指标种类', '指标项',
											'参考值', '建议值' ],
									colModel : [ {
										name : 'id',
										width : 150,
										align : "center",
										sorttype:'number',
										//summaryType:'count', 
										//summaryTpl : '({0}) total'
									}, {
										name : 'indiType',
										index : 'indiType',
										width : 200,
										align : "center"
									}, {
										name : 'indiName',
										index : 'indiName',
										width : 450,
										align : "center"
									}, {
										name : 'refer',
										index : 'refer',
										width : 200,
										align : "center",
										//summaryType:'sum'
									}, {
										name : 'value',
										index : 'value',
										width : 200,
										editable : true,
										align : "center",
										//summaryType:'sum'
									} ],
									cmTemplate : {
										sortable : false
									},
									height : '100%',
									width : tab_width,
									//shrinkToFit : true,
									rowNum : 100,
									rowList : [ 10, 20, 40 ],
									viewrecords : true,
									sortorder : "desc",
									sortName : "id",
									caption : "指标体系评价-数据折算",
									rownumbers: true,		//和grouping冲突，值为false
									
									grouping : true,
									groupingView : {
										groupField : [ 'indiType' ],
										groupColumnShow : [ true ],
										groupText : [ '<b>{0} - {1} 项指标</b>' ],
										groupCollapse : false,
										//groupSummary : [true],
										groupDataSorted : true,// 分组中的数据是否排序
										groupOrder : [ 'asc' ]// 分组后的排序
										// groupCollapse: true,
										// groupOrder:['desc'],
									},
									/*footerrow: true,
								    userDataOnFooter: true,*/

									gridComplete : function() {
										var ids = jQuery("#convert_list")
												.jqGrid('getDataIDs');
										var input;
										for ( var i = 0; i < ids.length; i++) {
											input = "<input type='text' />";
											jQuery("#convert_list").jqGrid(
													'setRowData', ids[i], {
														value : input
													});
										}
									}
								});
					};	
});

