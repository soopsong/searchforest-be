package com.searchforest.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("com.searchforest.keyword")
@EnableJpaRepositories(basePackages = {
        "com.searchforest.keyword.repository"
})
@EntityScan(basePackages = {
        "com.searchforest.keyword.domain"
})
public class KeywordModule {
}
