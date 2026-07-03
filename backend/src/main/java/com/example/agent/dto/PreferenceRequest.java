package com.example.agent.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PreferenceRequest {
    @NotBlank
    private String key;

    @NotBlank
    private String value;
}

