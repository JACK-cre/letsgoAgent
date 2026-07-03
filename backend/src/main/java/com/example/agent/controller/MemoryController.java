package com.example.agent.controller;

import com.example.agent.dto.MemoryRequest;
import com.example.agent.dto.PreferenceRequest;
import com.example.agent.entity.UserMemory;
import com.example.agent.service.MemoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users/{userId}")
public class MemoryController {

    private final MemoryService memoryService;

    public MemoryController(MemoryService memoryService) {
        this.memoryService = memoryService;
    }

    @GetMapping("/memories")
    public List<UserMemory> memories(@PathVariable Long userId) {
        return memoryService.listMemories(userId);
    }

    @PostMapping("/memories")
    public Map<String, String> addMemory(@PathVariable Long userId, @Valid @RequestBody MemoryRequest request) {
        memoryService.addMemory(userId, request);
        return Map.of("status", "saved");
    }

    @DeleteMapping("/memories/{memoryId}")
    public Map<String, String> deleteMemory(@PathVariable Long userId, @PathVariable Long memoryId) {
        memoryService.deleteMemory(userId, memoryId);
        return Map.of("status", "deleted");
    }

    @PostMapping("/preferences")
    public Map<String, String> upsertPreference(@PathVariable Long userId, @Valid @RequestBody PreferenceRequest request) {
        memoryService.upsertPreference(userId, request);
        return Map.of("status", "saved");
    }
}

