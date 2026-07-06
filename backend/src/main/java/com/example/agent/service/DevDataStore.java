package com.example.agent.service;

import com.example.agent.entity.User;
import com.example.agent.entity.UserMemory;
import com.example.agent.entity.UserPreference;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Profile("dev")
public class DevDataStore {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final Map<Long, Map<String, UserPreference>> preferences = new ConcurrentHashMap<>();
    private final Map<Long, List<UserMemory>> memories = new ConcurrentHashMap<>();
    private final AtomicLong preferenceId = new AtomicLong(1);
    private final AtomicLong memoryId = new AtomicLong(1);

    public DevDataStore() {
        User user = new User();
        user.setId(1L);
        user.setUsername("demo");
        user.setNickname("Demo User");
        user.setLanguage("zh-CN");
        user.setTimezone("Asia/Shanghai");
        users.put(1L, user);

        putPreference(1L, "answer_style", "清晰、直接、带一点专业建议", "seed");
        putPreference(1L, "language", "中文", "seed");
    }

    public User getUser(Long userId) {
        return users.get(userId);
    }

    public List<UserPreference> listPreferences(Long userId) {
        return new ArrayList<>(preferences.getOrDefault(userId, Map.of()).values());
    }

    public void putPreference(Long userId, String key, String value, String source) {
        Map<String, UserPreference> userPreferences = preferences.computeIfAbsent(userId, ignored -> new ConcurrentHashMap<>());
        UserPreference preference = userPreferences.computeIfAbsent(key, ignored -> {
            UserPreference created = new UserPreference();
            created.setId(preferenceId.getAndIncrement());
            created.setUserId(userId);
            created.setPreferenceKey(key);
            return created;
        });
        preference.setPreferenceValue(value);
        preference.setSource(source);
        preference.setConfidence(BigDecimal.ONE);
    }

    public List<UserMemory> listMemories(Long userId) {
        return memories.getOrDefault(userId, List.of()).stream()
                .filter(memory -> "ACTIVE".equals(memory.getStatus()))
                .sorted(Comparator.comparing(UserMemory::getImportance).reversed())
                .toList();
    }

    public void addMemory(UserMemory memory) {
        memory.setId(memoryId.getAndIncrement());
        memory.setStatus("ACTIVE");
        memories.computeIfAbsent(memory.getUserId(), ignored -> new ArrayList<>()).add(memory);
    }

    public void deleteMemory(Long userId, Long memoryId) {
        memories.getOrDefault(userId, List.of()).stream()
                .filter(memory -> memoryId.equals(memory.getId()))
                .findFirst()
                .ifPresent(memory -> memory.setStatus("DELETED"));
    }
}

