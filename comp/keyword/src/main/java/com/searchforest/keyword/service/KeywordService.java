package com.searchforest.keyword.service;


import com.searchforest.keyword.domain.Keyword;
import com.searchforest.keyword.domain.LeafKeyword;
import com.searchforest.keyword.domain.SubKeyword;
import com.searchforest.keyword.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeywordService {


    private final KeywordRepository keywordRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String aiServerUrl = "";


    public Keyword requestToAIServer(List<String> messages) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("messages", messages);
        body.put("mode", "keyword");

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Keyword> responseEntity = restTemplate.postForEntity(
                    aiServerUrl,
                    requestEntity,
                    Keyword.class
            );
            return responseEntity.getBody();
        } catch (Exception e) {
            // ✅ mock 데이터 fallback
            return Keyword.builder()
                    .text("language model")
                    .weight(0.1)
                    .sublist(List.of(
                            SubKeyword.builder()
                                    .text("transformer")
                                    .weight(0.8)
                                    .sublist(List.of(
                                            LeafKeyword.of("self-attention", 0.6),
                                            LeafKeyword.of("multi-head attention", 0.5),
                                            LeafKeyword.of("positional encoding", 0.4)
                                    ))
                                    .build(),
                            SubKeyword.builder()
                                    .text("pretrained model")
                                    .weight(0.9)
                                    .sublist(List.of(
                                            LeafKeyword.of("bert", 0.7),
                                            LeafKeyword.of("gpt", 0.6),
                                            LeafKeyword.of("electra", 0.4)
                                    ))
                                    .build(),
                            SubKeyword.builder()
                                    .text("masked language modeling")
                                    .weight(0.85)
                                    .sublist(List.of(
                                            LeafKeyword.of("token masking", 0.6),
                                            LeafKeyword.of("mlm objective", 0.5),
                                            LeafKeyword.of("context prediction", 0.5)
                                    ))
                                    .build(),
                            SubKeyword.builder()
                                    .text("fine-tuning")
                                    .weight(0.85)
                                    .sublist(List.of(
                                            LeafKeyword.of("task adaptation", 0.6),
                                            LeafKeyword.of("domain transfer", 0.5),
                                            LeafKeyword.of("parameter-efficient tuning", 0.5)
                                    ))
                                    .build(),
                            SubKeyword.builder()
                                    .text("causal language modeling")
                                    .weight(0.85)
                                    .sublist(List.of(
                                            LeafKeyword.of("autoregressive model", 0.6),
                                            LeafKeyword.of("next token prediction", 0.5),
                                            LeafKeyword.of("gpt decoder", 0.7)
                                    ))
                                    .build()
                    ))
                    .build();
        }
    }

    //Todo DB에 result list 저장.
    public void save(Keyword keyword) {
        keywordRepository.save(keyword);
    }
}
