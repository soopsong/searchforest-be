package com.searchforest.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/login")
    public String oAuth2Login(){
        return "OAuth2Login";
    }


}
