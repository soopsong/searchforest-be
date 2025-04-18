package com.searchforest.paper.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.searchforest.paper.domain.Paper;
import com.searchforest.paper.repository.PaperRepository;
import lombok.RequiredArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.Year;
import java.util.*;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PaperService {


    private final PaperRepository paperRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public Paper getCachedList(String title) {
        return paperRepository.findByTitle(title).orElse(null);
    }

    public void save(Paper aiResults) {
        paperRepository.save(aiResults);
    }

    public Paper requestToAIServer(String keyword) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // {keyword: ~~~} 형식
        String requestBody = "{ \"keyword\": \"" + keyword + "\", \"mode\": \"paper\" }";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Todo
        // AI server 에 keyword 전송해서 response 받아오기
        // response 형식 정해지는 대로 fix.
        String aiServerUrl = "";

        ResponseEntity<Paper> responseEntity = restTemplate.postForEntity(
                aiServerUrl,
                requestEntity,
                Paper.class
        );


        // responseEntity list 로 변환해서 전송
        return responseEntity.getBody();
    }

/// ///////////////////////////////////////////////////////////////////////////////////////////////////
//    private final PaperRepository paperRepository;
//    private final RestTemplate restTemplate = new RestTemplate();
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    private final List<String> TOPICS = Arrays.asList(
//            "cat:cs.AI", "cat:cs.CL", "cat:cs.LG", "cat:cs.CV", "cat:cs.NE", "cat:cs.IR", "cat:cs.SE", "cat:cs.DB",
//            "cat:stat.ML", "cat:stat.AP", "cat:stat.CO", "cat:math.OC", "cat:math.ST", "cat:math.PR",
//            "cat:eess.AS", "cat:eess.IV", "cat:q-bio.NC", "cat:q-fin.ST", "cat:econ.EM" , "All"
//    );
//
//    public int fetchAllByTopicsAndYears(int startYear, int endYear, int maxResults, int threadCount) throws InterruptedException {
//        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
//        Set<String> existingIds = ConcurrentHashMap.newKeySet();
//        existingIds.addAll(paperRepository.findAllArxivIds());
//
//        List<Callable<Integer>> tasks = new ArrayList<>();
//
//        for (String topic : TOPICS) {
//            for (int year = startYear; year <= endYear; year++) {
//                final int targetYear = year;
//                final String topicQuery = topic;
//                tasks.add(() -> fetchAndSaveByQuery(topicQuery, targetYear, maxResults, existingIds));
//            }
//        }
//
//        int totalSaved = 0;
//        List<Future<Integer>> results = executor.invokeAll(tasks);
//        for (Future<Integer> result : results) {
//            try {
//                totalSaved += result.get();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//
//        executor.shutdown();
//        return totalSaved;
//    }
//
//    private int fetchAndSaveByQuery(String category, int year, int maxResults, Set<String> existingIds) {
//        int savedCount = 0;
//        int start = 0;
//        int pageSize = 300;
//        while (start < maxResults) {
//            try {
//                String dateRange = year + "01010000 TO " + year + "12312359";
//                String apiUrl = "http://export.arxiv.org/api/query?search_query=" + category + "+AND+submittedDate:[" + dateRange + "]&start=" + start + "&max_results=" + pageSize;
//
//                HttpHeaders headers = new HttpHeaders();
//                headers.set("User-Agent", "SearchForestBot/1.0 (mailto:mh991221@gmail.com)");
//                HttpEntity<String> entity = new HttpEntity<>(headers);
//
//                ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
//
//
//                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//                DocumentBuilder builder = factory.newDocumentBuilder();
//                Document doc = builder.parse(new InputSource(new StringReader(response.getBody())));
//
//                NodeList entries = doc.getElementsByTagName("entry");
//                if (entries.getLength() == 0) break;
//
//                List<Paper> papersToSave = new ArrayList<>();
//                for (int i = 0; i < entries.getLength(); i++) {
//                    Element entry = (Element) entries.item(i);
//
//                    String idUrl = entry.getElementsByTagName("id").item(0).getTextContent();
//                    String arxivId = idUrl.substring(idUrl.lastIndexOf("/") + 1).replaceAll("v\\d+$", "");
//
//                    if (!existingIds.add(arxivId)) continue;
//
//                    // Semantic Scholar 조회 부분 주석 처리
//                    /*
//                    String s2ApiUrl = "https://api.semanticscholar.org/graph/v1/paper/arXiv:" + arxivId + "?fields=paperId";
//                    try {
//                        ResponseEntity<String> s2Response = restTemplate.getForEntity(s2ApiUrl, String.class);
//                        JsonNode root = objectMapper.readTree(s2Response.getBody());
//                        if (!root.has("paperId")) continue;
//                    } catch (Exception e) {
//                        continue;
//                    }
//                    */
//
//                    String title = entry.getElementsByTagName("title").item(0).getTextContent().trim();
//                    String summary = entry.getElementsByTagName("summary").item(0).getTextContent().trim();
//                    String published = entry.getElementsByTagName("published").item(0).getTextContent();
//
//                    List<String> authors = new ArrayList<>();
//                    NodeList authorNodes = entry.getElementsByTagName("author");
//                    for (int j = 0; j < authorNodes.getLength(); j++) {
//                        authors.add(((Element) authorNodes.item(j)).getElementsByTagName("name").item(0).getTextContent());
//                    }
//
//                    List<String> categories = new ArrayList<>();
//                    NodeList categoryNodes = entry.getElementsByTagName("category");
//                    for (int j = 0; j < categoryNodes.getLength(); j++) {
//                        categories.add(((Element) categoryNodes.item(j)).getAttribute("term"));
//                    }
//
//                    String pdfUrl = null;
//                    NodeList links = entry.getElementsByTagName("link");
//                    for (int j = 0; j < links.getLength(); j++) {
//                        Element link = (Element) links.item(j);
//                        if ("application/pdf".equals(link.getAttribute("type"))) {
//                            pdfUrl = link.getAttribute("href");
//                            break;
//                        }
//                    }
//
//                    Paper paper = Paper.builder()
//                            .arxivId(arxivId)
//                            .title(title)
//                            .abstractText(summary)
//                            .published(published)
//                            .authors(authors)
//                            .categories(categories)
//                            .pdfUrl(pdfUrl)
//                            .isOpenAccess(null)
//                            .build();
//
//                    papersToSave.add(paper);
//                }
//
//                if (!papersToSave.isEmpty()) {
//                    paperRepository.saveAll(papersToSave);
//                    savedCount += papersToSave.size();
//                }
//
//                start += pageSize;
//
//                if (entries.getLength() < pageSize) break; // 마지막 페이지
//            } catch (Exception e) {
//                System.err.println("❌ Error in fetchAndSaveByQuery(" + category + ", " + year + "): " + e.getMessage());
//                break;
//            }
//        }
//        return savedCount;
//    }
//
//
//    public int enrichExistingPapersWithSemanticScholar() throws InterruptedException {
//        List<Paper> papers = paperRepository.findAll();
//        ExecutorService executor = Executors.newFixedThreadPool(10);
//
//        List<Callable<Paper>> tasks = new ArrayList<>();
//
//        for (Paper paper : papers) {
//            tasks.add(() -> {
//                try {
//                    String s2ApiUrl = "https://api.semanticscholar.org/graph/v1/paper/arXiv:" + paper.getArxivId() +
//                            "?fields=paperId,title,abstract,citationCount,isOpenAccess,publicationDate,publicationTypes,openAccessPdf,authors,fieldsOfStudy,referenceIds";
//                    ResponseEntity<String> response = restTemplate.getForEntity(s2ApiUrl, String.class);
//                    JsonNode root = objectMapper.readTree(response.getBody());
//
//                    if (root.has("paperId")) {
//                        paper.setSemanticScholarId(root.path("paperId").asText());
//                        paper.setCitationCount(root.path("citationCount").asInt());
//                        paper.setIsOpenAccess(root.path("isOpenAccess").asBoolean());
//                        paper.setOpenAccessPdfUrl(root.path("openAccessPdf").path("url").asText(null));
//                        paper.setPublicationDate(root.path("publicationDate").asText(null));
//                        paper.setPublicationTypes(root.path("publicationTypes").isArray() ? root.path("publicationTypes").get(0).asText(null) : null);
//
//                        List<String> authors = new ArrayList<>();
//                        root.path("authors").forEach(a -> authors.add(a.path("name").asText()));
//                        paper.setAuthors(authors);
//
//                        List<String> fields = new ArrayList<>();
//                        root.path("fieldsOfStudy").forEach(f -> fields.add(f.asText()));
//                        paper.setS2FieldsOfStudy(fields);
//
//                        List<String> refs = new ArrayList<>();
//                        root.path("referenceIds").forEach(r -> refs.add(r.asText()));
//                        paper.setReferenceIds(refs);
//
//                        return paper;
//                    } else {
//                        return null; // 없으면 삭제 대상으로
//                    }
//                } catch (Exception e) {
//                    System.err.println("❌ Semantic Scholar enrich 실패: " + paper.getArxivId());
//                    return null;
//                }
//            });
//        }
//
//        List<Future<Paper>> futures = executor.invokeAll(tasks);
//        List<Paper> toUpdate = new ArrayList<>();
//        List<Paper> toDelete = new ArrayList<>();
//
//        for (int i = 0; i < futures.size(); i++) {
//            try {
//                Paper result = futures.get(i).get();
//                if (result != null) {
//                    toUpdate.add(result);
//                } else {
//                    toDelete.add(papers.get(i));
//                }
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//
//        executor.shutdown();
//        if (!toUpdate.isEmpty()) paperRepository.saveAll(toUpdate);
//        if (!toDelete.isEmpty()) paperRepository.deleteAll(toDelete);
//
//        return toUpdate.size();
//    }
//    public List<Paper> getAllPapers() {
//        return paperRepository.findAll();
//    }

}
