package com.example.agent.skill;

import com.example.agent.ai.AiGateway;
import com.example.agent.memory.AgentContext;
import org.springframework.stereotype.Component;

@Component
public class RecommendationSkill extends AbstractAiSkill {

    public RecommendationSkill(AiGateway aiGateway) {
        super(aiGateway);
    }

    @Override
    public String code() {
        return "recommendation";
    }

    @Override
    public String name() {
        return "个性化推荐 Skill";
    }

    @Override
    public boolean supports(AgentContext context) {
        String message = context.getUserMessage();
        return message.contains("推荐")
                || message.contains("建议")
                || message.contains("适合我")
                || message.contains("选哪个");
    }

    @Override
    public SkillResult execute(AgentContext context) {
        return callAi(context, "根据用户偏好和记忆给出个性化推荐。推荐要有理由，并说明适合用户的点。");
    }
}

