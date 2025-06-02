package com.searchforest.paper.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.net.MalformedURLException;
import java.net.URL;
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
    @Column(name = "paper_id", nullable = false, unique = true)
    private String paperId; // 논문 식별의 기준 (arXiv 기준)

    private String title;

    @Lob
    @Column(name = "abstract_text", columnDefinition = "TEXT")
    private String abstractText;

    @Lob
    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;


    //private String published;

//    @ElementCollection
//    private List<String> categories;


    private URL pdfUrl;

    @PrePersist
    public void prePersist() {
        // pdfUrl 이 없는 경우 semantic scholar url로 설정

        if (pdfUrl == null) {
            try {
                pdfUrl = new URL("https://www.semanticscholar.org/");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    // Semantic Scholar에서 보강되는 데이터들
    private Integer citationCount;

    @ElementCollection
    @CollectionTable(name = "paper_authors", joinColumns = @JoinColumn(name = "paper_id"))
    @Column(name = "author")
    private List<String> authors;

    private int year;

    private double simScore;

//    @ElementCollection
//    private List<String> referenceIds;

//    @ElementCollection
//    private List<String> s2FieldsOfStudy;


//    private Boolean isOpenAccess;
//
//    private String openAccessPdfUrl;
//
//    private String publicationTypes;
//
//    private String publicationDate;
//
//    private String semanticScholarId; // paperId (선택)
}
