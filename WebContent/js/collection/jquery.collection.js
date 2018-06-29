;(function($){
	$.extend({
		/*数据采集的通用方法*/
		"collection":{
			/*！通用的请求的默认参数
			*async:是否同步
			*cache:是否使用缓存
			*type:请求类型
			*dataType:返回的数据类型(text\json)
			*url:请求路径
			*success:请求成功的回调函数
			*error:求情失败的回调函数
			*/
			"default_commonAjaxRequest_config":{
				async:false,
				cache:false,
				type:'POST',
				url:'/',
				dataType:'text',
				success:function(data){
					return $.success_func(data);
				},
				error:function(){
					return $.error_func();
				}
			},
			/*通用的请求函数*/
			"commonAjaxRequest":function(config){
				console.log(config);
				var new_config=$.extend({},$.collection.default_commonAjaxRequest_config,config);
				console.log(new_config);
				$.ajax(new_config);
			},
			/*!数据从学科提交至学校
			*collegeId:学校代码
			*disciplineId学科代码
			*/
			"default_submit2college_config":{
				collegeId:'',
				disciplineId:'',
			},
			/*学科提交至学校的函数*/
			"submit2college":function(config){
				var new_config=$.extend({},$.collection.default_submit2college_config,config);
				if(new_config.collegeId!=''&&new_config.disciplineId!=''){
					var request_config={
						url:'/DSEP/FlowActions/discip2College/'+new_config.collegeId+'/'+new_config.disciplineId,
					};
					$.collection.commonAjaxRequest(request_config);
				}
			},
			/*!设置select选项
			*elemmentId:元素ID
			*url:请求路径
			*dataType:返回数据类型
			*init_value:第一个键
			*init_title:第一个显示的数据
			*selected_value:选中的key
			*/
			"default_setSellectOptions_config":{
				elementId:'',
				url:'#',
				dataType:'json',
				init_value:'all',
				init_title:'全部',
				selected_value:'###',
			},
			/*设置select的初始数据*/
			"setSelectOptions":function(config)
			{
				var new_config=$.extend({},$.collection.default_setSellectOptions_config,config);
				if(new_config.elementId!=''&&new_config.url!='#')
				{
					var request_config={
						url:new_config.url,
						dataType:new_config.dataType,
						success:function(data)
						{
							$('#'+new_config.elementId).empty();
							$('#'+new_config.elementId).append('<option value=all>' + new_config.init_title + '</option>');
							$.each(data, function(i, item) {
								if (new_connfig.init_value == i)
									$('#'+new_config.elementId).append('<option value="'+i+'" selected=selected>'+ item + '</option>');
								else
									$('#'+new_config.elementId).append('<option value="'+i+'">' + item+ '</option>');
							});
						},
					};
					$.collection.commonAjaxRequest(request_config);
				}
				
			},
			/*设置学科数据的状态默认值
			*collegeId:学校ID
			*disciplineId:学科ID
			*status_elementId:状态标签的Id
			*/
			"default_setDisciplineStatus_config":{
				status_elementId:'',
				collegeId:'',
				disciplineId:'',
			},
			/*设置学科数据状态*/
			"setDisciplineStatus":function(config){
				var new_config=$.extend({},$.collection.default_setDisciplineStatus,config);
				if(new_config.collegeId!=''&&new_config.disciplineId!=''&&status_elementId!='')
				{
					var request_config={
						url:'/DSEP/Collection/getstatus/' + new_config.collegeId+ '/' + new_config.disciplineId,
						success:function(data){
							var status;
							console.log(data);
							switch (data) {
								case "0":
									status = "学科正在修改";
									break;
								case '1':
									status = "已提交至学校";
									break;
								case "2":
									status = "已提交至学位中心";
									break;
								case "3":
									status = "退回至学科";
									break;
								case "4":
									status = "退回至学校";
									break;
								case "5":
									status = "提交终止";
									break;
							}
							$("#"+status_elementId).html(status);
						}
					};
					$.collection.commonAjaxRequest(request_config);
				}
			}
			
		},
		
		
		"success_func":function(data)
		{
			if(data=='success')
				alert('操作成功！');
			else
				alert('操作失败！');
		},
		"error_func":function()
		{
			alert('操作错误！');
		},
		"fail_func":function()
		{
			alert('操作失败！');
		}
		
	});
})(jQuery);