package com.zhaojunan.personalwebsite.backend.controller;

import com.zhaojunan.personalwebsite.backend.model.Message;
import com.zhaojunan.personalwebsite.backend.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private EmailService emailService;

    @PostMapping
    public String receiveMessage(@RequestBody Message message) {
        logger.info("Received a message {}", message);
        emailService.sendEmail(message);
        logger.info("Email service invoked successfully");
        return "The message has been sent as an email successfully!";
    }

}
