/**
 * 逻辑检查——逻辑规则检验js函数
 * 
 * @author lubin
 * @author yuyaolin
 */

function checkDisciplinesOrUnitsNumber(value, colname) {
	if (value.length == 0){
		pass_validate = '0';
		return [ true, "此字段不允许为空！" ];
	}	
	else {
		var index = value.indexof("%"); // 判断字符串中是否包含字符%

		if (index != -1) // 包含%，填写参与百分比
			checkPercentage(value, colname);
		else
			// 不包含%，填写所排次序
			checkRank(value, colname);
	}
}

/**
 * 检查参与学科数本学科所占百分比a(b%)或比例a(b)的逻辑是否正确.
 * 
 * @param paramsAndValues
 * @param value
 * @param colname
 * @returns {Array}
 * @author YangJunLin
 * 
 */
function checkRankAndPercertage(value, colname) {
	var num_a = parseInt(value.substring(0, value.indexOf("(")));
	var num_b = parseInt(value.substring(value.indexOf("(") + 1, value.indexOf(")")));
	var num_c = parseInt(value.substring(value.indexOf("[") + 1, value.indexOf("%")));
	var num_d = parseInt(value.substring(0, value.indexOf("[")));

	var regex = /^[1-9]\d*\([1-9]\d*\)|[1-9]\d*\[[1-9]\d*\%\]|[1-9]\d*\[\d*\.\d+\%\]$/;

	if (regex.test(value)) {
		if (num_a == 1 && num_b == 1) {
			return [ true, "" ];
		} else if (num_d == 1 && num_c == 100) {
			return [ true, "" ];
		} else if (num_a > 1 && num_b > 0 && num_b <= num_a) {
			return [ true, "" ];
		} else if (num_b > num_a) {
			pass_validate = '0';
			return [ false, colname + ":参与数不应该大于总数。" ];
		} else if (num_d > 1 && num_c > 0 && num_c < 100) {
			return [ true, "" ];
		} else if (num_d == 1 && num_c != 100) {
			pass_validate = '0';
			return [ false, colname + ":仅有一个参与学科，本学科参与百分比应为100%。" ];
		} else if (num_d != 1 && num_c == 100) {
			pass_validate = '0';
			return [ false, colname + ":有多个参与学科，本学科参与百分比不应为100%。" ];
		} else if (num_c > 100) {
			pass_validate = '0';
			return [ false, colname + ":本学科所占百分比不应该超过100%。" ];
		} else {
			pass_validate = '0';
			return [ false, colname + ":请输入合理的数据。" ];
		}
	} else {
		pass_validate = '0';
		return [ false, colname + ":数据格式错误。" ];
	}
}