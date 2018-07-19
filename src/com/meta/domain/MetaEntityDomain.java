package com.meta.domain;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dsep.util.Configurations;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.meta.entity.MetaEntity;
import com.meta.entity.MetaEntityStyle;
/**
 * 前端需要使用的实体类领队模型，包括实体类的基本信息，属性信息，实体显示样式信息，属性显示样式
 * @author thbin
 * @version 2014-02-23
 */
public class MetaEntityDomain  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1049241956398832253L;
	private MetaEntity entity;
	private MetaCheckRuleDomain checkRule;
	private MetaEntityStyle entityStyle;
	private List<MetaAttrDomain> attrDomains;
	//private Map<String, MetaAttrDomain> attrDomains = new LinkedHashMap<String, MetaAttrDomain>();
	
	/*private String id;
	private String name;
	private String chsName;
	private String remark;
	
	private String styleId;
	private String formType;
	private int formWidth;
	private int formHeight;
	private String css;*/
	
	/**
	 * 构造一个实体领域对象，包括实体信息、实体显示信息和属性领域对象
	 * @param entity 实体信息
	 * @param entityStyle 实体显示样式
	 * @param attrDomains 属性领域对象
	 */
	public MetaEntityDomain(MetaEntity entity, MetaEntityStyle entityStyle, List<MetaAttrDomain> attrDomains)
	{
		this.entity = entity;
		this.entityStyle = entityStyle;
		this.attrDomains = attrDomains;
	}
	/**
	 * 获得该实体ID
	 * @return
	 */
	public String getId() {
		return entity.getId();
	}
	/**
	 * 该实体英文名字，也即是该实体对应的数据库表名称
	 * @return
	 */
	public String getName() {
		return entity.getName();
	}
	/**
	 * 根据版本号获得表名，如果版本号为空，则返回原表名，否则返回备份表表名，目前所有的备份表均采用一个表名
	 * @param versionId
	 * @return
	 */
	public String getName(String versionId)
	{
		return entity.getName() + Configurations.getBackupTablePostfix(versionId);
	}
	/**
	 * 该实体的中文名字
	 * @return
	 */
	public String getChsName() {
		return entity.getChsName();
	}
	/**
	 * 数据类型，采集项的数据来源（采集、公共库）
	 * @return
	 */
	public String getDataType(){
		return entity.getDataType();
	}
	/**
	 * 返回该实体主键的名字
	 * @return
	 */
	public String getPkName(){
		return entity.getPkName();
	}
	/**
	 * 
	 * @return 当前实体的身份字段，一般可以定位到一个实体，如专家字段的专家姓名、科研项目的项目名称
	 */
	public String getIdAttr(){
		return entity.getIdAttr();
	}
	/**
	 * 该实体的注释信息
	 * @return
	 */
	public String getRemark() {
		return entity.getRemark();
	}
	/**
	 * 获得实体逻辑校验规则Id
	 * @return
	 */
	public String getCheckRuleId(){
		return entity.getCheckRule();
	}
	/**
	 * 获取采集项最大记录数，0为无限制
	 * @return
	 */
	public int getMaxNum()
	{
		return entity.getMaxNum();
	}
	/**
	 * 获得校验规则名称
	 * 
	 * @return
	 */
	public String getCheckRuleName() {
		return checkRule.getName();
	}

	/**
	 * 获得属性逻辑检查规则分类
	 * 
	 * @return
	 */
	public String getCheckRuleCategory() {
		return checkRule.getCategory();
	}

	/**
	 * 获得校验规则对应的JS
	 * 
	 * @return
	 */
	public String getCheckRuleJS() {
		return checkRule.getJsCheck();
	}

	/**
	 * 获得校验规则对应的类信息
	 * 
	 * @return
	 */
	public String getCheckRuleClass() {
		if(this.checkRule == null)
			return null;
		return checkRule.getClassCheck();
	}

	/**
	 * 获得校验规则注释
	 * 
	 * @return
	 */
	public String getCheckRuleRemark() {
		return checkRule.getRemark();
	}

	/**
	 * 获得校验规则对应的可用参数个数
	 * 
	 * @return
	 */
	public int getCheckRuleParamCount() {
		return checkRule.getParamCount();
	}

	/**
	 * 获得校验规则对应的参数类型
	 * 
	 * @return
	 */
	public String[] getCheckRuleParamType() {
		if (checkRule != null)
			return checkRule.getParamType();
		return new String[0];
	}

	/**
	 * 获得校验规则对应的参数取值
	 * 
	 * @return
	 */
	public String[] getCheckRuleParamValue() {
		if (checkRule != null)
			return checkRule.getParamValue();
		return new String[1];
	}
	/**
	 * 获得实体数据计算规则Id
	 * @return
	 */
	public String getDataRuleId(){
		return entity.getDataRule();
	}
	/**
	 * 实体当前显示ID
	 * @return
	 */
	public String getStyleId() {
		return entityStyle.getId();
	}

	/**
	 * 返回该实体当前显示风格对应的缺省排序属性
	 * @return
	 */
	public String getOrderAttr(){
		return entityStyle.getOrderAttr();
	}
	public boolean isEditable(){
		return entityStyle.isEditable();
	}
	/**
	 * 实体风格对应的显示界面类型T-表格型，F-表单型
	 * @return
	 */
	public String getFormType() {
		return entityStyle.getFormType();
	}
	/**
	 * 实体风格对应的表单宽度
	 * @return
	 */
	public int getFormWidth() {
		return entityStyle.getFormWidth();
	}
	/**
	 * 实体风格对应的表单高度
	 * @return
	 */
	public int getFormHeight() {
		return entityStyle.getFormHeight();
	}
	/**
	 * 实体的最大字数
	 * @return
	 */
	public int getMaxWords(){
		return entityStyle.getMaxWords();
	}
	/**
	 * 实体风格对应的CSS文件
	 * @return
	 */
	public String getCss() {
		return entityStyle.getCss();
	}
	/**
	 * 属性领域对象列表get函数
	 * @return
	 */
	public List<MetaAttrDomain> getAttrDomains() {
		return attrDomains;
	}
	/**
	 * 属性领域对象列表set函数
	 * @return
	 */
	public void setAttrDomains(List<MetaAttrDomain> attrDomains) {
		this.attrDomains = attrDomains;
	}
	/**
	 * 实体对象get函数
	 * @return
	 */
	public MetaEntity getEntity() {
		return entity;
	}
	/**
	 * 实体对象set函数
	 * @return
	 */
	public void setEntity(MetaEntity entity) {
		this.entity = entity;
	}
	/**
	 * 实体样式对象get函数
	 * @return
	 */
	public MetaEntityStyle getEntityStyle() {
		return entityStyle;
	}
	/**
	 * 实体样式对象set函数
	 * @return
	 */
	public void setEntityStyle(MetaEntityStyle entityStyle) {
		this.entityStyle = entityStyle;
	}
	public MetaCheckRuleDomain getCheckRule() {
		return checkRule;
	}
	public void setCheckRule(MetaCheckRuleDomain checkRule) {
		this.checkRule = checkRule;
	}
	/**
	 * 获取初始化规则
	 * @return
	 */
	public String getInitRule(){
		return entityStyle.getInitRule();
	}
	/**
	 * 获取初始化记录数
	 * @return
	 */
	public int getInitRows(){
		return entityStyle.getInitRows();
	}
	/**
	 * 获取模板Id
	 * @return
	 */
	public String getTemplateId(){
		return entity.getTemplateId();
	}
	
	public String getControlTypeRles(){
		return entityStyle.getControlTypeRule();
	}
}
