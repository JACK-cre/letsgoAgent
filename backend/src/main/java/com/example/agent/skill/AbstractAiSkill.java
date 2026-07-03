package com.example.agent.skill;

import com.example.agent.ai.AiGateway;
import com.example.agent.entity.UserMemory;
import com.example.agent.memory.AgentContext;

import java.util.stream.Collectors;

public abstract class AbstractAiSkill implements AgentSkill {

    protected final AiGateway aiGateway;

    protected AbstractAiSkill(AiGateway aiGateway) {
        this.aiGateway = aiGateway;
    }

    protected SkillResult callAi(AgentContext context, String taskInstruction) {
        String systemPrompt = """
                你是一个多用户个性化助手 Agent。
                你需要尊重用户隐私，只根据已提供的上下文回答。
                回答要符合用户偏好，必要时给出可执行建议。
                """;
        String memories = context.getLongTermMemories().stream()
                .map(UserMemory::getContent)
                .collect(Collectors.joining("\n- ", "- ", ""));
        String userPrompt = """
                用户消息:
                %s

                用户偏好:
                %s

                长期记忆:
                %s

                近期对话:
                %s

                当前 Skill 任务:
                %s
                """.formatted(
                context.getUserMessage(),
                context.getPreferences(),
                memories.isBlank() ? "暂无" : memories,
                context.getRecentMessages(),
                taskInstruction
        );
        return SkillResult.builder()
                .skillCode(code())
                .answer(aiGateway.complete(context, systemPrompt, userPrompt))
                .build();
    }
}

