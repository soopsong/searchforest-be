package com.searchforest.keyword.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sf_subKeyword")
public class SubKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String text;
    private double weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    @ToString.Exclude
    @JsonIgnore
    private Keyword keyword;

    private Integer totalCitation;

    @OneToMany(mappedBy = "subKeyword", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LeafKeyword> sublist;
}
