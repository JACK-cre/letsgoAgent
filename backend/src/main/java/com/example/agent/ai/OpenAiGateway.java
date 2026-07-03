package com.example.agent.ai;

import com.example.agent.memory.AgentContext;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "agent.ai.mode", havingValue = "openai")
@ConditionalOnBean(ChatClient.Builder.class)
public class OpenAiGateway implements AiGateway {

    private final ChatClient chatClient;

    public OpenAiGateway(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public String complete(AgentContext context, String systemPrompt, String userPrompt) {
        return chatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .call()
                .content();
    }
}

