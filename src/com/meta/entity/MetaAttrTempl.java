package com.meta.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "META_ATTR_TEMPL")
public class MetaAttrTempl implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1593445572022786348L;
	private String id;
	private String entityId;
	private String name;
	private String chsName;
	private String dataType;
	private int length;
	private String isNull;
	private String defaultValue;
	private int seqNo;
	private String index;
	private int indexNo;
	private String dicId;
	private String remark;
	private String templRule;
	
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
	
	@Column(name = "NAME", length = 50, nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "CHSNAME", length = 50, nullable = false)
	public String getChsName() {
		return chsName;
	}
	public void setChsName(String chsName) {
		this.chsName = chsName;
	}
	
	@Column(name = "DATATYPE", length = 20, nullable = false)
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	@Column(name = "LENGTH", nullable = false)
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
	@Column(name = "ISNULL", length = 1, nullable = false)
	public String getIsNull() {
		return isNull;
	}
	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
	
	@Column(name = "DEFAULTVALUE", length = 20, nullable = true)
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@Column(name = "SEQNO", nullable = false)
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	
	@Column(name = "INDEX", length = 1, nullable = false)
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	
	@Column(name = "INDEXNO", nullable = false)
	public int getIndexNo() {
		return indexNo;
	}
	public void setIndexNo(int indexNo) {
		this.indexNo = indexNo;
	}
	
	@Column(name = "DICID", length = 20, nullable = true)
	public String getDicId() {
		return dicId;
	}
	public void setDicId(String dicId) {
		this.dicId = dicId;
	}
	
	@Column(name = "REMARK", length = 100, nullable = true)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "TEMPLRULE", length = 1, nullable = false)
	public String getTemplRule() {
		return templRule;
	}
	public void setTemplRule(String templRule) {
		this.templRule = templRule;
	}
	
	
}
