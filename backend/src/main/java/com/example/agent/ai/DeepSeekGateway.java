package com.example.agent.ai;

import com.example.agent.memory.AgentContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
@ConditionalOnProperty(name = "agent.ai.mode", havingValue = "deepseek")
public class DeepSeekGateway implements AiGateway {

    private final RestClient restClient;
    private final String apiKey;
    private final String model;

    public DeepSeekGateway(
            @Value("${agent.deepseek.base-url}") String baseUrl,
            @Value("${agent.deepseek.api-key}") String apiKey,
            @Value("${agent.deepseek.model}") String model
    ) {
        this.apiKey = apiKey;
        this.model = model;
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public String complete(AgentContext context, String systemPrompt, String userPrompt) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("DEEPSEEK_API_KEY 未配置，无法调用 DeepSeek。");
        }

        Map<String, Object> request = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of("role", "system", "content", systemPrompt),
                        Map.of("role", "user", "content", userPrompt)
                ),
                "temperature", 0.7
        );

        DeepSeekResponse response = restClient.post()
                .uri("/chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .body(request)
                .retrieve()
                .body(DeepSeekResponse.class);

        if (response == null || response.choices() == null || response.choices().isEmpty()) {
            return "DeepSeek 没有返回有效内容，请稍后重试。";
        }
        DeepSeekMessage message = response.choices().get(0).message();
        if (message == null || message.content() == null || message.content().isBlank()) {
            return "DeepSeek 返回为空，请稍后重试。";
        }
        return message.content();
    }

    private record DeepSeekResponse(List<DeepSeekChoice> choices) {
    }

    private record DeepSeekChoice(DeepSeekMessage message) {
    }

    private record DeepSeekMessage(String role, String content) {
    }
}

