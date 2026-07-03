package com.example.agent.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatRequest {
    @NotNull
    private Long userId;

    @NotBlank
    private String sessionId;

    @NotBlank
    private String message;
}

