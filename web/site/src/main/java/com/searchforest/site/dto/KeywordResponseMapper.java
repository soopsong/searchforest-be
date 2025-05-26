package com.searchforest.site.dto;

import com.searchforest.keyword.domain.Keyword;

import java.util.UUID;

public class KeywordResponseMapper {
    public static KeywordResponse from(Keyword keyword, UUID sessionId) {
        return KeywordResponse.builder()
                .sessionId(sessionId)
                .text(keyword.getText())
                .weight(keyword.getWeight())
                .sublist(
                        keyword.getSublist().stream().map(sub -> SubKeywordDto.builder()
                                .text(sub.getText())
                                .weight(sub.getWeight())
                                .sublist(
                                        sub.getSublist().stream().map(leaf ->
                                                LeafKeywordDto.builder()
                                                        .text(leaf.getText())
                                                        .weight(leaf.getWeight())
                                                        .build()
                                        ).toList()
                                )
                                .build()
                        ).toList()
                )
                .build();
    }
}
