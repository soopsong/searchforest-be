package com.searchforest.site.user;

import com.searchforest.keyword.domain.Keyword;
import com.searchforest.keyword.service.KeywordService;
import com.searchforest.log.domain.UserKeywordLog;
import com.searchforest.log.domain.UserPaperLog;
import com.searchforest.log.service.LogService;
import com.searchforest.paper.domain.Paper;
import com.searchforest.paper.repository.PaperRepository;
import com.searchforest.paper.service.PaperService;
import com.searchforest.site.dto.*;
import com.searchforest.user.domain.PaperHistory;
import com.searchforest.user.domain.Sessions;
import com.searchforest.user.domain.TextHistory;
import com.searchforest.user.domain.User;
import com.searchforest.user.repository.SessionRepository;
import com.searchforest.user.service.PaperHistoryService;
import com.searchforest.user.service.SessionService;
import com.searchforest.user.service.TextHistoryService;
import com.searchforest.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final KeywordService keywordService;
    private final SessionService sessionService;
    private final TextHistoryService textHistoryService;
    private final PaperService paperService;
    private final PaperHistoryService paperHistoryService;
    private final PaperRepository paperRepository;

    private final LogService logService;

    @Operation(description = "기본 홈페이지")
    @GetMapping("")
    public String home() {
        return "hi user";
    }

    //user 의 session List 를 보내는 api(상위 5개)
    @Operation(description = "user 의 history list를 제공하는 api")
    @GetMapping("/history/keyword")
    public ResponseEntity<List<TextHistoryResponse>> getUserHistories(@AuthenticationPrincipal User user) {
        List<Sessions> sessions = sessionService.getSessions(user.getId());

        List<TextHistoryResponse> result = sessions.stream()
                .map(session -> {
                    String rootMessage = textHistoryService.getRootMessage(session.getId());
                    List<String> messages = textHistoryService.getSubTextHistory(session.getId());
                    return new TextHistoryResponse(session.getId(),rootMessage,messages);
                })
                .toList();

        return ResponseEntity.ok(result);
    }

    //sessionId로 요청시 해당 session 의 message 를 전부 list 로 제공.
//    @Operation(description = "해당 session Id 의 message list 를 제공하는 api, history용")
//    @GetMapping("/history/{sessionId}")
//    public ResponseEntity<List<String>> getSessionMessages(@PathVariable UUID sessionId) {
//        List<String> contents = textHistoryService.getTextHistory(sessionId);
//        return ResponseEntity.ok(contents);
//    }
//

    @Operation(description = "회원 keyword 검색(최초 검색, session 생성)")
    @GetMapping("/search/keyword")
    public ResponseEntity<List<KeywordResponse>> textSearch(@AuthenticationPrincipal User user,
                                                            @RequestParam String text) {

        Sessions newSession = sessionService.createSession(user.getId());
        UUID sessionId = newSession.getId();

        textHistoryService.save(TextHistory.builder()
                .sessionId(sessionId)
                .rootContent(text)
                .timestamp(LocalDateTime.now())
                .build());

        List<Keyword> aiResults = keywordService.requestToAIServer(text);

        keywordService.enrichCitationCounts(aiResults);

        List<KeywordResponse> response = aiResults.stream()
                .map(keyword -> KeywordResponse.from(keyword, sessionId))
                .toList();

        logService.save(UserKeywordLog.builder()
                .sessionId(sessionId)
                .userId(user.getId())
                .startedAt(LocalDateTime.now())
                .endedAt(LocalDateTime.now())
                .searchedKeywords(new ArrayList<>(List.of(text)))
                .keywordSearchCount(1)
                .build());

        return ResponseEntity.ok(response);
    }

    @Operation(description = "회원 keyword 검색(해당 session 내, node 클릭으로 이어지는 검색)")
    @GetMapping("/search/keyword/{sessionId}")
    public ResponseEntity<List<KeywordResponse>> textSearch(@AuthenticationPrincipal User user,
                                                            @RequestParam String text,
                                                            @PathVariable UUID sessionId) {

        TextHistory root = textHistoryService.findRootBySessionId(sessionId);

        Sessions session = sessionService.findBySessionId(sessionId);

        session.setUpdatedAt(LocalDateTime.now());
        sessionService.save(session);

        if (root == null) {
            return ResponseEntity.badRequest().build();
        }

        root.setTimestamp(LocalDateTime.now());

        List<String> subList = root.getSubContent();
        if (subList == null) {
            subList = new ArrayList<>();
            root.setSubContent(subList);
        }
        if (!subList.contains(text) && !root.getRootContent().equals(text)) {
            subList.add(text);
        }

        textHistoryService.save(root);

        root = textHistoryService.findRootBySessionId(sessionId);

        List<Keyword> aiResults = keywordService.requestToAIServer(text);

        keywordService.enrichCitationCounts(aiResults);

        List<KeywordResponse> response = aiResults.stream()
                .map(keyword -> KeywordResponse.from(keyword, sessionId))
                .toList();

        //log 저장.
        UserKeywordLog log = logService.findBySessionId(sessionId);
        log.getSearchedKeywords().add(text);
        log.setEndedAt(LocalDateTime.now());
        log.setKeywordSearchCount(log.getKeywordSearchCount() + 1);
        logService.save(log);

        return ResponseEntity.ok(response);
    }

    @Operation(description = "논문 데이터 AI 서버에 검색 요청")
    @GetMapping("/search/paper")
    public ResponseEntity<List<Paper>> paperSearch(@RequestParam String text) {

        // 2. AI 서버 요청
        List<Paper> aiResults = paperService.requestToAIServer(text, text);

        paperRepository.saveAll(aiResults);

        return ResponseEntity.ok(aiResults);
    }

    @Operation(description = "유저가 paper 클릭시, history로 저장하기 위함")
    @PostMapping("/paper")
    public ResponseEntity<?> paperClicked(@AuthenticationPrincipal User user,
                                          @RequestParam String paperId) {

        Paper paper = paperService.findByPaperId(paperId);
        if(paper == null){
            return ResponseEntity.status(404).body(Map.of("Not found", "해당 논문이 존재하지 않습니다."));
        }

        paperHistoryService.save(PaperHistory.builder()
                .id(paper.getPaperId())
                .title(paper.getTitle())
                .userId(user.getId())
                .searchedAt(LocalDateTime.now())
                .url(paper.getPdfUrl())
                .build());

        UserPaperLog userPaperLog = logService.findUserPaperByUserId(user.getId());

        if(userPaperLog == null){
            logService.save(UserPaperLog.builder()
                    .userId(user.getId())
                    .paperSearchCount(1)
                    .searchedPapers(new ArrayList<>(List.of(paperId)))
                    .build());
        }else{
            userPaperLog.getSearchedPapers().add(paperId);
            userPaperLog.setPaperSearchCount(userPaperLog.getPaperSearchCount() + 1);
            logService.save(userPaperLog);
        }

        return ResponseEntity.ok(paper);
    }

    @Operation(description = "paper history 조회")
    @GetMapping("/history/paper")
    public ResponseEntity<?> paperHistory(@AuthenticationPrincipal User user) {

        // 2. AI 서버 요청
        List<PaperHistory> paperHistory = paperHistoryService.getPaperHistory(user.getId());

        if(paperHistory == null){
            return ResponseEntity.status(404).body(Map.of("Not found", "논문 검색 기록이 없습니다."));
        }

        return ResponseEntity.ok(paperHistory);
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
