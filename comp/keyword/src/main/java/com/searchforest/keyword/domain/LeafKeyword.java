package com.searchforest.keyword.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sf_leafKeyword")
public class LeafKeyword {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private String text;
    private double weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subkeyword_id")
    @ToString.Exclude
    @JsonIgnore
    private SubKeyword subKeyword;

    private Integer totalCitation;

    public static LeafKeyword of(String text, double value) {
        return LeafKeyword.builder()
                .text(text)
                .weight(value)
                .build();
    }

}