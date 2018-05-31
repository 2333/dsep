package com.dsep.service.email;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.SUPPORTS)
public interface EmailSendService {

	/**
	 * 发送邮件
	 * @param toAddress
	 *            接收邮箱的地址
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @return true:发送成功;false:发送失败
	 * @throws MessagingException 
	 * @throws IOException 
	 */
	public boolean sendMail(String toAddress, String subject, String content)
			throws MessagingException, IOException;

	/**
	 * 发送邮件
	 * @param toAddress 
	 * 			  接收邮箱地址的集合
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @param attachmentPath 附件地址
	 * @param attachmentName 附件名称
	 * @return true:发送成功;false:发送失败
	 * @throws MessagingException 
	 * @throws IOException 
	 */
	public boolean sendMail(List<String> toAddress, String subject, String content,
			String attachmentPath, String attachmentName)
			throws MessagingException, IOException;

	/**
	 * 发送邮件
	 * @param toAddress 接收邮箱地址的集合
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @return
	 * @throws MessagingException 
	 * @throws AddressException 
	 * @throws IOException 
	 */
	public boolean sendMail(List<String> toAddress, String subject,
			String content) throws AddressException, MessagingException,
			IOException;

}
