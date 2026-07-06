package com.example.agent.service;

import com.example.agent.dto.MemoryRequest;
import com.example.agent.dto.PreferenceRequest;
import com.example.agent.entity.UserMemory;
import com.example.agent.entity.UserPreference;
import com.example.agent.mapper.UserMemoryMapper;
import com.example.agent.mapper.UserPreferenceMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Profile("!dev")
public class MemoryService {

    private final UserMemoryMapper memoryMapper;
    private final UserPreferenceMapper preferenceMapper;

    public MemoryService(UserMemoryMapper memoryMapper, UserPreferenceMapper preferenceMapper) {
        this.memoryMapper = memoryMapper;
        this.preferenceMapper = preferenceMapper;
    }

    public List<UserMemory> listMemories(Long userId) {
        return memoryMapper.findActiveByUserId(userId);
    }

    public void addMemory(Long userId, MemoryRequest request) {
        UserMemory memory = new UserMemory();
        memory.setUserId(userId);
        memory.setMemoryType(request.getMemoryType());
        memory.setContent(request.getContent());
        memory.setTags(request.getTags());
        memory.setImportance(request.getImportance() == null ? 1 : request.getImportance());
        memoryMapper.insert(memory);
    }

    public void deleteMemory(Long userId, Long memoryId) {
        memoryMapper.softDelete(memoryId, userId);
    }

    public void upsertPreference(Long userId, PreferenceRequest request) {
        UserPreference preference = new UserPreference();
        preference.setUserId(userId);
        preference.setPreferenceKey(request.getKey());
        preference.setPreferenceValue(request.getValue());
        preference.setSource("user");
        preference.setConfidence(BigDecimal.ONE);
        UserPreference existing = preferenceMapper.findOne(userId, request.getKey());
        if (existing == null) {
            preferenceMapper.insert(preference);
        } else {
            preferenceMapper.update(preference);
        }
    }
}
