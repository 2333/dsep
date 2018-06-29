<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div>
<div id="batch_add_dv" class="table">
	<form id="batch_add_fm">
		<div>
			<table>
				<tr>
					<td><a id="add_one_row" class="button" href="#"> <span
							class="icon icon-adduser"></span>添加
					</a></td>
				</tr>
			</table>
		</div>
		<table id="collect_batch_add_tb"></table>
	</form>
</div>
</div>
<script type="text/javascript">
var batchRecords;
var selectedId;//选中的一行
var defaultDataValues;//初始化显示内容
var pass_validate='1';
var counter=0;//添加的行号RowId
function addOneBatchRow(){
	if(checkEntityCount("collect_batch_add_tb")){
		$("#collect_batch_add_tb").jqGrid('addRow',{  
	        rowID : ++counter,   
	            position :"last",  
	            useDefValues : true,  
	            useFormatter : true,  
	         	initdata :defaultDataValues,
	        }); 
		 	$("#collect_batch_add_tb").jqGrid('editRow',counter,false,setRowControllers);
		 	var del_link = "<a class='' href='#' onclick='delBatchItem("+counter+")'>删除</a>"; 
		jQuery("#collect_batch_add_tb").jqGrid('setRowData',counter,{oper :del_link});
		jQuery("#collect_batch_add_tb").jqGrid('setRowData',counter,{SEQ_NO :++batchRecords});	 	
	}
}
$(document).ready(function(){
	
	
});
/**
 * 删除
 */
function delBatchItem(id)
{
	var ids = jQuery("#collect_batch_add_tb").jqGrid('getDataIDs');
	var rowData = $("#collect_batch_add_tb").jqGrid("getRowData");
	$.each(ids,function(i,item){
		if(item>id)
		{
			jQuery("#collect_batch_add_tb").jqGrid('setRowData',item,{SEQ_NO :rowData[i]['SEQ_NO']-1});		
		}
	});
	$("#collect_batch_add_tb").jqGrid("delRowData",id);
	batchRecords--;
}
/**
 * 确认
 */
function resureBatchItem(id)
{
	jQuery("#collect_batch_add_tb").jqGrid('saveRow',id);
	var resure_link="<a class='' href='#' onclick='editBatchItem("+id+")'>编辑</a>";
	var del_link = "<a class='' href='#' onclick='delBatchItem("+id+")'>删除</a>"; 
	jQuery("#collect_batch_add_tb").jqGrid('setRowData',id,{oper :resure_link+' | '+del_link});
}
/**
 * 编辑
 */
 function editBatchItem(id)
 {
	
		$("#collect_batch_add_tb").jqGrid('editRow',id,false,setRowControllers);
		//var resure_link="<a class='' href='#' onclick='resureBatchItem("+id+")'>确认</a>";
		var del_link = "<a class='' href='#' onclick='delBatchItem("+id+")'>删除</a>"; 
		jQuery("#collect_batch_add_tb").jqGrid('setRowData',id,{oper :del_link});
 }
 /**
 *取消
 */
 function cancelBatchItem(id)
 {
	 
	 jQuery("#collect_batch_add_tb").jqGrid('restoreRow',id);
	 var resure_link="<a class='' href='#' onclick='editBatchItem("+id+")'>编辑</a>";
	 var del_link = "<a class='' href='#' onclick='delBatchItem("+id+")'>删除</a>"; 
	 jQuery("#collect_batch_add_tb").jqGrid('setRowData',id,{oper :resure_link+' | '+del_link});
	 
 }
 /**
 *批量提交之前进行检验
 */
 function validateRows()
 {
	 var ids = jQuery("#collect_batch_add_tb").jqGrid('getDataIDs');
	 
	 for(var i=0;i<ids.length;i++)
	 {
		var isPassValid='0';//是否通过前端检验,0为未通过，1为通过
		currentSaveRowId = ids[i];
		currentJqGridId = 'collect_batch_add_tb'; 
		jQuery("#collect_batch_add_tb").jqGrid("saveRow",ids[i],
				{	url:'clientArray',
					aftersavefunc:function(data){
					if(data){
						isPassValid='1';
						 /* var edit_link="<a class='' href='#' onclick='editBatchItem("+ids[i]+")'>编辑</a>";
						 var del_link = "<a class='' href='#' onclick='delBatchItem("+ids[i]+")'>删除</a>"; 
						 jQuery("#collect_batch_add_tb").jqGrid('setRowData',ids[i],{oper :edit_link+' | '+del_link}); */
					}
				}});
		if(isPassValid=='0'){
			$("#info_dialog").css({"z-index":"2000"});//检查信息对话框弹出最上层
			var positionX = 100;//设置弹出框的位置
			var positionY = 200;
			var myOffset = new Object();
			myOffset.left = positionX;
			myOffset.top = positionY;
			$("#info_dialog").offset(myOffset);
			for(var j=i-1;j>=0;j--)
			{
				$("#collect_batch_add_tb #"+ids[j]).find('td').css('background-color','transparent');
				$("#collect_batch_add_tb").jqGrid('editRow',ids[j],false,setRowControllers);
				var del_link = "<a class='' href='#' onclick='delBatchItem("+ids[j]+")'>删除</a>"; 
			    jQuery("#collect_batch_add_tb").jqGrid('setRowData',ids[j],{oper :del_link}); 
			}
			return ids[i];
		}	
	 }
	 return 'success';
	 
 }
function batchColseDialog(){
	var dialogParent = $('#batch_add_dv').parent();  
 	//克隆弹框里面的内容
 	var dialogOwn = $('#batch_add_dv').clone();
 	dialogOwn.appendTo(dialogParent);
 	$("#batch_add_dv").dialog("destroy").remove();
}
/*批量添加的对话框*/
function batch_Dialog(data)
{
		$("#add_one_row").click(function(){
			addOneBatchRow();
		});
		var dialogParent = $('#batch_add_dv').parent();  
	 	//克隆弹框里面的内容
	 	var dialogOwn = $('#batch_add_dv').clone();
	  	  initBatchJqgrid(data);
	 	  $('#batch_add_dv').dialog({
	  		    title:"批量添加",
	  		    height:'600',
	  			width:'90%',
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  		    hide:'fade',
	  			show:'fade',
	  		    autoOpen:true,
	  		  	zIndex : 300,
	  		    close:function(){
	  		    	if(isOpen){
	  		  			$("#yy_mm_dv").dialog("close");
	  		  		}
	  		    	$("#collect_batch_add_tb").jqGrid('GridUnload');
	  		    	dialogOwn.appendTo(dialogParent);
	  		   		$(this).dialog("destroy").remove();
	  		    	//batchColseDialog();
	  		    	//records=$("#jqGrid_collect_tb").jqGrid('getGridParam','records');//当前总记录数
	  		    	batchRecords= records;
	  		    },
	  		    buttons:{  
	  		    	"提交":function(){ 
	  		    		//batchSubmit();
	  		    		var valid_res=validateRows();
	  		    		console.log(valid_res);
	  		    		if(valid_res!='success')
	  		    		{
	  		    			$("#collect_batch_add_tb #"+valid_res).find('td').css('background-color','yellow');
	  		    		}else{
	  		    			var batchThis = $(this);
	  		    			batchSubmit(batchThis);
	  		    		}
	  		    		
	  	             },
	 	  
	 	            "取消":function(){
	  		    		
						$("#collect_batch_add_tb").jqGrid('GridUnload');
	 	            	$("#batch_add_dv").dialog("close");
	 	            	//records=$("#jqGrid_collect_tb").jqGrid('getGridParam','records');//当前总记录数
	 	            	batchRecords = records;
	 	            }
	  		    }}); 
	 
}
/*批量添加中的jqgrid*/
function initBatchJqgrid(data)
{
	var mydata;
	initDefaultDataValues(data);
	$("#collect_batch_add_tb").jqGrid('GridUnload');
	$("#collect_batch_add_tb").jqGrid({
		colModel:data.colConfigs,
		data: mydata, 
		datatype: "local",
		height:"100%",
		autowidth : true,
		shrinkToFit : false,
		rowNum:10,
		rowList:[10,20,30],
		viewrecords: true,
		sortorder: "desc",
		caption: tableName,
		gridComplete: function(){
			
		},
	}).navGrid('#collect_batch_add_tb',{edit:false,add:false,del:false});	
}
function initDefaultDataValues(data)
{
	var defaultData='{';
	$.each(data.colConfigs,function(i,item){
		if(i>0){
			if(i==data.colConfigs.length-1)
				defaultData+='\"'+item.name+'\":\"'+''+'\"';
			else
				defaultData+='\"'+item.name+'\":\"'+''+'\",';
		}
	});
	defaultData+='}';
	defaultDataValues=JSON.parse(defaultData);
	
	
}
/*页面整体提交*/
function batchSubmit(batchThis)
{   
	
	var ids = jQuery("#collect_batch_add_tb").jqGrid('getDataIDs');
	var dataString='{\"tableId\":[\"'+tableId+'\"],\"titleValues\":[';
	dataString+='\"SEQ_NO\",';
	for(var i=0;i<titleValues.length;i++)
	{
		if(i!=titleValues.length-1)
			dataString+='\"'+titleValues[i]+'\",';
		else
			dataString+='\"'+titleValues[i]+'\"],';
	}
	dataString +='\"rows\":[';
	var rowData = $("#collect_batch_add_tb").jqGrid("getRowData");
	//console.log('jqgrid length : '+rowData.length);
	    if (rowData.length < 1) { alert_dialog("没有数据！"); return; }  
	    for (var i = 0; i < ids.length; i++) { 
	    	dataString +='{';
	    	dataString+='\"SEQ_NO\":\"'+rowData[i]['SEQ_NO']+'\",';
	    	$.each(titleValues,function(j,item){
	    		if(j==(titleValues.length-1)){
	    			//dataString +='\"'+item+'\":\"'+$.trim(rowData[i][item])+'\"';
	    			if($.inArray(item,fileValues)!=-1){
	    				//alert($.inArray(item,fileValues)+" : " +rowData[i][item]);
	    				if(rowData[i][item]!='请上传附件'){
	    					dataString +='\"'+item+'\":\"'+rowData[i][item]+'\"';
	    				}else{
	    					dataString +='\"'+item+'\":\"'+''+'\"';
	    				}
	    			}else{
	    				dataString +='\"'+item+'\":\"'+rowData[i][item]+'\"';
	    			}
	    			
	    		}else{
	    			if($.inArray(item,fileValues)!=-1){
	    				//alert($.inArray(item,fileValues)+" : " +rowData[i][item]);
	    				if(rowData[i][item]!='请上传附件'){
	    					dataString+='\"'+item+'\":\"'+rowData[i][item]+'\",';
	    				}else{
	    					dataString+='\"'+item+'\":\"'+''+'\",';
	    				}
	    			}else{
	    				dataString+='\"'+item+'\":\"'+rowData[i][item]+'\",';
	    			}
	    		}
	    	});
	    	if(i==(ids.length-1))
	    		dataString+='}';
	    	else
	    		dataString+='},';
	    }
	    dataString+=']}';
		
		//var jsonString=JSON.parse(dataString);//把json字符串封装成json对象
		/* var unitId=$("#unit_Id").val();
		var discId=$("#disc_Id").val(); */
		//var myurl="${ContextPath}/Collect/toCollect/JqOper/batchSubmit/"+unitId+"/"+discId;
		/* batchUrl+=unitId+'/'+discId; */
		initBatchUrl();
		$.post(batchUrl,{params:dataString},
				function(result){
					if(result.status=='success'){
						alert_dialog('操作成功！');
						$("#collect_batch_add_tb").jqGrid('GridUnload');
						$("#batch_add_dv").dialog("close");
						$("#jqGrid_collect_tb").jqGrid('setGridParam').trigger("reloadGrid");
					}else
						{
							$.each(result,function(i,item){
								if(i!='status')
									$("#collect_batch_add_tb").jqGrid("delRowData",ids[i]);
							
							});
							for(var j=0;j<rowData.length;j++){
								$("#collect_batch_add_tb #"+ids[j]).find('td').css('background-color','transparent');
								$("#collect_batch_add_tb").jqGrid('editRow',ids[j],false,setRowControllers);
								var del_link = "<a class='' href='#' onclick='delBatchItem("+ids[j]+")'>删除</a>"; 
					    		jQuery("#collect_batch_add_tb").jqGrid('setRowData',ids[j],{oper :del_link}); 
							}
								alert_dialog('窗口保留数据提交失败,请重新提交！');
						}
		}); 
	
}
</script>