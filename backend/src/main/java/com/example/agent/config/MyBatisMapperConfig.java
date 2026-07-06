package com.example.agent.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!dev")
@MapperScan("com.example.agent.mapper")
public class MyBatisMapperConfig {
}

