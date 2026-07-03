package com.example.agent.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatResponse {
    private Long userId;
    private String sessionId;
    private String selectedSkill;
    private String answer;
}

