<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div >
	<table id="collectTemp_tb"></table>
	<div id="collectTemp_pager"></div>
</div>
<div id="fileForm" class="infofr_div">
      <fieldset class = "infofr_fieldset" >
      		<legend class="smallTitle">采集项模板</legend>
      		<form action="" class = "infofr_form" id = "basicInfoForm" >
      			<label id="sumbitTime_lb" for="sumbitTime_lb" class="fr_label" ><span class="TextFont">上传时间：</span></label>
      			<input id="fileFormsbTime" type="text" readonly="readonly" 
      				class="info_input" size="30"/>
      			<span class = "validSpan"></span>
      			<a href="#" class ="button" id ="downFileAttach" style="display:none">
      				<span class="icon icon-download"></span>下载附件</a>
				<a href="#" class="button" id ="delFileAttach" style="display:none">
					<span class="icon icon-del"></span>删除附件</a>
      			<input id="attachNo" type="text" readonly="readonly" style="display:none"/>
				<input id="fileFormId" type="text" style="display:none"/>
      		</form>
      </fieldset>	
      <fieldset id="operFileForm"class = "infofr_fieldset" >
      		<legend class="smallTitle">操作</legend>
      		<form action="" class = "infofr_form" id = "basicInfoForm" >
      			<a  id="uploadFileBt" class="button" href="#">
      				<span class="icon icon-submit"></span>上传附件</a>
				<a  id="downLoadTemplet" class="button" href="#">
					<span class="icon icon-download"></span>下载模板</a>
      		</form>
      </fieldset>	
</div>
<div hidden="true">
	<div id="uploadFileForm_dialog_dv" class="table">
		<form id="collect_uploadFileForm" method="post" action="javascript:;"
			enctype="multipart/form-data">
			<table>
				<tr class="hidden">
		             <td>
		             	<input type="hidden" id="sessionid" value="${pageContext.session.id}" />
		             </td>
           		</tr>
				<tr>
					<td><span class="TextFont">文件上传：</span></td>
				<td align="left" colspan="4">
					<input id="file_form_upload" name="file_upload" type="file">
					<div id="fileFormQueue"></div>
					<a href="#" class="button" id="upload_file_form">上传</a>| 
         			<a href="#" class="button" id="cancel_file_form">取消上传</a>	|
				</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script src="${ContextPath}/js/fileOper/filename_oper.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('input[type=submit], a.button , button').button();
	$("#uploadFileBt").click(function(){
		upLoadFileForm();
	});
	//附件上传
	$('#upload_file_form').click(function(){
			$('#file_form_upload').uploadify('upload');
	});
		
	//附件取消
		$('#cancel_file_form').click(function(){
			$('#file_form_upload').uploadify('cancel');
	});
	$("#file_form_upload").uploadify({  
            'swf'            : '${ContextPath}/js/uploadify/uploadify.swf',  
            'uploader'       : '${ContextPath}/templateManage/collect/upLoadTemp'
								+'?jsessionid='+ $("#sessionid").val(),//后台处理的请求  
            'queueID'        : 'fileFormQueue',
            'queueSizeLimit' : 1,  
            'auto'           : false,
            'buttonText'     : '选择文件',
            'onUploadSuccess':function(file,data,response){
            	alert(data);
            }
        });
	function upLoadFileForm(){
			$("#uploadFileForm_dialog_dv").dialog({
				 title:"上传附件",
	 	  		 height:'250',
	 	  	     width:'400',
	 	  		 position:'center',
	 	  		 modal:true,
	 	  		 draggable:true,
	 	  	     buttons:{  
		  		    	"关闭":function(){ 
		  		    	    $("#uploadFileForm_dialog_dv").dialog("close");
		  		    	}
			     }
			  });
	}
	 $("#collectTemp_tb").jqGrid({
			datatype: "json",
	        url:"${ContextPath}/templateManage/collect/templates",
			colNames:['ID','模板名称','上传时间','操作'],
			colModel:[	
						{name:'id',align:"center", width:100,hidden:true},
						{name:'name',index:'unitId',align:"center", width:100},
						{name:'date',align:"center", width:200,sortable:false},
						{name:'oper',index:'discId',align:"center", width:100},	
			],
			rownumbers:true,
			height:"100%",
			autowidth:true,
			pager: '#collectTemp_pager',
			rowNum:20,
			rowList:[20,30,40],
			viewrecords: true,
			sortorder: "asc",
			sortname: "id",
		    jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
             root: "rows",  //包含实际数据的数组  
             page: "pageIndex",  //当前页  
             total: "totalPage",//总页数  
             records:"totalCount", //查询出的记录数  
             repeatitems : false      
         },
			gridComplete: function(){
				
			},
			caption: "采集项模板"
		}).navGrid('#collectTemp_pager',{edit:false,add:false,del:false});
});

</script>