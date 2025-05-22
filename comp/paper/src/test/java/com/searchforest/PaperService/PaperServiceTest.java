//package com.searchforest.PaperService;
//
//import com.searchforest.paper.domain.Paper;
//import com.searchforest.paper.repository.PaperRepository;
//import com.searchforest.paper.service.PaperService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Import;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.*;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.*;
//
//@SpringBootTest
//@Import(PaperServiceTest.MockRestTemplateConfig.class)
//public class PaperServiceTest {
//
//    @Autowired
//    private PaperService paperService;
//
//    @Autowired
//    private PaperRepository paperRepository;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @TestConfiguration
//    static class MockRestTemplateConfig {
//        @Bean
//        public RestTemplate restTemplate() {
//            return Mockito.mock(RestTemplate.class);
//        }
//    }
//
//    private Paper paper1;
//    private Paper paper2;
//    private List<Paper> mockPapers;
//
//    @BeforeEach
//    void init() {
//        paperRepository.deleteAll();
//
//        paper1 = Paper.builder()
//                .arxivId("1234.5678")
//                .title("Graph Neural Network Paper")
//                .build();
//
//        paper2 = Paper.builder()
//                .arxivId("2345.6789")
//                .title("Advanced GNN Techniques")
//                .build();
//
//        mockPapers = List.of(paper1, paper2);
//    }
//
//    @Test
//    @DisplayName("AI 서버 응답을 잘 받아오는지 테스트")
//    void testRequestToAIServer() {
//        ResponseEntity<List<Paper>> mockResponse = new ResponseEntity<>(mockPapers, HttpStatus.OK);
//
//        Mockito.when(restTemplate.exchange(
//                anyString(),
//                eq(HttpMethod.POST),
//                any(HttpEntity.class),
//                any(ParameterizedTypeReference.class)
//        )).thenReturn(mockResponse);
//
//        List<Paper> result = paperService.requestToAIServer("gnn");
//
//        assertThat(result).hasSize(2);
//        assertThat(result.get(0).getArxivId()).isEqualTo("1234.5678");
//    }
//
//    @Test
//    @DisplayName("논문 리스트를 저장하고 DB에서 조회되는지 테스트")
//    void testSaveAndFindAll() {
////        paperService.save(mockPapers);
////
////        List<Paper> saved = paperRepository.findAll();
////
////        assertThat(saved).hasSize(2);
////        assertThat(saved).extracting(Paper::getArxivId)
////                .containsExactlyInAnyOrder("1234.5678", "2345.6789");
//    }
//}
