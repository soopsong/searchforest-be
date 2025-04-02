package com.searchforest.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.searchforest.config",
        "com.searchforest.web",
        "com.searchforest.site"
})
public class SearchForestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchForestApplication.class, args);
    }
}
