package com.searchforest.site.guest;

import com.searchforest.keyword.domain.Keyword;
import com.searchforest.keyword.service.KeywordService;
import com.searchforest.paper.domain.Paper;
import com.searchforest.paper.service.PaperService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guest")
public class GuestController {

    private final KeywordService keywordService;
    private final PaperService paperService;

    @Operation(description = "비회원 text 검색")
    @GetMapping("/search/keyword")
    public ResponseEntity<Keyword> guestTextSearch(@RequestParam String text,
                                                   @RequestParam UUID sessionId) {
        // 👉 비회원은 message 저장 없이 검색만 처리
        Keyword results = keywordService.getCachedList(text);

        if (results != null) {
            return ResponseEntity.ok(results);
        }

        Keyword aiResults = keywordService.requestToAIServer(text);
        keywordService.save(aiResults);

        return ResponseEntity.ok(aiResults);
    }

    @Operation(description = "비회원 논문 검색")
    @GetMapping("/search/paper")
    public ResponseEntity<List<Paper>> guestPaperSearch(@RequestParam String keyword,
                                                        @RequestParam UUID sessionId) {
        List<Paper> aiResults = paperService.requestToAIServer(keyword);
        paperService.save(aiResults);

        return ResponseEntity.ok(aiResults);
    }
}
