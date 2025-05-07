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

import java.util.*;

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

    public Keyword requestToAIServer(List<String> messages) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // JSON 생성
        Map<String, Object> body = new HashMap<>();
        body.put("messages", messages);
        body.put("mode", "text");

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Keyword> responseEntity = restTemplate.postForEntity(
                aiServerUrl,
                requestEntity,
                Keyword.class
        );

        return responseEntity.getBody();
    }


    //Todo DB에 result list 저장.
    public void save(Keyword keyword) {
        keywordRepository.save(keyword);
    }
}
