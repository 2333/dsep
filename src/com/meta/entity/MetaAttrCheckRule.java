package com.meta.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "META_ATTR_CHECK_RULE")
public class MetaAttrCheckRule  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5848294189783397225L;
	private String id;
	private String ruleId;
	private String value1;
	private String value2;
	private String value3;
	private String value4;
	private String value5;

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

	@Column(name = "RULEID", length = 20, nullable = false)
	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	@Column(name = "VALUE1", length = 100)
	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	@Column(name = "VALUE2", length = 100)
	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	@Column(name = "VALUE3", length = 100)
	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	@Column(name = "VALUE4", length = 100)
	public String getValue4() {
		return value4;
	}

	public void setValue4(String value4) {
		this.value4 = value4;
	}

	@Column(name = "VALUE5", length = 100)
	public String getValue5() {
		return value5;
	}

	public void setValue5(String value5) {
		this.value5 = value5;
	}

}
