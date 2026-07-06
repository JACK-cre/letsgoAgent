package com.example.agent.mapper;

import com.example.agent.entity.UserPreference;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserPreferenceMapper {

    @Select("SELECT * FROM user_preference WHERE user_id = #{userId} ORDER BY updated_at DESC")
    List<UserPreference> findByUserId(Long userId);

    @Select("""
            SELECT * FROM user_preference
            WHERE user_id = #{userId} AND preference_key = #{preferenceKey}
            """)
    UserPreference findOne(@Param("userId") Long userId, @Param("preferenceKey") String preferenceKey);

    @Insert("""
            INSERT INTO user_preference (user_id, preference_key, preference_value, source, confidence)
            VALUES (#{userId}, #{preferenceKey}, #{preferenceValue}, #{source}, #{confidence})
            """)
    void insert(UserPreference preference);

    @Update("""
            UPDATE user_preference
            SET preference_value = #{preferenceValue},
                source = #{source},
                confidence = #{confidence},
                updated_at = CURRENT_TIMESTAMP
            WHERE user_id = #{userId} AND preference_key = #{preferenceKey}
            """)
    void update(UserPreference preference);
}
