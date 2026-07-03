package com.example.agent.ai;

import com.example.agent.memory.AgentContext;

public interface AiGateway {
    String complete(AgentContext context, String systemPrompt, String userPrompt);
}

