package com.dsep.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



@Entity
@Table(name="DSEP_BASE_NEWS")
public class News implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private String type;
	private Date date;
	private String publisher;
	private String importantLevel;
	private String content;
	private String unitId;
	private String discId;
	
	private Attachment attachment;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "guid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name="ID" ,length=32)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="TITLE",length=200,nullable=false)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name="NEWS_TYPE",length=4,nullable=false)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name="NEWS_DATE")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Column(name="PUBLISHER",length=20)
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	@Column(name="IMPORTANT_LEVEL",length=20,nullable=false)
	public String getImportantLevel() {
		return importantLevel;
	}
	public void setImportantLevel(String importantLevel) {
		this.importantLevel = importantLevel;
	}
	
	@Column(name="CONTENT",length=3000)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name="UNIT_ID",length=10)
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	@Column(name="DISC_ID",length=10)
	public String getDiscId() {
		return discId;
	}
	public void setDiscId(String discId) {
		this.discId = discId;
	}
	
	@OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="ATTACHMENT_ID")
	public Attachment getAttachment() {
		return attachment;
	}
	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
