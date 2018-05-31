//检查本列小于指定列
function checkLessThanOneCol(paramsAndValues,value,column){
	var result = checkNumNonNegativeInt(value,column);
	console.log(result);
	if(!result[0]){
		return result;
	}else{
		var parm1 = paramsAndValues[0].split(",")[1];// 获取第一个参数的值
		var oneValue = $("input[id$='"+currentSaveRowId+"_"+parm1+"']").val();
		if(parseInt(value)<=parseInt(oneValue)){
			return [true,""];
		}else{
			pass_validate = '0';// 错误的时候为0
			return [false,column+"字段应小于："+oneValue+" !"];
		}
	}
}
//对于本字段是否等于其他字段的和进行判断
function checkAggregateCols(paramsAndValues,value,column){
	var rightData = true; 
	var result = checkNumPositiveInt(value,column);
	console.log(result);
	if(!result[0]){
		return result;
	}else{
		var parm1 = paramsAndValues[0].split(",")[1];// 获取第一个参数的值
		var columns = parm1.split(";");
		var colValues=0;
		if(value.length==0){
			pass_validate = '0';// 错误的时候为0
			return [false,column+" : 字段不许为空！"];
		}else{
			$.each(columns,function(i,col){
				 if($.trim(col)!=''){
					 var colValue= $("input[id$='"+currentSaveRowId+"_"+col+"']").val();
					 if(checkNonNegativeInt($.trim(colValue))){
						 colValues+=parseInt(colValue);
					 }else{
						 rightData = false;
					 }
				 }
			});
			/*if(!rightData){
				pass_validate = '0';// 错误的时候为0
				 return [false,"存在非法输入数据，请重新检查！"];
			}else if(colValues==value){
				return [true,""];
			}else{
				pass_validate = '0';// 错误的时候为0
				return [false,column+" 字段填写值错误！建议值为： "+colValues+",请检查后重新填写！"];
			}*/
			if(rightData){
				if(colValues==value){
					return [true,""];
				}else{
					pass_validate = '0';// 错误的时候为0
					return [false,column+" 字段填写值错误！建议值为： "+colValues+",请检查后重新填写！"];
				}
			}else{
				return [true,""];
			}
		}
	}
}
/*
 * 检查多个字段不大于总数字段
 */
function checkAggreColsNoBigerTotal(paramsAndValues,value,column){
	var result = checkNumPositiveInt(value,column);
	if(!result[0]){
		return result;
	}else{
		var parm1 = paramsAndValues[1].split(",")[1];// 获取第一个参数的值
		//var parm2 = paramsAndValues[1].split(",")[1];// 获取第二个参数的值
		console.log( parm1);
		var columns = parm1.split(";");
		var colValues=0;
		if(value.length==0){
			pass_validate = '0';// 错误的时候为0
			return [false,column+" : 字段不许为空！"];
		}else{
			$.each(columns,function(i,col){
				if($.trim(col)!=''){
					var colValue= $("input[id$='"+currentSaveRowId+"_"+col+"']").val();
					 colValues+=parseInt(colValue);
				}
			});
			console.log(colValues);
			if(colValues<=value){
				return [true,""];
			}else{
				pass_validate = '0';// 错误的时候为0
				return [false,column+" 字段填写值错误！建议至少为： "+colValues+",请检查后重新填写！"];
			}
		}
	}
}
function checkEqualAndMoreMulCols(paramsAndValues,value,column){
	console.log(paramsAndValues);
	console.log('计算多列！'+value+ ":" +column);
	var equalResult = checkAggregateCols(paramsAndValues,value,column);
	if(equalResult[0]){
		//paramsAndValues.splice(0,1);
		var lessResult = checkAggreColsNoBigerTotal(paramsAndValues,value,column);
		return lessResult;
	}else{
		return equalResult;
	}
}