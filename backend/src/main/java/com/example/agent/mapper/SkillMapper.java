package com.example.agent.mapper;

import com.example.agent.entity.SkillDefinition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SkillMapper {

    @Select("SELECT * FROM skill_definition WHERE enabled = TRUE ORDER BY id ASC")
    List<SkillDefinition> findEnabled();
}

