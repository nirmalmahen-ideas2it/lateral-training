package com.myfirst.microservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    CommandLineRunner showConfig(Environment env) {
        return args -> {
            System.out.println("----- Loaded properties -----");
            System.out.println("server.port = " + env.getProperty("server.port"));
            System.out.println("Database URL = " + env.getProperty("spring.cloud.datasource.url"));
        };
    }

}
