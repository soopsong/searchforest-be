package com.searchforest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.searchforest.config"
})
public class PaperTestApp {

    public static void main(String[] args) {
        SpringApplication.run(PaperTestApp.class, args);
    }

//    @Configuration
//    @EntityScan(basePackages = {
//            "com.searchforest.paper.domain"
//    })
//    @EnableJpaRepositories(basePackages = {
//            "com.searchforest.paper.repository"
//    })
//    class JpaConfig {
//
//    }
}
