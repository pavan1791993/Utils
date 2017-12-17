package com.demo.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailSender {

	private static final Logger logger = LoggerFactory.getLogger(MailSender.class);

	public static Session getSession(final String username, final String password) {
		final Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		return Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}

	public static void sendHtmlMail(final Session session, final String from, final String to, final String subject,
			final String body) {
		try {
			logger.trace(">>> sendHtmlMail");
			final Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setContent(body, "text/html");
			Transport.send(message);
			logger.trace("<<< sendHtmlMail");
		} catch (final Exception e) {
			logger.error(">>> sendTextMail : {}", e);
		}
	}

	public static void sendTextMail(final Session session, final String from, final String to, final String subject,
			final String body) {
		try {
			logger.trace(">>> sendTextMail");
			final Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
			logger.trace("<<< sendTextMail");
		} catch (final Exception e) {
			logger.error(">>> sendTextMail : {}", e);
		}
	}

	private MailSender() {

	}

}
