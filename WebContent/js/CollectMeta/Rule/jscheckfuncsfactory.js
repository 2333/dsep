function jsCheckFuncsFactory(value, colname) {
	console.log(colname);
	var paramsAndValues=new Array();
	$.each(jsCheckfunParamsDic,function(i,item){
		if(i==colname){
			paramsAndValues = item;
		}
	 });
	var funcName = jsCheckFunNameDic[colname];            //保存后台传过来的数组名
	if(paramsAndValues.length==0){
		return eval(funcName+"(value,colname)");
	}else{
		return eval(funcName+"(paramsAndValues,value,colname)");
	}
	
}