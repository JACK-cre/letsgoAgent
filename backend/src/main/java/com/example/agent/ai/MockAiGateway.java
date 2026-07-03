package com.example.agent.ai;

import com.example.agent.memory.AgentContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "agent.ai.mode", havingValue = "mock", matchIfMissing = true)
public class MockAiGateway implements AiGateway {

    @Override
    public String complete(AgentContext context, String systemPrompt, String userPrompt) {
        String nickname = context.getUser() == null ? "你" : context.getUser().getNickname();
        return "我已经根据 " + nickname + " 的资料、偏好和近期上下文处理了这次请求。\n\n"
                + "当前是本地模拟 AI 模式。部署时配置 OPENAI_API_KEY 并将 AGENT_AI_MODE 设置为 openai，就可以切换到真实模型。";
    }
}

