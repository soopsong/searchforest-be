package com.searchforest.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("com.searchforest.log")
@EnableJpaRepositories(basePackages = {
        "com.searchforest.log.repository"
})
@EntityScan(basePackages = {
        "com.searchforest.log.domain"
})
public class LogModule {
}
