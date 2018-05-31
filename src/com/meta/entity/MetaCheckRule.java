package com.meta.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "META_CHECK_RULE")
public class MetaCheckRule  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 558956665476742588L;
	private String id;
	private String name;
	private String category;
	private String remark;
	private String jsCheck;
	private String classCheck;
	private String param1;
	private String param2;
	private String param3;
	private String param4;
	private String param5;
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
	@Column(name = "CATEGORY", length = 2, nullable = false)
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	@Column(name = "REMARK", length = 200, nullable = true)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "JSCHECK", length = 50, nullable = true)
	public String getJsCheck() {
		return jsCheck;
	}
	public void setJsCheck(String jsCheck) {
		this.jsCheck = jsCheck;
	}
	@Column(name = "CLASSCHECK", length = 50, nullable = true)
	public String getClassCheck() {
		return classCheck;
	}
	public void setClassCheck(String classCheck) {
		this.classCheck = classCheck;
	}
	@Column(name = "PARAM1", length = 1, nullable = true)
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	@Column(name = "PARAM2", length = 1, nullable = true)
	public String getParam2() {
		return param2;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	@Column(name = "PARAM3", length = 1, nullable = true)
	public String getParam3() {
		return param3;
	}
	public void setParam3(String param3) {
		this.param3 = param3;
	}
	@Column(name = "PARAM4", length = 1, nullable = true)
	public String getParam4() {
		return param4;
	}
	public void setParam4(String param4) {
		this.param4 = param4;
	}
	@Column(name = "PARAM5", length = 1, nullable = true)
	public String getParam5() {
		return param5;
	}
	public void setParam5(String param5) {
		this.param5 = param5;
	}
	
}
