package com.dsep.entity.project;

import com.dsep.entity.Attachment;

public class JudgeResult {

	private String id;
	private String score;
	private String opinion;
	private String isAccept;
	private Attachment attachment;
	private ApplyItem applyItem;
	
	public String getIsAccept() {
		return isAccept;
	}
	public void setIsAccept(String isAccept) {
		this.isAccept = isAccept;
	}
	public ApplyItem getApplyItem() {
		return applyItem;
	}
	public void setApplyItem(ApplyItem applyItem) {
		this.applyItem = applyItem;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public Attachment getAttachment() {
		return attachment;
	}
	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
	
}
