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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "META_ENTITY_CATEGORY")
public class MetaEntityCategory  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2727146239946888328L;
	private String id;
	private String parentId;
	private String name;
	private String occassion;

	//private MetaEntityCategory parent;
	private Set<MetaEntityCategory> children;

	private Set<MetaEntity> entities = new TreeSet<MetaEntity>();

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

	@Column(name = "PARENTID", length = 20, nullable = true) 
	public String getParentId() {
		return parentId; 
	} 
	public void setParentId(String parentId){
		this.parentId = parentId; 
	}
	
	@Column(name = "NAME", length = 50, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "OCCASSION", length = 1, nullable = false)
	public String getOccassion() {
		return occassion;
	}

	public void setOccassion(String occassion) {
		this.occassion = occassion;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "PARENTID")
	@OrderBy(value = "ID asc")
	public Set<MetaEntityCategory> getChildren() {
		return children;
	}

	public void setChildren(Set<MetaEntityCategory> children) {
		this.children = children;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "CATEGORYID")
	@OrderBy(value = "ID asc")
	public Set<MetaEntity> getEntities() {
		return entities;
	}
	
	public void setEntities(Set<MetaEntity> entities) {
		this.entities = entities;
	}
}
