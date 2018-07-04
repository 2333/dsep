package com.dsep.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DSEP_RBAC_RIGHT")
public class Right implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1690913410067502402L;
	private String id;
	private String name; 
	private String parentId;
	private String url;
	private String category;
	private int levelNo;
	private int seqNo;
	private String memo;
	
	@Id
	@Column(name="ID",length=5)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name="NAME",length=20)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="PARENT_ID",length=5)
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	@Column(name="URL",length=256)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Column(name="CATEGORY",length=1)
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	@Column(name="LEVEL_NO",nullable=false)
	public int getLevelNo() {
		return levelNo;
	}
	public void setLevelNo(int levelNo) {
		this.levelNo = levelNo;
	}
	@Column(name="SEQ_NO",nullable=false)
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	@Column(name="MEMO",length=100)
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
