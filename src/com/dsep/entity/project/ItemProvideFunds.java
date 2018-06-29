package com.dsep.entity.project;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(value = { "passItem" })
public class ItemProvideFunds {

	private String id;
	private Date provideTime;
	private Double provideAmount;
	private Double balance;
	private PassItem passItem;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getProvideTime() {
		return provideTime;
	}
	public void setProvideTime(Date provideTime) {
		this.provideTime = provideTime;
	}
	public Double getProvideAmount() {
		return provideAmount;
	}
	public void setProvideAmount(Double provideAmount) {
		this.provideAmount = provideAmount;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public PassItem getPassItem() {
		return passItem;
	}
	public void setPassItem(PassItem passItem) {
		this.passItem = passItem;
	}
	
}
