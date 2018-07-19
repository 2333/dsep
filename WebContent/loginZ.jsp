<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cxt" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html>
	<head>
		<script type="text/javascript">
		function getInternetExplorerVersion()
		// Returns the version of Internet Explorer or a -1
		// (indicating the use of another browser).
		{
		  var rv = -1; // Return value assumes failure.
		  if (navigator.appName == 'Microsoft Internet Explorer')
		  {
		    var ua = navigator.userAgent;
		    var re  = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
		    if (re.exec(ua) != null)
		      rv = parseFloat( RegExp.$1 );
		  }
		  return rv;
		}
		function checkVersion()
		{
		  var msg = "You're not using Internet Explorer.";
		  var ver = getInternetExplorerVersion();

		  if ( ver > -1 )
		  {
		    if ( ver >= 8.0 ) 
		      	msg = "You're using a recent copy of Internet Explorer."
		    else{
		      	msg = "系统不支持您当前版本的浏览器，建议您使用Chrome、火狐或者IE8以上版本的浏览器";
		    	alert(msg); 
		    }
		  }
		}
		</script>
		<script src="${cxt}/js/jquery-1.9.1.js"></script>
		<script src="${cxt}/js/jquery.validate.min.js"></script>
		<script src="${cxt}/js/jquery.form.min.js"></script>
		<title>信息网</title>
		<style type="text/css">
			body
			{
				margin:0px;
			}
			#header
			{
				height:82px;
				width: 732px;
				margin: 118px auto 0 auto;
				background:#FFFFFF url(images/logoZ.jpg) repeat-x;
			}
			#main
			{
				height:300px;
				background:#FFFFFF url(images/login_bg.png) repeat-x;
			}

			#form1
			{
				width:340px;
				margin:0px auto;
				padding-top:30px;
				padding-left:20px;
			}

			.inputbox
			{
				margin:10px 0px;
				padding:7px 17px;
				position:relative;
			}
			
			.inputbox input
			{
				margin-left:8px;
				padding:5px 5px 5px 5px;
				line-height:17px;
				outline-style:none;
				width:250px;
				font-size: 15px;
			}


			.inputbox #user_checknum
			{
				width:70px;
			}
			
			.icon-user
			{
				display:block;
				width:33px;
				height:33px;
				float:left;
				background:url(images/user.png) no-repeat left;
			}
			

			.icon-password
			{
				display:block;
				width:33px;
				height:33px;
				float:left;
				background:url(images/pwd.png) no-repeat left;
			}
			
			.icon-key
			{
				display:block;
				width:33px;
				height:33px;
				float:left;
				background:url(images/check.png) no-repeat left;
			}

			.inputbox a.button
			{
				cursor:pointer;
				margin:0 auto;
				display:block;
				color:white;
				width:117px;
				line-height:32px;
				text-decoration:none;
				background:url(images/button.png) no-repeat left;
			}

			.inputbox a.button:hover
			{
				background:url(images/button_ho.png) no-repeat left;
			}
			
			#sub_right
			{
				position:absolute;
				left:150px;
				top:10px;
			}

			#another
			{
				color: #FFF;
				text-decoration: none;
				font-size: 13px;
			}
			
			label.error
			{
				color: #FFF;
				font-size: 13px;
				display:block;
				margin-left:40px;
			}

			#footer
			{
				margin-top: 20px;
				height:200px;
			}

			#footer p
			{
				font-size: 10px;
				color: #bbb;
				line-height: 2em;
				text-align: center;
			}
		</style>
	</head>
	<body onload="checkVersion()">
		<div id="header">

		</div>
		<div id="main">
			<form id="form1" action="#">
	            <div class="inputbox">
	                <span class="icon-user"></span>
	                <input type="text" id="user_id" name="loginId" value="" placeholder="用户ID"/>
	            </div>
	            <div class="inputbox">
	                <span class="icon-password"></span>
	                <input type="password" id="user_password" name="password" value="" placeholder="密码"/>
	                <label for="user_password"  generated="true" class="error" style="display:none;" >密码或用户名错误！</label>
	            </div>
	            <div class="inputbox">
	                <div id="sub_left"><span class="icon-key"></span>
	                <input type="text" id="user_checknum" name="userCheckNum" value="" placeholder="验证码"/></div>
	                <div id="sub_right"><img src= "${cxt}/rbac/generatecode" onclick="changeValidateCode(this)" title="刷新验证码" style="cursor: pointer;" /></div>
	            </div>
	            <div class="inputbox">
					<a class="button">&nbsp;</a> 
	            </div>
	        </form>
		</div>
		<div id="footer">
			<p><a href="http://dlsw.baidu.com/sw-search-sp/soft/9d/14744/ChromeStandaloneSetup.1414465267.exe">测试期间，为保证功能正常运行，请使用Chrome浏览器进行登录</a></p>
		</div>
		<script type="text/javascript">
			var checknum;
			
			function changeValidateCode(obj){
		  		var timeNow = new Date().getTime();
		  		obj.src="${cxt}/rbac/generatecode?time="+timeNow;
		  		
		  	}	
			
			$(document).ready(function(){
				/*表单验证*/ 
				$("#form1").validate({
                    rules: {
                    	loginId: {
                            required: true
                        },
                        password: {
                            required: true
                        },
                        /* userCheckNum:{
                            required: true,
                            remote: {
                                url: "${cxt}/rbac/checkcode", //url地址
                                type: "post", //发送方式
                                dataType: "json", //数据格式 
                                data:{
                                	user_checknum:function(){return $("#user_checknum").val();}
                                } 
                            } 
                         } */
                    }, 
                    messages: {
                    	loginId: {
                            required: "请输入用户账号"
                        },
                        password: {
                            required: "请输入密码"
                        },
                        /* userCheckNum:{
                        	required:"请输入验证码",
                        	remote:jQuery.format("验证码错误")
                        } */
                    }
	            });
				
				$("a.button").click(function(){
					if($('#form1').valid()){
						//$("#form1").submit();
						/* $("#form1").ajaxSubmit({
							success: function(result){
								alert(result);
								console.log(result);
								if(result=='success'){
									location.href='${cxt}/rbac/tologin';
								}else{
									$("#responseMes").html('用户名或密码错误，请重新输入！');
								}
							}
						}); */
						$.ajax({
			                cache: false,
			                type: "POST",
			                url:'${cxt}/rbac/login',
			                data:$('#form1').serialize(),// 提交formid
			                async: true,
			                error: function(request) {
			                    alert_dialog("连接错误！");
			                },
			                success: function(result){
			                	//console.log(result);
								if(result=='success'){
									location.href='${cxt}/rbac/tologin';
								}else{
									$("label[for='user_password']").show();
								}
			                }
			            });
					}
				});
			});
			
			/*处理键盘事件，回车提交表单*/
			function keyEnter() {
                if (event.keyCode == 13) {
                	$("a.button").click();
                }
            }
            document.onkeydown = keyEnter;
		</script>
	</body>
</html>