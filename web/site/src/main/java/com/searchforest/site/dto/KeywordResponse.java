package com.searchforest.site.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.searchforest.keyword.domain.Keyword;
import com.searchforest.keyword.domain.SubKeyword;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "text", "weight", "sessionId", "totalCitation", "sublist" })
public class KeywordResponse {
    private String text;
    private double weight;
    private List<SubKeywordDto> sublist;
    private UUID sessionId;

    private Integer totalCitation;

    public static KeywordResponse from(Keyword keyword, UUID sessionId) {
        return KeywordResponse.builder()
                .sessionId(sessionId)
                .text(keyword.getText())
                .weight(keyword.getWeight())
                .sublist(keyword.getSublist().stream()
                        .map(SubKeywordDto::from)
                        .toList())
                .totalCitation(keyword.getTotalCitation())
                .build();
    }
}
