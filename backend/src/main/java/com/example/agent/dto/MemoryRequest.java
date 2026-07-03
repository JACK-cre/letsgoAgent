package com.example.agent.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemoryRequest {
    @NotBlank
    private String memoryType;

    @NotBlank
    private String content;

    private String tags;
    private Integer importance = 1;
}

