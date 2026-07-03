package com.example.agent.memory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Component
public class ShortTermMemoryManager {

    private final StringRedisTemplate redisTemplate;
    private final int recentMessageLimit;

    public ShortTermMemoryManager(
            StringRedisTemplate redisTemplate,
            @Value("${agent.memory.recent-message-limit:12}") int recentMessageLimit
    ) {
        this.redisTemplate = redisTemplate;
        this.recentMessageLimit = recentMessageLimit;
    }

    public List<String> getRecentMessages(String sessionId) {
        List<String> values = redisTemplate.opsForList().range(key(sessionId), 0, recentMessageLimit - 1);
        if (values == null) {
            return Collections.emptyList();
        }
        return values;
    }

    public void appendMessage(String sessionId, String role, String content) {
        String key = key(sessionId);
        redisTemplate.opsForList().leftPush(key, role + ": " + content);
        redisTemplate.opsForList().trim(key, 0, recentMessageLimit - 1);
        redisTemplate.expire(key, Duration.ofDays(7));
    }

    private String key(String sessionId) {
        return "agent:recent_messages:" + sessionId;
    }
}

