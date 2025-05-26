package com.searchforest.web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
//    private final KeywordService keywordService;
//    private final ImageKeywordService imageKeywordService;


    @GetMapping("/")
    public ResponseEntity<?> home() {
        return ResponseEntity.ok(Map.of("message", "홈 페이지입니다."));
    }


}
