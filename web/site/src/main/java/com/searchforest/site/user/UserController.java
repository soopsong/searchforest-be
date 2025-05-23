package com.searchforest.site.user;

import com.searchforest.keyword.domain.Keyword;
import com.searchforest.keyword.service.KeywordService;
import com.searchforest.paper.domain.Paper;
import com.searchforest.paper.service.PaperService;
import com.searchforest.site.dto.KeywordResponse;
import com.searchforest.site.dto.SessionResponse;
import com.searchforest.user.domain.Sessions;
import com.searchforest.user.domain.TextHistory;
import com.searchforest.user.domain.User;
import com.searchforest.user.service.SessionService;
import com.searchforest.user.service.TextHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final KeywordService keywordService;
    private final SessionService sessionService;
    private final TextHistoryService textHistoryService;
    private final PaperService paperService;

    @Operation(description = "기본 홈페이지")
    @GetMapping("")
    public String home() {
        return "hi user";
    }

    //user 의 session List 를 보내는 api(상위 5개)
    @Operation(description = "user 의 session Id list 를 제공하는 api")
    @GetMapping("/sessions")
    public ResponseEntity<List<SessionResponse>> getUserSessions(@AuthenticationPrincipal User user) {
        List<Sessions> sessions = sessionService.getSessions(user.getId());

        List<SessionResponse> response = sessions.stream()
                .map(SessionResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    //sessionId로 요청시 해당 session 의 message 를 전부 list 로 제공.
    @Operation(description = "해당 session Id 의 message list 를 제공하는 api, history용")
    @GetMapping("/sessions/history/{sessionId}")
    public ResponseEntity<List<String>> getSessionMessages(@PathVariable UUID sessionId) {
        List<String> contents = textHistoryService.getTextHistory(sessionId);
        return ResponseEntity.ok(contents);
    }


    // text 검색
    // GET /search?keyword=XXX&sessionId=XXX
    @Operation(description = "회원 keyword 검색(최초 검색, session 생성)")
    @GetMapping("/search/keyword")
    public ResponseEntity<KeywordResponse> textSearch(@AuthenticationPrincipal User user,
                                                      @RequestParam String text) {

        // session 생성
        Sessions newSession = sessionService.createSession(user.getId());

        // 검색한 메시지 저장
        textHistoryService.save(TextHistory.builder()
                .sessionId(newSession.getId())
                .rootContent(text)
                .timestamp(LocalDateTime.now())
                .build());

        List<String> messages = textHistoryService.getTextHistory(newSession.getId());

        Keyword aiResults = keywordService.requestToAIServer(messages);

        //Todo 저장해야해?
        keywordService.save(aiResults);

        KeywordResponse response = KeywordResponse.builder()
                .sessionId(newSession.getId())
                .text(aiResults.getText())
                .weight(aiResults.getWeight())
                .sublist(aiResults.getSublist())
                .build();


        return ResponseEntity.ok(response);
    }

    @Operation(description = "회원 keyword 검색(해당 session 내, node 클릭으로 이어지는 검색)")
    @GetMapping("/search/keyword/{sessionId}")
    public ResponseEntity<Keyword> textSearch(@AuthenticationPrincipal User user,
                                              @RequestParam String text,
                                              @PathVariable UUID sessionId) {

        // 회원인 경우에만 메시지 저장

        textHistoryService.save(TextHistory.builder()
                .sessionId(sessionId)
                .rootContent(text)
                .timestamp(LocalDateTime.now())
                .build());


        List<String> messages = textHistoryService.getTextHistory(sessionId);

        Keyword aiResults = keywordService.requestToAIServer(messages);

        //Todo 저장해야할까?
        keywordService.save(aiResults);


        return ResponseEntity.ok(aiResults);
    }

    @Operation(description = "논문 데이터 검색")
    @GetMapping("/search/paper")
    public ResponseEntity<List<Paper>> paperSearch(@RequestParam String keyword,
                                                   @RequestParam UUID sessionId) {
        // 1. 메시지 저장
        textHistoryService.save(TextHistory.builder()
                .sessionId(sessionId)
                .rootContent(keyword)
                .timestamp(LocalDateTime.now())
                .build());

        // 2. AI 서버 요청
        List<Paper> aiResults = paperService.requestToAIServer(keyword);
//        paperService.save(aiResults);

        return ResponseEntity.ok(aiResults);
    }


    //test용 회원 정보 조회 api
    @GetMapping("/me")
    @Operation(description = "test 용 회원 정보 조회 api")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal OAuth2User oauthUser) {
        if (oauthUser == null) {
            return ResponseEntity.status(401).body(Map.of("error", "로그인되지 않은 사용자입니다."));
        }

        // OAuth2 provider에 따라 구조가 다름 (예: kakao_account, email, name 등)
        Map<String, Object> attributes = oauthUser.getAttributes();

        return ResponseEntity.ok(Map.of(
                "attributes", attributes
        ));
    }
}
