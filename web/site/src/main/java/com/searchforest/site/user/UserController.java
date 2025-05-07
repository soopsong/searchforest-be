package com.searchforest.site.user;

import com.searchforest.keyword.domain.Keyword;
import com.searchforest.keyword.service.KeywordService;
import com.searchforest.paper.domain.Paper;
import com.searchforest.paper.service.PaperService;
import com.searchforest.site.dto.SessionResponse;
import com.searchforest.user.domain.Messages;
import com.searchforest.user.domain.Sessions;
import com.searchforest.user.domain.User;
import com.searchforest.user.service.MessageService;
import com.searchforest.user.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final KeywordService keywordService;
    private final SessionService sessionService;
    private final MessageService messageService;
    private final PaperService paperService;

    @Operation(description = "기본 홈페이지")
    @GetMapping("")
    public String home(){
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

    @Operation(description = "새로운 세션을 생성하는 API")
    @PostMapping("/sessions")
    public ResponseEntity<SessionResponse> createNewSession(@AuthenticationPrincipal User user) {
        Sessions newSession = sessionService.createSession(user.getId());
        return ResponseEntity.ok(SessionResponse.from(newSession));
    }


    //sessionId로 요청시 해당 session 의 message 를 전부 list 로 제공.
    @Operation(description = "해당 session Id 의 message list 를 제공하는 api")
    @GetMapping("/sessions/{sessionId}")
    public ResponseEntity<List<String>> getSessionMessages(@PathVariable UUID sessionId) {
        List<String> contents = messageService.getMessages(sessionId);
        return ResponseEntity.ok(contents);
    }


    // text 검색
    // GET /search?keyword=XXX&sessionId=XXX
    @Operation(description = "회원 text 검색")
    @GetMapping("/search/keyword")
    public ResponseEntity<Keyword> textSearch(@RequestParam String text,
                                              @RequestParam UUID sessionId) {
        // 1. 메시지 저장
        messageService.save(Messages.builder()
                .sessionId(sessionId)
                .content(text)
                .timestamp(LocalDateTime.now())
                .build());

        // 2. session 메시지 리스트 가져오기
        List<String> messages = messageService.getMessages(sessionId);

        // 3. 캐시 조회 (선택) - 단일 keyword 기반이므로 이건 생략하거나 변경 가능
        // Keyword cached = keywordService.getCachedList(text);
        // if (cached != null) return ResponseEntity.ok(cached);

        // 4. AI 서버 요청
        Keyword aiResults = keywordService.requestToAIServer(messages);
        keywordService.save(aiResults);

        return ResponseEntity.ok(aiResults);
    }
    @Operation(description = "논문 데이터 검색")
    @GetMapping("/search/paper")
    public ResponseEntity<List<Paper>> paperSearch(@RequestParam String keyword,
                                                   @RequestParam UUID sessionId) {
        // 1. 메시지 저장
        messageService.save(Messages.builder()
                .sessionId(sessionId)
                .content(keyword)
                .timestamp(LocalDateTime.now())
                .build());

        // 2. AI 서버 요청
        List<Paper> aiResults = paperService.requestToAIServer(keyword);
//        paperService.save(aiResults);

        return ResponseEntity.ok(aiResults);
    }
}
