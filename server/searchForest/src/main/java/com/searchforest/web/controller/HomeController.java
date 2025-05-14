package com.searchforest.web.controller;


import com.searchforest.imageKeyword.service.ImageKeywordService;
import com.searchforest.keyword.domain.Keyword;
import com.searchforest.keyword.service.KeywordService;
import com.searchforest.user.domain.UserLogin;
import com.searchforest.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
//    private final KeywordService keywordService;
//    private final ImageKeywordService imageKeywordService;

    private final AuthenticationManager authenticationManager;
    private final UserService userService;



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin request, HttpServletRequest httpRequest) {
        try {
            // 인증 시도
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

            Authentication authentication = authenticationManager.authenticate(authToken);

            // 인증 성공 → SecurityContext에 저장 + 세션 생성
            SecurityContextHolder.getContext().setAuthentication(authentication);
            httpRequest.getSession(true); // 세션 생성 (JSESSIONID)

            return ResponseEntity.ok(Map.of("message", "로그인 성공"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "아이디 또는 비밀번호가 올바르지 않습니다."));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserLogin request) {
        try {
            userService.signup(request);
            return ResponseEntity.ok(Map.of("message", "회원가입 성공"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }


}
