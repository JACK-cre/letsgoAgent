package com.example.agent.controller;

import com.example.agent.entity.SkillDefinition;
import com.example.agent.mapper.SkillMapper;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillMapper skillMapper;

    public SkillController(ObjectProvider<SkillMapper> skillMapperProvider) {
        this.skillMapper = skillMapperProvider.getIfAvailable();
    }

    @GetMapping
    public List<SkillDefinition> skills() {
        if (skillMapper == null) {
            return devSkills();
        }
        return skillMapper.findEnabled();
    }

    private List<SkillDefinition> devSkills() {
        List<SkillDefinition> skills = new ArrayList<>();
        skills.add(skill("profile", "用户画像 Skill", "根据用户资料和偏好调整回答方式。"));
        skills.add(skill("memory", "记忆管理 Skill", "处理记住、更新、删除用户偏好的请求。"));
        skills.add(skill("recommendation", "个性化推荐 Skill", "根据用户偏好、记忆和当前上下文进行推荐。"));
        skills.add(skill("task", "任务规划 Skill", "把目标拆成可执行计划和步骤。"));
        skills.add(skill("knowledge", "知识问答 Skill", "面向一般知识、业务知识和文档知识的问答。"));
        skills.add(skill("fallback", "兜底 Skill", "当意图不明确时给出稳妥回答或追问。"));
        return skills;
    }

    private SkillDefinition skill(String code, String name, String description) {
        SkillDefinition skill = new SkillDefinition();
        skill.setSkillCode(code);
        skill.setSkillName(name);
        skill.setDescription(description);
        skill.setEnabled(true);
        return skill;
    }
}
