package com.meta.entity;

import java.sql.Time;
import java.util.Date;

import jxl.write.DateTime;

/**
 * 测试实体，测试从外部数据库中取数据
 * @author Charles
 *
 */
public class PgshTest {
	private String id;
	private String name;
	private String password;
	private String ok;
	private Date theDate;
	private double doubleValue;
	private int intValue;
	private char charValue;
	private String booleanValue;
	private Time theTime;
	
	
	public int getIntValue() {
		return intValue;
	}
	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}
	
	public Date getTheDate() {
		return theDate;
	}
	public void setTheDate(Date theDate) {
		this.theDate = theDate;
	}
	public double getDoubleValue() {
		return doubleValue;
	}
	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}
	public void setTheTime(Time theTime) {
		this.theTime = theTime;
	}
	public double getDoubleNumber() {
		return doubleValue;
	}
	public void setDoubleNumber(double doubleNumber) {
		this.doubleValue = doubleNumber;
	}
	public String getBooleanValue() {
		return booleanValue;
	}
	public void setBooleanValue(String booleanValue) {
		this.booleanValue = booleanValue;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOk() {
		return ok;
	}
	public void setOk(String ok) {
		this.ok = ok;
	}
	public Date getTheTime() {
		return theDate;
	}
	public void setTheTime(Date theTime) {
		this.theDate = theTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
