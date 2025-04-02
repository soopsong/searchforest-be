package com.searchforest.keyword.domain;

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
    private Long id;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    @ToString.Exclude
    private Keyword keyword;

    @ElementCollection
    @CollectionTable(name = "subkeyword_sublist", joinColumns = @JoinColumn(name = "subkeyword_id"))
    @Column(name = "sublist_item")
    private List<String> sublist;
}
