package com.searchforest.paper.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.searchforest.paper.domain.Paper;
import com.searchforest.paper.repository.PaperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PaperService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String aiServerUrl = "http://15.165.143.12:8003/papers";
    private final PaperRepository paperRepository;

    public List<Paper> requestToAIServer(String keyword) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("query", keyword);
        body.put("sort_by", "similarity");
        body.put("page", 1);
        body.put("page_size", 20);
        body.put("include_summary", true);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            String url = aiServerUrl + "?query=" + URLEncoder.encode(keyword, StandardCharsets.UTF_8)
                    + "&page=1&page_size=10";

            ResponseEntity<String> response = restTemplate.getForEntity(
                    url,
                    String.class
            );

            String jsonString = response.getBody();
            return parsePapersFromJson(jsonString);

        } catch (Exception e) {
            //return new Keyword();
            return mockDataInjection(keyword);
        }

    }

    public List<Paper> parsePapersFromJson(String jsonString) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(jsonString);
        JsonNode papersNode = root.path("papers");

        List<Paper> papers = new ArrayList<>();

        for (JsonNode paperNode : papersNode) {
            String paperId = paperNode.path("paper_id").asText();
            String title = paperNode.path("title").asText();
            String abstractText = paperNode.path("abstract").asText();
            List<String> authors = new ArrayList<>();
            for (JsonNode authorNode : paperNode.path("authors")) {
                authors.add(authorNode.asText());
            }
            int year = paperNode.path("year").asInt();
            int citationCount = paperNode.path("citation_count").asInt();
            String summary = paperNode.path("summary").asText();
            double simScore = paperNode.path("sim_score").asDouble();

            Paper paper = Paper.builder()
                    .paperId(paperId)
                    .title(title)
                    .abstractText(abstractText)
                    .authors(authors)
                    .year(year)
                    .citationCount(citationCount)
                    .simScore(simScore)
                    .summary(summary)
                    .build();

            // pdfUrl 지정 (항상 null이면 prePersist로 들어가게 두거나 수동으로도 지정 가능)
            try {
                paper.setPdfUrl(new URL("https://www.semanticscholar.org/"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            papers.add(paper);
        }

        return papers;
    }

    public List<Paper> mockDataInjection(String keyword){
        // ✅ Fallback mock data
        return List.of(
                Paper.builder()
                        .paperId("100001")
                        .title("A Survey on Fuzz Testing in CI/CD")
                        .abstractText("Fuzz testing has become an essential technique in modern continuous integration and delivery pipelines.")
                        .authors(List.of("Jane Doe", "John Smith"))
                        .year(2022)
                        .citationCount(42)
                        .simScore(0.9123)
                        .build(),
                Paper.builder()
                        .paperId("100002")
                        .title("Automated Vulnerability Detection via Symbolic Execution")
                        .abstractText("Symbolic execution enables systematic exploration of program paths for bug discovery.")
                        .authors(List.of("Alice Johnson", "Bob Lee"))
                        .year(2021)
                        .citationCount(38)
                        .simScore(0.8812)
                        .build(),
                Paper.builder()
                        .paperId("100003")
                        .title("Advancements in Mutation-Based Fuzzing")
                        .abstractText("This paper explores recent developments in mutation strategies for generating fuzz test inputs.")
                        .authors(List.of("Carol White", "David Green"))
                        .year(2023)
                        .citationCount(17)
                        .simScore(0.8476)
                        .build(),
                Paper.builder()
                        .paperId("100004")
                        .title("Fuzzing Industrial Control Systems")
                        .abstractText("A comprehensive study of fuzz testing techniques applied to critical infrastructure and control systems.")
                        .authors(List.of("Ethan Black", "Fiona Blue"))
                        .year(2020)
                        .citationCount(26)
                        .simScore(0.8321)
                        .build(),
                Paper.builder()
                        .paperId("100005")
                        .title("Fuzz Testing for Web Applications: Challenges and Trends")
                        .abstractText("We review the state of fuzzing for modern web platforms and present ongoing challenges.")
                        .authors(List.of("George Kim", "Hannah Park"))
                        .year(2022)
                        .citationCount(31)
                        .simScore(0.8195)
                        .build()
        );
    }


//    public List<Paper> requestToAIServer(String keyword) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        String requestBody = "{ \"keyword\": \"" + keyword + "\", \"mode\": \"paper\" }";
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
//        String aiServerUrl = "http://your-ai-server-url.com/search";
//
//        try {
//            ResponseEntity<List<Paper>> responseEntity = restTemplate.exchange(
//                    aiServerUrl,
//                    HttpMethod.POST,
//                    requestEntity,
//                    new ParameterizedTypeReference<List<Paper>>() {}
//            );
//
//            if (responseEntity.getBody() != null) {
//                paperRepository.saveAll(responseEntity.getBody());
//            }
//
//            return responseEntity.getBody();
//        } catch (Exception e) {
//            return mockDataInjection(keyword);}
//    }
//
    public Paper findByPaperId(String paperId) {
        return paperRepository.findByPaperId(paperId)
                .orElse(null);
    }

}
