<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<style type="text/css">
<!--
-->
.file-box {
	position: relative;
	width: 580px
}

.txt {
	height: 22px;
	border: 1px solid #cdcdcd;
	width: 180px;
}

.btn {
	background-color: #FFF;
	border: 1px solid #CDCDCD;
	height: 24px;
	width: 70px;
}

.file {
	position: absolute;
	top: 0;
	right: 80px;
	height: 24px;
	filter: alpha(opacity :     0);
	opacity: 0;
	width: 260px
}
</style>
<div id="feedback_detial_dv" class="from">
	<form>
		<div id="achievement">
			<p class="smallTitle" style="margin: 20px 0 10px 0; ">填写导出文件名称</p>
			<span style="margin-left: 30px;">导出文件名称：</span><input type="text"><select name="文件后缀"><option>.txt</option><option>.cvs</option></select>
			<!-- <p style="margin-left: 30px;font-size: 13px; color: gray;">请导入模板格式文件，支持导入<span style="font-size: 13px; color:blue;">EXS(*.exs)</span>或<span style="font-size: 13px; color:blue;">vEXS(*.vexs)</span>格式的文件</p>
			 -->
		</div>
		<div id="achievement">
			<p class="smallTitle" style="margin: 20px 0 10px 0; ">导出文件路径</p>
			<div class="file-box" style="margin-left: 3px;">
				<form action="" method="post" enctype="multipart/form-data">
				
				 <div style="margin-bottom: 5px">
				 <input type="radio" name="choose" id="default" value="默认路径" checked="checked" />
				 <span style="margin-left: 3px; margin-bottom: 5px">默认路径：</span> 
				 <input type='text' name='textfield' id='textfield' class='txt' size="10" disabled="disabled" value="C:\User\Download"/> 
				 </div>
				 <br>
				 <input type="radio" name="choose" id="manual" value="手动路径" />
				 <span style="margin-left: 3px;">手动选择：</span> 
				 <input id="manual1" disabled="disabled" type='text' name='textfield' id='textfield' class='txt' size="10" /> 
				 <input id="manual2" disabled="disabled" type='button' class='btn' value='手动选择...' /> 
				 <input id="manual3" disabled="disabled" type="file" name="fileField" class="file" id="fileField" size="28"
						onchange="document.getElementById('textfield').value=this.value" />
					<!-- <p style="margin-left: 30px;font-size: 13px; color: gray;">请导入模板格式文件，支持导入<span style="font-size: 13px; color:blue;">EXS(*.exs)</span>或<span style="font-size: 13px; color:blue;">vEXS(*.vexs)</span>格式的文件</p>
					 -->
				</form>
			</div>
		</div>


	</form>
</div>
<script type="text/javascript">
	$(document).ready(
			function() {
				$("#manual").change(function() {
					$("#manual1").removeAttr("disabled");
					$("#manual2").removeAttr("disabled");
					$("#manual3").removeAttr("disabled");
				});
				$("#default").change(function() {
					$("#manual1").attr("disabled","disabled");
					$("#manual2").attr("disabled","disabled");
					$("#manual3").attr("disabled","disabled");
				});
				
				var globalWidth = $("#feedback_detial_dv").width() * 0.56;
				common_Css();//button的样式
				//成果
				$("#feedback_detail_tb").jqGrid(
						{
							datatype : 'local',
							colNames : [ '点击下载模板', '点击下载模板', '点击下载模板', '点击下载模板',
									'点击下载模板' ],
							colModel : [ {
								name : 'kyxmmc',
								index : 'kyxmmc',
								width : 100,
								sorttype : "String",
								align : "center",
								editable : true,
								edittype : "text"
							}, {
								name : 'xmfzr',
								index : 'xmfzr',
								width : 100,
								align : "center",
								editable : true
							}, {
								name : 'xmbh',
								index : 'xmbh',
								width : 100,
								align : "center",
								sorttype : "String",
								editable : true
							}, {
								name : 'xmly',
								index : 'xmly',
								width : 100,
								align : "center",
								sorttype : "String",
								editable : true
							}, {
								name : 'xmlx',
								index : 'xmlx',
								width : 100,
								align : "center",
								sorttype : "date",
								editable : true
							} ],
							height : 'auto',
							autowidth : true,
							shrinkToFit : true,
							pager : '#object_pager_tb',
							pgbuttons : true,
							rowNum : 10,
							rowList : [ 10, 20, 30 ],
							viewrecords : true,
							sortname : 'kyxmmc',
							sortorder : "desc",
							caption : "文件模板下载",
							modal : false
						}).navGrid('#feedback_detail_tb', {
					edit : false,
					add : false,
					del : false
				});
				var detaildata = [ {
					kyxmmc : "001模板",
					xmfzr : "002模板",
					xmbh : "003模板",
					xmly : "004模板",
					xmlx : "005模板"
				}, {
					kyxmmc : "006模板",
					xmfzr : "007模板",
					xmbh : "008模板",
					xmly : "009模板",
					xmlx : "010模板"
				}];
				for ( var i = 0; i <= detaildata.length; i++)
					jQuery("#feedback_detail_tb").jqGrid('addRowData', i + 1,
							detaildata[i]);
				$("#feedback_detail_tb").setGridWidth(globalWidth);
				
				 
			});
</script>
