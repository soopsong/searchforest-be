package com.searchforest.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("com.searchforest.user")
@EnableJpaRepositories(basePackages = {
        "com.searchforest.user.repository"
})
@EntityScan(basePackages = {
        "com.searchforest.user.domain"
})
public class UserModule {
}
