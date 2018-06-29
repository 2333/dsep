package com.dsep.domain;

import com.dsep.entity.Attachment;

public class AttachmentHelper {

	private Attachment attachment;
	private String path;
	private String storageName;
	
	public Attachment getAttachment() {
		return attachment;
	}
	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getStorageName() {
		return storageName;
	}
	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}
}
