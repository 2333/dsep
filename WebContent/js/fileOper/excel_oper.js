function outputJS(url){
	jQuery.ajax({
		url:url,
		contentType:"application/x-www-form-urlencoded; charset=utf-8",
		type:"post",
		async: true,
		dataType:"json",
		beforeSend:function(){
			if($("#outputModal").length == 0){
				$("html").append('<div id="outputModal" style="display:none;"></div>');
			}
			$("#outputModal").dialog({
				title:"正在导出文件，请稍等",
				height:'0',
				width:'200',
				position:'center',
				draggable:false,
				modal:true
			}).show();
		},
		success:function(data){
			if(data == false){
				alert_dialog("没有数据可以导出！");
			}else{
				var filePath = data.filepath;
				
				//base64加密
				var baseHelper = new Base64();  
				filePath = baseHelper.encode(filePath);
				
				var link = contextPath + '/download.jsp?'+"filepath="+filePath+"&"+"filename="+data.filename;		
				window.open(link,"_self");
			}
		},
		complete:function(){
			$("#outputModal").dialog("close");
		}
	});
}

//下载证明材料
function downloadProveMaterial(path){
	var fileName = getFileName(path, "/");
	var filePath = getFilePath(path, "/");
	
	//base64加密
	var baseHelper = new Base64();  
	filePath = baseHelper.encode(filePath);
	
	var link = contextPath+'/download.jsp?'+"filepath="+filePath+"&filename="+fileName;		
	window.open(link,"_self");
}

function outputJSWithParam(url,dataParam){
	jQuery.ajax({
		url:url,
		type:"post",
		data:dataParam,
		async: false,
		dataType:"json",
		beforeSend:function(){
			if($("#outputModal").length == 0){
				console.log("create div");
				$("html").append('<div id="outputModal" style="display:none;"></div>');
			}
			$("#outputModal").dialog({
				title:"正在导出文件，请稍等",
				height:'0',
				width:'200',
				position:'center',
				draggable:false,
				modal:true
			}).show();
			console.log("dialog show");
		},
		success:function(data){
			if(data){
				
				var filePath = data.filepath;
				
				//base64加密
				var baseHelper = new Base64();  
				filePath = baseHelper.encode(filePath);
				
				var link = contextPath + '/download.jsp?'+"filepath="+filePath+"&"+"filename="+data.filename;		
				window.open(link,"_self");
			}
		},
		complete:function(){
			$("#outputModal").dialog("close");
		}
	});
}