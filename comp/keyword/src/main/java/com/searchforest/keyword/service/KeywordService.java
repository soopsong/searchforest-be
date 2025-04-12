package com.searchforest.keyword.service;


import com.searchforest.keyword.domain.Keyword;
import com.searchforest.keyword.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class KeywordService {


    private final KeywordRepository keywordRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String aiServerUrl = "";

    public Keyword getCachedList(String text) {
        /*
            Repository 확인해서 해당 text 가 있는지 확인
            있다면 text 를 포함한 keyword return
            없다면 null return
         */

        Keyword keyword = keywordRepository.findByText(text).orElse(null);

        if(keyword == null) {
            return null;
        }

        return keyword;
    }

    public Keyword requestToAIServer(String keyword) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // {keyword: ~~~} 형식
        String requestBody = "{ \"keyword\": \"" + keyword + "\", \"mode\": \"text\" }";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Todo
        // AI server 에 keyword 전송해서 response 받아오기
        // response 형식 정해지는 대로 fix.
        // 일단은 String[]으로 받아서 list 로 return
        ResponseEntity<Keyword> responseEntity = restTemplate.postForEntity(
                aiServerUrl,
                requestEntity,
                Keyword.class
        );


        // responseEntity list 로 변환해서 전송
        return responseEntity.getBody();
    }


    //Todo DB에 result list 저장.
    public void save(Keyword keyword) {
        keywordRepository.save(keyword);
    }
}
