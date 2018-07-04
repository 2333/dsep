<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div id="logic_checkrule_detail" class="tabs">
	<ul>
		<li><a href="#zhuanjia">专家</a></li>
		<li><a href="#gaoshuipingydycpy">高水平运动员、裁判员</a></li>
		<li><a href="#guanjunjiaolian">冠军教练</a></li>
	</ul>
	<div id="zhuanjia" class="scroll-x">
		<table border="1">
			<tr>
				<th width="40%">专家类别</th>
				<th width="15%">专家姓名</th>
				<th width="15%">出生年月</th>
				<th width="15%">获批年月</th>
				<th width="15%">备注</th>
			</tr>
			<tr>
				<td>
					<p>
						1.“专家类别”栏中，限填“中国科学院/工程院院士、千人计划入选者、军队科技领军人才培养对象、长江学者特聘/讲座教授、国家杰青基金获得者、973首席科学家、国家级教学名师、教育部高校青年教师奖获得者、教育部跨世纪人才、百千万人才工程国家级人选、中科院百人计划入选者、教育部新世纪人才”。同一专家有多种称谓时，请按以上顺序选择一种，不重复填写。

						<br> <br>2.“千人计划入选者”不包含“短期千人”和“青年千人”；国家杰青基金获得者应依据其“申请学科”进行填报（需在“备注”栏中注明申请时的学科代码），原“杰青B类”不计入内。

						<br> <br>3.长江学者应依据其设岗学科进行填报。“聘期”内的长江学者，若人事关系不在聘任单位，应由聘任单位填写（需在“备注”栏注明人事关系所在单位名称）；不在“聘期”内的长江学者，由人事关系所在单位填写。

						<br> <br>4.院士需在“备注”栏中注明所在学部。 <br> <br>
						5.2011年12月31日前已故专家不再计入师资队伍。
					</p>
				</td>
				<td>不能为空。</td>
				<td>格式须为YYYYMM，且不应该超过当前时间，可以限定过去150年内出生。</td>
				<td>格式须为YYYYMM，且不应该超过当前时间。</td>
				<td>无，可以为空。院士需在“备注”栏中注明所在学部。</td>
			</tr>
		</table>
	</div>

	<div id="gaoshuipingydycpy">
		<table border="1">
			<tr>
				<th>裁判员/运动员类别</th>
				<th>项目</th>
				<th>裁判员/运动员姓名</th>
				<th>出生年月</th>
				<th>颁证年月</th>
				<th>证书编号</th>
			</tr>
			<tr>
				<td>限填“国际级裁判、国际级健将、运动健将”三种，不能为空。</td>
				<td>不能为空。</td>
				<td>不能为空。</td>
				<td>格式须为YYYYMM，且不应该超过当前时间。</td>
				<td>格式须为YYYYMM，且不应该超过当前时间。</td>
				<td>由英文字母和数字组成的字符串。</td>
			</tr>
		</table>
	</div>

	<div id="guanjunjiaolian">
		<table border="1">
			<tr>
				<th width="20%">比赛类型</th>
				<th width="20%">冠军姓名</th>
				<th width="20%">获冠军项目</th>
				<th width="20%">获冠军时间</th>
				<th width="20%">教练姓名</th>
			</tr>
			<tr>
				<td>近十年内的世界比赛或全国比赛，限填信息。</td>
				<td>非空。</td>
				<td>限填信息。</td>
				<td>格式YYYYMM，不能超过系统当前时间。</td>
				<td>非空。</td>
			</tr>
		</table>
	</div>
</div>



