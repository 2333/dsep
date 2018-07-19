package com.meta.entity;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.dsep.util.DateProcess;

@Entity
@Table(name = "META_ATTRIBUTE")
public class MetaAttribute  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8922925480823805438L;
	private String id;
	private String entityId;
	private String name;
	private String chsName;
	private String dataType;
	private int dataLength;
	private String isNull;
	private String defaultValue;
	private int seqNo;
	private int indexNo;
	private String dicId;
	private String dataRule;
	private String checkRule;
	private String remark;
	private String isSimCheck;
	
	//private MetaEntity entity;
	
	public void setIsSimCheck(String isSimCheck) {
		this.isSimCheck = isSimCheck;
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
	
	@Column(name = "ENTITYID", length = 20, nullable = false)
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
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
	
	@Column(name = "DATATYPE", length = 20, nullable = false)
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	@Column(name = "DATALENGTH", nullable = false)
	public int getDataLength() {
		return dataLength;
	}
	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}
	
	@Column(name = "ISNULL", length = 1, nullable = false)
	public String getIsNull() {
		return isNull;
	}
	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
	
	@Column(name = "DEFAULTVALUE", length = 20, nullable = true)
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@Column(name = "SEQNO", nullable = false)
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	
	@Column(name = "INDEXNO", nullable = false)
	public int getIndexNo() {
		return indexNo;
	}
	public void setIndexNo(int indexNo) {
		this.indexNo = indexNo;
	}
	
	@Column(name = "DICID", length = 20, nullable = true)
	public String getDicId() {
		return dicId;
	}
	public void setDicId(String dicId) {
		this.dicId = dicId;
	}
	
    @Column(name = "DATARULE", length = 32, nullable = true)
	public String getDataRule() {
		return dataRule;
	}
	public void setDataRule(String dataRule) {
		this.dataRule = dataRule;
	}
	
	@Column(name = "CHECKRULE", length = 32, nullable = true)
	public String getCheckRule() {
		return checkRule;
	}
	public void setCheckRule(String checkRule) {
		this.checkRule = checkRule;
	}
	
	@Column(name = "REMARK", length = 100, nullable = true)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name="ISSIMCHECK",length=1)
	public String getIsSimCheck() {
		return isSimCheck;
	}
	
	
	public void setIsCheck(String isSimCheck) {
		this.isSimCheck = isSimCheck;
	}
	
	/*@ManyToOne(targetEntity = MetaEntity.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "ENTITYID")
	public MetaEntity getEntity() {
		return entity;
	}
	public void setEntity(MetaEntity entity) {
		this.entity = entity;
	}*/
	public void addDefaultValue(Map<String, String> rowData){
		MetaDataType type = MetaDataType.getDataType(this.dataType);
		switch(type){
		case INT:
		case DOUBLE:
			rowData.put(name, "0");
			break;
		case DATE:
			rowData.put(name, DateProcess.getShowingDate(new Date()));
			break;
		case DATETIME:
			rowData.put(name, DateProcess.getShowingTime(new Date()));
			break;
		default:
			rowData.put(name, "---");
			break;
		}
	}
	
}
