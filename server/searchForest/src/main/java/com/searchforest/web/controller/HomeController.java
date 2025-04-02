package com.searchforest.web.controller;


import com.searchforest.keyword.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/")
@RequiredArgsConstructor
public class HomeController {
    private final KeywordService keywordService;

    // 검색 api -> keyword 에 온 파라미터를 DB에 검색
    // Json 으로 보낼건지, String list 로 보낼건지?
    // GET /search?keyword=XXX
    @GetMapping("/search")
    public ResponseEntity<List<String>> search(@RequestParam String keyword) {
        /* todo
           keyword 에 온 parameter 를 DB에 검색하는 method 연결
           DB에 있다면 그대로 반환, 없다면 @GetMapping 으로 /search 호출.
        */
        List<String> results = keywordService.getCachedList(keyword);

        // DB에 있다면 그대로 result 반환
        if(results != null && !results.isEmpty()) {
            return ResponseEntity.ok(results);
        }

        //Todo Ai server 에 검색 결과 반환 요청
        List<String> aiResults = keywordService.requestToAIServer(keyword);

        //Todo DB에 (keyword, list) 쌍 저장.
        keywordService.saveResult(keyword, aiResults);

        return ResponseEntity.ok(aiResults);
    }

}
