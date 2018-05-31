package com.dsep.entity.expert;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(value = {"rule"})
public class ExpertSelectionRuleDetail {
	private String id;
	private String itemName;
	private String sequ;
	private Boolean isNumber;
	private String operator;
	private Boolean isNecessary;
	private Boolean conditionChecked;
	private String restrict1;
	private String restrict2;
	private String restrict3;
	private String restrict4;
	private String restrict5;
	private String restrict6;
	private String restrict7;
	private String restrict8;
	private String restrict9;
	private String restrict10;

	private ExpertSelectionRule rule;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	
	public String getSequ() {
		return sequ;
	}

	public void setSequ(String sequ) {
		this.sequ = sequ;
	}

	public Boolean getIsNumber() {
		return isNumber;
	}

	public void setIsNumber(Boolean isNumber) {
		this.isNumber = isNumber;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Boolean getIsNecessary() {
		return isNecessary;
	}

	public void setIsNecessary(Boolean isNecessary) {
		this.isNecessary = isNecessary;
	}

	public Boolean getConditionChecked() {
		return conditionChecked;
	}

	public void setConditionChecked(Boolean conditionChecked) {
		this.conditionChecked = conditionChecked;
	}

	public String getRestrict1() {
		return restrict1;
	}

	public void setRestrict1(String restrict1) {
		this.restrict1 = restrict1;
	}

	public String getRestrict2() {
		return restrict2;
	}

	public void setRestrict2(String restrict2) {
		this.restrict2 = restrict2;
	}

	public String getRestrict3() {
		return restrict3;
	}

	public void setRestrict3(String restrict3) {
		this.restrict3 = restrict3;
	}

	public String getRestrict4() {
		return restrict4;
	}

	public void setRestrict4(String restrict4) {
		this.restrict4 = restrict4;
	}

	public String getRestrict5() {
		return restrict5;
	}

	public void setRestrict5(String restrict5) {
		this.restrict5 = restrict5;
	}

	public String getRestrict6() {
		return restrict6;
	}

	public void setRestrict6(String restrict6) {
		this.restrict6 = restrict6;
	}

	public String getRestrict7() {
		return restrict7;
	}

	public void setRestrict7(String restrict7) {
		this.restrict7 = restrict7;
	}

	public String getRestrict8() {
		return restrict8;
	}

	public void setRestrict8(String restrict8) {
		this.restrict8 = restrict8;
	}

	public String getRestrict9() {
		return restrict9;
	}

	public void setRestrict9(String restrict9) {
		this.restrict9 = restrict9;
	}

	public String getRestrict10() {
		return restrict10;
	}

	public void setRestrict10(String restrict10) {
		this.restrict10 = restrict10;
	}

	public ExpertSelectionRule getRule() {
		return rule;
	}

	public void setRule(ExpertSelectionRule rule) {
		this.rule = rule;
	}
	
	

}
