package com.dsep.entity.survey;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.dsep.entity.Attachment;

@Entity
@Table(name="DSEP_Q_STORAGE")
public class Questionnaire {
	
	private String id ;
	private String name ;
	private String path;
	// 问卷创建日期
	private Date createDate;
	// 问卷最后修改日期
	private Date lastModifyDate;
	// 问卷发布日期
	private Date startDate;
	// 问卷截止日期
	private Date endDate;
	private String type;
	private int status;
	
	
	@Id
	@Column(name="ID",length=32)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="NAME",length=200,nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="PATH",length=200,nullable=false)
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	@Column(name="START_DATE")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Column(name="END_DATE")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Column(name="CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Column(name="LAST_MODIFY_DATE")
	public Date getLastModifyDate() {
		return lastModifyDate;
	}
	
	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	
	@Column(name="TYPE",length=64,nullable=false)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	//状态码 ：0：未发布 1：进行中 2：已结束
	@Column(name="STATUS",nullable=false)
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
