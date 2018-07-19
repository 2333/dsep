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
			<p class="smallTitle">文件模板下载</p>
			<table class="radio_table">
				<tr>
					<td>
						<div>
							<div>
								<table id="feedback_detail_tb"></table>
								<div id="detail_pager_tb"></div>
							</div>
						</div>
					</td>
				</tr>
			</table> 
			<p style="margin-left: 30px;font-size: 13px; color: gray;">在下载的模板中填写数据，填写完成后保存再导入。</p>
		</div>
		<div id="achievement">
			<p class="smallTitle" style="margin: 20px 0 10px 0; ">导入文件</p>
			<div class="file-box" style="margin-left: 10px;">
				<form action="" method="post" enctype="multipart/form-data">
					<span style="margin-left: 30px;">导入文件：</span> <input type='text' name='textfield'
						id='textfield' class='txt' size="10" /> <input type='button'
						class='btn' value='选择文件...' /> <input type="file"
						name="fileField" class="file" id="fileField" size="28"
						onchange="document.getElementById('textfield').value=this.value" />
					<p style="margin-left: 30px;font-size: 13px; color: gray;">请导入模板格式文件，支持导入<span style="font-size: 13px; color:blue;">EXS(*.exs)</span>或<span style="font-size: 13px; color:blue;">vEXS(*.vexs)</span>格式的文件</p>
				</form>
			</div>
		</div>


	</form>
</div>
<script type="text/javascript">
	$(document).ready(
			function() {
				var globalWidth = $("#feedback_detial_dv").width() * 0.66;
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
					kyxmmc : "教师信息模板",
					xmfzr : "教师信息模板",
					xmbh : "教师信息模板",
					xmly : "教师信息模板",
					xmlx : "教师信息模板"
				}];
				for ( var i = 0; i <= detaildata.length; i++)
					jQuery("#feedback_detail_tb").jqGrid('addRowData', i + 1,
							detaildata[i]);
				$("#feedback_detail_tb").setGridWidth(globalWidth);
				
				
			});
</script>
