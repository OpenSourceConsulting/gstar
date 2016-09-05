package com.gemmystar.api.common.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component("gemmyMailSender")
public class MailSender {

	@Autowired
    private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String fromMailAddr;

	public MailSender() {
	}
	
	
	public void sendMail(String subject, String body, String toEmail) {
		mailSender.send(constructEmail(subject, body, toEmail));
	}
	
	
	private SimpleMailMessage constructEmail(String subject, String body, String toEmail) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(toEmail);
        email.setFrom(fromMailAddr);
        return email;
    }
	
}
