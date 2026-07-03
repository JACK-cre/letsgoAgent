package com.example.agent.mapper;

import com.example.agent.entity.UserPreference;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserPreferenceMapper {

    @Select("SELECT * FROM user_preference WHERE user_id = #{userId} ORDER BY updated_at DESC")
    List<UserPreference> findByUserId(Long userId);

    @Insert("""
            INSERT INTO user_preference (user_id, preference_key, preference_value, source, confidence)
            VALUES (#{userId}, #{preferenceKey}, #{preferenceValue}, #{source}, #{confidence})
            ON DUPLICATE KEY UPDATE
              preference_value = VALUES(preference_value),
              source = VALUES(source),
              confidence = VALUES(confidence),
              updated_at = CURRENT_TIMESTAMP
            """)
    void upsert(UserPreference preference);
}

