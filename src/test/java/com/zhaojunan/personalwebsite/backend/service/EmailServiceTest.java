package com.zhaojunan.personalwebsite.backend.service;

import com.zhaojunan.personalwebsite.backend.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    private Message testMessage;

    @BeforeEach
    void setUp() {
        testMessage = new Message();
        testMessage.setSenderName("John Doe");
        testMessage.setEmail("john.doe@example.com");
        testMessage.setContent("This is a test message.");
    }

    @Test
    public void testSendEmail_ShouldCallMailSender() {
        emailService.sendEmail(testMessage);
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

}
