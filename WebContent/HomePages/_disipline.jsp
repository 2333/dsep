<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div style="position:relative;margin-top:10px;">
	<!-- <div class="info_box left_block" style="width:300px;min-height:500px;">
		<div class="info_header">
			状态信息
		</div>
		<ul class="info_list">
			<li>
				评估当前阶段：第五轮评估正在进行中。
			</li>
			<li>
				学科数据状况：已有5000/10661将学科成果数据提交至中心。
			</li>
			<li>
				结果计算进度：学科数据正在计算中，已完成70%。
			</li>
			<li>
				报告生成进度：学科报告正在生成中，已完成2132/4952。
			</li>
		</ul>
	</div>    -->
	
	<div class="info_box" style="min-height:400px;">
		<div class="info_header">
			您可以
		</div>
		<ul class="info_list express_entry" >
			<li>
				<a href="${ContextPath}/Collect/toCollect">1.${textConfiguration.discInfoFill}（点击进入）</a>
			</li>
			<li>
				<a href="${ContextPath}/TCollect/toTCollect">2.查看教师成果（点击进入）</a>
			</li>
			<li>
				<a href="${ContextPath}/publicity/viewPub">3.查看公示信息（点击进入）</a>
			</li>
			<li>
				<a href="${ContextPath}/publicity/viewObjection">4.公示异议汇总（点击进入）</a>
			</li>
			<li>
				<a href="${ContextPath}/feedback/feedResponse">5.数据反馈答复处理（点击进入）</a>
			</li>
		</ul>
	</div>   
</div>