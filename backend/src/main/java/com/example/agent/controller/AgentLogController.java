package com.example.agent.controller;

import com.example.agent.entity.AgentCallLog;
import com.example.agent.mapper.AgentCallLogMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/agent/logs")
public class AgentLogController {

    private final AgentCallLogMapper logMapper;

    public AgentLogController(AgentCallLogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @GetMapping
    public List<AgentCallLog> logs() {
        return logMapper.findRecent();
    }
}

