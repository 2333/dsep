package com.meta.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "META_ATTR_MAP")
public class MetaAttributeMap {
	private String id;//id
	private MetaAttribute originAttr;
	private MetaAttribute targetAttr;
	/**
	 * 转换规则
	 */
	private String rule;
	/**
	 * 转换规则备注参数
	 */
	private String remark;
	@Id
	@GenericGenerator(name = "generator", strategy = "guid")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", length = 32, nullable = false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@ManyToOne(targetEntity = MetaAttribute.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "ORI_ATTR_ID")
	public MetaAttribute getOriginAttr() {
		return originAttr;
	}
	public void setOriginAttr(MetaAttribute originAttr) {
		this.originAttr = originAttr;
	}
	@ManyToOne(targetEntity = MetaAttribute.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "TAR_ATTR_ID")
	public MetaAttribute getTargetAttr() {
		return targetAttr;
	}
	public void setTargetAttr(MetaAttribute targetAttr) {
		this.targetAttr = targetAttr;
	}
	@Column(name = "rule", length = 20)
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	
	@Column(name = "remark", length = 200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
