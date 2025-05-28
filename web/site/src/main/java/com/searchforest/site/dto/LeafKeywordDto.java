package com.searchforest.site.dto;

import com.searchforest.keyword.domain.LeafKeyword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LeafKeywordDto {
    private String text;
    private double weight;

    public static LeafKeywordDto from(LeafKeyword leafKeyword) {
        return LeafKeywordDto.builder()
                .text(leafKeyword.getText())
                .weight(leafKeyword.getWeight())
                .build();
    }
}