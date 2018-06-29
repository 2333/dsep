package com.dsep.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="DSEP_D_LOGIC_RESULT_2013")
public class LogicCheckResult {
	private String id;
	private String unitId;
	private String discId;
	private String entityId;
	private String dataId;
	private String seqNo;
	private String attrId;
	private String errorNo;
	private String error;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "assigned")
	@GeneratedValue(generator = "generator")
	@Column(name = "ID", length = 32, nullable = false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "UNIT_ID", length = 5, nullable = false)
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	@Column(name = "DISC_ID", length = 6, nullable = false)
	public String getDiscId() {
		return discId;
	}
	public void setDiscId(String discId) {
		this.discId = discId;
	}
	
	@Column(name = "ENTITY_ID", length = 50, nullable = false)
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	
	@Column(name = "DATA_ID", length = 32, nullable = false)
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	
	@Column(name = "SEQ_NO", length = 0, nullable = false)
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	
	@Column(name = "ATTR_ID", length = 50, nullable = false)
	public String getAttrId() {
		return attrId;
	}
	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}
	
	@Column(name = "ERROR_NO", length = 5, nullable = true)
	public String getErrorNo() {
		return errorNo;
	}
	public void setErrorNo(String errorNo) {
		this.errorNo = errorNo;
	}
	
	@Column(name = "ERROR", length = 256, nullable = true)
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
