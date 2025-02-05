package com.zhaojunan.personalwebsite.backend.controller;

import com.zhaojunan.personalwebsite.backend.model.Message;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {

    @PostMapping
    public String receiveMessage(@RequestBody Message message) {
        System.out.println("Received message from: " + message.getSenderName() + " (" + message.getEmail() + ")");
        System.out.println("Content: " + message.getContent());
        return "Message received successfully!";
    }

}
