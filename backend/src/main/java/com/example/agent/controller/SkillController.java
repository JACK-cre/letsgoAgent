package com.example.agent.controller;

import com.example.agent.entity.SkillDefinition;
import com.example.agent.mapper.SkillMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillMapper skillMapper;

    public SkillController(SkillMapper skillMapper) {
        this.skillMapper = skillMapper;
    }

    @GetMapping
    public List<SkillDefinition> skills() {
        return skillMapper.findEnabled();
    }
}

