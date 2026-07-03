package com.example.agent.service;

import com.example.agent.memory.AgentContext;
import com.example.agent.skill.AgentSkill;
import com.example.agent.skill.FallbackSkill;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class SkillRouterService {

    private final List<AgentSkill> skills;
    private final FallbackSkill fallbackSkill;

    public SkillRouterService(List<AgentSkill> skills, FallbackSkill fallbackSkill) {
        this.skills = skills.stream()
                .sorted(Comparator.comparing(skill -> skill instanceof FallbackSkill))
                .toList();
        this.fallbackSkill = fallbackSkill;
    }

    public AgentSkill route(AgentContext context) {
        return skills.stream()
                .filter(skill -> !(skill instanceof FallbackSkill))
                .filter(skill -> skill.supports(context))
                .findFirst()
                .orElse(fallbackSkill);
    }
}

