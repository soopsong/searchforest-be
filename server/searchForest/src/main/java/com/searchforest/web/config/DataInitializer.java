package com.searchforest.web.config;

import com.searchforest.paper.domain.Paper;
import com.searchforest.paper.repository.PaperRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initMockPapers(PaperRepository paperRepository) {
        return args -> {
            if (paperRepository.count() > 0) {
                return; // 이미 데이터가 있으면 초기화하지 않음
            }

            List<Paper> mockPapers = List.of(
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

            paperRepository.saveAll(mockPapers);
        };
    }
}
