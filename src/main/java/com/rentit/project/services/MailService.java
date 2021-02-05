package com.rentit.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.rentit.project.config.EmailConfig;
import com.rentit.project.models.UserEntity;

@Service
public class MailService {

	@Autowired
	private EmailConfig email;

	public void sendMail(String text, UserEntity user, String subject) {

		// create mailsender
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(email.getHost());
		mailSender.setPort(email.getPort());
		mailSender.setUsername(email.getUsername());
		mailSender.setPassword(email.getPassword());

		// Create emailinstance
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("noreply@rentit24.tech");
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject(subject);
		mailMessage.setText(text);

		// Send 
		mailSender.send(mailMessage);

	}

}
