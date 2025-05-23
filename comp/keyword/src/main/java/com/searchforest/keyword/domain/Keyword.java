package com.searchforest.keyword.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.net.URL;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sf_keyword")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String text;
    private int weight;

    // Todo
    // depth 관련 설정을 어떻게 할건지?
    // String list 가 아니라 subKeyword list 로 하는게 나으려나?
    // subKeyword 는 String list 를 갖는 거지.

    @OneToMany(mappedBy = "keyword", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubKeyword> sublist;
}
