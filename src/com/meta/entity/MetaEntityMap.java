package com.meta.entity;

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

@Entity
@Table(name = "META_ENTITY_MAP")
public class MetaEntityMap {
	
	private String id;//主键
	private MetaEntity originEntity;//源实体
	private MetaEntity targetEntity;//目标实体
	private String catId;//门类id
	private Set<MetaAttributeMap> attributeMaps = new TreeSet<MetaAttributeMap>();//属性集合
	
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
	
	@ManyToOne(targetEntity = MetaEntity.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "ORI_ENTITY_ID")
	public MetaEntity getOriginEntity() {
		return originEntity;
	}
	public void setOriginEntity(MetaEntity originEntity) {
		this.originEntity = originEntity;
	}
	@ManyToOne(targetEntity = MetaEntity.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "TAR_ENTITY_ID")
	public MetaEntity getTargetEntity() {
		return targetEntity;
	}
	public void setTargetEntity(MetaEntity targetEntity) {
		this.targetEntity = targetEntity;
	}
	@Column(name = "CAT_ID", length = 20, nullable = false)
	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
	@JoinColumn(name = "ENTITY_MAP_ID")
	public Set<MetaAttributeMap> getAttributeMaps() {
		return attributeMaps;
	}
	public void setAttributeMaps(Set<MetaAttributeMap> attributeMaps) {
		this.attributeMaps = attributeMaps;
	}

}
