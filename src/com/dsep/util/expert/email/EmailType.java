package com.dsep.util.expert.email;


/**
 * 目前发送邮件的类型有
 * 1、给一名专家的邮箱发送邀请邮件
 * 2、给一名专家的多个邮箱发送邀请邮件
 * 3、给一名专家的邮箱发送催促邮件
 * 4、给一名专家的多个邮箱发送催促邮件
 * 5、
 */
public enum EmailType {
	InvitationForOneExpertOneAddr(0), 
	InvitationForOneExpertMultiAddrs(1),
	UrgencyForOneExpertOneAddr(2),
	UrgencyForOneExpertMultiAddrs(3);
	
	private int emailType;

	EmailType(int type) {
		this.emailType = type;
	}

	public int getIndex() {
		return emailType;
	}
	
	 @Override
     public String toString() {
         return String.valueOf(this.emailType);
     } 
}
