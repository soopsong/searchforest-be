package com.searchforest.keyword.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.net.URL;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sf_keyword")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private URL url;
    // Todo
    // depth 관련 설정을 어떻게 할건지?
    // String list 가 아니라 subKeyword list 로 하는게 나으려나?
    // subKeyword 는 String list 를 갖는 거지.

    private List<String> sublist;
}
