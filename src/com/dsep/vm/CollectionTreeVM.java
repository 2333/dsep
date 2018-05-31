package com.dsep.vm;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.dsep.entity.dsepmeta.CategoryCollect;


/**
 * 数据采集结构树
 * @author 
 *
 */
public class CollectionTreeVM {
	/**
	 * 显示的名称
	 */
	private String name;
	/**
	 * 父节点ID
	 */
	private String pId;
	/**
	 * 自身id
	 */
	private String Id;
	/**
	 * 是否展开
	 */
	private boolean open;
	/**
	 * 
	 */
	private String title;
	/**
	 * 
	 */
	private boolean click;
	/**
	 * 
	 */
	private String value;
	/**
	 * 实体说明信息
	 */
	private String entityMemo;//实体说明
	
	private String templateId;
	public CollectionTreeVM()
	{
		
	}
	public CollectionTreeVM(CategoryCollect c){
		this.Id= c.getId();
		this.name= c.getMetaChsName();
		this.pId= c.getParentId();
		this.title = c.getMetaChsName();
		this.open= true;
		this.click=true;
		this.value=c.getMetaId();
		setEntityMemo(c.getRemark());
		this.templateId = c.getTemplateId();
	
	}
	public CollectionTreeVM(String id, String name, String pId, String title, boolean open, boolean click, String value) {
		this.Id = id;
		this.name = name;
		this.pId = pId;
		this.title = title;
		this.open = open;
		this.click = click;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public boolean isClick() {
		return click;
	}
	public void setClick(boolean click) {
		this.click = click;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getEntityMemo() {
		return entityMemo;
	}
	public void setEntityMemo(String entityMemo) {
		if(entityMemo==null){
			this.entityMemo="";
		}else{
			this.entityMemo = entityMemo;
		}	
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
}
