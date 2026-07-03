package com.example.agent.skill;

import com.example.agent.memory.AgentContext;

public interface AgentSkill {
    String code();

    String name();

    boolean supports(AgentContext context);

    SkillResult execute(AgentContext context);
}

