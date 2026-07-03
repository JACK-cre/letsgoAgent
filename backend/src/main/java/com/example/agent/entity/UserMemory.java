package com.example.agent.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserMemory {
    private Long id;
    private Long userId;
    private String memoryType;
    private String content;
    private String tags;
    private Integer importance;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

