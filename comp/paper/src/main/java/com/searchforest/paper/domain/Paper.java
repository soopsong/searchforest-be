package com.searchforest.paper.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sf_paper")
public class Paper {

    @Id
    private String arxivId; // 논문 식별의 기준 (arXiv 기준)

    private String title;

    @Lob
    @Column(name = "abstract_text", columnDefinition = "TEXT")
    private String abstractText;


    private String published;

    @ElementCollection
    private List<String> categories;

    private String pdfUrl;


    // Semantic Scholar에서 보강되는 데이터들
    private Integer citationCount;

    @ElementCollection
    private List<String> referenceIds;

    @ElementCollection
    private List<String> s2FieldsOfStudy;

    @ElementCollection
    private List<String> authors;

    private Boolean isOpenAccess;

    private String openAccessPdfUrl;

    private String publicationTypes;

    private String publicationDate;

    private String semanticScholarId; // paperId (선택)
}
