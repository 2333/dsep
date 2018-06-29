package com.dsep.service.email.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.dom4j.dom.*;

import com.dsep.dao.email.EmailSendDao;
import com.dsep.service.email.EmailSendService;
import com.dsep.util.EmailMessage;

public class EmailSendServiceImpl implements EmailSendService {

	private EmailSendDao emailSendDao;

	private EmailMessage emailMessage;

	private int MAX_NUMBER;

	public void setMAX_NUMBER(int mAX_NUMBER) {
		MAX_NUMBER = mAX_NUMBER;
	}

	public EmailMessage getEmailMessage() {
		return emailMessage;
	}

	public void setEmailMessage(EmailMessage emailMessage) {
		this.emailMessage = emailMessage;
	}

	public EmailSendDao getEmailSendDao() {
		return emailSendDao;
	}

	public void setEmailSendDao(EmailSendDao emailSendDao) {
		this.emailSendDao = emailSendDao;
	}

	public EmailSendServiceImpl() {

	}

	/**
	 * 生成接收邮箱的二维数组,邮件服务器可能有最大发送限制
	 * @param theAddress 接收邮箱的List
	 * @return
	 * @throws AddressException
	 */
	private Address[][] getToAddress(List<String> theAddress)
			throws AddressException {
		int dimensionSize = theAddress.size() / MAX_NUMBER;
		int lastDimensionSize = theAddress.size() % MAX_NUMBER;
		if (lastDimensionSize != 0)
			dimensionSize++;
		Address[][] to = new Address[dimensionSize][];
		for (int i = 0; i < dimensionSize; i++) {
			if (i != dimensionSize - 1)
				to[i] = new Address[MAX_NUMBER];
			else if (lastDimensionSize != 0)
				to[i] = new Address[lastDimensionSize];
			else
				to[i] = new Address[MAX_NUMBER];
		}
		for (int i = 0; i < dimensionSize; i++)
			for (int j = 0; j < to[i].length; j++)
				to[i][j] = new InternetAddress(theAddress.get(i * MAX_NUMBER
						+ j));
		return to;
	}

	@Override
	public boolean sendMail(String toAddress, String subject, String content)
			throws MessagingException, IOException {
		// TODO Auto-generated method stub
		Address[] to = new Address[1];
		to[0] = new InternetAddress(toAddress);
		emailMessage.sendEmail(subject, content, to);
		return true;
	}

	@Override
	public boolean sendMail(List<String> toAddress, String subject,
			String content) throws AddressException, MessagingException,
			IOException {
		// TODO Auto-generated method stub
		Address[][] theAddress = getToAddress(toAddress);
		for (int i = 0; i < theAddress.length; i++)
			emailMessage.sendEmail(subject, content, theAddress[i]);
		return true;
	}

	@Override
	public boolean sendMail(List<String> toAddress, String subject,
			String content, String attachmentPath, String attachmentName)
			throws MessagingException, IOException {
		Address[][] theAddress = getToAddress(toAddress);
		for (int i = 0; i < theAddress.length; i++)
			emailMessage.sendEmail(subject, content, attachmentPath,
					attachmentName, theAddress[i]);
		return true;
	}
}
