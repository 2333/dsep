package com.meta.domain;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.meta.entity.MetaAttrCheckRule;
import com.meta.entity.MetaCheckRule;

/**
 * 逻辑校验规则领域对象（包含一个属性简要的规则信息和适合于当前属性的规则参数值）
 * @author thbin
 * @version 2014-3-6
 */
public class MetaCheckRuleDomain implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8428571342778899661L;
	private MetaCheckRule checkRule;
	private MetaAttrCheckRule attrCheckRule;
	/**
	 * 本校验规则所需的参数个数
	 */
	private int paramCount;
	/**
	 * 本校验规则所需参数的类型
	 */
	private String[] paramType;
	/**
	 * 本校验规则所需参数的取值
	 */
	private String[] paramValue;
	
	public MetaCheckRuleDomain(MetaCheckRule checkRule, MetaAttrCheckRule attrCheckRule){
		this.checkRule = checkRule;
		this.attrCheckRule = attrCheckRule;
		fillParam();
	}
	/**
	 * 返回规则在属性表中的ID（属性规则表中）
	 * @return
	 */
	public String getAttrCheckId(){
		return attrCheckRule.getId();
	}
	/**
	 * 返回规则自身的ID号（规则表中）
	 * @return
	 */
	public String getId(){
		return checkRule.getId();
	}
	/**
	 *获得规则名称 
	 * @return
	 */
	public String getName(){
		return checkRule.getName();
	}
	/**
	 * 获得规则类型
	 * @return
	 */
	public String getCategory(){
		return checkRule.getCategory();
	}
	/**
	 * 获得规则说明信息
	 * @return
	 */
	public String getRemark(){
		return checkRule.getRemark();
	}
	/**
	 * 获得前台JS校验的函数名
	 * @return
	 */
	public String getJsCheck(){
		return checkRule.getJsCheck();
	}
	/**
	 * 获得后台进行校验的类名
	 * @return
	 */
	public String getClassCheck(){
		return checkRule.getClassCheck();
	}
	/**
	 * 获得本函数所需的参数个数
	 * @return
	 */
	public int getParamCount(){
		return paramCount;
	}
	/**
	 * 获得当前参数的类型信息
	 * @return
	 */
	public String[] getParamType() {
		return paramType;
	}
	/**
	 * 获得当前参数的取值信息
	 * @return
	 */
	public String[] getParamValue() {
		return paramValue;
	}
	
	private void calcParamCount(){
		paramCount = 0;
		if(StringUtils.isNotBlank(checkRule.getParam1())){
			paramCount++;
			if(StringUtils.isNotBlank(checkRule.getParam2())){
				paramCount++;
				if(StringUtils.isNotBlank(checkRule.getParam3())){
					paramCount++;
					if(StringUtils.isNotBlank(checkRule.getParam4())){
						paramCount++;
						if(StringUtils.isNotBlank(checkRule.getParam5())){
							paramCount++;
						}
					}
				}
			}
		}
	}
	private void fillParam(){
		int i=0;
		calcParamCount();
		if(paramCount>0){
			paramType = new String[paramCount];
			paramValue = new String[paramCount];
			if(StringUtils.isNotBlank(checkRule.getParam1())){
				paramType[i] = checkRule.getParam1();
				paramValue[i] = attrCheckRule.getValue1();
				i++;
				if(StringUtils.isNotBlank(checkRule.getParam2())){
					paramType[i] = checkRule.getParam2();
					paramValue[i] = attrCheckRule.getValue2();
					i++;
					if(StringUtils.isNotBlank(checkRule.getParam3())){
						paramType[i] = checkRule.getParam3();
						paramValue[i] = attrCheckRule.getValue3();
						i++;
						if(StringUtils.isNotBlank(checkRule.getParam4())){
							paramType[i] = checkRule.getParam4();
							paramValue[i] = attrCheckRule.getValue4();
							i++;
							if(StringUtils.isNotBlank(checkRule.getParam5())){
								paramType[i] = checkRule.getParam5();
								paramValue[i] = attrCheckRule.getValue5();
								i++;
							}
						}
					}
				}
			}
		}
	}
	
	public MetaCheckRule getCheckRule() {
		return checkRule;
	}
	public void setCheckRule(MetaCheckRule checkRule) {
		this.checkRule = checkRule;
	}
	public MetaAttrCheckRule getAttrCheckRule() {
		return attrCheckRule;
	}
	public void setAttrCheckRule(MetaAttrCheckRule attrCheckRule) {
		this.attrCheckRule = attrCheckRule;
	}
}
