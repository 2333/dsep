package com.meta.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "META_DOMAIN")
public class MetaDomain  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5535011225461766628L;
	private String id;
	private String name;
	private String prefix;
	private String postfix;
	private String occasion;
	private String state;//该轮次评审是否可用
	private String innerState;//本轮评审的内部状态，预参评、参评、公示等
	private String remark;
	
	//private Set<MetaEntity> entities;
	
	
	@Override
	public boolean equals(Object obj) {
		if(this==obj)
			return true;
		if(obj==null)
			return false;
		if(!(obj instanceof MetaDomain))
			return false;
		
		final MetaDomain domain= (MetaDomain) obj;
		return this.id == domain.id;
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
	
	@Column(name = "NAME", length = 50, nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "PREFIX", length = 10, nullable = false)
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	@Column(name = "POSTFIX", length = 10, nullable = false)
	public String getPostfix() {
		return postfix;
	}
	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}
	
	@Column(name = "OCCASION",length = 5, nullable = false)
	public String getOccasion()	{		
		return occasion;
	}
	public void setOccasion(String occasion){
		this.occasion = occasion;
	}
	
	@Column(name = "STATE",length = 1, nullable = false)
	public String getState()	{		
		return state;
	}
	public void setState(String state){
		this.state = state;
	}
	
	@Column(name = "REMARK", length = 100)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name= "INNER_STATE",length = 2, nullable = false)
	public String getInnerState() {
		return innerState;
	}

	public void setInnerState(String innerState) {
		this.innerState = innerState;
	}
	
	/*@Cascade(value={CascadeType.SAVE_UPDATE})
	@OneToMany(targetEntity=MetaEntity.class,fetch = FetchType.EAGER)
	@JoinColumn(name="DOMAINID")
	public Set<MetaEntity> getEntities() {
		return entities;
	}
	public void setEntities(Set<MetaEntity> entities) {
		this.entities = entities;
	}*/	
}
