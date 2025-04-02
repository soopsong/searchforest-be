package com.searchforest.web.controller;


import com.searchforest.imageKeyword.service.ImageKeywordService;
import com.searchforest.keyword.domain.Keyword;
import com.searchforest.keyword.service.KeywordService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final KeywordService keywordService;
    private final ImageKeywordService imageKeywordService;

    @GetMapping("/")
    public String home(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/user";
        }
        return "redirect:/guest"; // 또는 그냥 public 홈 페이지
    }

}
