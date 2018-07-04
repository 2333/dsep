package com.dsep.util;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class EmailMessage {

	private String PROTOCOL;
	private String IS_ENABLED_DEBUG_MOD;
	/**
	 * 邮箱服务器地址
	 */
	private String mailServerHost;

	public void setPROTOCOL(String pROTOCOL) {
		PROTOCOL = pROTOCOL;
	}

	public void setIS_ENABLED_DEBUG_MOD(String iS_ENABLED_DEBUG_MOD) {
		IS_ENABLED_DEBUG_MOD = iS_ENABLED_DEBUG_MOD;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public void setIsHTML(boolean isHTML) {
		this.isHTML = isHTML;
	}

	public void setIsSSL(boolean isSSL) {
		this.isSSL = isSSL;
	}

	public void setMAX_NUMBER(int mAX_NUMBER) {
		MAX_NUMBER = mAX_NUMBER;
	}

	/**
	 * 邮箱服务器端口号
	 */
	private String mailServerPort;
	/**
	 * 发送邮箱地址
	 */
	private String fromAddress;
	/**
	 * 发送邮箱密码
	 */
	private String userPass;
	/**
	 * 发送邮箱是否要求身份验证
	 */
	private boolean validate;
	/**
	 * 是否是HTML格式邮件
	 */
	private boolean isHTML;
	/**
	 * 邮件服务器是否需要安全连接
	 */
	private boolean isSSL;
	/**
	 * 邮件服务器一次最多发送的邮件数量
	 */
	private int MAX_NUMBER;

	public EmailMessage() {

	}

	/**
	 * 获取系统属性
	 * @return
	 */
	private Properties getMailProperty() {
		Properties p = new Properties();
		p.setProperty("mail.smtp.host", mailServerHost);
		p.setProperty("mail.smtp.port", mailServerPort);
		p.setProperty("mail.smtp.auth", validate ? "true" : "false");
		p.setProperty("mail.debug", IS_ENABLED_DEBUG_MOD);
		p.setProperty("mail.smtp.timeout", "25000");
		if (isSSL) {
			p.setProperty("mail.smtp.starttls.enable", "true");
			p.setProperty("mail.smtp.socketFactory.fallback", "false");
			p.setProperty("mail.smtp.socketFactory.port", mailServerPort);
		}
		return p;
	}

	/**
	 * 将邮件内容转变为html格式
	 * @param content 邮件内容
	 * @param message 邮件信息类的实体
	 * @throws MessagingException 
	 * @throws IOException 
	 */
	private void setHtmlContent(String content, Message message,
			String attachmentPath, String attachmentName)
			throws MessagingException, IOException {
		Multipart m = new MimeMultipart();
		BodyPart bp = new MimeBodyPart();
		bp.setContent(content, "text/html; charset=utf-8");

		m.addBodyPart(bp);
		
		BodyPart attachmentBp = null;
		
		if (null != attachmentPath) {
			attachmentBp = new MimeBodyPart();
			DataSource source = new FileDataSource(attachmentPath);
			attachmentBp.setDataHandler(new DataHandler(source));
			if (null != attachmentName)
				attachmentBp.setFileName(MimeUtility.encodeText(attachmentName));
			m.addBodyPart(attachmentBp);
		}
		message.setContent(m);
	}

	/**
	 * 获取邮件信息
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @return
	 * @throws MessagingException
	 * @throws IOException 
	 */
	private Message getMessage(String subject, String content,
			String attachmentPath, String attachmentName)
			throws MessagingException, IOException {
		/*p.setProperty("mail.transport.protocol", PROTOCOL);*/
		Authenticator auth = null;
		if (validate) {
			auth = new myAuthenticator(fromAddress, userPass);
		}
		Session session = Session.getDefaultInstance(getMailProperty(), auth);
		Message message = new MimeMessage(session);
		Address from = new InternetAddress(fromAddress);
		message.setFrom(from);
		message.setSubject(subject);
		message.setSentDate(new Date());
		if (isHTML) {
			setHtmlContent(content, message, attachmentPath, attachmentName);
		} else {
			message.setText(content);
		}
		message.saveChanges();
		return message;
	}

	/**
	 * 发送邮件
	 * @param subject 邮件主题 
	 * @param content 邮件内容
	 * @param address 邮件地址
	 * @param attachmentPath 附件地址
	 * @param attachmentName 附件名称
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void sendEmail(String subject, String content,
			String attachmentPath, String attachmentName, Address[] address)
			throws MessagingException, IOException {
		Transport.send(
				getMessage(subject, content, attachmentPath, attachmentName),
				address);
	}

	/**
	 * 发送邮件
	 * @param subject 邮件主题 
	 * @param content 邮件内容
	 * @param address 邮件地址
	 * @param attachmentPath 附件地址
	 * @param attachmentName 附件名称
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void sendEmail(String subject, String content, Address[] address)
			throws MessagingException, IOException {
		Transport.send(getMessage(subject, content, null, null), address);
	}
}

class myAuthenticator extends Authenticator {
	String userName;
	String userPass;

	public myAuthenticator() {
	}

	public myAuthenticator(String userName, String userPass) {
		this.userName = userName;
		this.userPass = userPass;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, userPass);
	}
}
