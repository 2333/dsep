var jqgridConfig_global;
/*控制表格是否可以编辑*/
function jqGird_control(edittype) {
	console.log(edittype);
	edittype='editable';
	$('#batchAdd,#batchEdit,#itemAdd,#itemEdit,#itemDel,#itemMulSearch,#batchSubmit').unbind('click');
	if (edittype == 'editable') {
		$("#itemEdit").click(
				function() {
					/*弹出窗口的方式编辑  */
					var gr = jQuery("#jqGrid_tb").jqGrid(
							'getGridParam', 'selrow');
					if (gr != null) {
						jQuery("#jqGrid_tb").jqGrid('editGridRow',
								gr, {
									closeAfterEdit : true,
									width : 500,
									top : 100,
									left : 300,
									reloadAfterSubmit : true,
									beforeShowForm : pickdate,
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
		$('#itemAdd').click(function(id) {
			jQuery("#jqGrid_tb").jqGrid('editGridRow', "new", {
				closeAfterAdd : true,
				width : 500,
				top : 100,
				left : 300,
				reloadAfterSubmit : true,
				beforeShowForm : pickdate,
				afterComplete : function(a, data, c) {
					if (a.responseText == 'success')
						alert('添加成功！');
					else
						alert('添加失败！');

				}
			});

		});
		$('#itemDel').click(
						function() {
							var gr = jQuery("#jqGrid_tb").jqGrid(
									'getGridParam', 'selrow');
							if (gr != null) {
								var rowData = $("#jqGrid_tb")
										.jqGrid("getRowData", gr);
								var index = 'ROW_ID';
								var itemId = rowData[index];
								//alert(itemId);
								jQuery("#jqGrid_tb")
										.jqGrid(
												'delGridRow',
												gr,
												{
													top : 100,
													left : 300,
													url : requestUrl+'/Collection/collectionDel?tableId='
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
		$('#itemMulSearch').click(function(){
    		jQuery("#jqGrid_tb").jqGrid('searchGrid',{multipleSearch: true,multipleGroup:true,closeAfterSearch:true});
    		
    	});
		$('#batchAdd').click(function(){
			batch_Add_Dialog();
		});
		$("#batchEdit").click(function(){
			batchEdit();
		});
		$("#batchSubmit").click(function(){
			batchSubmit();
		});
	} else {
		$('#itemAdd,#itemEdit,#itemDel,#itemMulSearch,#batchAdd,#batchEdit,#batchSubmit')
				.click(function(event) {
					alert('您无权对数据进行此项操作！');
					event.preventDefault();
				});
	}
}
/*jqgrid生成函数以及渲染数据*/
function myAjaxForJqgrid(table) {
	var commonUrl=requestUrl+"/Collection/initJqgrid?tableId="+ table;
	commonAjaxRequest(commonUrl,'json',renderJqGrid,error_function);
}
/*jqgrid生成函数以及渲染数据*/
function myAjaxForJqgrid_modify(table) {
	var commonUrl=requestUrl+"/Collection/initJqgrid?tableId="+ table;
	commonAjaxRequest(commonUrl,'json',renderSelect_jqgrid,error_function);
}
/*渲染选择表单和数据表单*/
function renderSelect_jqgrid(data)
{   
	jqgridConfig_global=data;
	dynamic_create_select_bar(data);
	renderJqGrid(data);
}
/*渲染动态选择表单*/
function dynamic_create_select_bar(data)
{
	columnIds= new Array();
	$.each(data.colConfigs,function(i,item){
		columnIds.push(item['name']);
		$("#select_bar").prepend(createTR(item[name],item['label']));
	});
	
}
function createTR(id,name)
{
	var tr="<tr>" +
				"<td>" +
					"<span class=\"TextFont\">" +name+":"+
					"</span>" +"<input type=\"text\" id=" +id+
							">"+
				"</td>" +
			"</tr>";
	return tr;

}
function numbercheck(value, colname) {
	 if (value < 0||value >20) 
         return [false,"请输入0至20之间的数字"];
      else 
         return [true,""];
    }
function ValidateIdCard(value,colname){
	  var aCity = { 11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江 ", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北 ", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏 ", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外 " };
	        var iSum = 0;
	        //if (!/^d{17}(d|x)$/i.test(value)) return 'Error:非法证号';
	        value = value.replace(/x$/i, "a");
	        if (aCity[parseInt(value.substr(0, 2))] == null) 
	        	return [false,"非法地区"];
	        var sBirthday = value.substr(6, 4) + "-" + Number(value.substr(10, 2)) + "-" + Number(value.substr(12, 2));
	        var d = new Date(sBirthday.replace(/-/g, "/"));
	        if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate())) 
	        	return [false,"非法生日"];
	        for (var i = 17; i >= 0; i--) 
	        	iSum += (Math.pow(2, i) % 11) * parseInt(value.charAt(17 - i), 11);
	        if (iSum % 11 != 1) return [false,"非法证号"];
	        else
	        	return [true,""];
	}
function ValidateStrLength(value,colname)
{
	 var l = 0;
	 var a = value.split("");
	 for (var i=0;i<a.length;i++) {
	  if (a[i].charCodeAt(0)<299) {
	   l++;} 
	  else {
	   l+=2;}}
	 if(l<5) return[false,"字符串长度不能小于5"];
	 else return[true,""];
}
/*渲染jqgrid*/
function renderJqGrid(data)
{  
	jqgridConfig_global=data;
	for(var i=0;i<data.colConfigs.length;i++)
	  {
		if((data.colConfigs[i].editrules.custom)&&(data.colConfigs[i].editrules.custom_func=="numbercheck"))
			data.colConfigs[i].editrules.custom_func=numbercheck;
		else if((data.colConfigs[i].editrules.custom)&&(data.colConfigs[i].editrules.custom_func=="ValidateIdCard"))
			data.colConfigs[i].editrules.custom_func=ValidateIdCard;
		else if((data.colConfigs[i].editrules.custom)&&(data.colConfigs[i].editrules.custom_func=="ValidateStrLength"))
			data.colConfigs[i].editrules.custom_func=ValidateStrLength;
		else if(data.colConfigs[i].editoptions.dataInit=='loadAutoComplete')
			data.colConfigs[i].editoptions.dataInit=loadAutoComplete;
	  }
	var collegeId = $("#college").val();
	var disciplineId = $("#dsep").val();
	$("#discipline_info").show();
	$("#jqGrid_tb").jqGrid('GridUnload');
	tableId = data.tableId;
	titleValues = data.titleValues;
	collectionDateCols = new Array();
	collectionDateCols = data.dateColIds;
	$("#jqGrid_tb").jqGrid(
					{
						url : requestUrl+'/Collection/collectionData/'
								+ tableId
								+ '/'
								+ titleValues
								+ '/'
								+ collegeId
								+ '/'
								+ disciplineId,
						editurl : requestUrl+'/Collection/collectionEdit/'
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
						pager : '#pager_tb',
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
							$("#jqGrid_tb").setGridWidth(
									$("#content").width());
						},
						prmNames : {
							page : "page",
							rows : "rows",
							sort : "sidx",
							order : "sord"
						},
						onSelectRow:function(rowid){
							if($.inArray(rowid,selectedRowIds)==-1)
								selectedRowIds.push(rowid);	
							jQuery('#jqGrid_tb').jqGrid('editRow',rowid,false,original_pickdates);
						},
						gridComplete: function(){
							
						}
					}).navGrid('#pager_tb', {
				edit : false,
				add : false,
				del : false,search:false
			});
}
//批量添加
function batch_Add_Dialog()
{
	var initbatchDataUrl=requestUrl+"/Collection/initBatchData?tableId="+ tableId;
	commonAjaxRequest(initbatchDataUrl,'json',function(data){initBatchData=data},error_function);
	var commonUrl=requestUrl+"/Collection/initJqgrid?tableId="+ tableId;
	commonAjaxRequest(commonUrl,'json',batch_Dialog,error_function);
	
}
/*批量添加的对话框*/
function batch_Dialog(data)
{
	  	  initBatchJqgrid(data);
	 	  $('#batch_add_dv').dialog({
	  		    title:"批量添加",
	  		    height:'500',
	  			width:'80%',
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  		    hide:'fade',
	  			show:'fade',
	  		    autoOpen:true,
	  		    buttons:{  
	  		    	"提交":function(){ 
	  		    		$("#batch_add_dv").dialog("close");
	  	                },
	 	  
	 	            "取消":function(){
	 	            	$("#batch_add_dv").dialog("close"); 
	 	            }
	  		    }}); 
	 
}
function initBatchJqgrid(data)
{
	var lastsel3;//选择行
	$("#batch_jqgrid_tb").jqGrid('GridUnload');
	$("#batch_jqgrid_tb").jqGrid({
        datatype: 'local',
		colModel:data.colConfigs,
		height:"100%",
		autowidth : true,
		shrinkToFit : false,
		rowNum:10,
		rowList:[10,20,30],
		viewrecords: true,
		sortorder: "desc",
		caption: "批量添加",
	    jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
            root: "rows",  //包含实际数据的数组  
            page: "pageIndex",  //当前页  
            total: "totalPage",//总页数  
            records:"totalCount", //查询出的记录数  
            repeatitems : false,      
		},
	}).navGrid('#batch_jqgrid_tb',{edit:false,add:false,del:false});	
}
/*页面整体提交*/
function batchSubmit()
{   
	if(selectedRowIds.length>0){
	var ids = jQuery("#jqGrid_tb").jqGrid('getDataIDs');
	for(var i=0;i < ids.length;i++){
			jQuery('#jqGrid_tb').jqGrid('saveRow',ids[i]);
	}
	/*var dataString='{\"'+name+'\":2}';*/
	var dataString='{"colsNum":'+selectedRowIds.length+',\"titleValues\":[';
	for(var i=0;i<titleValues.length;i++)
	{
		if(i!=titleValues.length-1)
			dataString+='\"'+titleValues[i]+'\",';
		else
			dataString+='\"'+titleValues[i]+'\"],';
	}
	dataString +='\"rows\":[';
	var rowData = $("#jqGrid_tb").jqGrid("getRowData");  
	    if (rowData.length < 1) { alert("没有数据！"); return; }  
	    for (var i = 0; i < selectedRowIds.length; i++) { 
	    	dataString +='{';
	    	$.each(titleValues,function(j,item){
	    		if(j==(titleValues.length-1))
	    			dataString +='\"'+item+'\":\"'+$.trim(rowData[selectedRowIds[i]-1][item])+'\"';
	    		else
	    			dataString+='\"'+item+'\":\"'+$.trim(rowData[selectedRowIds[i]-1][item])+'\",';
	    		});
	    	if(i==(selectedRowIds.length-1))
	    		dataString+='}';
	    	else
	    		dataString+='},';
	    }
	    dataString+=']}';
		selectedRowIds=new Array();
		//var jsonString=JSON.parse(dataString);//把json字符串封装成json对象
		var myurl=requestUrl+"/Collection/batchSubmit";
		$.post(myurl,{params:dataString},
				function(result){
					if(result=='success'){
						alert('操作成功！');
					}else
						{
							alert('操作失败！');
						}
		});
	}else
		{
			alert('您没有修改任何数据！');
		}
}
/*在原来页面编辑和添加的时间控件*/
function original_pickdates(id){
/*	 $.datepicker.regional['zh-CN'] = {  
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
  $.datepicker.setDefaults($.datepicker.regional['zh-CN']);*/
  $.each(collectionDateCols,function(i,item){
	   jQuery("#"+id+"_"+item,"#jqGrid_tb").datepicker({
		   dateFormat: 'yy-mm-dd',  //日期格式，自己设置              buttonImage: 'calendar.gif',  //按钮的图片路径，自己设置              buttonImageOnly: true,  //Show an image trigger without any button.             showOn: 'both',//触发条件，both表示点击文本域和图片按钮都生效         yearRange: '1990:2008',//年份范围          clearText:'清除',//下面的就不用详细写注释了吧，呵呵，都是些文本设置         closeText:'关闭',         prevText:'前一月',         nextText:'后一月',         currentText:' ',          monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],  
	   });
  });	
}
/*jqgrid的自动提示*/
function loadAutoComplete(elem)
{		
		var elem_id=$(elem).attr('id');
		var colname=elem_id.split('_')[1];
		var selectSource=new Array();
		for(var i=0;i<jqgridConfig_global.colConfigs.length;i++)
		{	
			
			if(jqgridConfig_global.colConfigs[i].name==colname){
				var values=jqgridConfig_global.colConfigs[i].editoptions.value;
				$.each(values,function(i,item){
					var tmp=new Array();
					tmp['label']=item;
					tmp['value']=i;
					selectSource.push(item);
				});
			}
				
		}
		console.log(selectSource);
		$(elem).focus().autocomplete({
			source:selectSource,
			minLength:0,
			select:function(event,ui){
				$(elem).val(ui.item.label);
			}
		}).click(function () {
			$("[id^=ui-id-]").css('z-index',10000);
			  // 点击的时候进行查找
			  $(this).val('');
			  $(this).autocomplete('search','');
			});
		
}