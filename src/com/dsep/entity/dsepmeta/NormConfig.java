package com.dsep.entity.dsepmeta;

import java.util.Date;

/**
 * 数据规范化配置表
 *
 */
public class NormConfig {

	private String id;
	private String entityId;
	private String tableChsName;
	private int normStatus;
	private Date normTime;
	private String userId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getTableChsName() {
		return tableChsName;
	}
	public void setTableChsName(String tableChsName) {
		this.tableChsName = tableChsName;
	}
	public int getNormStatus() {
		return normStatus;
	}
	public void setNormStatus(int normStatus) {
		this.normStatus = normStatus;
	}
	public Date getNormTime() {
		return normTime;
	}
	public void setNormTime(Date normTime) {
		this.normTime = normTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
