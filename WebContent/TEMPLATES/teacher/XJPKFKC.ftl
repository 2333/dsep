	<table class="textCenter">
		<caption>表5  精品开放课程</caption>
		<thead>
		<tr class="simSun wuHao">
			<td><span>序号</span></td>
			<td width="6.57cm"><span>课程名称</span></td>
			<td width="4.57cm"><span>课程类别</span></td>
			<td width="4.07cm"><span>获批年度</span></td>
			<td width="4.57cm"><span>建设单位</span></td>
			<td width="3.07cm"><span>课程负责人</span></td>
		</tr>	
		</thead>
		<tbody>
			<!--freemarker start-->
			<#list XJPKFKC as XJPKFKCData>
			<tr class="simSun wuHao">
				<td>${XJPKFKCData_index + 1}</td>
				<td width="6.57cm">${(XJPKFKCData.KCMC)?html}</td>
				<td width="4.57cm">${(XJPKFKCData.KCLB)?html}</td>
				<td width="4.07cm">${(XJPKFKCData.PZND)?html}</td>
				<td width="4.57cm">${(XJPKFKCData.JSDW)?html}</td>
				<td width="3.07cm">${(XJPKFKCData.KCFZRXM)?html}</td>
			</tr>
			</#list>
			<!--freemarker end-->			
		</tbody>
	</table>
<!-- 	<div class="shuoMing">
		<p style="margin-top:6.0pt;margin-right:0cm;margin-bottom:0cm;margin-left:31.5pt;margin-bottom:.0001pt;text-indent:-31.5pt;line-height:13.0pt">
			<span>说明:<span>1.“课程类别”栏中，通过下拉列表选择填写“省级精品视频公开课、国家级精品视频公开课、省级精品资源共享课、国家级精品资源共享课、国家级精品课程、省级精品课程”；</span></span>
		</p>
		<p style="margin-left:31.5pt;line-height:13.0pt">
			<span>2."课程负责人"，需按顺序填写所有课程负责人姓名，通过勾选形式实现，勾选项有"本人/他人"，例如"&#9633;本人   &#9633;他人_____"，此项涉及多个负责人，填写栏后面设置"+"键，可通过点击"+"方式添加多条课程负责人记录；</span>
		</p>
		<p style="margin-left:31.5pt;line-height:13.0pt">
			<span>3.“建设单位”，需按顺序填写所有建设单位名称，通过勾选形式实现，勾选项有"本单位/其他单位"。例如"&#9633;本单位   &#9633;其他单位_____"，此项涉及多个单位名称，填写栏后面设置“+”键，可通过点击“+”方式添加多条单位记录。</span>
		</p>		
	</div> -->
	<table class="shuoMingTable">
		<tr>
			<td>说明：</td>
			<td>1.“课程类别”栏中，通过下拉列表选择填写“省级精品视频公开课、国家级精品视频公开课、省级精品资源共享课、国家级精品资源共享课、国家级精品课程、省级精品课程”；</td>
		</tr>
		<tr>
			<td></td>
			<td>2."课程负责人"，需按顺序填写所有课程负责人姓名，通过勾选形式实现，勾选项有"本人/他人"，例如"&#9633;本人   &#9633;他人_____"，此项涉及多个负责人，填写栏后面设置"+"键，可通过点击"+"方式添加多条课程负责人记录；</td>
		</tr>
		<tr>
			<td></td>
			<td>3.“建设单位”，需按顺序填写所有建设单位名称，通过勾选形式实现，勾选项有"本单位/其他单位"。例如"&#9633;本单位   &#9633;其他单位_____"，此项涉及多个单位名称，填写栏后面设置“+”键，可通过点击“+”方式添加多条单位记录。</td>
		</tr>
	</table>