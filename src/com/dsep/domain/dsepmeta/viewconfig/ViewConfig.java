package com.dsep.domain.dsepmeta.viewconfig;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import com.dsep.domain.dsepmeta.viewconfig.views.CKFormViewConfig;
import com.dsep.domain.dsepmeta.viewconfig.views.FileFormViewConfig;
import com.dsep.domain.dsepmeta.viewconfig.views.FormViewConfig;
import com.dsep.domain.dsepmeta.viewconfig.views.InitedJqGridConfig;
import com.dsep.domain.dsepmeta.viewconfig.views.JqgridFormConfig;
import com.dsep.domain.dsepmeta.viewconfig.views.JqgridViewConfig;
import com.dsep.domain.dsepmeta.viewconfig.views.ReadOnlyJqgridConfig;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.meta.domain.MetaEntityDomain;
import com.meta.entity.MetaControlType;

public abstract class ViewConfig {
	protected String id;
	protected String name;
	protected ViewType type;
	protected String dataType;//采集的数据来源
	//key应该为控件类型（枚举）
	protected Map<MetaControlType,List<String>> idsOfControlDic;
	
	protected String templateId;
	public ViewConfig(){
		
	}
	public ViewConfig(MetaEntityDomain e){
		setViewConfig(e);
	}
	public void setViewConfig(MetaEntityDomain e){
		setId(e.getId());
		setName(e.getName());
		setType(ViewType.getViewType((e.getFormType())));
		setTemplateId(e);
		setDataType(e.getDataType());
	}
	public static ViewConfig produce(MetaEntityDomain e) {
		ViewType viewType = ViewType.getViewType(e.getFormType());
		switch(viewType){
		case JQGRID:
			return new JqgridViewConfig(e);
		case FORM:
			return new FormViewConfig();
		case JQFORM:
			return new JqgridFormConfig(e);
		case INITJQGRID:
			return new InitedJqGridConfig(e);
		case ReadOnlyJQGRID:
			 return new ReadOnlyJqgridConfig(e);
		case CKFORM:
			return new CKFormViewConfig(e);
		case FILEFORM:
			return new FileFormViewConfig(e);
		default:
			throw new NoClassDefFoundError("未定义该类型的ViewConfig："+ viewType);
		}
	}	
	public ViewType getType(){
		return this.type;
	}
	
	public void setType(ViewType type) {
		this.type = type;
	}
	/** 将某一列设为可以显示
	 * @param colName 列名
	 * @param label	      需要显示中文列名
	 * @param Visible 是否可见
	 * @param width   显示宽度
	 */
	public abstract void setVisible(String colName,String label,boolean Visible,int width);
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(MetaEntityDomain e) {
		this.templateId = e.getTemplateId();
	}
	public String getDataType() {
		return dataType;
	}
	
	public void setDataType(String dataType) {
		if(StringUtils.isBlank(dataType)){
			this.dataType="C";
		}else{
			this.dataType = dataType;
		}
		
	}
}

