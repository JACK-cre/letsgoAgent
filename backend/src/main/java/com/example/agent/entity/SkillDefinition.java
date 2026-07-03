package com.example.agent.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SkillDefinition {
    private Long id;
    private String skillCode;
    private String skillName;
    private String description;
    private Boolean enabled;
    private String requiredContext;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

