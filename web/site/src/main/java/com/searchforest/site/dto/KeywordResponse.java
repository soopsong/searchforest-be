package com.searchforest.site.dto;


import com.searchforest.keyword.domain.Keyword;
import com.searchforest.keyword.domain.SubKeyword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class KeywordResponse {
    private UUID sessionId;
    private String text;
    private double weight;
    private List<SubKeywordDto> sublist;

    //test용
    private String currentText;

    public static KeywordResponse from(Keyword keyword, String root, UUID sessionId) {
        return KeywordResponse.builder()
                .sessionId(sessionId)
                .text(keyword.getText())
                .weight(keyword.getWeight())
                .sublist(keyword.getSublist().stream()
                        .map(SubKeywordDto::from)
                        .toList())
                .currentText("root") // 🔧 여기를 추가 (예: 임의 고정 or 따로 파라미터로 받을 수도)
                .build();
    }

}
