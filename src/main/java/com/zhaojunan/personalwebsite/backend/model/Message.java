package com.zhaojunan.personalwebsite.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // Generates Getters, Setters, toString(), equals(), hashCode()
@NoArgsConstructor  // Generates a No-Args Constructor
@AllArgsConstructor
public class Message {

    private String senderName;
    private String email;
    private String content;

}
