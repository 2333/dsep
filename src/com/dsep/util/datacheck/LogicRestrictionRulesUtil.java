package com.dsep.util.datacheck;

import java.util.List;
import java.util.Map;

import com.meta.entity.MetaDicItem;

/**
 * 逻辑规则校验
 * 
 * @author lubin
 * @author yuyaolin
 * 
 */
public class LogicRestrictionRulesUtil {

	/**
	 * 检验限填信息是否正确
	 * @param str
	 * @param list 字典集合
	 * @return
	 */
	public static String checkRestrictionInfo(String str,Map<String,String> map) {
		if(LogicStringRulesUtil.stringNullUtil(str)){
			if(map.get(str)!= null)
				return LogicCheckErrorCode.Check_Pass;
			else 
				return LogicCheckErrorCode.Value_Restriction_Error;
		}
		else
			return LogicCheckErrorCode.Value_Null_Error;
	}


}
