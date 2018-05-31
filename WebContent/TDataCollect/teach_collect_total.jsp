<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="con_header inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="icon icon-disciplinecollect"></span>
				<span class="TextFont">教师成果简况信息</span>
			</td>
		</tr>
	</table>
</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">科研获奖部分</span>
			</td>
			<td>&nbsp&nbsp&nbsp&nbsp&nbsp</td>
			<td>
				<span class="TextFont">项目类别：</span>
				<select>
					<option>全部</option>
					<option>国防971计划</option>
					<option>自然科学基金</option>
					<option>国防971计划</option>
					<option>军队工程项目</option>
				</select>
			</td>
			<td>&nbsp&nbsp&nbsp</td>
			<td>
				<span class="TextFont">项目下达单位：</span>
				<select>
					<option>全部</option>
					<option>教育部</option>
					<option>军工部</option>
					<option>工信部</option>
				</select>
			</td>
			<td>&nbsp&nbsp&nbsp</td>
			<td>
				<span class="TextFont">项目开始时间：</span>
				<input type="text" />
			</td>
			<td>&nbsp&nbsp&nbsp</td>
			<td>
				<span class="TextFont">项目结束时间：</span>
				<input type="text" />
			</td>
		</tr>
	</table>
	<table class="layout_table left">
			<tr>
			<td>
				<span class="TextFont">发表论文部分</span>
			</td>
			<td>&nbsp&nbsp&nbsp&nbsp&nbsp</td>
			<td>
				<span class="TextFont">论文收录类型：</span>
				<select>
					<option>全部</option>
					<option>SCI</option>
					<option>EI</option>
					<option>CSCD</option>
					<option>其他</option>
				</select>
			</td>
			<td>&nbsp&nbsp&nbsp</td>
			<td>
				<span class="TextFont">发表年月在-与-之间</span>
				<input type="text" />-<input type="text" />
			</td>
		</tr>
		</table>
		<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">授权专利部分</span>
			</td>
			<td>&nbsp&nbsp&nbsp&nbsp&nbsp</td>
			<td>
				<span class="TextFont">专利类别：</span>
				<select>
					<option>全部</option>
					<option>发明专利</option>
					<option>外观专利</option>
					<option>实用型专利</option>
					<option>军事专利</option>
				</select>
			</td>
			<td>&nbsp&nbsp&nbsp</td>
			<td>
				<span class="TextFont">专利颁授年月在-与-之间</span>
				<input type="text">-<input type="text">
			</td>
		</tr>
		</table>
		<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">出版著作部分</span>
			</td>
			<td>&nbsp&nbsp&nbsp&nbsp&nbsp</td>
			<td>
				<span class="TextFont">著作类别：</span>
				<select>
					<option>全部</option>
					<option>专著</option>
					<option>编著</option>
					<option>译著</option>
					<option>国家级规划教材</option>
				</select>
			</td>
			<td>&nbsp&nbsp&nbsp</td>
			<td>
				<span class="TextFont">出版类型：</span>
				<select>
					<option>全部</option>
					<option>首次出版</option>
					<option>重印</option>
					<option>再版</option>
				</select>
			</td>
			<td>&nbsp&nbsp&nbsp</td>
			<td>
				<span class="TextFont">出版年月在-与-之间</span>
				<input type="text" >-<input type="text" >
			</td>
			<td>&nbsp&nbsp&nbsp</td>
			<td>
				 <a id="search_list" class="button" href="#"><span class="icon icon-search "></span>查找</a >
			</td>
		</tr>
	</table>
</div>
<div class="selectbar layout_holder">
     <div class="table">
		<table id="teach_KYHJ_tb"></table>
		<div id="pager_teach_KYHJ_tb"></div>
	</div>
	<div class="table">
		<table id="teach_FBLW_tb"></table>
		<div id="pager_teach_FBLW_tb"></div>
	</div>
	<div class="table">
		<table id="teach_SQZL_tb"></table>
		<div id="pager_teach_SQZL_tb"></div>
	</div>
	<div class="table">
		<table id="teach_CBZZ_tb"></table>
		<div id="pager_teach_CBZZ_tb"></div>
	</div>
</div>
<div hidden="true">
	<div id="dialog-confirm" title="警告"></div>
</div>
<script src="${ContextPath}/js/jquery.common.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$( "input[type=submit], a.button , button" ).button();
	/********************  jqGrid ***********************/
	var kyhjData=[
	              {"xmxddw":"科技部","xmlb":"国家一般课题","xmbh":"0948686","xmmc":"自动化无人机","xmcddw":"北航","xmksny":"201309","xmjsny":"201609"},
	              {"xmxddw":"科技部","xmlb":"国家一般课题","xmbh":"0948686","xmmc":"自动化无人机","xmcddw":"北航","xmksny":"201309","xmjsny":"201609"},
	              {"xmxddw":"科技部","xmlb":"国家一般课题","xmbh":"0948686","xmmc":"自动化无人机","xmcddw":"北航","xmksny":"201309","xmjsny":"201609"},
	              {"xmxddw":"科技部","xmlb":"国家一般课题","xmbh":"0948686","xmmc":"自动化无人机","xmcddw":"北航","xmksny":"201309","xmjsny":"201609"},
	              {"xmxddw":"科技部","xmlb":"国家一般课题","xmbh":"0948686","xmmc":"自动化无人机","xmcddw":"北航","xmksny":"201309","xmjsny":"201609"},
	              {"xmxddw":"科技部","xmlb":"国家一般课题","xmbh":"0948686","xmmc":"自动化无人机","xmcddw":"北航","xmksny":"201309","xmjsny":"201609"},
				];
	//科研项目
	$("#teach_KYHJ_tb").jqGrid({
		datatype: "local",
		data:kyhjData,
		colNames:['项目下达单位','项目类别','项目编号','项目名称','项目承担单位','项目开始年月','项目结束年月'],
		colModel:[
			{name:'xmxddw',align:"center", width:100,editable:true},
			{name:'xmlb',align:"center", width:100,editable:true},
			{name:'xmbh',align:"center", width:100,editable:true},
			{name:'xmmc',align:"center", width:100,editable:true},
			{name:'xmcddw',align:"center", width:100,editable:true},
			{name:'xmksny',align:"center", width:100,editable:true},
			{name:'xmjsny',align:"center", width:100,editable:true},
		],
		rownumbers: true,
		height:"100%",
		autowidth:true,
		pager: '#pager_teach_KYHJ_tb',
		rowNum:20,
		rowList:[20,30,40],
		viewrecords: true,
		caption: "科研获奖"
	}).navGrid('#teach_KYHJ_tb',{edit:false,add:false,del:false});	
	
	var fblwData=[{"fbqk":"无人机技术","issnh":"4856860","fbny":"201309","lwmc":"无人机安全","sllx":"SCI","dyzz":"张三","brpx":"2(2)"},
	              {"fbqk":"无人机技术","issnh":"4856860","fbny":"201309","lwmc":"无人机安全","sllx":"SCI","dyzz":"张三","brpx":"2(2)"},
	              {"fbqk":"无人机技术","issnh":"4856860","fbny":"201309","lwmc":"无人机安全","sllx":"SCI","dyzz":"张三","brpx":"2(2)"},
	              {"fbqk":"无人机技术","issnh":"4856860","fbny":"201309","lwmc":"无人机安全","sllx":"SCI","dyzz":"张三","brpx":"2(2)"},
	              {"fbqk":"无人机技术","issnh":"4856860","fbny":"201309","lwmc":"无人机安全","sllx":"SCI","dyzz":"张三","brpx":"2(2)"},
	              {"fbqk":"无人机技术","issnh":"4856860","fbny":"201309","lwmc":"无人机安全","sllx":"SCI","dyzz":"张三","brpx":"2(2)"},
	              ];
	$("#teach_FBLW_tb").jqGrid({
		datatype: "local",
		data:fblwData,
		colNames:['发表期刊（会议名称）','ISSN号','发表年月','论文名称','收录类型','论文第一作者','本人排序'],
		colModel:[
			{name:'fbqk',align:"center", width:100,editable:true},
			{name:'issnh',align:"center", width:100,editable:true},
			{name:'fbny',align:"center", width:100,editable:true},
			{name:'lwmc',align:"center", width:100,editable:true},
			{name:'sllx',align:"center", width:100,editable:true},
			{name:'dyzz',align:"center", width:100,editable:true},
			{name:'brpx',align:"center", width:100,editable:true},
		],
		rownumbers: true,
		height:"100%",
		autowidth:true,
		pager: '#pager_teach_KYHJ_tb',
		rowNum:20,
		rowList:[20,30,40],
		viewrecords: true,
		caption: "发表论文"
	}).navGrid('#teach_FBLW_tb',{edit:false,add:false,del:false});	
	
	var sqzlData=[{'zlsqdqhgj':'中国','zllx':'软件使用技术','zlh':'849585','zlmc':'无人飞机的导航','dyfmr':'张三','zlqrxm':'李四','zlsqsj':'201309'},
	              {'zlsqdqhgj':'中国','zllx':'软件使用技术','zlh':'849585','zlmc':'无人飞机的导航','dyfmr':'张三','zlqrxm':'李四','zlsqsj':'201309'},
	              {'zlsqdqhgj':'中国','zllx':'软件使用技术','zlh':'849585','zlmc':'无人飞机的导航','dyfmr':'张三','zlqrxm':'李四','zlsqsj':'201309'},
	              {'zlsqdqhgj':'中国','zllx':'软件使用技术','zlh':'849585','zlmc':'无人飞机的导航','dyfmr':'张三','zlqrxm':'李四','zlsqsj':'201309'},
	              {'zlsqdqhgj':'中国','zllx':'软件使用技术','zlh':'849585','zlmc':'无人飞机的导航','dyfmr':'张三','zlqrxm':'李四','zlsqsj':'201309'}]
	$("#teach_SQZL_tb").jqGrid({
		datatype: "local",
		data: sqzlData,
		colNames:['专利授权地区或国家','专利类别','专利号','专利名称','第一发明人','专利权人姓名','专利授权年月'],
		colModel:[
			{name:'zlsqdqhgj',align:"center", width:100,editable:true},
			{name:'zllx',align:"center", width:100,editable:true},
			{name:'zlh',align:"center", width:100,editable:true},
			{name:'zlmc',align:"center", width:100,editable:true},
			{name:'dyfmr',align:"center", width:100,editable:true},
			{name:'zlqrxm',align:"center", width:100,editable:true},
			{name:'zlsqsj',align:"center", width:100,editable:true},
		],
		rownumbers: true,
		height:"100%",
		autowidth:true,
		pager: '#pager_teach_KYHJ_tb',
		rowNum:20,
		rowList:[20,30,40],
		viewrecords: true,
		caption: "授权专利"
	}).navGrid('#teach_SQZL_tb',{edit:false,add:false,del:false});	
	/**出版专著
	*/
	var cbzzData = [{'zzmc':'软件建模过程','zzlx':'编著','ISBNH':'85958585','dizz':'张三','brpx':'2(1)','brjs':'主编','cblx':'首次出版'},
	            {'zzmc':'软件建模过程','zzlx':'编著','ISBNH':'85958585','dizz':'张三','brpx':'2(1)','brjs':'主编','cblx':'首次出版'},
	            {'zzmc':'软件建模过程','zzlx':'编著','ISBNH':'85958585','dizz':'张三','brpx':'2(1)','brjs':'主编','cblx':'首次出版'},
	            {'zzmc':'软件建模过程','zzlx':'编著','ISBNH':'85958585','dizz':'张三','brpx':'2(1)','brjs':'主编','cblx':'首次出版'},
	            {'zzmc':'软件建模过程','zzlx':'编著','ISBNH':'85958585','dizz':'张三','brpx':'2(1)','brjs':'主编','cblx':'首次出版'},
	            {'zzmc':'软件建模过程','zzlx':'编著','ISBNH':'85958585','dizz':'张三','brpx':'2(1)','brjs':'主编','cblx':'首次出版'}]
	$("#teach_CBZZ_tb").jqGrid({
		datatype: "local",
		data: cbzzData,
		colNames:['专著名称','著作类型','ISBN号','第一作者','本人排序','本人角色','出版类型'],
		colModel:[
			{name:'zzmc',align:"center", width:100,editable:true},
			{name:'zzlx',align:"center", width:100,editable:true},
			{name:'ISBNH',align:"center", width:100,editable:true},
			{name:'dizz',align:"center", width:100,editable:true},
			{name:'brpx',align:"center", width:100,editable:true},
			{name:'brjs',align:"center", width:100,editable:true},
			{name:'cblx',align:"center", width:100,editable:true},
		],
		rownumbers: true,
		height:"100%",
		autowidth:true,
		pager: '#pager_teach_KYHJ_tb',
		rowNum:20,
		rowList:[20,30,40],
		viewrecords: true,
		caption: "出版著作"
	}).navGrid('#teach_CBZZ_tb',{edit:false,add:false,del:false});	

});
</script>