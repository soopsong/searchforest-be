package com.searchforest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.searchforest.config"
})
public class KeywordTestApp {
    public static void main(String[] args) {
        SpringApplication.run(KeywordTestApp.class, args);}
}