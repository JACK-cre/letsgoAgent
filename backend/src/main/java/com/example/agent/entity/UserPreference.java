package com.example.agent.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserPreference {
    private Long id;
    private Long userId;
    private String preferenceKey;
    private String preferenceValue;
    private String source;
    private BigDecimal confidence;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

