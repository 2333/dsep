<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="con_header" style="overflow: hidden">
	<table class="layout_table left">
		<tr>
			<td><span class="icon icon-web"></span> <span
				class="TextFont">指标体系评价</span></td>
		</tr>
	</table>
</div>
<div id="indicator_content">
	<ul>
		<li><a href="#weight_div">指标比重</a></li>
		<li><a href="#convert_div">指标折算</a></li>
	</ul>
	<div id="weight_div">
		<table id="weight_list"></table>
	</div>
	<div id="convert_div">
		<div>
			<table id="convert_list"></table>
		</div>
		<div>
			<div id="data_form">
				<form id="myForm" action="#" method="post" class="fr_form">
					<table class="fr_table">
						<tr>
							<td><label for="xks">1、“科研获奖、教学成果奖及重点学科”中，</label> <label
								for="xks">国家级：省部级=<input type="text" name="name"
									size="12" class="data_normalize_css">:<input
									type="text" name="name" size="12" class="data_normalize_css">（初值：10
									:1）
							</label></td>
						</tr>
						<tr>
							<td><label for="xks">2、“各类获奖”中，</label> <label for="xks">特等奖：一等奖：二等奖：三等奖=<input
									type="text" name="name" size="12" class="data_normalize_css">:<input
									type="text" name="name" size="12" class="data_normalize_css">:<input
									type="text" name="name" size="12" class="data_normalize_css">:<input
									type="text" name="name" size="12" class="data_normalize_css">（初值：4
									: 2 : 1 : 0.5）
							</label></td>
						</tr>
						<tr>
							<td><label for="xks">3、“国家及省级三大奖”中，</label> <label for="xks">自然科学奖:技术发明奖：科技进步奖=<input
									type="text" name="name" size="12" class="data_normalize_css">:<input
									type="text" name="name" size="12" class="data_normalize_css">:<input
									type="text" name="name" size="12" class="data_normalize_css">（初值：1.5
									: 1.25 : 1）
							</label></td>
						</tr>
						<tr>
							<td><label for="xks">4、“优秀学位论文”中，</label> <label for="xks">全国优博入选论文：全国优博提名论文：计算机学会优博：MPA教指委优秀硕士论文=<input
									type="text" name="name" size="12" class="data_normalize_css">:<input
									type="text" name="name" size="12" class="data_normalize_css">:<input
									type="text" name="name" size="12" class="data_normalize_css">:<input
									type="text" name="name" size="12" class="data_normalize_css">（初值：3
									: 1 : 1 : 0.5）
							</label></td>
						</tr>
						<tr>
							<td><label for="xks">5、“发表论文数”中，</label> <label for="xks">国外收录论文（如：SCI、EI、SSCI等）数：国内收录论文（CSCD、CSSCI）数=<input
									type="text" name="name" size="12" class="data_normalize_css">:<input
									type="text" name="name" size="12" class="data_normalize_css">（初值：1.5
									: 1）
							</label></td>
						</tr>
						<tr>
							<td><label for="xks">6、“授予学位数”中，</label> <label for="xks">博士：硕士=<input
									type="text" name="name" size="12" class="data_normalize_css">:<input
									type="text" name="name" size="12" class="data_normalize_css">（初始值：6
									: 1）
							</label></td>
						</tr>
						<tr>
							<td><label for="xks">7、“MBA优秀案例”中，</label> <label for="xks">哈佛商学院案例：毅伟商学院案例：MBA教指委百篇优秀案例=<input
									type="text" name="name" size="12" class="data_normalize_css">:<input
									type="text" name="name" size="12" class="data_normalize_css">:<input
									type="text" name="name" size="12" class="data_normalize_css">（初始值：1.5
									:1.3 :1）
							</label></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>

</div>
<div class="selectbar" style="overflow: hidden">
	<table class="layout_table right">
		<tbody>
			<tr>
				<td width="90"><a id="save" class="button" href="#"><span class="icon icon-store "></span>保存</a></td>
			</tr>
		</tbody>
	</table>
</div>
<script src="/DSEP/js/Expert/evaluate_indicator.js"></script>

