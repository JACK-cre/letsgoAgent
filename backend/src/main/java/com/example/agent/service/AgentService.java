package com.example.agent.service;

import com.example.agent.dto.ChatRequest;
import com.example.agent.dto.ChatResponse;
import com.example.agent.entity.AgentCallLog;
import com.example.agent.entity.User;
import com.example.agent.entity.UserMemory;
import com.example.agent.mapper.AgentCallLogMapper;
import com.example.agent.memory.AgentContext;
import com.example.agent.memory.ShortTermMemoryManager;
import com.example.agent.skill.AgentSkill;
import com.example.agent.skill.SkillResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AgentService {

    private final UserProfileService userProfileService;
    private final MemoryService memoryService;
    private final ShortTermMemoryManager shortTermMemoryManager;
    private final SkillRouterService skillRouterService;
    private final AgentCallLogMapper logMapper;

    public AgentService(
            UserProfileService userProfileService,
            MemoryService memoryService,
            ShortTermMemoryManager shortTermMemoryManager,
            SkillRouterService skillRouterService,
            AgentCallLogMapper logMapper
    ) {
        this.userProfileService = userProfileService;
        this.memoryService = memoryService;
        this.shortTermMemoryManager = shortTermMemoryManager;
        this.skillRouterService = skillRouterService;
        this.logMapper = logMapper;
    }

    public ChatResponse chat(ChatRequest request) {
        long startedAt = System.currentTimeMillis();
        User user = userProfileService.getUser(request.getUserId());
        Map<String, String> preferences = userProfileService.preferenceMap(request.getUserId());
        List<UserMemory> memories = memoryService.listMemories(request.getUserId());
        List<String> recentMessages = shortTermMemoryManager.getRecentMessages(request.getSessionId());

        AgentContext context = AgentContext.builder()
                .userId(request.getUserId())
                .sessionId(request.getSessionId())
                .userMessage(request.getMessage())
                .user(user)
                .preferences(preferences)
                .longTermMemories(memories)
                .recentMessages(recentMessages)
                .build();

        AgentSkill skill = skillRouterService.route(context);
        SkillResult result = skill.execute(context);

        shortTermMemoryManager.appendMessage(request.getSessionId(), "user", request.getMessage());
        shortTermMemoryManager.appendMessage(request.getSessionId(), "assistant", result.getAnswer());

        AgentCallLog log = new AgentCallLog();
        log.setUserId(request.getUserId());
        log.setSessionId(request.getSessionId());
        log.setUserMessage(request.getMessage());
        log.setSelectedSkill(result.getSkillCode());
        log.setModelResponse(result.getAnswer());
        log.setLatencyMs(System.currentTimeMillis() - startedAt);
        logMapper.insert(log);

        return ChatResponse.builder()
                .userId(request.getUserId())
                .sessionId(request.getSessionId())
                .selectedSkill(result.getSkillCode())
                .answer(result.getAnswer())
                .build();
    }
}

