package com.example.agent.memory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ShortTermMemoryManager {

    private final StringRedisTemplate redisTemplate;
    private final boolean localStore;
    private final int recentMessageLimit;
    private final Map<String, Deque<String>> localMessages = new ConcurrentHashMap<>();

    public ShortTermMemoryManager(
            ObjectProvider<StringRedisTemplate> redisTemplateProvider,
            @Value("${agent.memory.store:redis}") String memoryStore,
            @Value("${agent.memory.recent-message-limit:12}") int recentMessageLimit
    ) {
        this.redisTemplate = redisTemplateProvider.getIfAvailable();
        this.localStore = "local".equalsIgnoreCase(memoryStore);
        this.recentMessageLimit = recentMessageLimit;
    }

    public List<String> getRecentMessages(String sessionId) {
        if (localStore || redisTemplate == null) {
            Deque<String> values = localMessages.get(sessionId);
            if (values == null) {
                return Collections.emptyList();
            }
            return new ArrayList<>(values);
        }
        List<String> values = redisTemplate.opsForList().range(key(sessionId), 0, recentMessageLimit - 1);
        if (values == null) {
            return Collections.emptyList();
        }
        return values;
    }

    public void appendMessage(String sessionId, String role, String content) {
        String value = role + ": " + content;
        if (localStore || redisTemplate == null) {
            Deque<String> values = localMessages.computeIfAbsent(sessionId, ignored -> new LinkedList<>());
            values.addFirst(value);
            while (values.size() > recentMessageLimit) {
                values.removeLast();
            }
            return;
        }
        String key = key(sessionId);
        redisTemplate.opsForList().leftPush(key, value);
        redisTemplate.opsForList().trim(key, 0, recentMessageLimit - 1);
        redisTemplate.expire(key, Duration.ofDays(7));
    }

    private String key(String sessionId) {
        return "agent:recent_messages:" + sessionId;
    }
}
