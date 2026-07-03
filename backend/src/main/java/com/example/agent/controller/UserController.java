package com.example.agent.controller;

import com.example.agent.entity.User;
import com.example.agent.entity.UserPreference;
import com.example.agent.service.UserProfileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserProfileService userProfileService;

    public UserController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/{userId}/profile")
    public Map<String, Object> profile(@PathVariable Long userId) {
        User user = userProfileService.getUser(userId);
        List<UserPreference> preferences = userProfileService.listPreferences(userId);
        return Map.of("user", user, "preferences", preferences);
    }
}

