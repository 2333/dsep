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
	var attrId;//主属性
	var seqNo;//序号
	var records;//总记录数
	var sortorder;//排序方式
	var sortname;//排序字段
	var jsCheckfunParamsDic;//前台js验证函数参数及其值的字典
	var jsCheckFunNameDic;//
	var contextPath="${ContextPath}";//绝对路径
	var isSelect = false;
	var maxNum;//记录的最大条数
	var viewType;//页面显示类型
	var versionId;//数据版本号
	var openStatus;//批次的状态，1为开启，0为关闭
	var dataUrl='';
	var batchUrl="";
	var sortUrl="";
	var delPrefixUrl="${ContextPath}/publicity/prepub/JqOper/backupEdit";
	var isAllData = true;//是所有的数据
	var rowNumber= false;//显示默认的序号
	var catId="";//门类Id
	var unitId="";//学校Id
	var discId="";//学科Id
	/**
	 * 初始化数据版本号和公示批次的开启状态，查看公示页面调用此函数
	 */
	function initRoundMessage(version_id,open_status){
		versionId = version_id;
		openStatus = open_status;
	}
	//初始化门类Id
	function initCatId(cat_id){
		catId= cat_id;
	}
	//初始化学校和学科ID
	function initDiscId(disc_id){
		discId = disc_id;
	}
	
	function initUnitId(unit_id){
		unitId = unit_id;
	}
	
	//初始化dataUrl
	function initDataUrl(){
		dataUrl='${ContextPath}/publicity/viewPub/CollectData/collectionData/'+tableId+'/'+versionId+'/';
		if(catId!=""){
			dataUrl+=catId;
		}else{
			dataUrl+=unitId+'/'+discId;
		}
		dataUrl += "?currentRoundId="+$("#ddl_publicity_round").val();
		
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
			if(item.name=="SEQ_NO"){
				item.width=50;
			}
		});
	}

	function loadJqTable(data,dataUrl)
	{
		console.log(data.colConfigs);
		$("#jqGrid_collect_tb").jqGrid({
			url : dataUrl,
			datatype : 'json',
			mtype : 'POST',
			colModel : data.colConfigs,
			height : "100%",
			autowidth : false,
			shrinkToFit:false,
			pager : '#pager_collect_tb',
			pgbuttons : true,
			rowNum : 20,
			rowList : [ 20, 30, 40 ],
			viewrecords : true,
			rownumbers:rowNumber,
			multiselect: isSelect,
			sortname : data.defaultSortCol,
			sortorder : "asc",
			caption : data.name,
			loadComplete : function() {
				$("#jqGrid_collect_tb").setGridWidth(
						$("#jq_collect_table").width()-5);
			},
			jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
				root : "rows", //包含实际数据的数组  
				page : "pageIndex", //当前页  
				total : "totalPage",//总页数  
				records : "totalCount", //查询出的记录数  
				repeatitems : false,
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
				sortorder=$("#jqGrid_collect_tb").jqGrid('getGridParam','sortorder');//排序方式
				sortname=$("#jqGrid_collect_tb").jqGrid('getGridParam','sortname');//排序字段	
			},
			onPaging:function(){
				//alert("是否翻页，修改的数据没有保存！");
			}
		}).navGrid('#pager_collect_tb', {
		edit : false,
		add : false,
		del : false,search:false});
	}

	//初始化获取数据的连接
	function bindFunc(isEditable)
	{
		
	}
	//批量添加
	function batch_Add_Dialog(jqGridConfig)
	{
		batch_Dialog(jqGridConfig);	//此函数在collect_batch_add_view.jsp页面
	}
	function renderPubJqGrid(data)
	{  
		viewType= data.type;
		if(viewType=='CKFORM'){
			tableId= data.id;
			tableName=data.name;
			$("#ckeditor_Form").show();
			$("#collect_comment").hide();
			$("#gview_jqGrid_collect_fm").hide();
			$("#gview_jqGrid_collect_tb").hide();
			$("#pager_collect_tb").hide();
			$("#jqgird_oper").hide();
			isEditable='0';//为0 checkeditor不可编辑
			initCKForm(data);
		}else{
			isEditable='1';//当为1的时候进行操作按扭 的添加
			renderCollectComment("collect_comment",data.memo);
			controllerDic= data.idsOfControlDic;
			jsCheckFunNameDic = data.jsCheckFunNameDic;
			jsCheckfunParamsDic = data.jsCheckParamsAndValueDic;
			initRules(data);
			jqGridConfig=data;
			data=addEditCol(data);
			data=addObjectCountCol(data);
			initCols(data);
			$("#jqGrid_collect_tb").jqGrid('GridUnload');
			$("#jqGrid_collect_fm").jqGrid('GridUnload');
			tableId= data.id;
			tableName=data.name;
			attrId=data.attrId;
			console.log(attrId);
			titleValues = data.editableColIds;
			maxNum=data.maxNum;
			initDataUrl();
			//var catId = $("#ddl_disc_category").val();
			//dataUrl='${ContextPath}/publicity/prepub/CollectData/collectionData/'+tableId+'/'+versionId+'/'+catId;
			if(viewType=='JQFORM')
			{	//显示采集表单
				$("#gview_jqGrid_collect_fm").show();
				$("#collect_comment").show();
				$("#gview_jqGrid_collect_tb").hide();
				$(".collect_oper_bts").hide();	
				$("#ckeditor_Form").hide();
				initJqForm(data,dataUrl);
			}else if(viewType='JQGRID'){
				$("#gview_jqGrid_collect_tb").show();
				$("#collect_comment").show();
				$("#pager_collect_tb").show();
				$("#gview_jqGrid_collect_fm").hide();
				$(".collect_oper_bts").hide();	
				$("#ckeditor_Form").hide();
				loadJqTable(data,dataUrl);
			}
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
	/*动态添加异议计数列*/
	function addObjectCountCol(data)
	{
		var ObjectCountCol={  
			  label : '',  
			  name : 'objectionCount',  
			  sortable : false,  
			  hidden : true,
			  editable : true,
			  width : 80,  
			  fixed : false,  
			  align : "center",
			  search:false,
		};
		data.colConfigs.unshift(ObjectCountCol);
		return data;
	
	}
	/*
	 * 对已经提出异议 的成果进行标记
	 *itemId是jqgrid的id,id是jqgrid的行号
	 */
	function hightLightItem(rowData,itemId,id){
		console.log(rowData['objectionCount']);
		if(rowData['objectionCount']>0){
			$("#"+itemId+ " #"+id).find('td').css('color','red');
		}else{
			$("#"+itemId+ " #"+id).find('td').css('color','black');
		}
		
	}
	/**
	 * 添加操作按钮，不同用途的数据操作按钮由此添加
	 */
	function addJQEditBt(){
		var userType = "${user.userType}";
		console.log(userType);
		if(openStatus=='1'){
			switch(userType){
				case '1'://中心用户
					jQuery("#jqGrid_collect_tb").setGridParam().hideCol("oper");
				 break;
				case '2'://学校
				case '3'://学科
					jQuery("#jqGrid_collect_tb").setGridParam().showCol("oper");
					var ids = jQuery("#jqGrid_collect_tb").jqGrid('getDataIDs');
					for(var i=0;i < ids.length;i++){
						var object="<a class='' href='#' onclick='raiseObject("+ids[i]+","+'0'+")'>提出异议</a>";
						jQuery("#jqGrid_collect_tb").jqGrid('setRowData',ids[i],{oper :object});
						var rowData = $("#jqGrid_collect_tb").jqGrid("getRowData",ids[i]);//获取本行数据
						hightLightItem(rowData,"jqGrid_collect_tb",ids[i]);//高亮本行数据
					}
					break;
				default: 
					jQuery("#jqGrid_collect_tb").setGridParam().hideCol("oper");
				break;
			}
		}else{
			jQuery("#jqGrid_collect_tb").setGridParam().hideCol("oper");
		}
	}
	function addJFEditBt(){
		var userType = "${user.userType}";
		console.log(userType);
		if(openStatus=='1'){
			switch(userType){
				case '1'://中心用户
					jQuery("#jqGrid_collect_fm").setGridParam().hideCol("oper");
				 break;
				case '2'://学校
				case '3'://学科
					jQuery("#jqGrid_collect_fm").setGridParam().showCol("oper");
					console.log(userType);
					var ids = jQuery("#jqGrid_collect_fm").jqGrid('getDataIDs');
					for(var i=0;i < ids.length;i++){
						var object="<a class='' href='#' onclick='raiseObject("+ids[i]+","+'1'+")'>提出异议</a>";
						jQuery("#jqGrid_collect_fm").jqGrid('setRowData',ids[i],{oper :object});
						var rowData = $("#jqGrid_collect_fm").jqGrid("getRowData",ids[i]);//获取本行数据
						hightLightItem(rowData,"jqGrid_collect_fm",ids[i]);//高亮本行数据
					}
					break;
				default: 
					jQuery("#jqGrid_collect_fm").setGridParam().hideCol("oper");
				break;
			}
		}else{
			jQuery("#jqGrid_collect_fm").setGridParam().hideCol("oper");
		}
	}
	function renderCollectComment(elementId,content)
	{
		$('#'+elementId).empty();
		var html = $.parseHTML( content );
		$('#'+elementId).append(html);
	}
	
	$(document).ready(function(){
		$( "input[type=submit], a.button , button" ).button(); 	
		$("#gview_jqGrid_collect_tb").hide();//id为jqgrid根据<table Id="">自动生成
		$("#gview_jqGrid_collect_fm").hide();
		$("#tb_export").hide();
	});
</script>
<div id ="jq_collect_parent" class="selectbar right_block" hidden="true">
	<jsp:include page="/CollectMeta/collect_oper_banner.jsp"></jsp:include>
	<div id="jq_collect_table">
		<jsp:include page="/CollectMeta/collect_jqgrid.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/collect_jqform.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/collect_ckeditor.jsp"></jsp:include>
		<!-- <div id="collect_comment" class="comment">
		</div> -->
	</div>
	<div hidden="true">
		<jsp:include page="/PublicAndFeedback/Publicity/add_objection_dialog.jsp"></jsp:include> 
	</div>
</div>