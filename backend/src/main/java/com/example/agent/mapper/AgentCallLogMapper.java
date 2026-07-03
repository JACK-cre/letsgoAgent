package com.example.agent.mapper;

import com.example.agent.entity.AgentCallLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AgentCallLogMapper {

    @Insert("""
            INSERT INTO agent_call_log (user_id, session_id, user_message, selected_skill, model_response, latency_ms)
            VALUES (#{userId}, #{sessionId}, #{userMessage}, #{selectedSkill}, #{modelResponse}, #{latencyMs})
            """)
    void insert(AgentCallLog log);

    @Select("SELECT * FROM agent_call_log ORDER BY created_at DESC LIMIT 50")
    List<AgentCallLog> findRecent();
}

