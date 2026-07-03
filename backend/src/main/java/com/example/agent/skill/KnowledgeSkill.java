package com.example.agent.skill;

import com.example.agent.ai.AiGateway;
import com.example.agent.memory.AgentContext;
import org.springframework.stereotype.Component;

@Component
public class KnowledgeSkill extends AbstractAiSkill {

    public KnowledgeSkill(AiGateway aiGateway) {
        super(aiGateway);
    }

    @Override
    public String code() {
        return "knowledge";
    }

    @Override
    public String name() {
        return "知识问答 Skill";
    }

    @Override
    public boolean supports(AgentContext context) {
        String message = context.getUserMessage();
        return message.contains("什么是")
                || message.contains("解释")
                || message.contains("为什么")
                || message.contains("区别");
    }

    @Override
    public SkillResult execute(AgentContext context) {
        return callAi(context, "回答用户的问题。若缺少资料，要明确说明，并给出可验证或可继续补充的方向。");
    }
}

