package com.example.agent.service;

import com.example.agent.entity.User;
import com.example.agent.entity.UserPreference;
import com.example.agent.mapper.UserMapper;
import com.example.agent.mapper.UserPreferenceMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserProfileService {

    private final UserMapper userMapper;
    private final UserPreferenceMapper preferenceMapper;

    public UserProfileService(UserMapper userMapper, UserPreferenceMapper preferenceMapper) {
        this.userMapper = userMapper;
        this.preferenceMapper = preferenceMapper;
    }

    public User getUser(Long userId) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在: " + userId);
        }
        return user;
    }

    public List<UserPreference> listPreferences(Long userId) {
        return preferenceMapper.findByUserId(userId);
    }

    public Map<String, String> preferenceMap(Long userId) {
        Map<String, String> result = new LinkedHashMap<>();
        for (UserPreference preference : listPreferences(userId)) {
            result.put(preference.getPreferenceKey(), preference.getPreferenceValue());
        }
        return result;
    }
}

