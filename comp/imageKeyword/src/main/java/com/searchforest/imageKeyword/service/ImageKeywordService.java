package com.searchforest.imageKeyword.service;

import com.searchforest.imageKeyword.domain.ImageKeyword;
import com.searchforest.imageKeyword.repository.ImageKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageKeywordService {

    private final ImageKeywordRepository imageKeywordRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String aiServerUrl = "";

    public ImageKeyword getCachedList(String keyword) {
        ImageKeyword imagekeyword = imageKeywordRepository.findByText(keyword).orElse(null);

        return imagekeyword;
    }

    public ImageKeyword save(ImageKeyword aiResults) {

        return imageKeywordRepository.save(aiResults);
    }

    public ImageKeyword requestToAIServer(String keyword) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // {keyword: ~~~} 형식
        String requestBody = "{ \"keyword\": \"" + keyword + "\", \"mode\": \"image\" }";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Todo
        // AI server 에 keyword 전송해서 response 받아오기
        // response 형식 정해지는 대로 fix.
        // 일단은 String[]으로 받아서 list 로 return
        ResponseEntity<ImageKeyword> responseEntity = restTemplate.postForEntity(
                aiServerUrl,
                requestEntity,
                ImageKeyword.class
        );


        // responseEntity list 로 변환해서 전송
        return responseEntity.getBody();
    }


}
