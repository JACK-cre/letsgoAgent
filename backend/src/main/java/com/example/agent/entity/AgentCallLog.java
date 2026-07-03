package com.example.agent.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgentCallLog {
    private Long id;
    private Long userId;
    private String sessionId;
    private String userMessage;
    private String selectedSkill;
    private String modelResponse;
    private Long latencyMs;
    private LocalDateTime createdAt;
}

