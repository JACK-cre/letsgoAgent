package com.example.agent.skill;

import com.example.agent.ai.AiGateway;
import com.example.agent.memory.AgentContext;
import org.springframework.stereotype.Component;

@Component
public class FallbackSkill extends AbstractAiSkill {

    public FallbackSkill(AiGateway aiGateway) {
        super(aiGateway);
    }

    @Override
    public String code() {
        return "fallback";
    }

    @Override
    public String name() {
        return "兜底 Skill";
    }

    @Override
    public boolean supports(AgentContext context) {
        return true;
    }

    @Override
    public SkillResult execute(AgentContext context) {
        return callAi(context, "正常回答用户。如果意图不明确，先给出最可能的帮助方向，并提出一个必要的澄清问题。");
    }
}

