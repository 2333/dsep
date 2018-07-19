;(function($){
	$.extend({
		"collection_tree":{
			/*!对TREE的参数设置
			*callback中包含相关的回调函数
			*
			*/
			"default_treeSetting":{
				data: {
					key: {
						title:"title"
					},
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: function(event, treeId, treeNode,clickFlag){
						if(treeNode.children==null){
							alert('没有绑定任何事件！');
						}	
					},
				}
			},
			/*!初始化TREE的默认参数
			*url:请求路径
			*type:返回数据类型
			*setting:tree的相关设置,在这里写回调函数
			*elementId:tree标签的ID
			*/
			"default_initCollectionTree_config":{
				elementId:'',
				url:'',
				type:'json',
				setting:{},
			},
			/*初始化采集树*/
			"initCollectionTree":function(config)
			{
				var new_config=$.extend({},$.collection_tree.default_initCollectionTree_config,config);
				var new_treeSetting=$.extend({},$.collection_tree.default_treeSetting,config.setting);
				if(new_config.elementId!=''&&new_config.url!=''){
					var request_config={
						url:new_config.url,
						dataType:'json',
						success:function(data){
							var zNodes=data;
							$.fn.zTree.init($('#'+new_config.elementId), new_treeSetting,zNodes);//tree的实例化
						},
					};
					$.collection.commonAjaxRequest(request_config);
				}
			},
		},
		
	});
	
})(jQuery)