<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="menu">
	<div id="menu_title"></div>
	<div class="menu_lv1">
	<!-- <div id="dsepType"> -->
		<h3 class="dsepType">
			<span class="icon icon-departmentmanager"></span>学科信息管理
		</h3>
		<div class="dsepType">
			<ul class="menu_lv2">
				<li><a href="${ContextPath}/Collection/toCollectionTest?tableId=DSEP_GJJSBJJWHZKYXM_0">学科信息采集</a></li>
				<li><a href="${ContextPath}/rbac/datacheck">重复数据检查</a></li>
				<li><a href="${ContextPath}/Collection/backup">学科备份管理</a></li>
			</ul>
		</div>
		<!-- </div> -->
		<!-- <div id="schoolType"> -->
		<h3 class="schoolType">
			<span class="icon icon-departmentmanager"></span>学科信息管理
		</h3>
		<div class="schoolType">
			<ul class="menu_lv2">
				<li><a href="${ContextPath}/SchoolCollection/SchoolCollectionView">学科信息汇总</a></li>	
				<li><a href="${ContextPath}/Collection/toCollectionTest?tableId=DSEP_GJJSBJJWHZKYXM_0">学科信息查询</a></li>
				<li><a href="${ContextPath}/rbac/datacheck">重复数据检查</a></li>				
			</ul>
		</div>
		<!-- </div> -->
		<!-- <div id="centerType"> -->
		<h3 class="centerType">
			<span class="icon icon-departmentmanager"></span>学科信息管理
		</h3>
		<div class="centerType">
			<ul class="menu_lv2">
				<li><a href="${ContextPath}/CenterCollection/CenterCollectionView">学科信息汇总</a></li>	
				<li><a href="${ContextPath}/Collection/toCollectionTest?tableId=DSEP_GJJSBJJWHZKYXM_0">学科信息查询</a></li>
				<li><a href="${ContextPath}/rbac/datacheck">重复数据检查</a></li>
				<li><a href="${ContextPath}/rbac/publiclibcheck">公共库比对</a></li>
				<li><a href="${ContextPath}/rbac/spotcheck">论文抽查</a></li>
				<li><a href="">结果计算</a></li>				
			</ul>
		</div>
		<!-- </div> -->
		<h3 class="teacherType">
			<span class="icon icon-departmentcollect"></span>教师成果采集
		</h3>
		<div class="teacherType">
			<ul class="menu_lv2">
				<li><a href="${ContextPath}/Collection/toCollection?tableId=DSEP_CBZZ_0">出版专著</a></li>	
				<li><a href="${ContextPath}/Collection/toCollection?tableId=DSEP_CBJC_0">出版教材</a></li>
				<li><a href="${ContextPath}/Collection/toCollection?tableId=DSEP_KYHJ_0">科研获奖</a></li>
				<li><a href="${ContextPath}/Collection/toCollection?tableId=DSEP_XSLW_0">学术论文</a></li>
				<li><a href="${ContextPath}/Collection/toCollection?tableId=DSEP_KYXM_0">科研项目</a></li>
				<li><a href="${ContextPath}/Collection/toCollection?tableId=DSEP_JXCG_0">教学成果</a></li>
				<li><a href="${ContextPath}/Collection/toCollection?tableId=DSEP_FMZL_0">发明专利</a></li>					
			</ul>
		</div>
		<h3>
			<span class="icon icon-sysmanage"></span>系统管理
		</h3>
		<div>
			<ul class="menu_lv2">
				<li><a href="${ContextPath}/rbac/usermanage">用户管理</a></li>
				<li><a href="${ContextPath}/rbac/rolemanage">角色管理</a></li>
				<li><a href="${ContextPath}/rbac/right">权限管理</a></li>
			</ul>
		</div>
	</div>
</div>
<script type="text/javascript">
	 $(document).ready(function(){
		 $(".teacherType,.dsepType,.schoolType,.centerType").hide();
			var userType="${userSession.userType}";
			if(userType=="1")
			{
				$(".dsepType").show();
			}else
				if(userType=="2")
				{
					$(".schoolType").show();	
				}else
					if(userType=="3")
					{
						$(".centerType").show();
					}else if(userType=='4')
						  {
								$(".teacherType").show();
						  }
			
		
	}); 
</script>