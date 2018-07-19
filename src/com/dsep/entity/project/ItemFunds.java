package com.dsep.entity.project;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(value = { "passItem" })
public class ItemFunds {
	private String id;
	private String invoiceNumber;
	private Date usingTime;
	private String checkPeople;
	private String usingAim;
	private Double consumption;
	private String operator;
	private PassItem passItem;
	private String detail;
	
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public Date getUsingTime() {
		return usingTime;
	}
	public void setUsingTime(Date usingTime) {
		this.usingTime = usingTime;
	}
	public String getCheckPeople() {
		return checkPeople;
	}
	public void setCheckPeople(String checkPeople) {
		this.checkPeople = checkPeople;
	}
	public String getUsingAim() {
		return usingAim;
	}
	public void setUsingAim(String usingAim) {
		this.usingAim = usingAim;
	}
	public Double getConsumption() {
		return consumption;
	}
	public void setConsumption(Double consumption) {
		this.consumption = consumption;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public PassItem getPassItem() {
		return passItem;
	}
	public void setPassItem(PassItem passItem) {
		this.passItem = passItem;
	}

}
