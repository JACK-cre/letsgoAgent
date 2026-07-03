package com.example.agent.skill;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkillResult {
    private String skillCode;
    private String answer;
}

