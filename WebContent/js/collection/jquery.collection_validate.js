;(function($){
	$.extend({
		/*采集数据验证函数*/
		"collection_vaildate":{
			/*数字合法验证*/
			"numbercheck":function(value,colname){
				if (value < 0||value >20) 
					return [false,"请输入0至20之间的数字"];
				else 
					return [true,""];
			},
			/*身份证验证*/
			"validateIdCard":function(value,colname){
				var aCity = { 11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江 ", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北 ", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏 ", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外 " };
				var iSum = 0;
				value = value.replace(/x$/i, "a");
				if (aCity[parseInt(value.substr(0, 2))] == null) 
					return [false,"非法地区"];
				var sBirthday = value.substr(6, 4) + "-" + Number(value.substr(10, 2)) + "-" + Number(value.substr(12, 2));
				var d = new Date(sBirthday.replace(/-/g, "/"));
				if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate())) 
					return [false,"非法生日"];
				for (var i = 17; i >= 0; i--) 
					iSum += (Math.pow(2, i) % 11) * parseInt(value.charAt(17 - i), 11);
				if (iSum % 11 != 1) 
					return [false,"非法证号"];
				else
					return [true,""];
			},
			/*字符长度验证*/
			"validateStrLength":function(value,colname){
				var l = 0;
				var a = value.split("");
				for (var i=0;i<a.length;i++) {
					if (a[i].charCodeAt(0)<299) {
						l++;} 
					else {
						l+=2;}}
					if(l<5) return[false,"字符串长度不能小于5"];
					else return[true,""];
			},
		},
	})
})(jQuery)