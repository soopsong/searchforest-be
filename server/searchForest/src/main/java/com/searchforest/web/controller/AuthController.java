package com.searchforest.web.controller;

import com.searchforest.user.domain.User;
import com.searchforest.user.domain.UserLogin;
import com.searchforest.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

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

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        // 세션이 있으면 무효화
        var session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // SecurityContext 초기화
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(Map.of("message", "로그아웃 성공"));
    }

    @PostMapping("/user")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "로그인이 필요합니다."));
        }

        // 사용자 이메일로 사용자 정보 조회
        User user = userService.findByEmail(userDetails.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "사용자 정보를 찾을 수 없습니다."));
        }

        return ResponseEntity.ok(Map.of(
                "username", user.getUsername(),
                "email", user.getEmail()
                // 필요 시 다른 정보도 추가 가능
        ));
    }
}
