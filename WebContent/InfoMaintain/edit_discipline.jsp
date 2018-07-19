<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="form">
	<form id=disciplineEdit_fr class="fr_form" method="post">
		<table id="disciplineEdit_tb" class="fr_table">
			<tr class="hidden">
             	<td >ID：</td>  
             	<td >  
                	<input type="text" name="id" value="${user.id}" readonly/>  
             	</td>
             	<td >userType</td>  
             	<td >  
                	<input type="text" name="userType" value="${user.userType}" readonly/>  
             	</td>
             	<td >discId</td>  
             	<td >  
                	<input type="text" name="discId" value="${user.discId}" readonly/>  
             	</td>
             	<td >unitId</td>  
             	<td >  
                	<input type="text" name="unitId" value="${user.unitId}" readonly/>  
             	</td>
          	</tr>  
			<tr> 
				<td><label for="loginId" >${textConfiguration.discNumber }：</label></td>
				<td width="300px">
					<input id="login_id" type="text" name="loginId" class="discAndUnitJqGridInputL onlyread" value="${user.loginId}" readonly="readonly"/>
				</td>
				
				<td><label for="school_name" >所属学校：</label></td>
				<td>
					<input type="text"  name="schoolName" class="discAndUnitJqGridInputR onlyread" value="${user.schoolName}" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>
					<Span ><b>联系人信息</b></Span>
				</td>
			</tr>
			<tr>
				<td><label for="name">${textConfiguration.discName}：</label></td>
				<td >
					<input type="text" name="name" class="discAndUnitJqGridInputL onlyread" value="${user.name}" readonly="readonly" />	
				</td>
				<td><label for="email" class="alert_info">*联系人邮箱：</label></td>
				<td>
					<input type="text" name="email" value="${user.email}" class="discAndUnitJqGridInputR"/>
					<span class = "dialog_validSpan"></span>	
				</td>
			</tr>			
			<tr>
				<td><label for="address">通讯地址：</label>
				<td>
					<input type="text" name="address" value="${user.address}" class="discAndUnitJqGridInputL"/>
				</td> 

				<td><label for="cell_phone">通讯电话：</label></td>
				<td>
					<input type="text" name="cellPhone" value="${user.cellPhone}" class="discAndUnitJqGridInputR"/>
				</td>
			</tr>
			<tr>
				<td><label for="office_addr">办公地址：</label></td>
				<td>
					<input type="text" name="officeAddr" value="${user.officeAddr}" class="discAndUnitJqGridInputL"/>
				</td>
				<td><label for="office_phone">办公电话：</label></td>
				<td>
					<input type="text" name="officePhone" value="${user.officePhone}" class="discAndUnitJqGridInputR"/>
				</td>	
			</tr>
			<tr>
				<td valign="top"><label for="memo" >备注：</label></td>
				<td colspan='4'>
					<textarea name="memo" class="uniAndDiscJqGridMemo" style='resize:none;height:50px;' value="${user.memo}">${user.memo}
					</textarea>
				</td> 
			</tr>
			
		</table>
			
	</form>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$( "input[type=submit], a.button , button" )
		  .button()
		  .click(function( event ) {
			event.preventDefault();
		});
		$(".fr_table td:nth-child(even)").addClass("fr_left");
		$(".fr_table td:nth-child(odd)").addClass("fr_right");
		
		validator(".fr_form");
	
	});

</script>