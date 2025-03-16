package com.zhaojunan.personalwebsite.backend.controller;

import com.zhaojunan.personalwebsite.backend.model.Message;
import com.zhaojunan.personalwebsite.backend.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
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
    public void testReceiveMessage_ShouldCallEmailServiceAndReturnSuccess() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testMessage)))
                .andExpect(status().isOk())
                .andExpect(content().string("The message has been sent as an email successfully!"));

        // Verify that emailService.sendEmail was called exactly once
        verify(emailService, times(1)).sendEmail(testMessage);
    }

}
