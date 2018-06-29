package com.meta.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.omg.CORBA.PRIVATE_MEMBER;

@Entity
@Table(name = "META_ATTR_STYLE")
public class MetaAttrStyle  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2429844970662154283L;
	private String id;
	private String attrId;
	private String entityStyleId;
	private int dispLength;
	private String editable;
	private String sortable;
	private String isHidden;
	private String controlType;
	private int colNo;
	private int rowNo;
	private int colNums;
	private int rowNums;
	private String css;
	private String align;
	
	//private MetaEntityStyle entityStyle;
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
	
	@Column(name = "ATTRID", length = 20, nullable = false)
	public String getAttrId() {
		return attrId;
	}
	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}
	
	@Column(name = "ENTITYSTYLEID", length = 20, nullable = true)
	public String getEntityStyleId() {
		return entityStyleId;
	}
	public void setEntityStyleId(String entityStyleId) {
		this.entityStyleId = entityStyleId;
	}
	
	@Column(name = "DISPLENGTH", nullable = false)
	public int getDispLength() {
		return dispLength;
	}
	public void setDispLength(int dispLength) {
		this.dispLength = dispLength;
	}
	
	@Column(name = "EDITABLE", length = 1, nullable = false)
	public String getEditable() {
		return editable;
	}
	public void setEditable(String editable) {
		this.editable = editable;
	}
	@Column(name = "ISHIDDEN", length = 1, nullable = false)
	public String getIsHidden() {
		return isHidden;
	}
	@Column(name="SORTABLE",columnDefinition="varchar(1) default '0'",
			length=1, nullable = false)
	public String getSortable() {
		return sortable;
	}
	public void setSortable(String sortable) {
		this.sortable = sortable;
	}
	public void setIsHidden(String isHidden) {
		this.isHidden = isHidden;
	}
	
	@Column(name = "CONTROLTYPE", length = 2, nullable = false)
	public String getControlType() {
		return controlType;
	}
	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
	
	@Column(name = "COLNO", nullable = false)
	public int getColNo() {
		return colNo;
	}
	public void setColNo(int colNo) {
		this.colNo = colNo;
	}
	
	@Column(name = "ROWNO", nullable = false)
	public int getRowNo() {
		return rowNo;
	}
	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}
	
	@Column(name = "COLNUMS", nullable = false)
	public int getColNums() {
		return colNums;
	}
	public void setColNums(int colNums) {
		this.colNums = colNums;
	}
	
	@Column(name = "ROWNUMS", nullable = false)
	public int getRowNums() {
		return rowNums;
	}
	public void setRowNums(int rowNum) {
		this.rowNums = rowNum;
	}
	@Column(name = "CSS", length = 50, nullable = true)
	public String getCss() {
		return css;
	}
	public void setCss(String css) {
		this.css = css;
	}
	@Column(name = "ALIGN", length = 10, nullable = false)
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	
	/*@ManyToOne(targetEntity = MetaEntityStyle.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "ENTITYSTYLEID")
	public MetaEntityStyle getEntityStyle() {
		return entityStyle;
	}
	public void setEntityStyle(MetaEntityStyle entityStyle) {
		this.entityStyle = entityStyle;
	}*/
}
