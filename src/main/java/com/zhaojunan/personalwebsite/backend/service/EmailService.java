package com.zhaojunan.personalwebsite.backend.service;

import com.zhaojunan.personalwebsite.backend.model.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Properties;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${application.email.from}")
    private String fromEmail;

    @Value("${application.email.to}")
    private String toEmail;

    public EmailService() {
        this.mailSender = configureMailSender(); // Fetch SMTP creds from AWS Secrets Manager
    }

    private JavaMailSender configureMailSender() {
        Map<String, String> secrets = AwsSecretsManagerService.getSecret();
        String smtpUsername = secrets.get("SMTP_USERNAME");
        String smtpPassword = secrets.get("SMTP_PASSWORD");

        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost("email-smtp.us-east-1.amazonaws.com");
        mailSenderImpl.setPort(587);
        mailSenderImpl.setUsername(smtpUsername);
        mailSenderImpl.setPassword(smtpPassword);

        Properties props = mailSenderImpl.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSenderImpl;
    }

    public void sendEmail(Message message) {
        String subject = String.format("%s from %s left a message for you!",
                message.getSenderName(), message.getEmail());
        sendEmail(subject, message.getContent());
    }

    public void sendEmail(String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

}
