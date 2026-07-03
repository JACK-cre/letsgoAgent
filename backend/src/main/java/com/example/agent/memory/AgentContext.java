package com.example.agent.memory;

import com.example.agent.entity.User;
import com.example.agent.entity.UserMemory;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class AgentContext {
    private Long userId;
    private String sessionId;
    private String userMessage;
    private User user;
    private Map<String, String> preferences;
    private List<UserMemory> longTermMemories;
    private List<String> recentMessages;
    private String intent;
}

