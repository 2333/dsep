/*标签页标签模板*/
var tabTemplate = "<li><a href='#{href}'><span class ='#{icon}'></span><span>#{label}</span></a> <span class='ui-icon ui-icon-close' role='presentation'></span></li>",
	  tabindex = 3;

/*添加新的标签页*/
function addTab(tabname, url, icon) {
	  var label = tabname,
		id = "tab" + tabindex,
		li = $( tabTemplate.replace( /#\{href\}/g, "#" + id ).replace( /#\{icon\}/g, icon).replace( /#\{label\}/g, label ) );
	  
	  $.post(url, function(data){
		  tabContentHtml ="<div id='" + id + "'>"+ data +"</div>";
		  $( "#content" ).tabs().find( ".ui-tabs-nav" ).append( li );
		  
		  $( "#content" ).tabs().append( tabContentHtml );
		  $( "#content" ).tabs( "refresh" );
		  
		  var active = $("ul.ui-tabs-nav").find("li").length - 1;//获取将要打开的标签页的索引
		  $( "#content" ).tabs( "option", "active", active);//激活标签页
		  tabindex++;
	  }, 'html');
}

//调整列表后自适应
function tableAdjust(tableid){
	$("#" + tableid).setGridWidth($('#content').width()*0.99);
}


/*根据标签名激活标签*/
function activeTab(a)
{
	if(a.text == undefined || a.url == "#") return;
	var flag = false;
	//如果已经有打开的标签，那么打开
	$("ul.ui-tabs-nav").find("li").each(function(index, elem){
		if(a.text == $(elem).find("a").find('span').last().html())
		{
			$( "#content" ).tabs( "option", "active", index);
			flag = true;
		}
	});
	//如果没有打开，那么新建一个标签页
	var icon = $(a).find('span').attr('class');
	if(!flag)
		addTab(a.text, a.href, icon);
}

/*获取树结构勾选项的id数组*/
function getCheckedArray(treeId)
{
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodes = treeObj.getCheckedNodes(true);
	var idArray = new Array();
	if(nodes)
	{
		for(i = 0; i < nodes.length; i ++)
		{
			idArray.push(nodes[i].id);
		}
	}
	return idArray;
}
/*tree的一些方法*/
/*tree的配置信息*/
var setting = {
		view:{
			showIcon: showIconForTree
		},
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};
/*显示图片*/
function showIconForTree(treeId, treeNode) {
	return !treeNode.isParent;
};

//获取树结构勾选项的id数组
function getCheckedArray(treeId)
{
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodes = treeObj.getCheckedNodes(true);
	var idArray = new Array();
	if(nodes)
	{
		for(i = 0; i < nodes.length; i ++)
		{
			idArray.push(nodes[i].id);
		}
	}
	return idArray;
}
/*日期控件*/
function pickdate(){
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
    $.each(collectionDateCols, function (n, value) {	  	
    		$('#'+value).datepicker({changeMonth:true,changeYear:true});
    }); 
}
/*button的样式*/
function common_Css()
{
	$('#editDialog,#addDialog').css('display', 'none');
	$("input[type=submit], a.button , button").button();
}

//alert对话框
function alert_dialog(alert_string)
{
	$( "div.alert_dialog" ).empty().append("<p>" + alert_string +"</p>");
	$( "div.alert_dialog" ).dialog({
		  position : 'center',
	      height:150,
	      modal:true,
	      buttons: {
	        "确定": function() {
	        	$( this ).dialog( "close" );
	        }
	      }
	});
}

//confirm对话框
function confirm_dialog(confirm_string, callback){
	  $( "div.alert_dialog" ).empty().append("<p>" + confirm_string + "</p>");
	  $( "div.alert_dialog" ).dialog({
	      height:150,
	      modal:true,
	      buttons: {
	        "确定": function() {
	        	$( this ).dialog( "close" );
	        	callback();
	        },
	        "取消": function() {
	            $( this ).dialog( "close" );
	          }
	        }
	  });
}

//CheckBox的选中事件
function checkbox_select(){   
	//将值为True的Checkbox的选中
	$.each($("input[type='checkbox']"), function(index, item){      
		if($(item).attr("value")!= "undefine" && $(item).attr("value") == "true"){
	    	$(item).attr("checked","checked");
	    }else{
	    	$(item).removeAttr("checked");
	    }
	});
	
    $("input[type='checkbox']").bind("click", function () {
        if(typeof($(this).attr("checked"))=="undefined"){
			$(this).attr("checked", this.checked);
			$(this).attr("value", true);
		}else{
			$(this).removeAttr("checked");
			$(this).attr("value", false);
		}
    });
}