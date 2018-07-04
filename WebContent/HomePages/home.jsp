<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<div class="info_box">
	<div class="info_header">
		系统公告
	</div>
	<ul class="info_list news_list">
		<c:if test="${!empty list}">
			<c:forEach items="${list}" var="item">
				<li>
					<span class="hidden">${item.news.id}</span>
					<span class="news-date">
						<c:set var="date"  value="${item.news.date}"/> 
						${fn:substring(date, 0, 10)}
					</span>
					<c:set var="important"  value="重要"/> 
					<c:if test="${item.importantLevelName == important}">
			            <span class="news_important">[${item.importantLevelName}]</span>
			        </c:if> 
			        <c:if test="${item.importantLevelName != important}">
                    	<span class="news_normal">[${item.importantLevelName}]</span>
                    </c:if> 
					[${item.newsTypeName}]${item.news.title}
				</li>
			</c:forEach>	
		</c:if> 
	</ul>
	<div class="info_more">
		<a href="#" id="moreNews">更多公告..</a>
	</div>
</div>


<c:choose>
	<c:when test="${userSession.userType == 1}">
		<jsp:include page="_center.jsp"/>
	</c:when>
	<c:when test="${userSession.userType == 2}">
		<jsp:include page="_school.jsp"/>
	</c:when>
	<c:when test="${userSession.userType == 3}">
		<jsp:include page="_disipline.jsp"/>
	</c:when>
	<c:when test="${userSession.userType == 4}">
		<jsp:include page="_teacher.jsp"/>
	</c:when>
	<c:when test="${userSession.userType == 5}">
		<jsp:include page="_expert.jsp"/>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>

<script type="text/javascript">

	$(document).ready(function(){
		
		$("#moreNews").click(function(){
			$.post('${ContextPath}/news/news', function(data){
				  $( "#content" ).empty();
				  $( "#content" ).append( data );
			  }, 'html');
			return false; 
		});
		
		$(".news_list li").click(function(){
			$.post('${ContextPath}/news/newsdetail',{newsId:$(this).find("span.hidden").text()}, function(data){
	   		  $('#dialog').empty();
			  $('#dialog').append( data );
		 	  $('#dialog').dialog({
	   	  		    title:"公告详情",
	   	  		    height:'750',
	   	  			width:'900',
	   	  			position:'center',
	   	  			modal:true,
	   	  			draggable:true,
	   	  		    hide:'fade',
	   	  			show:'fade',
	   	  		    autoOpen:true,
	   	  		    buttons:{  
	   	 	            "关闭":function(){
	   	 	            	$("#dialog").dialog("close");
	   	 	            }
	   	  		    }
		 	  }); 
	   	 	}, 'html');
			return false; 
		});
		
		$(".express_entry a").click(function(){
			$.post($(this).attr("href"), function(data){
				  $( "#content" ).empty();
				  $( "#content" ).append( data );
			  }, 'html');
			return false;
		});
		
	});
	
	
</script>