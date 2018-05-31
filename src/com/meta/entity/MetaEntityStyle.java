package com.meta.entity;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.omg.CORBA.PRIVATE_MEMBER;

@Entity
@Table(name = "META_ENTITY_STYLE")
public class MetaEntityStyle  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5310091991297616264L;
	private String id;
	private String entityId;
	private String orderAttr;
	private String formType;
	/**
	 * 规则的配置方式：字段名1:[value1,value2];字段2:[value1,value2]
	 */
	private String initRule;
	/**
	 * 配置方式：类型：行号1、行号2
	 */
	private String controlTypeRule;
	private int initRows;//初始化的行数
	private int formWidth;
	private int formHeight;
	private int maxWords;
	private String css;
	private String occassion;
	private boolean editable;

	private Set<MetaAttrStyle> attrStyles = new TreeSet<MetaAttrStyle>();

	@Id
	@GenericGenerator(name = "generator", strategy = "assigned")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", length = 20, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "ENTITYID", length = 20, nullable = false)
	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	
	@Column(name = "ORDERATTR", length = 50, nullable = false)
	public String getOrderAttr() {
		return orderAttr;
	}

	public void setOrderAttr(String orderAttr) {
		this.orderAttr = orderAttr;
	}
	@Column(name = "FORMTYPE", length = 1, nullable = false)
	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	@Column(name = "FORMWIDTH", nullable = false)
	public int getFormWidth() {
		return formWidth;
	}

	public void setFormWidth(int formWidth) {
		this.formWidth = formWidth;
	}

	@Column(name = "FORMHEIGHT", nullable = false)
	public int getFormHeight() {
		return formHeight;
	}

	public void setFormHeight(int formHeight) {
		this.formHeight = formHeight;
	}

	@Column(name = "CSS", length = 50, nullable = true)
	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	@Column(name = "OCCASSION", length = 50, nullable = true)
	public String getOccassion() {
		return occassion;
	}

	public void setOccassion(String occassion) {
		this.occassion = occassion;
	}

	@Column(name = "EDITABLE", length = 1, nullable = true)
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	@Column(name="MAXWORDS",length = 10, nullable = true)
	public int getMaxWords() {
		return maxWords;
	}

	public void setMaxWords(int maxWords) {
		this.maxWords = maxWords;
	}
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
	@JoinColumn(name = "ENTITYSTYLEID")
	public Set<MetaAttrStyle> getAttrStyles() {
		return attrStyles;
	}
	
	public void setAttrStyles(Set<MetaAttrStyle> attrStyles) {
		this.attrStyles = attrStyles;
	}
	@Column(name="INIT_RULE",length = 200, nullable = true)
	public String getInitRule() {
		return initRule;
	}

	public void setInitRule(String initRule) {
		this.initRule = initRule;
	}
	@Column(name="INIT_ROWS",nullable = false ,columnDefinition="integer default 0")
	public int getInitRows() {
		return initRows;
	}

	public void setInitRows(int initRows) {
		this.initRows = initRows;
	}
	
	@Column(name="CONTROL_TYPE_RULES",nullable = true, length=200)
	public String getControlTypeRule() {
		return controlTypeRule;
	}

	public void setControlTypeRule(String controlTypeRule) {
		this.controlTypeRule = controlTypeRule;
	}
}
