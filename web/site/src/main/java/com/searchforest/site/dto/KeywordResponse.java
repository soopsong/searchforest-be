package com.searchforest.site.dto;


import com.searchforest.keyword.domain.Keyword;
import com.searchforest.keyword.domain.SubKeyword;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeywordResponse {
    private String text;
    private double weight;
    private List<SubKeywordDto> sublist;
    private UUID sessionId;

    public static KeywordResponse from(Keyword keyword, UUID sessionId) {
        return KeywordResponse.builder()
                .sessionId(sessionId)
                .text(keyword.getText())
                .weight(keyword.getWeight())
                .sublist(keyword.getSublist().stream()
                        .map(SubKeywordDto::from)
                        .toList())
                .build();
    }
}
