package com.example.agent.mapper;

import com.example.agent.entity.UserMemory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMemoryMapper {

    @Select("""
            SELECT * FROM user_memory
            WHERE user_id = #{userId} AND status = 'ACTIVE'
            ORDER BY importance DESC, updated_at DESC
            LIMIT 30
            """)
    List<UserMemory> findActiveByUserId(Long userId);

    @Insert("""
            INSERT INTO user_memory (user_id, memory_type, content, tags, importance, status)
            VALUES (#{userId}, #{memoryType}, #{content}, #{tags}, #{importance}, 'ACTIVE')
            """)
    void insert(UserMemory memory);

    @Update("UPDATE user_memory SET status = 'DELETED', updated_at = CURRENT_TIMESTAMP WHERE id = #{id} AND user_id = #{userId}")
    void softDelete(@Param("id") Long id, @Param("userId") Long userId);
}
