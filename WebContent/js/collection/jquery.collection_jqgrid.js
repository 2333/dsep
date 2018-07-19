;(function($){
	$.extend({
			"collection_jqgrid":{
				"test":function()
				{
					console.log('001');
				},
				/*！初始化jqgrid的默认配置
				*tableId:请求表的Id
				*jqgirdId:jqgrid表格Id
				*collegeId:学校ID
				*disciplineId:学科ID
				*pagerId:页脚id
				*parentId:父容器id
				*/
				"default_initJqgrid_config":{
					tableId:'',
					jqgridId:'',
					collegeId:'',
					disciplineId:'',
					pagerId:'',
					parentId:'',
				},
				/*初始化jqgrid的函数*/
				"initJqgrid":function(config){
					var new_config=$.extend({},$.collection_jqgrid.default_initJqgrid_config,config);
					var request_config={
						url:'/DSEP'+'/Collection/testInitJqgrid?tableId='+ new_config.tableId,
						dataType:'json',
						success:function(data){
							//jqgridConfig_global=data;设置全局变量
							console.log('---------data--------------');
							for(var i=0;i<data.colConfigs.length;i++)
							{
								if((data.colConfigs[i].editrules.custom)&&(data.colConfigs[i].editrules.custom_func=="numbercheck"))
									data.colConfigs[i].editrules.custom_func=$.collection_validate.numbercheck;
								else if((data.colConfigs[i].editrules.custom)&&(data.colConfigs[i].editrules.custom_func=="ValidateIdCard"))
									data.colConfigs[i].editrules.custom_func=$.collection_validate.validateIdCard;
								else if((data.colConfigs[i].editrules.custom)&&(data.colConfigs[i].editrules.custom_func=="ValidateStrLength"))
									data.colConfigs[i].editrules.custom_func=$.collection_validate.validateStrLength;
								else if(data.colConfigs[i].editoptions.dataInit=='loadAutoComplete')
									data.colConfigs[i].editoptions.dataInit=$.collection_tools.autoLoadcompelet;
							}
							var collegeId = new_config.collegeId;
							var disciplineId = new_config.disciplineId;
							//$("#discipline_info").show();
							$("#"+new_config.jqgridId).jqGrid('GridUnload');
							var tableId = data.tableId;
							var titleValues = data.titleValues;
							var collectionDateCols = new Array();//日期列，在自动补全的时候还会用
							collectionDateCols = data.dateColIds;
							$("#"+new_config.jqgridId).jqGrid(
							{
								url :'/DSEP/Collection/collectionData/'
									+ tableId
									+ '/'
									+ titleValues
									+ '/'
									+ collegeId
									+ '/'
									+ disciplineId,
								editurl : '/DSEP/Collection/collectionEdit/'
									+ tableId
									+ '/'
									+ titleValues
									+ '/'
									+ collectionDateCols
									+ '/'
									+ collegeId
									+ '/'
									+ disciplineId,
								datatype : 'json',
								mtype : 'POST',
								colModel : data.colConfigs,
								height : "100%",
								autowidth : true,
								shrinkToFit : false,
								pager : '#'+new_comfig.pagerId,
								pgbuttons : true,
								rowNum : 10,
								rowList : [ 10, 20, 30 ],
								viewrecords : true,
								sortname : data.defaultSortName,
								sortorder : "desc",
								caption : data.tableName,
								jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
									root : "rows", //包含实际数据的数组  
									page : "pageIndex", //当前页  
									total : "totalPage",//总页数  
									records : "totalCount", //查询出的记录数  
									repeatitems : false,
								},
								loadComplete : function() {
								$("#"+new_config.jqgridId).setGridWidth(
									$("#"+new_config.parentId).width());
								},
								prmNames : {
									page : "page",
									rows : "rows",
									sort : "sidx",
									order : "sord"
								},
								onSelectRow:function(rowid){
								},
								gridComplete: function(){
								
								}
						}).navGrid('#'+new_config.jqgridId, {
								edit : false,
								add : false,
								del : false,
								search:false
							});
						},
					};
					$.collection.commonAjaxRequest(request_config);
				},
				/*!表格的编辑、添加、删除默认配置
				*buttonId:按钮ID
				*jqgridId:表格Id
				*/
				"default_oprFormItem_config":{
					tableId:'',
					buttonId:'',
					jqgridId:'',
				},
				/*表格编辑函数*/
				"editFormItem":function(config){
					var new_config=$.extend({},$.collection_jqgrid.default_oprFormItem_config,config);
					if(new_config.buttonId!=''&&new_config.jqgridId!='')
					{
						$("#"+new_config.buttonId).click(function() {
							/*弹出窗口的方式编辑  */
							var gr = jQuery("#"+new_config.jqgridId).jqGrid('getGridParam', 'selrow');
							if (gr != null) {
								jQuery("#"+new_config.jqgridId).jqGrid('editGridRow',
								gr, {
									closeAfterEdit : true,
									width : 500,
									top : 100,
									left : 300,
									reloadAfterSubmit : true,
									beforeShowForm : $.collection_tools.pickdate,
									afterComplete : function(a, data, c) {
										if (a.responseText == 'success')
											alert('编辑成功！');
										else
											alert('编辑失败!');
									}
								});
							} else
							alert("请选择条目！");
						});
					
					}
				},
				/*表格的添加操作*/
				"addFormItem":function(config)
				{
					var new_config=$.extend({},$.collection_jqgrid.default_oprFormItem_config,config);
					if(new_config.buttonId!=''&&new_config.jqgridId!=''){
					$('#'+new_config.buttonId).click(function(id) {
						jQuery("#"+new_config.jqgridId).jqGrid('editGridRow', "new", {
							closeAfterAdd : true,
							width : 500,
							top : 100,
							left : 300,
							reloadAfterSubmit : true,
							beforeShowForm : $.collection_tools.pickdate,
							afterComplete : function(a, data, c) {
								if (a.responseText == 'success')
									alert('添加成功！');
								else
									alert('添加失败！');

							}
						});

					});
					}
				},
				/*表格的删除操作*/
				"delFormItem":function(config){
					var new_config=$.extend({},$.collection_jqgrid.default_oprFormItem_config,config);
					if(new_config.tableId!=''&&new_config.buttonId!=''&&new_config.jqgridId!=''){
						$('#'+new_config.buttonId).click(function(){
							var gr = jQuery("#"+new_config.jqgridId).jqGrid('getGridParam', 'selrow');
							if (gr != null) {
								var rowData = $("#"+new_config.jqgridId).jqGrid("getRowData", gr);
								var index = 'ROW_ID';
								var itemId = rowData[index];
								vat tableId=new_config.tableId;
								//alert(itemId);
								jQuery("#"+new_config.jqgridId).jqGrid(
												'delGridRow',
												gr,
												{
													top : 100,
													left : 300,
													url :'/DSEP/Collection/collectionDel?tableId='
															+ tableId
															+ '&itemId='
															+ itemId,
													reloadAfterSubmit : true,
													afterComplete : function(
															a, data, c) {
														if (a.responseText == 'success')
															alert('删除成功！');
														else
															alert('删除失败！');
													}
												});
							} else
								alert("请选择条目！");
						});
					}
				},
				/*表格的查找操作*/
				"searchFormItem":function(config)
				{
					var new_config=$.extend({},$.collection_jqgrid.default_oprFormItem_config,config);
					if(new_config.buttonId!=''&&new_config.jqgridId!=''){
						$('#'+new_config.buttonId).click(function(){
							jQuery("#"+new_config.jqgridId).jqGrid('searchGrid',{multipleSearch: true,multipleGroup:true,closeAfterSearch:true});
    		
						});
					}
				},
				/*阻止对表格进行操作
				*元素Id的数组
				*/
				"default_oprPrevent_config":{
					elementIds:[],
				},
				"oprPrevent":function(config)
				{
					var new_config=$.extend({},default_oprPrevent_config.elementIds,config);
					if(new_config.length!=0)
					{
						$.each(new_config,function(i,elemId){
						$('#'+elemId).click(function(event) {
								alert('您无权对数据进行此项操作！');
							event.preventDefault();
						});
						});
					}
				
				},
				
			},
		
		}
	);
})(jQuery);