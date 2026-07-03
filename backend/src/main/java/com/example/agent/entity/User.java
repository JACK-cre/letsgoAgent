package com.example.agent.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String nickname;
    private String language;
    private String timezone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

