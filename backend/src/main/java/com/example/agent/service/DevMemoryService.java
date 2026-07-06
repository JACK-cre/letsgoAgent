package com.example.agent.service;

import com.example.agent.dto.MemoryRequest;
import com.example.agent.dto.PreferenceRequest;
import com.example.agent.entity.UserMemory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("dev")
public class DevMemoryService extends MemoryService {

    private final DevDataStore dataStore;

    public DevMemoryService(DevDataStore dataStore) {
        super(null, null);
        this.dataStore = dataStore;
    }

    @Override
    public List<UserMemory> listMemories(Long userId) {
        return dataStore.listMemories(userId);
    }

    @Override
    public void addMemory(Long userId, MemoryRequest request) {
        UserMemory memory = new UserMemory();
        memory.setUserId(userId);
        memory.setMemoryType(request.getMemoryType());
        memory.setContent(request.getContent());
        memory.setTags(request.getTags());
        memory.setImportance(request.getImportance() == null ? 1 : request.getImportance());
        dataStore.addMemory(memory);
    }

    @Override
    public void deleteMemory(Long userId, Long memoryId) {
        dataStore.deleteMemory(userId, memoryId);
    }

    @Override
    public void upsertPreference(Long userId, PreferenceRequest request) {
        dataStore.putPreference(userId, request.getKey(), request.getValue(), "user");
    }
}

