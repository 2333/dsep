package com.dsep.domain.dsepmeta.viewconfig.views;

import java.io.Serializable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.meta.domain.MetaAttrDomain;
import com.meta.domain.MetaCheckRuleDomain;
import com.meta.entity.MetaControlType;

public class JqgridColConfig implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7770550294543490740L;
	private String name;
	private String index;
	private String label;

	
	private int width;
	private String align;
	private boolean hidden;
	
	
	private String formatter;
	//private Map<String,Object> formatoptions;
	
	private boolean sortable;
	private boolean editable;
	private String edittype;	
	private Map<String,Object> editoptions;
	
	private Map<Object,Object> editrules;
	
	private String searchtype;
	
	public JqgridColConfig(MetaAttrDomain a) {
		this.setBasicConfig(a);
		this.setFormatter(a);
		this.setEditer(a);
		this.setRules(a);
		this.setSearchtype(a);
		this.setSortable(a);
		
	}
	
	/** 设置列基本配置信息
	 * @param attr
	 */	
	private void setBasicConfig(MetaAttrDomain attr){
		this.name = attr.getName();
		this.index = attr.getName();
		this.label = attr.getChsName();		
		
		this.width = attr.getDispLength();
		this.align = attr.getAlign();//"center";
		this.hidden = attr.isHidden();			
	}
	
	/** 设置显示格式
	 * @param attr
	 */
	private void setFormatter(MetaAttrDomain attr){
		/* 临时代码，等到后续加入Formatter Type */
		MetaControlType mType = attr.getControlTypeObject();
		
		//this.formatoptions = new HashMap<String,Object>();
		switch(mType){
		case SELECT:
		case LINKSELECT:
			this.formatter = "select";
			break;
		case DATE:
			this.formatter = "date";
			break;
		default:
			this.formatter = "";
		}
	}
	
	/***********************设置编辑配置信息开始************************************/
	
	private void setEditer(MetaAttrDomain attr){
		//能否编辑
		this.editable = attr.isEditable();
		
		MetaControlType mType = attr.getControlTypeObject();
		this.setEditTypes(mType);	
		this.setEditOptions(attr);
	}
	
	private void setEditTypes(MetaControlType mType){
		
		switch(mType){
		case SELECT:	
		case LINKSELECT:
			this.edittype = "select";
			break;
		case TEXTAREA:
			this.edittype = "textarea";
			break;
		case CHECKBOX:
			this.edittype = "checkbox";
			break;
		case FILE:
			/*this.edittype = "file";
			break;*/
		case MULSELECT:
		case CHECKTEXT:
		case RADIOTEXT:
		case PERCENT:
			this.edittype = "button";
			break;
		default:
			this.edittype = "text";
			break;
		}	
	}
	
	private void setEditOptions(MetaAttrDomain attr){
		
		MetaControlType mType = attr.getControlTypeObject();
		this.editoptions = new HashMap<String,Object>();		
		switch(mType){
		case MULSELECT:
			Map<String,String> source1 = attr.getDicItems();
			this.editoptions.put("valueMulSel", setOptions(source1));
			break;
		case SELECT:
		case PROMPT:
		case MULPROMPT:
		case LINKPROMPT:
		case LINKSELECT:
			Map<String,String> source2 = attr.getDicItems();
			this.editoptions.put("value", setOptions(source2));
			if(mType.equals(MetaControlType.PROMPT))this.editoptions.put("dataInit", "loadAutoComplete");
			if(mType.equals(MetaControlType.MULPROMPT))this.editoptions.put("dataInit", "mulLoadAutoComplete");
			if(mType.equals(MetaControlType.LINKPROMPT))this.editoptions.put("dataInit", "linkLoadAutoComplete");
			break;
		case TEXTAREA:
			this.editoptions.put("rows", attr.getRowNums());
			this.editoptions.put("cols", attr.getColNums());
			break;
		case CHECKBOX:
			break;
		case FILE:
			break;
		default:
			break;
		}
	}
	
	/******************设置编辑配置信息结束****************************************/
	
	
	/** 设置约束条件
	 * @param a
	 */
	private void setRules(MetaAttrDomain a){
		this.editrules=new HashMap<Object,Object>();
		MetaCheckRuleDomain checkRule = a.getCheckRule();
		boolean isJsCheck = (checkRule != null) && (StringUtils.isNotBlank(checkRule.getJsCheck() ) );
		this.editrules.put("custom",(isJsCheck)?true:false);
		this.editrules.put("custom_func", "");
		if(!a.isNull()){
			this.editrules.put("required", true);
		}else{
			this.editrules.put("required", false);
		}
	}
	
	/**
	 * 将字典中添加空字符
	 * @param source
	 * @return
	 */
	private Map<String,String> setOptions(Map<String, String> source)
	{
		Map<String, String> newSource = new LinkedHashMap<String,String>(0);
		newSource.put("", "");
		for(String key: source.keySet())
		{
			newSource.put(key, source.get(key));
		}
		return newSource;
	}
	/**************以下为 getters & setters*****************************/
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getFormatter() {
		return formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}
	
	/*public Map<String,Object> getFormatoptions() {
		return formatoptions;
	}

	public void setFormatoptions(Map<String,Object> formatoptions) {
		this.formatoptions = formatoptions;
	}*/

	public String getEdittype() {
		return edittype;
	}

	public void setEdittype(String edittype) {
		this.edittype = edittype;
	}

	public Map<String, Object> getEditoptions() {
		return editoptions;
	}

	public void setEditoptions(Map<String, Object> editoptions) {
		this.editoptions = editoptions;
	}

	public Map<Object, Object> getEditrules() {
		return editrules;
	}

	public void setEditrules(Map<Object, Object> editrules) {
		this.editrules = editrules;
	}

	public String getSearchtype() {
		return searchtype;
	}

	public void setSearchtype(MetaAttrDomain attr) {
		String dataType = attr.getDataType();
		if(StringUtils.isNotBlank(dataType)){
			switch(dataType.toUpperCase()){
				case "INT":
					this.searchtype="integer";
					break;
				case "DOUBLE":
					this.searchtype="number";
					break;
				case "PERCENT":
					this.searchtype="percent";
					break;
				case "DATETIME":
					this.searchtype="datetime";
					break;
				case "YEARMONTH":
					this.searchtype="yearmonth";
					break;
				case "YEAR":
					this.searchtype="year";
					break;
				default: 
					this.searchtype="text";
					break;	
			}
		}
		
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(MetaAttrDomain a) {
		this.sortable = a.isSortable();
	}
	
}
