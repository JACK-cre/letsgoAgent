package com.example.agent;

import com.example.agent.ai.MockAiGateway;
import com.example.agent.memory.AgentContext;
import com.example.agent.service.SkillRouterService;
import com.example.agent.skill.FallbackSkill;
import com.example.agent.skill.RecommendationSkill;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SkillRouterServiceTest {

    @Test
    void routesRecommendationIntent() {
        MockAiGateway aiGateway = new MockAiGateway();
        RecommendationSkill recommendationSkill = new RecommendationSkill(aiGateway);
        FallbackSkill fallbackSkill = new FallbackSkill(aiGateway);
        SkillRouterService router = new SkillRouterService(List.of(recommendationSkill, fallbackSkill), fallbackSkill);

        AgentContext context = AgentContext.builder()
                .userMessage("帮我推荐一本适合我的书")
                .build();

        assertThat(router.route(context).code()).isEqualTo("recommendation");
    }
}

