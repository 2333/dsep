<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="selectsAnchor" style="padding:5px 0;">	
	<span id="addLogicSelects" class='icon icon-add qItemIcon' onclick="addLogicSelects(this);"></span>

	<br/><div class="selects">如果题目<select class="selectStyle s1" onchange="initSelect2Option()"></select>选择了<select class="selectStyle s2" onchange="initSelect3Option()"></select>，那么跳转到<select class="selectStyle s3" onchange="lockSelects()"></select></div>
</div>