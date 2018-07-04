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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "META_ENTITY")
public class MetaEntity  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5894484267404562413L;
	private String id;
	private String domainId;
	private String categoryId;
	private String name;
	private String chsName;
	private String pkName;
	private String idAttr;
	private String remark;
	private String checkRule;
	private String dataRule;
	private String templateId;
	private String dataType;//采集，还是公共库
	private String isExistFile;
	private int maxNum;

	private Set<MetaAttribute> attributes = new TreeSet<MetaAttribute>();
	private Set<MetaEntityStyle> entityStyles = new TreeSet<MetaEntityStyle>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MetaEntity))
			return false;

		final MetaEntity entity = (MetaEntity) obj;
		return this.id == entity.id;
	}

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

	@Column(name = "DOMAINID", length = 20, nullable = false)
	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	@Column(name = "CATEGORYID", length = 20, nullable = false)
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST})
	@JoinColumn(name = "ENTITYID")
	public Set<MetaEntityStyle> getEntityStyles() {
		return entityStyles;
	}

	public void setEntityStyles(Set<MetaEntityStyle> entityStyles) {
		this.entityStyles = entityStyles;
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

	@Column(name = "PKNAME", length = 50, nullable = false)
	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	@Column(name = "IDATTR", length = 50, nullable = false)
	public String getIdAttr() {
		return idAttr;
	}

	public void setIdAttr(String idAttr) {
		this.idAttr = idAttr;
	}
	
	@Column(name = "REMARK", length = 100, nullable = true)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(targetEntity = MetaAttribute.class, fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name = "ENTITYID")
	@OrderBy(value = "SEQNO asc")
	public Set<MetaAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<MetaAttribute> attributes) {
		this.attributes = attributes;
	}
	@Column(name = "CHECKRULE", length = 32, nullable = true)
	public String getCheckRule() {
		return checkRule;
	}

	public void setCheckRule(String checkRule) {
		this.checkRule = checkRule;
	}

	@Column(name = "DATARULE", length = 32, nullable = true)
	public String getDataRule() {
		return dataRule;
	}

	public void setDataRule(String dataRule) {
		this.dataRule = dataRule;
	}
	@Column(name="MAXNUM",length=50,nullable=true)
	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
	
	@Column(name="TEMPLATE_ID" ,length=32,nullable=true)
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	@Column(name="DATA_TYPE",length=1,nullable=true)
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	@Column(name="IS_EXIST_FILE",length=1,nullable=true)
	public String getIsExistFile() {
		return isExistFile;
	}

	public void setIsExistFile(String isExistFile) {
		this.isExistFile = isExistFile;
	}

}
