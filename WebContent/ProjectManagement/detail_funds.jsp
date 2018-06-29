<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<style>
	#funds_panel{margin:0px auto;width:70%;}
	#funds_title{height:40px;border-bottom:5px solid #33A1C9;margin:15px 0px;}
	#funds_title p{margin:0px auto;text-align: center;font-size:30px;font-weight:bold;font-family:黑体;}
	#funds_content{margin:20px;padding:10px 20px;min-height:300px;}
	#funds_attr{height:40px;border-top:2px solid #33A1C9;border-bottom:2px solid #33A1C9;margin:15px 0px;}
	#funds_attr p{margin:0px 20px;padding:10px 20px;font-size:15px;font-weight:bold;color:#bbb;}
</style>
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>经费使用
	</h3>
</div>
<div id="funds_panel">
	<div id="funds_title"><p>经费使用详情</p></div>
 	<div id="funds_content">${itemFunds.detail}</div>
	<div id="funds_attr"><p>使用时间：<c:set var="date" value="${itemFunds.usingTime}"/> ${fn:substring(date, 0, 10)}&nbsp;&nbsp;
			经办人： ${itemFunds.operator}&nbsp;&nbsp;发票号码：${itemFunds.invoiceNumber}</p>
	</div> 
</div>
    