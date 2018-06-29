<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id='TE' class="TE">
	<ul class="MP-nav" id="MPnav">
    	<li style="position:relative;">
         	<a href="#" id="template_choice" title="使用已有的问卷模版创建问卷">
         		<span id="spanTemplate" class="current">A 选择问卷模板</span>
         		<em id="BottomTmp_emA" class="bordBottomCssMc"></em>
         	</a> 
    	</li>
    	<li style="position:relative;">
        	<a href="#" id="create_blank" title="从头开始设计您的问卷">
        		<span id="spanBlank">B 创建空白问卷
        		</span>
        		<em id="BottomTmp_emB" class="bordBottomCssMc" style="display:none;"></em>
         	</a> 
    	</li>
 	</ul>
</div>
<div id="create_content">
</div>
<style>

</style>
<script type="text/javascript">
    var MPnav = document.getElementById("MPnav");
    var hrefas = MPnav.getElementsByTagName("a");
    
    var emA = document.getElementById("BottomTmp_emA");
    var emB = document.getElementById("BottomTmp_emB");
    
    var spanTemplate = document.getElementById("spanTemplate");
    var spanBlank = document.getElementById("spanBlank");
    
    $(document).ready(function(){
    		for (var i = 0; i < hrefas.length; i++) {
            	hrefas[i].onmouseover = function () {
                var span = this.getElementsByTagName("span")[0];
                if (span && span.className != "current")
                    span.className = "hover";
            	}
            hrefas[i].onmouseout = function () {
                var span = this.getElementsByTagName("span")[0];
                if (span && span.className != "current")
                    span.className = "";
            	}
        	}
    		$.post("${ContextPath}/survey/qcreate_template", function(data){
    			  $( "#create_content" ).append( data );
    		 	  }, 'html');
    			
    		//点击创建空白问卷
    		$("#create_blank").click(function(){
    			emB.style.display = "";
    			emA.style.display = "none";
    			spanTemplate.className="";
    			spanBlank.className="current";
       			$.post("${ContextPath}/survey/qcreate_blank", function(data){
      			  	$( "#create_content" ).empty();
      			  	$( "#create_content" ).append( data );
      		 	  	}, 'html');
      			event.preventDefault();
       		});
    	    
    		//点击按模板创建问卷
    	    $("#template_choice").click(function(){
    	    	emA.style.display = "";
    	    	emB.style.display = "none";
    	    	spanBlank.className="";
    	    	spanTemplate.className="current";
    	   	 	$.post("${ContextPath}/survey/qcreate_template", function(data){
    				 $( "#create_content" ).empty();
    				 $( "#create_content" ).append( data );
    			 	 }, 'html');
    			event.preventDefault();
    	   });
    });
  
</script>
