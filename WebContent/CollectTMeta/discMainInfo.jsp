<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="disc_main_dv" class="table">
	<form id="batch_add_fm">
     <table id="disc_main_tb"></table>
     <div id="pager_disc_main_tb"></div>
	</form>
</div>
<script type="text/javascript">
	function openDiscMainDialog(){
		 $('#disc_main_dv').dialog({
	  		    title:"学科简况信息",
	  		    height:'500',
	  			width:'80%',
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  		    hide:'fade',
	  			show:'fade',
	  		    autoOpen:true,
	  		    close:function(){
	  		    	
	  		    },
	  		    buttons:{  
	 	            "关闭":function(){
	 	        			$("#disc_main_dv").dialog("close");
	 	            	 }
	  		    }}); 
	}
	$(document).ready(function(){
		
		var discMainData = [{teacherName:'张三',kyxmjfze:'102',kyxmsm:'4',xmzspm:'2',hjgjj:'3',
			                 hjsbj:'2',fblwzs:'20',lwyxyzzh:'34',lwtycszh:'56',yxyz_tycspm:'3',
			                 zzzs:'3',zlzs:'4'},
			                {teacherName:'张三',kyxmjfze:'102',kyxmsm:'4',xmzspm:'2',hjgjj:'3',
				                 hjsbj:'2',fblwzs:'20',lwyxyzzh:'34',lwtycszh:'56',yxyz_tycspm:'3',
				                 zzzs:'3',zlzs:'4'},
				            {teacherName:'张三',kyxmjfze:'102',kyxmsm:'4',xmzspm:'2',hjgjj:'3',
					             hjsbj:'2',fblwzs:'20',lwyxyzzh:'34',lwtycszh:'56',yxyz_tycspm:'3',
					             zzzs:'3',zlzs:'4'},
					         {teacherName:'张三',kyxmjfze:'102',kyxmsm:'4',xmzspm:'2',hjgjj:'3',
						         hjsbj:'2',fblwzs:'20',lwyxyzzh:'34',lwtycszh:'56',yxyz_tycspm:'3',
						         zzzs:'3',zlzs:'4'},
						     {teacherName:'张三',kyxmjfze:'102',kyxmsm:'4',xmzspm:'2',hjgjj:'3',
					                 hjsbj:'2',fblwzs:'20',lwyxyzzh:'34',lwtycszh:'56',yxyz_tycspm:'3',
					                 zzzs:'3',zlzs:'4'},
					         {teacherName:'张三',kyxmjfze:'102',kyxmsm:'4',xmzspm:'2',hjgjj:'3',
						             hjsbj:'2',fblwzs:'20',lwyxyzzh:'34',lwtycszh:'56',yxyz_tycspm:'3',
						             zzzs:'3',zlzs:'4'},
						     {teacherName:'张三',kyxmjfze:'102',kyxmsm:'4',xmzspm:'2',hjgjj:'3',
							  hjsbj:'2',fblwzs:'20',lwyxyzzh:'34',lwtycszh:'56',yxyz_tycspm:'3',
							  zzzs:'3',zlzs:'4'},
							 {teacherName:'张三',kyxmjfze:'102',kyxmsm:'4',xmzspm:'2',hjgjj:'3',
							  hjsbj:'2',fblwzs:'20',lwyxyzzh:'34',lwtycszh:'56',yxyz_tycspm:'3',
							  zzzs:'3',zlzs:'4'},
							  {teacherName:'张三',kyxmjfze:'102',kyxmsm:'4',xmzspm:'2',hjgjj:'3',
						       hjsbj:'2',fblwzs:'20',lwyxyzzh:'34',lwtycszh:'56',yxyz_tycspm:'3',
						       zzzs:'3',zlzs:'4'},
						     {teacherName:'张三',kyxmjfze:'102',kyxmsm:'4',xmzspm:'2',hjgjj:'3',
						      hjsbj:'2',fblwzs:'20',lwyxyzzh:'34',lwtycszh:'56',yxyz_tycspm:'3',
						      zzzs:'3',zlzs:'4'}];
		$("#disc_main_tb").jqGrid({
			datatype: "local",
			data: discMainData,
			colNames:['教师姓名','科研项目经费总额','科研项目数目','项目总数排名','获奖国家级','获奖省部级','发表论文总数','论文影响因子之和','论文他引次数之和','论文影响因子*他引次数排名','专著总数','专利总数'],
			colModel:[
				{name:'teacherName',align:"center", width:100,editable:true},
				{name:'kyxmjfze',align:"center", width:100,editable:true},
				{name:'kyxmsm',align:"center", width:100,editable:true},
				{name:'xmzspm',align:"center", width:100,editable:true},
				{name:'hjgjj',align:"center", width:100,editable:true},
				{name:'hjsbj',align:"center", width:100,editable:true},
				{name:'fblwzs',align:"center", width:100,editable:true},
				{name:'lwyxyzzh',align:"center", width:100,editable:true},
				{name:'lwtycszh',align:"center", width:100,editable:true},
				{name:'yxyz_tycspm',align:"center", width:100,editable:true},
				{name:'zzzs',align:"center", width:100,editable:true},
				{name:'zlzs',align:"center", width:100,editable:true},
			],
			rownumbers: true,
			height:"100%",
			autowidth:true,
			pager: '#pager_disc_main_tb',
			rowNum:20,
			rowList:[20,30,40],
			viewrecords: true,
			caption: "学科简况信息"
		}).navGrid('#disc_main_tb',{edit:false,add:false,del:false});	
	
	});
</script>