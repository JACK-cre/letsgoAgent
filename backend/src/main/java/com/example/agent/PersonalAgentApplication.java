package com.example.agent;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.agent.mapper")
public class PersonalAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalAgentApplication.class, args);
    }
}

