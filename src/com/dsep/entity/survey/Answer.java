package com.dsep.entity.survey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DSEP_Q_ANSWER_2013")
public class Answer {
	
	//GUID 主键
	private String guid;

	//用户id
	private String userid;
	//问卷id
	private String qid;
	//题目id
	private String pid;
	//选项id
	private String itemid;
	//选择的选项或填入的结果
	private String result;
	
	@Id
	@Column(name="ID",length=32)
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	@Column(name="USER_ID",length=32)
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	@Column(name="QID",length=32)
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	
	@Column(name="PID",length=32)
	public String getPid() {
		return pid;
	}
	
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Column(name="ITEM_ID",length=32)
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	
	@Column(name="RESULT",length=200)
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
}
