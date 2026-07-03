package com.example.agent.skill;

import com.example.agent.ai.AiGateway;
import com.example.agent.dto.MemoryRequest;
import com.example.agent.dto.PreferenceRequest;
import com.example.agent.memory.AgentContext;
import com.example.agent.service.MemoryService;
import org.springframework.stereotype.Component;

@Component
public class MemorySkill extends AbstractAiSkill {

    private final MemoryService memoryService;

    public MemorySkill(AiGateway aiGateway, MemoryService memoryService) {
        super(aiGateway);
        this.memoryService = memoryService;
    }

    @Override
    public String code() {
        return "memory";
    }

    @Override
    public String name() {
        return "记忆管理 Skill";
    }

    @Override
    public boolean supports(AgentContext context) {
        String message = context.getUserMessage();
        return message.contains("记住")
                || message.contains("以后")
                || message.contains("偏好")
                || message.contains("喜欢")
                || message.contains("不喜欢");
    }

    @Override
    public SkillResult execute(AgentContext context) {
        String message = context.getUserMessage();
        if (message.contains("以后")) {
            PreferenceRequest request = new PreferenceRequest();
            request.setKey("answer_instruction");
            request.setValue(message);
            memoryService.upsertPreference(context.getUserId(), request);
        } else {
            MemoryRequest request = new MemoryRequest();
            request.setMemoryType("user_statement");
            request.setContent(message);
            request.setTags("auto-captured");
            request.setImportance(2);
            memoryService.addMemory(context.getUserId(), request);
        }
        return callAi(context, "确认已经保存用户明确表达的偏好或记忆，并简短说明后续会如何使用。");
    }
}

