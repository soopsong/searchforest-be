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

    public List<String> getCachedList(String text) {
        /*
            Repository 확인해서 해당 text 가 있는지 확인
            있다면 text 하위의 list return
            없다면 null return
         */
        List<String> list = new ArrayList<>();
        Keyword keyword = keywordRepository.findByText(text).orElse(null);

        if(keyword == null) {
            return null;
        }

//        list = keyword.getSublist().isEmpty() ? null : keyword.getSublist();

        return list;
    }

    public List<String> requestToAIServer(String keyword) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // {keyword: ~~~} 형식
        String requestBody = "{ \"keyword\": \"" + keyword + "\" }";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        //Todo
        // AI server 에 keyword 전송해서 response 받아오기
        // response 형식 정해지는 대로 fix.
        // 일단은 String[]으로 받아서 list 로 return
        ResponseEntity<String[]> responseEntity = restTemplate.postForEntity(
                aiServerUrl,
                requestEntity,
                String[].class
        );


        // responseEntity list 로 변환해서 전송
        return Arrays.asList(Objects.requireNonNull(responseEntity.getBody()));
    }


    //Todo DB에 result list 저장.
    public void saveResult(String text, List<String> list) {

//        Keyword keyword = Keyword.builder()
//                        .text(text)
//                        .sublist(list)
//                        .build();

//        keywordRepository.save(keyword);
    }
}
