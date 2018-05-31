package com.dsep.service.datacalculate.RuleEngine.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.dsep.service.datacalculate.RuleEngine.ObjectCalculateService;
import com.dsep.service.datacalculate.RuleEngine.commander.CommanderFactory;
import com.dsep.service.datacalculate.RuleEngine.commander.ComputeCommander;
import com.dsep.service.datacalculate.RuleEngine.getdata.DataCode;
import com.dsep.service.datacalculate.RuleEngine.getdata.GetRuleEngineDataService;
import com.dsep.util.datacalculate.CalculateFormula;

public class ObjectCalculateServiceImpl implements ObjectCalculateService{
		
	private GetRuleEngineDataService getRuleEngineDataService;
	private static Map<String,String> commNameAndClass=new HashMap<String,String>(10);
	
	public GetRuleEngineDataService getGetRuleEngineDataService() {
		return getRuleEngineDataService;
	}

	public void setGetRuleEngineDataService(
			GetRuleEngineDataService getRuleEngineDataService) {
		this.getRuleEngineDataService = getRuleEngineDataService;
	}
	

	@Override
	public double calculate(String formular,String indexItemId,
			String unitId,String discId) throws Exception{
		if(StringUtils.isBlank(formular))
			return 0.0;
		
		boolean start = false;
		boolean flagD = false;
		boolean flagE = false;
		boolean flagT = false;
		boolean flagF = false;
		boolean flagI = false;
		boolean flagL = false;
		boolean flagC = false;
		boolean isNumber = false;
		Map<String,String> m = new HashMap<String,String>();
		List<Object> objs = new ArrayList<Object>();
		
		String codeName = "";
		String entityId = "";
		String tableName = "";
		String fieldName = "";
		String indexCont = "";
		String limit = "";
		String condition = "";
		String temp = "";
		double num = 0;
		for(int i=0;i<formular.length();i++){
			char a = formular.charAt(i);
			char[] b = {a};
			String c = new String(b);
			if(c.equals("#")){
				flagD = true;
				continue;
			}
			if(flagD){
				if(!c.equals("(")){
					codeName += c;
					continue;
				}
				if(c.equals("(")){
					flagD = false;
					start = true;
					m.put(DataCode.COMMANDER_NAME, codeName);
					continue;
				}
			}
            if(start){
            	if(c.equals("E")&&!flagE&&!flagT&&!flagF&&!flagI&&!flagL&&!flagC){
    				flagE = true;
    				continue;
    			}
            	if(c.equals("T")&&!flagE&&!flagT&&!flagF&&!flagI&&!flagL&&!flagC){
            		flagT = true;
            		continue;
            	}
    			if(c.equals("F")&&!flagE&&!flagT&&!flagF&&!flagI&&!flagL&&!flagC){
    				flagF = true;
    				continue;
    			}
    			if(c.equals("I")&&!flagE&&!flagT&&!flagF&&!flagI&&!flagL&&!flagC){
    				flagI = true;
    				continue;
    			}
    			if(c.equals("L")&&!flagE&&!flagT&&!flagF&&!flagI&&!flagL&&!flagC){
    				flagL = true;
    				continue;
    			}
    			if(c.equals("C")&&!flagE&&!flagT&&!flagF&&!flagI&&!flagL&&!flagC){
    				flagC = true;
    				continue;
    			}
    			if(c.equals(";")){
    				if(flagE){
    					m.put(DataCode.ENTITY_ID, entityId.substring(1));
    					flagE = false;
    				}
    				if(flagF){
    					m.put(DataCode.FIELD, fieldName.substring(1));
    					flagF = false;
    				}
    				if(flagT){
    					m.put(DataCode.TABLE_NAME, tableName.substring(1));
    					flagT = false;
    				}
    				if(flagI){
    					m.put(DataCode.INDEX_CONT, indexCont.substring(1));
    					flagI = false;
    				} 
    				if(flagL){
    					m.put(DataCode.LIMIT, limit.substring(1));
    					flagL = false;
    				}
    				if(flagC){
    					m.put(DataCode.CONDITION, condition.substring(1));
    					flagC = false;
    				}
    				continue;
    			}
    			if(flagE){
    				entityId += c;
    				continue;
    			}
    			if(flagT){
    				tableName += c;
    				continue;
    			}
    			if(flagF){
    				fieldName += c;
    				continue;
    			}
    			if(flagI){
    				indexCont += c;
    				continue;
    			}
    			if(flagL){
    				limit += c;
    				continue;
    			}
    			if(flagC){
    				condition += c;
    				continue;
    			}
			}
            if(c.equals(")")&&start){
				start = false;
				m.put(DataCode.DISC_ID, discId);
				m.put(DataCode.UNIT_ID, unitId);
				m.put(DataCode.INDEX_ID, indexItemId);
				String checkInfo = checkMap(m);
				if(!checkInfo.equals("success"))
					throw new Exception(discId + "学科" + indexItemId + "末级指标" + checkInfo);
				double value = calculateCommander(m);
				//调用计算引擎拿到第一个结果
				objs.add(value);
				Set<String> keySet = m.keySet();
				for(String key:keySet)
					System.out.println(m.get(key));
				codeName = "";
				entityId = "";
				tableName = "";
				fieldName = "";
				indexCont = "";
				limit = "";
				condition = "";
				temp = "";
				
				m.clear();
				continue;
			}
            //如果不处于读取指令状态且c不为运算操作符
            if(!start&&!CalculateFormula.isOperator(c)&&!" ".equals(c)){  
            	//如果已经读到表达式末尾，则将temp和c合并并转换为double并录入objs
            	if(i==formular.length()-1){
            		temp += c;
            		num = Double.valueOf(temp);
                	objs.add(num);
                	continue;
            	}
            	//如果未读到末尾，则将c录入temp并继续读下一个字符
            	else{
            		isNumber = true;
                	temp += c;
                	continue;
            	}        
            }
            //如果上一个字符是数字且当前字符c为运算操作符，则将temp转换成double并录入objs，之后objs录入操作符c
            if(isNumber&&CalculateFormula.isOperator(c)){
            	isNumber = false;
            	num = Double.valueOf(temp);
            	objs.add(num);
            	objs.add(c);
            	temp = "";
            	continue;
            }
            if(!c.equals(" ")){
            	objs.add(c);
            	continue;
            }
            else continue;	
		}
		objs.add("#");
		
		double result = CalculateFormula.calculateFormula(objs);
		System.out.println(objs);
		return result;
	}
	
	
	private String checkMap(Map<String,String> m){
		Set<String> keySet = m.keySet();
	    String value = m.get(DataCode.COMMANDER_NAME);
	    if(value==null)
	    	return "缺少 COMMANDERNAME";
	    switch(value){
	    case DataCode.COMM_SUMFACTOR:
	    case DataCode.COMM_NUMBER:
	    	if((m.containsKey(DataCode.ENTITY_ID)||m.containsKey(DataCode.TABLE_NAME))
	    			&&m.containsKey(DataCode.FIELD)&&!m.containsKey(DataCode.LIMIT)
	    			&&!m.containsKey(DataCode.INDEX_CONT))
	    		return "success";
	    	else return "SUMFACTOR/NUMBER 参数配置错误";
	    case DataCode.COMM_FACTOR:
	    	if(!m.containsKey(DataCode.ENTITY_ID)&&!m.containsKey(DataCode.TABLE_NAME)
	    			&&!m.containsKey(DataCode.FIELD)&&!m.containsKey(DataCode.LIMIT)
	    			&&!m.containsKey(DataCode.CONDITION)&&m.containsKey(DataCode.INDEX_CONT))
	    		return "success";
	    	else return "FACTOR 参数配置错误";
	    case DataCode.COMM_COUNT:
	    	if((m.containsKey(DataCode.ENTITY_ID)||m.containsKey(DataCode.TABLE_NAME))
	    			&&!m.containsKey(DataCode.FIELD)&&!m.containsKey(DataCode.LIMIT)
	    			&&!m.containsKey(DataCode.INDEX_CONT))
	    		return "success";
	    	else return "COUNT 参数配置错误";
	    case DataCode.COMM_SUMNUMBER:
	    	if((m.containsKey(DataCode.ENTITY_ID)||m.containsKey(DataCode.TABLE_NAME))
	    			&&m.containsKey(DataCode.FIELD)&&!m.containsKey(DataCode.INDEX_CONT)
	    			&&m.containsKey(DataCode.LIMIT))
	    		return "success";
	    	else return "SUMNUMBER 参数配置错误";
	    default:return "不存在该指令";
	    }

	}
	
	private double calculateCommander(Map<String, String> commPackage) throws Exception {
		
		List<Map<String,String>> list=new LinkedList<Map<String,String>>();
		
		Set<String> keySet;
		String commName=commPackage.get(DataCode.COMMANDER_NAME);    //命令名称
		
		if(commName.equals(DataCode.COMM_FACTOR)){
			commPackage.put(DataCode.ENTITY_ID, "E2013O003");
			commPackage.put(DataCode.FIELD, DataCode.INDEX_FACTOR);
		}
		
		keySet=commPackage.keySet();
		   
		if(keySet.contains(DataCode.ENTITY_ID)){				//取本地采集的数据
			list=getRuleEngineDataService.getCalculateDataByID(commPackage);
		}else if(keySet.contains(DataCode.TABLE_NAME))          //取公共库数据
			list=getRuleEngineDataService.getCalculateDataByTableName(commPackage);
		
		this.commNameAndClass.put(DataCode.COMM_COUNT,"Count");    //tested
		this.commNameAndClass.put(DataCode.COMM_FACTOR, "FactorValue");  //tested
		this.commNameAndClass.put(DataCode.COMM_NUMBER, "Number");  //tested
		this.commNameAndClass.put(DataCode.COMM_SUMFACTOR, "SumFactor");   //tested
		this.commNameAndClass.put(DataCode.COMM_SUMNUMBER, "SumNumber");    //tested
		 
		String calssCommander=commNameAndClass.get(commName);          //获取要调用的计算类的类名
		
		ComputeCommander computeComm=CommanderFactory.getComputeCommander(calssCommander);
		double result=computeComm.compute(list);
		
		return result;
	}

	
}
