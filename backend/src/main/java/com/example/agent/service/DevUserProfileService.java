package com.example.agent.service;

import com.example.agent.entity.User;
import com.example.agent.entity.UserPreference;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Profile("dev")
public class DevUserProfileService extends UserProfileService {

    private final DevDataStore dataStore;

    public DevUserProfileService(DevDataStore dataStore) {
        super(null, null);
        this.dataStore = dataStore;
    }

    @Override
    public User getUser(Long userId) {
        User user = dataStore.getUser(userId);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在: " + userId);
        }
        return user;
    }

    @Override
    public List<UserPreference> listPreferences(Long userId) {
        return dataStore.listPreferences(userId);
    }

    @Override
    public Map<String, String> preferenceMap(Long userId) {
        Map<String, String> result = new LinkedHashMap<>();
        for (UserPreference preference : listPreferences(userId)) {
            result.put(preference.getPreferenceKey(), preference.getPreferenceValue());
        }
        return result;
    }
}

