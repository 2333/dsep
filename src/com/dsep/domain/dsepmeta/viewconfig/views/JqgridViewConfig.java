package com.dsep.domain.dsepmeta.viewconfig.views;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.metamodel.source.hbm.Helper.ValueSourcesAdapter;
import org.omg.CORBA.PRIVATE_MEMBER;


import com.dsep.domain.dsepmeta.viewconfig.ViewConfig;
import com.dsep.domain.dsepmeta.viewconfig.ViewType;
import com.dsep.domain.dsepmeta.viewconfig.views.JqgridColConfig;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.meta.domain.MetaAttrDomain;
import com.meta.domain.MetaCheckRuleDomain;
import com.meta.domain.MetaEntityDomain;
import com.meta.entity.MetaControlType;
import com.meta.entity.MetaDicItem;

public class JqgridViewConfig extends ViewConfig{
	private String pk;
	private String attrId;//主属性
	private String defaultSortCol;
	private String memo;
	private int maxNum;
	private List<String> editableColIds;
	private List<String> allColIds;	
	private List<JqgridColConfig> colConfigs;
	private Map<String,List<String>> jsCheckParamsAndValueDic;  //Map<ColId,list<"参数类型,参数值">>
	private Map<String,String> jsCheckFunNameDic;//Map<ColId,FunctionName>
	private Map<String, Map<String, Map<String, List<String>>>> linkSelectDicValues;
	private String entityRule;
	public JqgridViewConfig(){
		
	}
	/** 构造函数
	 * @param e
	 * @throws Exception 
	 */
	public JqgridViewConfig(MetaEntityDomain e){
			super(e);
			this.setJqConfig(e);
			this.setJqColsConfig(e);
			this.addLinkSelectDicValue(e);//对jqgrid关联下拉框的数据封装
	}
	

	/** 设置jqgrid的显示参数
	 * @param eStyle
	 */
	private void setJqConfig(MetaEntityDomain e){
		super.id = e.getId();
		super.name = e.getChsName();
		super.type = ViewType.getViewType(e.getFormType());
		this.maxNum=e.getMaxNum();
		this.pk=e.getPkName();
		this.attrId = e.getIdAttr();//主属性
		this.defaultSortCol=e.getOrderAttr();
		this.memo = e.getRemark();
		this.setEntityRule(e);
	}
	
	/** 设置jqgrid各列的显示参数
	 * @param aStyles
	 * @throws Exception 
	 */
	private void setJqColsConfig(MetaEntityDomain e){
		//取样式信息
		List<MetaAttrDomain> metaAttrs = e.getAttrDomains();
		//初始化Lists和Map
		this.allColIds = new LinkedList<String>();
		this.editableColIds = new LinkedList<String>();
		this.colConfigs = new LinkedList<JqgridColConfig>();
		this.initControlDic();
		//开始加属性样式信息
		for(MetaAttrDomain a : metaAttrs){
			this.allColIds.add(a.getName()); //该jqgrid 的 colId集合
			if(a.isEditable())
				this.editableColIds.add(a.getName()); //该jqgrid 的 可编辑字段的colId集合
			this.colConfigs.add(new JqgridColConfig(a));//jqgrid每列的设置参数
			addId2ControlDic(a);						//jqgrid的控件字典(Map<控件枚举类型，List<ColId>>)
			
			boolean isJsCheck = (a.getCheckRule()!= null) && (StringUtils.isNotBlank(a.getCheckRule().getJsCheck() ));
			if(isJsCheck){
			addItem2ParamsAndValueDic(a);				//jqgrid每列验证函数的参数以及值(Map<ColId,Map<参数类型,参数值>>)
			addItem2JsNameDic(a);
			}
		}
	}
	/** 初始化控件字典
	 * 
	 */
	private void initControlDic(){
		
		MetaControlType[] controlTypes = MetaControlType.values();
		
		super.idsOfControlDic = new EnumMap<MetaControlType, List<String>>(MetaControlType.class);
		
		for(MetaControlType t : controlTypes){
			super.idsOfControlDic.put(t, new LinkedList<String>());
		}
	}
	
	
	/** 控件字典添加元素
	 * @param a
	 */
	private void addId2ControlDic(MetaAttrDomain a){
		
		MetaControlType controlType = a.getControlTypeObject();
		String colId = a.getName();
		
		List<String> values =super.idsOfControlDic.get(controlType);
		if(values == null) values =  new LinkedList<String>();
		values.add(colId);
		super.idsOfControlDic.put(controlType, values);
		
	}
	
	private void addItem2ParamsAndValueDic(MetaAttrDomain a){
		if(this.jsCheckParamsAndValueDic == null) this.jsCheckParamsAndValueDic = new HashMap<String,List<String>>();		
		String[] paramsTypes = a.getCheckRuleParamType();
		String[] paramsValues = a.getCheckRuleParamValue();
		
		if(paramsTypes != null && paramsTypes.length > 0){
			List<String> paramsDic = new LinkedList<String>();
			for(int i=0; i < paramsTypes.length; i++){
				String paramType = paramsTypes[i];
				String paramValue=( paramsValues != null && i < paramsValues.length )?
									paramsValues[i]: "";
				paramsDic.add(paramType + "," + paramValue);
			}
			String colId =  a.getChsName();
		if(!this.jsCheckParamsAndValueDic.containsKey(colId))
			this.jsCheckParamsAndValueDic.put(colId, paramsDic);
		}
	
	}
	private void addLinkSelectDicValue(MetaEntityDomain e){
		if(this.linkSelectDicValues==null){
			this.linkSelectDicValues = new HashMap<String,Map<String,Map<String,List<String>>>>(0);
		}
		for(MetaAttrDomain a1: e.getAttrDomains()){
			if(a1.getControlTypeObject().equals(MetaControlType.LINKSELECT)||
					a1.getControlTypeObject().equals(MetaControlType.LINKPROMPT)){
				Map<String, Map<String, List<String>>> subLink = new HashMap<String,Map<String, List<String>>>(0);
				for(MetaAttrDomain a2:e.getAttrDomains()){
					if(isLink(a1,a2)){
						subLink.put(a2.getName(),a2.getDic().getLinkMap());
					}
				}
				if(this.linkSelectDicValues.containsKey(a1.getName())){
					linkSelectDicValues.get(a1.getName()).putAll(subLink);
				}else{
					linkSelectDicValues.put(a1.getName(), subLink);
				}
			}
		}
	}
	private void addItem2JsNameDic(MetaAttrDomain a){
	if(this.jsCheckFunNameDic == null) this.jsCheckFunNameDic = new HashMap<String,String>();
	MetaCheckRuleDomain checkRule = a.getCheckRule();
	if(!this.jsCheckFunNameDic.containsKey(a.getChsName()))
		this.jsCheckFunNameDic.put(a.getChsName(), checkRule.getJsCheck());
	}
	public void newColconfig(JqgridColConfig config){
		this.colConfigs.add(config);
	}
	
	@Override
	public void setVisible(String colName,String label,boolean Visible,int width) {
		for(JqgridColConfig config : this.colConfigs)
			if(config.getName().equals(colName)){
				config.setHidden(!Visible);
				config.setWidth(width);
				config.setLabel(label);
				break;
			}
	}
	/**
	 * 判断两个属性是否存在关联
	 * @param a1（主）
	 * @param a2（从）
	 * @return 
	 */
	private boolean isLink(MetaAttrDomain a1,MetaAttrDomain a2){
		for(MetaDicItem item1:a1.getDic().getDic().getDicItemSet()){
			if(a2.getControlTypeObject().equals(MetaControlType.SELECT)||
					a2.getControlTypeObject().equals(MetaControlType.LINKSELECT)||
					a2.getControlTypeObject().equals(MetaControlType.PROMPT)||
					a2.getControlTypeObject().equals(MetaControlType.LINKPROMPT)){
				for(MetaDicItem item2:a2.getDic().getDic().getDicItemSet()){
					if(item2.getpId()!=null&&item2.getpId().equals(item1.getId())){
						return true;
					}
				}
			}
		}
		return false;
	}
	/* ********************以下为getters*********************************** */
	public String getId(){
		return super.id;
	}
	
	public String getName(){
		return super.name;
	}
	
	public ViewType getType(){
		return super.type;
	} 
	
	public Map<MetaControlType,List<String>> getIdsOfControlDic(){
		return super.idsOfControlDic;
	}
	
	public String getPk() {
		return pk;
	}
	
	public String getDefaultSortCol() {
		return defaultSortCol;
	}

	public List<String> getColIds() {
		return allColIds;
	}

	public List<JqgridColConfig> getColConfigs() {
		return colConfigs;
	}

	public String getMemo() {
		return memo;
	}
	public List<String> getEditableColIds() {
		return editableColIds;
	}
	public List<String> getAllColIds() {
		return allColIds;
	}
	
	public Map<String, List<String>> getJsCheckParamsAndValueDic() {
		return jsCheckParamsAndValueDic;
	}
	public void setJsCheckParamsAndValueDic(
			Map<String, List<String>> jsCheckParamsAndValueDic) {
		this.jsCheckParamsAndValueDic = jsCheckParamsAndValueDic;
	}
	public Map<String, String> getJsCheckFunNameDic() {
		return jsCheckFunNameDic;
	}
	public void setJsCheckFunNameDic(Map<String, String> jsCheckFunNameDic) {
		this.jsCheckFunNameDic = jsCheckFunNameDic;
	}
	public int getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
	public String getAttrId() {
		return attrId;
	}
	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}
	public Map<String, Map<String, Map<String, List<String>>>> getLinkSelectDicValues() {
		return linkSelectDicValues;
	}
	public String getEntityRule() {
		return entityRule;
	}
	public void setEntityRule(MetaEntityDomain e) {
		if(e.getCheckRuleParamValue()!=null&&e.getCheckRuleParamValue().length>0)
		this.entityRule = e.getCheckRuleParamValue()[0];
	}
}
