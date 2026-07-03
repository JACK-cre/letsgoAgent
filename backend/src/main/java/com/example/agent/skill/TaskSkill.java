package com.example.agent.skill;

import com.example.agent.ai.AiGateway;
import com.example.agent.memory.AgentContext;
import org.springframework.stereotype.Component;

@Component
public class TaskSkill extends AbstractAiSkill {

    public TaskSkill(AiGateway aiGateway) {
        super(aiGateway);
    }

    @Override
    public String code() {
        return "task";
    }

    @Override
    public String name() {
        return "任务规划 Skill";
    }

    @Override
    public boolean supports(AgentContext context) {
        String message = context.getUserMessage();
        return message.contains("计划")
                || message.contains("拆解")
                || message.contains("步骤")
                || message.contains("任务")
                || message.contains("怎么做");
    }

    @Override
    public SkillResult execute(AgentContext context) {
        return callAi(context, "把用户目标拆解成清晰步骤，指出优先级和下一步行动。");
    }
}

