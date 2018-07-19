<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${ContextPath}/js/jquery.common.js"></script>
<script src="${ContextPath}/js/jquery.form.min.js"></script>
<script src="${ContextPath}/js/CollectMeta/Oper/collect_bk_oper.js"></script>
<script src="${ContextPath}/js/CollectMeta/Controllers/date_controller.js"></script>
<script src="${ContextPath}/js/CollectMeta/Controllers/yearMonth_controller.js"></script>
<script src="${ContextPath}/js/CollectMeta/Controllers/percent_controller.js"></script>
<script src="${ContextPath}/js/CollectMeta/Rule/logicchecknumberrules.js"></script>
<script src="${ContextPath}/js/CollectMeta/Rule/initRules.js"></script>
<script src="${ContextPath}/js/CollectMeta/Rule/logicchecklogisticrules.js"></script>
<script src="${ContextPath}/js/CollectMeta/Rule/logiccheckdaterules.js"></script>
<script src="${ContextPath}/js/CollectMeta/Rule/logiccheckstringrules.js"></script>
<script src="${ContextPath}/js/CollectMeta/Rule/jscheckfuncsfactory.js"></script>
<script src="${ContextPath}/js/CollectMeta/Rule/checkentitycount.js"></script>
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script type="text/javascript">
	var jqGridConfig;//存贮jqgrid的配置信息
	var lastsel;//最后选择行号
	var titleValues;//表的可编辑列名
	var tableId;//实体表的Id
	var tableName;//实体中文名
	var titleNames=new Array();//实体header名
	var controllerDic;
	var primaryKey;//条目的主键
	var seqNo;//序号
	var records;//总记录数
	var sortorder;//排序方式
	var sortname;//排序字段
	var jsCheckfunParamsDic;//前台js验证函数参数及其值的字典
	var jsCheckFunNameDic;//
	var contextPath="${ContextPath}";//绝对路径
	var maxNum;//记录的最大条数
	var viewType;//页面显示类型
	var roundStatus;//当前公示轮次的状态
	var isSelect=true;
	var versionId="${currentRound.backupVersionId}";
	var dataUrl='';
	var batchUrl="";
	var sortUrl="";
	var unPrepubUrl = "${ContextPath}/publicity/prepub/JqOper/unprepub_data";
	var prepubUrl = "${ContextPath}/publicity/prepub/JqOper/prepub_data";
	/* var delPrefixUrl = "${ContextPath}/publicity/prepub/JqOper/backupEdit"; */
	var isAllData = true;//是所有的数据
	var rowNumber = false;//是否显示默认序号
	var prepubStatus = "1";
	function initRoundStatus(round_status){
		roundStatus = round_status+"";
	}
	
	
	function loadJqTable(data,dataUrl){
		$("#jqGrid_collect_tb").jqGrid({
			url : dataUrl,
			datatype : 'json',
			mtype : 'POST',
			colModel : data.colConfigs,
			height : "100%",
			autowidth : true,
			shrinkToFit : false,
			pager : '#pager_collect_tb',
			pgbuttons : true,
			rowNum : 10,
			rowList : [ 10, 20, 30 ],
			viewrecords : true,
			rownumbers:rowNumber,
			multiselect: isSelect,
			sortname : data.defaultSortCol,
			sortorder : "asc",
			caption : data.name,
			jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
				root : "rows", //包含实际数据的数组  
				page : "pageIndex", //当前页  
				total : "totalPage",//总页数  
				records : "totalCount", //查询出的记录数  
				repeatitems : false,
			},
			loadComplete : function() {
				//$("#jqGrid_collect_tb").setGridWidth(
					//	$("#content").width());
			},
			prmNames : {
				page : "page",
				rows : "rows",
				sort : "sidx",
				order : "sord"
			},
			onSelectRow:function(rowid){
			    
			},
			gridComplete: function(){
				bindFunc(isEditable);
				if(isEditable=='1'){
					addJQEditBt();
				}else{
					jQuery("#jqGrid_collect_tb").setGridParam().hideCol("oper");
				}
				isShowButton();
				sortorder=$("#jqGrid_collect_tb").jqGrid('getGridParam','sortorder');//排序方式
				sortname=$("#jqGrid_collect_tb").jqGrid('getGridParam','sortname');//排序字段	
			}}).navGrid('#pager_collect_tb',{
				edit : false,
				add : false,
				del : false,search:false
		});
	}
	
	//查询功能
	function searchCollectItem(viewType){
		var itemId='';
		if(viewType=='JQGRID'){
			itemId='jqGrid_collect_tb';
		}else{
			itemId='jqGrid_collect_fm';
		}
		isAllData=false;
		$("#"+itemId).jqGrid('searchGrid', {
			modal:true,
			caption: "查找",  
	        Find: "查询",  
	        closeAfterSearch: true,  
	        multipleSearch: true,  
	        multipleGroup: true,
	       // showQuery: true,
	        groupOps: [{ op: "AND", text: "全部" },{ op:"OR",text: "任何"}]
		});
	}
	
	//初始化获取数据的连接
	function bindFunc(isEditable)
	{
		
	}
	//初始化显示的列
	function initCols(data){
		
			$.each(data.colConfigs,function(i,item){
				if(item.name=='UNIT_ID'){
					item.editable=false;
					item.hidden=false;
				}
				if(item.name=='DISC_ID'){
					item.editable=false;
					item.hidden=false;
				}
			});
	}
	//批量添加
	function batch_Add_Dialog(jqGridConfig)
	{
		batch_Dialog(jqGridConfig);	//此函数在collect_batch_add_view.jsp页面
	}
	function renderPreJqGrid(data)
	{  
		viewType= data.type;
		initCols(data);
		renderCollectComment("collect_comment",data.memo);
		controllerDic= data.idsOfControlDic;
		jsCheckFunNameDic = data.jsCheckFunNameDic;
		jsCheckfunParamsDic = data.jsCheckParamsAndValueDic;
		initRules(data);
		jqGridConfig=data;
		data=addEditCol(data);
		$("#jqGrid_collect_tb").jqGrid('GridUnload');
		tableId= data.id;
		tableName=data.name;
		titleValues = data.editableColIds;
		maxNum=data.maxNum;
		var catId = $("#ddl_disc_category").val();
		dataUrl='${ContextPath}/publicity/prepub/CollectData/collectionData/'+tableId+'/'+versionId+'/'+catId;
		if(viewType=='JQFORM')
		{	//显示采集表单
			$("#gview_jqGrid_collect_fm").show();
			$("#collect_comment").show();
			$("#gview_jqGrid_collect_tb").hide();
			$(".collect_oper_bts").hide();	
			$("#ckeditor_Form").hide();
			initJqForm(data);
		}else if(viewType='JQGRID'||viewType=='CKFORM'){
			$("#gview_jqGrid_collect_tb").show();
			$("#collect_comment").show();
			$("#pager_collect_tb").show();
			$("#gview_jqGrid_collect_fm").hide();
			$(".collect_oper_bts").hide();	
			$("#ckeditor_Form").hide();
			loadJqTable(data,dataUrl);
		}
	}
	/*动态添加编辑列*/
	function addEditCol(data)
	{
		var editCol={  
			  label : '操作',  
			  name : 'oper',  
			  sortable : false,  
			  width : 80,  
			  fixed : false,  
			  align : "center",
			  search:false,
		};
		data.colConfigs.unshift(editCol);
		return data;
	
	}
	/**
	 * 添加操作按钮，不同用途的数据操作按钮由此添加
	 */
	function addJQEditBt(){
		if( roundStatus == "1"){
			jQuery("#jqGrid_collect_tb").setGridParam().showCol("oper");
		}
		else{
			jQuery("#jqGrid_collect_tb").setGridParam().hideCol("oper");
		}	
		var ids = jQuery("#jqGrid_collect_tb").jqGrid('getDataIDs');
		if( prepubStatus == "1"){
			for(var i=0;i < ids.length;i++){
				//var edit_link = "<a class='' href='#' onclick='editCollectItem("+ids[i]+","+'0'+")'>编辑</a>";
				//delCollectItem函数位于/js/CollectMeta/Oper/collect_bk_oper.js中
				var del_link="<a class='' href='#' onclick='notPubCollectItem("+ids[i]+","+'0'+")'>不公示</a>";
				jQuery("#jqGrid_collect_tb").jqGrid('setRowData',ids[i],{oper :del_link});
			}
		}
		else{
			for(var i=0;i < ids.length;i++){
				//var edit_link = "<a class='' href='#' onclick='editCollectItem("+ids[i]+","+'0'+")'>编辑</a>";
				//delCollectItem函数位于/js/CollectMeta/Oper/collect_bk_oper.js中
				var del_link="<a class='' href='#' onclick='pubCollectItem("+ids[i]+","+'0'+")'>公示</a>";
				jQuery("#jqGrid_collect_tb").jqGrid('setRowData',ids[i],{oper :del_link});
			}
		}
		
	}
	
	function addJFEditBt(){
		if( roundStatus == "1"){
			jQuery("#jqGrid_collect_fm").setGridParam().showCol("oper");
		}
		else{
			jQuery("#jqGrid_collect_fm").setGridParam().hideCol("oper");
		}
		var ids = jQuery("#jqGrid_collect_fm").jqGrid('getDataIDs');
		if( prepubStatus == "1"){
			for(var i=0;i < ids.length;i++){
				//var edit_link = "<a class='' href='#' onclick='editCollectItem("+ids[i]+","+'1'+")'>编辑</a>";
				//delCollectItem函数位于/js/CollectMeta/Oper/collect_bk_oper.js中
				var del_link="<a class='' href='#' onclick='notPubCollectItem("+ids[i]+","+'1'+")'>不公示</a>";
				jQuery("#jqGrid_collect_fm").jqGrid('setRowData',ids[i],{oper :del_link});
			}	
		}
		else{
			for(var i=0;i < ids.length;i++){
				//var edit_link = "<a class='' href='#' onclick='editCollectItem("+ids[i]+","+'1'+")'>编辑</a>";
				//delCollectItem函数位于/js/CollectMeta/Oper/collect_bk_oper.js中
				var del_link="<a class='' href='#' onclick='pubCollectItem("+ids[i]+","+'1'+")'>公示</a>";
				jQuery("#jqGrid_collect_fm").jqGrid('setRowData',ids[i],{oper :del_link});
			}
		}
	}
	
	function renderCollectComment(elementId,content)
	{
		$('#'+elementId).empty();
		var html = $.parseHTML( content );
		$('#'+elementId).append(html);
	}
	
	//是否显示公示和不公示的按钮
	function isShowButton(){
		if(viewType=='JQFORM' || roundStatus != '1'){
			$("#prepub_collect_tb").hide();
			$("#unprepub_collect_tb").hide();
		}
		else{
			if( prepubStatus == "1"){
				$("#prepub_collect_tb").hide();
				if($("#jqGrid_collect_tb").jqGrid('getGridParam','records') > 0)
					$("#unprepub_collect_tb").show();
				else
					$("#unprepub_collect_tb").hide();
			}
			else{
				$("#unprepub_collect_tb").hide();
				if($("#jqGrid_collect_tb").jqGrid('getGridParam','records') > 0)
					$("#prepub_collect_tb").show();
				else
					$("#prepub_collect_tb").hide();
			}	
		}
	}
	
	//加载数据表格
	function reloadJqgrid(){
		var catId = $("#ddl_disc_category").val();
		if(viewType=='JQFORM'){
			$("#jqGrid_collect_fm").setGridParam({
				url:'${ContextPath}/publicity/prepub/CollectData/collectionData/'+tableId+'/'+versionId+'/'+catId
			}).trigger("reloadGrid");
		}
		else if(viewType='JQGRID'||viewType=='CKFORM'){
			$("#jqGrid_collect_tb").setGridParam({
				url:'${ContextPath}/publicity/prepub/CollectData/collectionData/'+tableId+'/'+versionId+'/'+catId
			}).trigger("reloadGrid");
		}
	}
	
	//获取所有被选中的行的主键数组
	function getSelectPrimaryKey(){
		var rowids = $("#jqGrid_collect_tb").jqGrid('getGridParam','selarrrow');
		var ids = [];
		$.each(rowids,function(i,value){
			ids.push($("#jqGrid_collect_tb").getCell(value,'ID'));
		});
		return ids;
	}
	
	//标签页的获取焦点事件
	$('#tab_status').children().focus(function() {
		if( $(this).attr("id") == "1" ){//标签页的状态
			prepubStatus = "1";
			if(versionId.substring(0,1) == "N")
				versionId = versionId.substring(1,versionId.length);
		}
		else{
			prepubStatus = "0";
			if(versionId.substring(0,1) != "N")
				versionId = "N"+versionId;
		}
		reloadJqgrid();//重新加载表格数据
	});
	
	//标签页的点击事件
	$('#tab_status').children().click(function(){
		if( $(this).attr("id") == "1" ){//标签页的状态
			prepubStatus = "1";
			if(versionId.substring(0,1) == "N")
				versionId = versionId.substring(1,versionId.length);
		}
		else{
			prepubStatus = "0";
			if(versionId.substring(0,1) != "N")
				versionId = "N"+versionId;
		}
		reloadJqgrid();//重新加载表格数据
	});
	
	//点击按钮是否起作用
	function isClickOk(){
		if( tableId != '' && tableId != null && tableId != "undefined" && $("#jqGrid_collect_tb").jqGrid('getGridParam','records') > 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	$(document).ready(function(){
		$( "input[type=submit], a.button , button" ).button(); 	
		$("#gview_jqGrid_collect_tb").hide();//id为jqgrid根据<table Id="">自动生成
		$("#gview_jqGrid_collect_fm").hide();
		$("#collect_Mul_Search").click(function(){
			console.log('search!');
			searchCollectItem(viewType);
		});
		
		$(".tabs").tabs({//生成标签页
			beforeLoad : function(event, ui) {
				event.preventDefault();
				return;
			},
			create : function(event, ui) {
				event.preventDefault();
				return;
			},
			load : function(event, ui) {
				event.preventDefault();
				return;
			}
		});
		
		//不公示整个采集项
		$("#unprepub_collect_item").click(function(){
			if( isClickOk() ){
				$.ajax({
					url:'${ContextPath}/publicity/prepub/JqOper/unprepub_wholeEntity',   
	                type:"POST",   
	                data:"versionId="+versionId+"&entityId="+tableId,   
	                success : function(data) {   
	                	console.log(data);
	                    if(data == true) {   
	                        alert("采集项不公示成功");
	                        reloadJqgrid();
	                    }   
	                    else{
	                    	alert("采集项不公示失败");
	                    	/* alert("异议添加失败"); */
	                    }
	                },   
	                error : function(data) {   
	                	/* alert('test'+ " "+data); */
	                    alert("出现异常错误");
	                }
				});
			}
		});
		
		//公示整个采集项
		$("#prepub_collect_item").click(function(){
			if( isClickOk() ){
				$.ajax({
					url:'${ContextPath}/publicity/prepub/JqOper/prepub_wholeEntity',   
	                type:"POST",   
	                data:"versionId="+versionId+"&entityId="+tableId,   
	                success : function(data) {   
	                	console.log(data);
	                    if(data == true) {   
	                        alert("采集项公示成功");
	                        reloadJqgrid();
	                    }   
	                    else{
	                    	alert("采集项公示失败");
	                    	reloadJqgrid();
	                    	/* alert("异议添加失败"); */
	                    }
	                },   
	                error : function(data) {   
	                	/* alert('test'+ " "+data); */
	                    alert("出现异常错误");
	                }
				});
			}
		});
		
		//不公示选中的数据项
		$("#unprepub_data_item").click(function(){
			if( isClickOk() ){
				var ids = getSelectPrimaryKey();
				if( ids.length == 0 )
					return;
				$.ajax({
					url:'${ContextPath}/publicity/prepub/JqOper/unprepub_dataList',   
	                type:"POST",   
	                data:"versionId="+versionId+"&entityId="+tableId+"&idList="+ids,   
	                success : function(data) {   
	                	console.log(data);
	                    if(data == true) {   
	                        alert("采集项不公示成功");
	                        reloadJqgrid();
	                    }   
	                    else{
	                    	alert("采集项不公示失败");
	                    	reloadJqgrid();
	                    	/* alert("异议添加失败"); */
	                    }
	                },   
	                error : function(data) {   
	                	/* alert('test'+ " "+data); */
	                    alert("出现异常错误");
	                }
				});
			}
		});
		
		//公示选中的数据项
		$("#prepub_data_item").click(function(){
			if( isClickOk() ){
				var ids = getSelectPrimaryKey();
				if( ids.length == 0 ){
					return ;
				}
				$.ajax({
					url:'${ContextPath}/publicity/prepub/JqOper/prepub_dataList',   
	                type:"POST",   
	                data:"versionId="+versionId+"&entityId="+tableId+"&idList="+ids,   
	                success : function(data) {   
	                	console.log(data);
	                    if(data == true) {   
	                        alert("采集项公示成功");
	                        reloadJqgrid();
	                    }   
	                    else{
	                    	alert("采集项公示失败");
	                    	reloadJqgrid();
	                    	/* alert("异议添加失败"); */
	                    }
	                },   
	                error : function(data) {   
	                	/* alert('test'+ " "+data); */
	                    alert("出现异常错误");
	                }
				});
			}
		});
	});

</script>
<div id ="jq_collect_parent" class="selectbar right_block" hidden="true">
	<%-- <jsp:include page="/CollectMeta/collect_oper_banner.jsp"></jsp:include> --%>
	<div id='jqgird_oper' class="inner_table_holder">
		<table class="left">
	     	<tr>
	     		<td>
	     			<a id="collect_Mul_Search" class="button" href="#">
		         		<span class="icon icon-search"></span>查询</a>
	     		</td>
	     	</tr>
	     </table>
	     <table class="right" id="unprepub_collect_tb">
	     	<tr>
	     		<td>
	     			<a id="unprepub_collect_item" class="button" href="#">
		         		<span class="icon icon-del"></span>不公示所有数据项</a>
		         	&nbsp;
		         	<a id="unprepub_data_item" class="button" href="#">
		         		<span class="icon icon-del"></span>不公示所选数据项</a>
	     		</td>
	     	</tr>
	     </table>
	     <table class="right" id="prepub_collect_tb">
	     	<tr>
	     		<td>
	     			<a id="prepub_collect_item" class="button" href="#">
		         		<span class="icon icon-del"></span>公示所有数据项</a>
		         	&nbsp;
		         	<a id="prepub_data_item" class="button" href="#">
		         		<span class="icon icon-del"></span>公示所选数据项</a>
	     		</td>
	     	</tr>
	     </table>
	</div>
	<div id="jq_collect_table" class="tabs">
		<ul id="tab_status">
			<li id="1">
				<a id="1" class="objectLink" href="#" >公示的数据</a>
			</li>
			<li id="0">
				<a id="0" class="objectLink" href="#" >不公示的数据</a>
			</li>
		</ul>
		<jsp:include page="/CollectMeta/collect_jqgrid.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/collect_jqform.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/collect_ckeditor.jsp"></jsp:include>
		
	</div>
	<div id="collect_comment" class="comment"></div>
	<div hidden="true">
		<jsp:include page="/CollectMeta/collect_batch_add_view.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/collect_import_excel.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/collect_sort.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/yyMMeditor.jsp"></jsp:include>
	</div>
	<div id="percent_dialog_div" class="percentdialog_div" style="display:none"> 
    </div>
    <jsp:include page="/export_download_div.jsp"></jsp:include>
    <div id="uploadFile" style="display:none">
    	<form id="uploadFileForm" method="post" action="javascript:;" enctype="multipart/form-data">
            <input type="file" name="file" style="margin-bottom:10px;width:200px;"/>
            <input type="submit" style="display:block;" value="确认导入"/>
        </form>
        <br/><hr><br/>
        <p><font color="red">请确认已下载对应模板（勿修改）</font></p><br/>
        <a href=""></a>
    </div>
    <div hidden="true">
    	<div id="dialog-confirm" title="警告"></div>
    </div>
</div>