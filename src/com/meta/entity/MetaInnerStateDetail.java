package com.meta.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="META_INNER_STATE_DETAIL")
@JsonAutoDetect
@JsonIgnoreProperties(value = {"metaDomain"})
public class MetaInnerStateDetail {
	
	private String id;
	private String inState;
	private String innerName;
	private Date startTime;
	private Date endTime;
	private MetaDomain metaDomain;
	private String domainName;
	private String memo;
	
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
	
	@Column(name="IN_STATE",length=2,nullable=false)
	public String getInnerState() {
		return inState;
	}
	public void setInnerState(String innerState) {
		this.inState = innerState;
	}
	@Column(name="INNER_NAME",length = 100,nullable=false)
	public String getInnerName() {
		return innerName;
	}
	public void setInnerName(String innerName) {
		this.innerName = innerName;
	}
	@Temporal(value=TemporalType.DATE) 
	@Column(name="START_TIME")
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@Temporal(value=TemporalType.DATE) 
	@Column(name="END_TIME")
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@ManyToOne(targetEntity = MetaDomain.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "DOMIAN_ID")
	public MetaDomain getMetaDomain() {
		return metaDomain;
	}
	public void setMetaDomain(MetaDomain metaDomain) {
		this.metaDomain = metaDomain;
	}
	@Column(name="MEMO",length=300)
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Column(name="DOMAIN_NAME",length=50)
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
}
