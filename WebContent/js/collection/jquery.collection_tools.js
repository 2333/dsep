;(function($){
	$.extend({
		"collection_tools":{
			/*!日期控件的默认参数
			*colId:是日期列的Id
			*/
			"default_pickdate_config":{
				colIds:{},
			},
			/*日期控件的函数*/
			"pickdate":function(){
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
				dateFormat: 'yy-mm-dd',  
				firstDay: 1,  
				initStatus: '请选择日期',  
				isRTL: false  
				};
			$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
			$.each($.collection_tools.default_pickdate_config, function (n, value) {	  	
				$('#'+value).datepicker({changeMonth:true,changeYear:true});
			}); 
			
			},
			/*自动补全的数据源
			*每一个source中包含colname(列名)、
			*和source(数据源)
			*/
			"default_accomplete_sources":{},
			/*自动不全函数*/
			"autoLoadcompelet":function(elem)
			{
				var elem_id=$(elem).attr('id');//元素ID
				var colname=elem_id.split('_')[1];//元素name
				var selectSource=new Array();//初始化数据源
				for(var i=0;i<collection_tools.default_accomplete_sources.length;i++)
				{	
			
					if($.collection_tools.default_accomplete_sources[i].colname==colname){
					var values=$.collection_tools.default_accomplete_sources[i].source;
						$.each(values,function(i,item){
							var tmp=new Array();
							tmp['label']=item;
							tmp['value']=i;
							selectSource.push(item);
						});
					}
				
				}
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
		
			},
		},
	})
})(jQuery);