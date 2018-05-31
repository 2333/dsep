	<table class="textCenter">
		<caption>表9  主讲课程情况</caption>
		<thead>
		<tr class="simSun wuHao">
			<td><span>序号</span></td>
			<td width="4.75cm"><span>课程名称</span></td>
			<td width="2.57cm"><span>授课教师</span></td>
			<td width="2.28cm"><span>课程类型</span></td>
			<td width="2.00cm"><span>开课年份</span></td>
			<td width="1.50cm"><span>学期</span></td>
			<td width="2.00cm"><span>学生人数</span></td>
			<td width="1.60cm"><span>学时数</span></td>
			<td width="1.50cm"><span>学分</span></td>
			<td width="2.50cm"><span>所在学院</span></td>
			<td width="1.84cm"><span>授课对象</span></td>
		</tr>	
		</thead>
		<tbody>
			<!--freemarker start-->
			<#list ZJKCQK as ZJKCQKData>
			<tr class="simSun wuHao">
				<td>${ZJKCQKData_index + 1}</td>
				<td width="4.75cm">${(ZJKCQKData.KCMC)?html}</td>
				<td width="2.57cm">${(ZJKCQKData.SKJS)?html}</td>
				<td width="2.28cm">${(ZJKCQKData.KCLX)?html}</td>
				<td width="2.00cm">${(ZJKCQKData.KKNF)}</td>
				<td width="1.50cm">${(ZJKCQKData.XQ)?html}</td>
				<td width="2.00cm">${(ZJKCQKData.XSRS)?html}</td>
				<td width="2.00cm">${(ZJKCQKData.XSS)?html}</td>
				<td width="2.00cm">${(ZJKCQKData.XF)?html}</td>
				<td width="2.50cm">${(ZJKCQKData.SZXY)?html}</td>
				<td width="1.84cm">${(ZJKCQKData.SKDX)?html}</td>
			</tr>
			</#list>
			<!--freemarker end-->			
		</tbody>
	</table>
<!-- 	<div class="shuoMing">
		<p style="margin-top:6.0pt;margin-right:0cm;margin-bottom:0cm;margin-left:31.5pt;margin-bottom:.0001pt;text-indent:-31.5pt;line-height:13.0pt">
			<span>说明:<span>1.课程类型包括： 课程类型一般分为:公共基础课、公共选修课、专业基础课、专业选修课。</span></span>
		</p>
		<p style="margin-left:31.5pt;line-height:13.0pt">
			<span>2.授课主要对象分为：博士研究生、硕士研究生、本科生、不限。</span>
		</p>
		<p style="margin-left:31.5pt;line-height:13.0pt">
			<span>3.此表中主讲课程应该每年单独进行填报，“学期”每年限制下拉两项，如2014年则为2014-2015学年第一学期、2014-2015学年第二学期。</span>
		</p>
	</div> -->
	<table class="shuoMingTable">
		<tr>
			<td>说明：</td>
			<td>1.课程类型包括： 课程类型一般分为:公共基础课、公共选修课、专业基础课、专业选修课。</td>
		</tr>
		<tr>
			<td></td>
			<td>2.授课主要对象分为：博士研究生、硕士研究生、本科生、不限。</td>
		</tr>
		<tr>
			<td></td>
			<td>3.此表中主讲课程应该每年单独进行填报，“学期”每年限制下拉两项，如2014年则为2014-2015学年第一学期、2014-2015学年第二学期。</td>
		</tr>
	</table>