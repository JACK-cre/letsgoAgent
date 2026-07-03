package com.example.agent.skill;

import com.example.agent.ai.AiGateway;
import com.example.agent.memory.AgentContext;
import org.springframework.stereotype.Component;

@Component
public class ProfileSkill extends AbstractAiSkill {

    public ProfileSkill(AiGateway aiGateway) {
        super(aiGateway);
    }

    @Override
    public String code() {
        return "profile";
    }

    @Override
    public String name() {
        return "用户画像 Skill";
    }

    @Override
    public boolean supports(AgentContext context) {
        String message = context.getUserMessage();
        return message.contains("我是谁")
                || message.contains("我的偏好")
                || message.contains("用户画像")
                || message.contains("了解我");
    }

    @Override
    public SkillResult execute(AgentContext context) {
        return callAi(context, "总结你对当前用户画像、偏好和上下文的理解，并说明你会如何个性化帮助用户。");
    }
}

