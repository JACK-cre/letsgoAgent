package com.example.agent.controller;

import com.example.agent.entity.AgentCallLog;
import com.example.agent.mapper.AgentCallLogMapper;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/agent/logs")
public class AgentLogController {

    private final AgentCallLogMapper logMapper;

    public AgentLogController(ObjectProvider<AgentCallLogMapper> logMapperProvider) {
        this.logMapper = logMapperProvider.getIfAvailable();
    }

    @GetMapping
    public List<AgentCallLog> logs() {
        if (logMapper == null) {
            return Collections.emptyList();
        }
        return logMapper.findRecent();
    }
}
