package com.dsep.service.datacalculate.RuleEngine.commander;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import com.dsep.service.datacalculate.RuleEngine.getdata.DataCode;

public class SumNumber implements ComputeCommander{

	
	@Override
	public double compute(List<Map<String,String>> data) {
		
		double result=0.0;
		
		Map<String,String> commParams=data.get(0);
		
		Set<String> keySet=commParams.keySet();
		
		String limitField="";
		double limitValue=0.0;
		boolean limitFlag=false;
		if(keySet.contains(DataCode.LIMIT)){
			limitField=commParams.get(DataCode.LIMIT);
			limitFlag=true;
			if(limitField.contains(DataCode.NUMBER)){
				limitValue=Double.parseDouble(limitField.substring(limitField.indexOf('&')+1));
				limitField="";
			}
			else if(limitField.contains(DataCode.FIELD))
				limitField=limitField.substring(limitField.indexOf('&')+1);
		}
		
		String field=data.get(0).get(DataCode.FIELD);
		
		if(limitFlag==false){  //字段取值没有限制	
			for(int i=1;i<data.size();i++){             //data的第0条数据存储的是命令的参数，要计算的数据从index为1的数据开始
				Map<String,String> dataItem=data.get(i);
				double num=0.0;
				String numStr=dataItem.get(field);
				if(StringUtils.isNotBlank(numStr))
					num=Double.parseDouble(numStr);   //取到要加总字段的名字
				result+=num;
			}
			
		}else{//字段取值有限制
			if(StringUtils.isNotBlank(limitField)){     //以另一个字段的值为上限
				for(int i=1;i<data.size();i++){
					Map<String,String> dataItem=data.get(i);
					double num=0.0;
					String numStr=dataItem.get(field);
					if(StringUtils.isNotBlank(numStr))
						num=Double.parseDouble(numStr);   //取到要加总字段的名字
					
					double numLimi=0.0;
					String numLimiStr=dataItem.get(limitField);
					if(StringUtils.isNotBlank(numLimiStr))
						numLimi=Double.parseDouble(numLimiStr);   //取到要加总字段的名字
					double limitNum=Double.parseDouble(numLimiStr);
					
					num=num<=limitNum?num:limitNum;
					
					result+=num;
				}
			}else{
				for(int i=1;i<data.size();i++){//以某一个数值为上限
					Map<String,String> dataItem=data.get(i);
					double num=0.0;
					String numStr=dataItem.get(field);
					if(StringUtils.isNotBlank(numStr))
						num=Double.parseDouble(numStr);   //取到要加总字段的名字
					
					num=num<=limitValue?num:limitValue;
					
					result+=num;
				}
			}
		}
		
		return result;
	}

}
