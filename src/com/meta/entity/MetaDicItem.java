package com.meta.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "META_DIC_ITEM")
@IdClass(value=MetaDicItemUionPK.class)
public class MetaDicItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5659963835665290551L;
	private String id;
	private String dicId;
	private Integer seqNo;//用户排序
	private String pId;
	private String rules;
	
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Id
	public String getDicId() {
		return dicId;
	}
	public void setDicId(String dicId) {
		this.dicId = dicId;
	}
	
	@Column(name = "SEQ_NO", length = 2)
	public Integer getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
	@Column(name="P_ID",length = 50)
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	@Column(name="RULES",length = 300)
	public String getRules() {
		return rules;
	}
	public void setRules(String rules) {
		this.rules = rules;
	}
	
}
