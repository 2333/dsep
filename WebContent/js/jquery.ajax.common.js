;(function($){
 $.ajaxSetup({   
    contentType:"application/x-www-form-urlencoded;charset=utf-8",
    complete:function(XMLHttpRequest,textStatus){ 
    console.log('-----ajaxSetup----'+textStatus);
    var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头，sessionstatus，  
    console.log(sessionstatus); 
    if(sessionstatus=="timeout"){   
    	var url= XMLHttpRequest.getResponseHeader("contextPath");
        //如果超时就处理 ，指定要跳转的页面  
       alert_dialog("登录超时，请重新登录！");
       setTimeout(function () { 
        	window.location.replace("/"+url); 
        }, 3000);
        //  
        }   
        }   
     }   
);
})(jQuery);