<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-cn" lang="zh-cn">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
	<meta http-equiv="Content-Language" content="zh-cn"/>
	<meta name="author" content="DSEP"/>
	<title>附件一</title>
	<style type="text/css" media="print">
	/* Page Definitions Start */
	@page{
		size:a4 landscape;
	}
	/* Page Definitions End */

	/* font-family Definition */
	body{
		font-family:SimHei;
	}
	.simHei{
		font-family:SimHei;
	}
    .simSun{
		font-family:SimSun;
	}
    .fangSong{
		font-family:FangSong;
	}
    .kaiTi{
		font-family:KaiTi;
	}
	/*font-size Definition*/
	.chu{
		font-size:42.0pt;
	}
	.xiaoChu{
		font-size:36.0pt;
	}
	.yiHao{
		font-size:26.0pt;
	}
	.xiaoYi{
		font-size:24.0pt;
	}
	.erHao{
		font-size:22.0pt;
	}
	.xiaoEr{
		font-size:18.0pt;
	}
	.sanHao{
		font-size:16.0pt;
	}
	.xiaoSan{
		font-size:15.0pt;
	}
	.siHao{
		font-size:14.0pt;
	}
	.xiaoSi{
		font-size:12.0pt;
	}
	.wuHao{
		font-size:10.5pt;
	}
	.xiaoWu{
		font-size:9.0pt;
	}
	.liuHao{
		font-size:7.5pt;
	}
	.xiaoLiu{
		font-size:6.5pt;
	}
	.qiHao{
		font-size:5.5pt;
	}
	.baHao{
		font-size:5.0pt;
	}
	/*文档内容样式*/
		table {
		-fs-table-paginate: paginate;/*设置表头thead分页时不直接断*/
		border: solid 0.5pt;
		border-spacing: 0;
		table-layout: fixed;
	}
	/*The table had a very bad looking break after a page, so I added also this*/
	tr{
		page-break-inside:avoid;
	}
	td{
		border: solid 0.5pt;
		padding: 0;
		text-align:center;
		align:center;
	}
	table caption{
		/*line-height:12.0pt;*/
		font-family:SimSun;
		caption-side:top;
		align:center;
		font-weight:bold;
		margin-bottom:12pt;
		margin-top:28pt;
	}
	table.shuoMingTable{
		border:0;
		padding:0;
		margin:5pt 0 0 0;
	}
	table.shuoMingTable tr{
		border:0;
		padding:0;
		margin:0;
	}
	table.shuoMingTable tr td:last-child{
		width:25.1cm;
	}
	table.shuoMingTable tr td{
		font-family:KaiTi;
		font-size:10.5pt;
		border:0;
		padding:0;
		margin:0;
		word-break: break-all;
		vertical-align:top;
		text-align:left;
	}
	.bookTitle{
		font-family:SimHei;
		font-size:15pt;
		font-weight:bold;
		text-align:center;
		align:center;
		margin-bottom:20pt;
	}
</style>
</head>

<body>
 <div>
	<div>
		<p class="bookTitle">教师成果信息简况表</p>	
		<!-- 表1  科研项目-->
		<#include "/teacher/XKYXM.ftl">
		<!-- 表2  发表论文-->
		<#include "/teacher/XFBLW.ftl">
		<!-- 表3  授权专利-->
		<#include "/teacher/XSQZL.ftl">
		<!-- 表4  出版著作（含教材）-->
		<#include "/teacher/XCBZZ.ftl">
		<!-- 表5  精品开放课程-->
		<#include "/teacher/XJPKFKC.ftl">
		<!-- 表6  获奖情况-->
		<#include "/teacher/XHJQK.ftl">
		<!-- 表7  参加国际、国内学术会议的交流合作情况-->
		<#include "/teacher/XCJHYJLHZQK.ftl">
		<!-- 表8  策划、举办或参加重要展览、演出活动-->
		<#include "/teacher/XZYZLYCHD.ftl">
		<!-- 表9  主讲课程情况-->
		<#include "/teacher/ZJKCQK.ftl">
		<!-- 表10  指导全日制研究生情况-->
		<#include "/teacher/ZDYJSQK.ftl">
	</div>
 </div>
</body>
</html>
