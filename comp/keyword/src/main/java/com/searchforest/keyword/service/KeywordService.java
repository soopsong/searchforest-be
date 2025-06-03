package com.searchforest.keyword.service;


import com.fasterxml.jackson.databind.JsonNode;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String aiServerUrl = "http://52.78.34.56:8002/graph";
    private final String aiServerUrlWhenClickNode = "http://52.78.34.56:8004/summarize";

    public List<Keyword> requestToAIServer(String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("root", text);
        body.put("top1", 10); // 10개 요청
        body.put("top2", 3);  // 각 subKeyword의 하위 키워드 3개

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<JsonNode> response = restTemplate.postForEntity(
                    aiServerUrl,
                    requestEntity,
                    JsonNode.class
            );

            JsonNode root = response.getBody().get("keyword_tree");
            if (root == null || !root.has("children")) {
                throw new IllegalArgumentException("AI 서버 응답에 'keyword_tree' 또는 'children'이 없습니다.");
            }

            return GraphResponseMapper.splitRootIntoKeywords(root);

        } catch (Exception e) {
            e.printStackTrace(); // 로그 기록 (실 서비스에서는 로그로 처리 권장)
            return Collections.emptyList(); // fallback: 빈 리스트 반환
        }
    }


    //Todo node 클릭시, AI 서버에 요청하는 method. 현재는 root node만 요청함. list로 요청 가능하도록 수정 필요.
    public Keyword requestToAIServerWhenClickNode(List<String> messages) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("root", messages.get(messages.size()-1));
        body.put("top1", 5);
        body.put("top2", 3);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<JsonNode> response = restTemplate.postForEntity(
                    aiServerUrl,
                    requestEntity,
                    JsonNode.class
            );

            JsonNode root = response.getBody().get("keyword_tree");

            return GraphResponseMapper.fromGraphJson(root);

//          return responseEntity.getBody();
        } catch (Exception e) {
            //return new Keyword();
            return mockDataInjection(messages);
        }
    }




    //Todo DB에 result list 저장.
    public void save(Keyword keyword) {
        keywordRepository.save(keyword);
    }


    public Keyword mockDataInjection(List<String> messages){
        if (messages.isEmpty()) {
                // ✅ fallback mock #2
                return Keyword.builder()
                        .text("self-attention")
                        .weight(1.0)
                        .sublist(List.of(
                                SubKeyword.builder()
                                        .text("multi-head attention")
                                        .weight(0.8)
                                        .sublist(List.of(
                                                LeafKeyword.of("projection layers", 0.5),
                                                LeafKeyword.of("head concatenation", 0.4),
                                                LeafKeyword.of("parallel attention heads", 0.6)
                                        ))
                                        .build(),
                                SubKeyword.builder()
                                        .text("scaled dot-product attention")
                                        .weight(0.8)
                                        .sublist(List.of(
                                                LeafKeyword.of("query-key dot product", 0.6),
                                                LeafKeyword.of("scaling factor", 0.5),
                                                LeafKeyword.of("softmax weighting", 0.4)
                                        ))
                                        .build(),
                                SubKeyword.builder()
                                        .text("positional encoding")
                                        .weight(0.8)
                                        .sublist(List.of(
                                                LeafKeyword.of("sine cosine embedding", 0.6),
                                                LeafKeyword.of("sequence order injection", 0.5),
                                                LeafKeyword.of("absolute position bias", 0.4)
                                        ))
                                        .build(),
                                SubKeyword.builder()
                                        .text("attention mechanism")
                                        .weight(0.8)
                                        .sublist(List.of(
                                                LeafKeyword.of("alignment score", 0.6),
                                                LeafKeyword.of("attention weights", 0.5),
                                                LeafKeyword.of("context vector", 0.4)
                                        ))
                                        .build(),
                                SubKeyword.builder()
                                        .text("transformer architecture")
                                        .weight(0.8)
                                        .sublist(List.of(
                                                LeafKeyword.of("encoder-decoder", 0.6),
                                                LeafKeyword.of("layer normalization", 0.5),
                                                LeafKeyword.of("residual connection", 0.4)
                                        ))
                                        .build()
                        ))
                        .build();
            }

            // ✅ fallback mock #1 (기존)
            return Keyword.builder()
                    .text("language model")
                    .weight(1)
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
