<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="header">
	<div id="logo"></div>
	<div id="status">
		<table class="right">
			<tr>
				<td align="right" style="height:20px;">
					<span>
						欢迎您，<span class="highlight_info">${userSession.name}！</span><a id="modify_password">修改密码</a>|
						<a href="${ContextPath}/rbac/logout" class="alert_info">安全退出</a>
					</span>
				</td>	   
			</tr>
			<tr>
				<td align="right" style="height:20px;">上次登录时间：${userSession.loginTime}；上次登录IP：${userSession.loginIp}</td>
			</tr>
		</table>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		var unitId = "${userSession.unitId}";
		
		var imgurl="${ContextPath}/images/logos/header_logo2.png";
		var path="${ContextPath}/images/logos/"+unitId+".png";
		if(CheckImgExists(path) == true){
			imgurl = path;
		}
		$("#logo").css("background","url("+imgurl+")no-repeat");
		
		
		$("#modify_password").click(function(){
			$.post('${ContextPath}/rbac/modify_password', function(data){
	   		  $('#dialog').empty();
			  $('#dialog').append( data );
		 	  $('#dialog').dialog({
	   	  		    title:"修改密码",
	   	  		    height:'300',
	   	  			width:'500',
	   	  			position:'center',
	   	  			modal:true,
	   	  			draggable:true,
	   	  		    hide:'fade',
	   	  			show:'fade',
	   	  		    autoOpen:true,
		   	  		buttons:{  
	   	  		    	"确定":function(){ 
	   	  		    		if($('.fr_form').valid()){
	   	  		    			var pwd=$('#modify_password_form').serialize();
		   	  	         		$.ajax({
			   	  	      			type:'POST',
			   	  	      			url:'${ContextPath}/rbac/save_password',
			   	  	      			data:pwd,
				   	  	      		success: function(data){
			    	  	      			if(data){
			    	  	      				alert_dialog("修改密码成功！");	
			  	      	   		    		$("#dialog").dialog("close");
			    	  	      			}
			    	  	      			else
			    	  	      			{
				    	  	      			$("#dialog").dialog("close");
			    	  	      				alert_dialog("修改密码失败！");	
			    	  	      			}
		    	  	      			},
		    	  	      			error: function(data){
		    	  	      				alert_dialog("Error:"+ data);
		    	  	      			}
		   	  	      			});
	   	  		    		}
	   	  	            },
	   	 	            "关闭":function(){
	   	 	            	$("#dialog").dialog("close");
	   	 	            }
	   	  		    }
		 	  }); 
	   	 	}, 'html');
			return false; 
		});
		
	});
	
	function CheckImgExists(url) {
 			var oreq;
		// 判断是否把XMLHTTPRequest实现为一个本地javascript对象
			if (window.XMLHttpRequest){
				// code for IE7+, Firefox, Chrome, Opera, Safari
 				 oreq=new XMLHttpRequest();
  			}
			else{
				// code for IE6, IE5
  				oreq=new ActiveXObject("Microsoft.XMLHTTP");
  			}
			oreq.open("GET",url,false);
			oreq.send();
			if(oreq.status == 404) return false;
			return true; 
/*          var ImgObj = new Image(); //判断图片是否存在
            ImgObj.src = url;
            console.log(ImgObj.width);
            //没有图片，则返回-1
            if (ImgObj.fileSize > 0 || (ImgObj.width > 0 && ImgObj.height > 0)) {
                return true;
            } else {
               return false;
            } */ 
        }
</script>