/*初始化用户信息以及显示信息*/
function initCenterUserInfo(userType) {
	if (userType == "3") {
		globalInfoControl();
		$("#collegeId").val(collegeId);
		$("#disciplineId").val(disciplineId);
		if(disciplineId!='')
		{
			searchCollects(collegeId,disciplineId);
		}
	}
}
//点击查询按钮
function searchCollects(collegeId,disciplineId) {
					if (disciplineId == '') {
						alert('请输入学科编号！');
					} else {
						if (tree_flag) {
							globalInfoControl_p(collegeId,disciplineId);
						} else {
							$('#hide_tree').html(
											'<span class="icon icon-left" title="关闭/打开树形列表"></span>');
							$("#hide_tree").animate(
											{
												left : 200
											},
											500,
											function() {
												$('#discipline_info').css(
																'margin-left',
																'220px');
											});
							$("#ztreeConllection").show();
							tree_flag = true;
							globalInfoControl_p(collegeId,disciplineId);
						}
					}
				}