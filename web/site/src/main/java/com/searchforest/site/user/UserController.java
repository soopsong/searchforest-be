package com.searchforest.site.user;

import com.searchforest.imageKeyword.domain.ImageKeyword;
import com.searchforest.imageKeyword.service.ImageKeywordService;
import com.searchforest.keyword.domain.Keyword;
import com.searchforest.keyword.service.KeywordService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final KeywordService keywordService;
    private final ImageKeywordService imageKeywordService;

    @Operation(description = "기본 홈페이지")
    @GetMapping({"","/"})
    public String home(){
        return "hi user";
    }

    // text 검색
    // 검색 api -> keyword 에 온 파라미터를 DB에 검색
    // Json 으로 보낼건지, String list 로 보낼건지?
    // GET /search?keyword=XXX
    @Operation(description = "회원 text 검색")
    @GetMapping("/search/text")
    public ResponseEntity<Keyword> textSearch(@RequestParam String keyword) {
        /* todo
           keyword 에 온 parameter 를 DB에 검색하는 method 연결
           DB에 있다면 그대로 반환, 없다면 ai server 에 결과 요청 후 반환
        */
        Keyword results = keywordService.getCachedList(keyword);

        // DB에 있다면 그대로 result 반환
        if(results != null) {
            return ResponseEntity.ok(results);
        }

        //Todo Ai server 에 검색 결과 반환 요청
        Keyword aiResults = keywordService.requestToAIServer(keyword);

        //Todo DB에 Keyword 저장.
        keywordService.save(aiResults);

        // keyword를 Json으로 반환
        return ResponseEntity.ok(aiResults);
    }


    // Todo
    // Image로 맞춰서 수정하기
    @Operation(description = "회원 이미지 검색")
    @GetMapping("/search/image")
    public ResponseEntity<ImageKeyword> imageSearch(@RequestParam String keyword) {
        /* todo
           keyword 에 온 parameter 를 DB에 검색하는 method 연결
           DB에 있다면 그대로 반환, 없다면 ai server 에 결과 요청 후 반환
        */
        ImageKeyword results = imageKeywordService.getCachedList(keyword);

        // DB에 있다면 그대로 result 반환
        if(results != null) {
            return ResponseEntity.ok(results);
        }

        //Todo Ai server 에 검색 결과 반환 요청
        ImageKeyword aiResults = imageKeywordService.requestToAIServer(keyword);

        //Todo DB에 Keyword 저장.
        imageKeywordService.save(aiResults);

        // keyword를 Json으로 반환
        return ResponseEntity.ok(aiResults);
    }
}
