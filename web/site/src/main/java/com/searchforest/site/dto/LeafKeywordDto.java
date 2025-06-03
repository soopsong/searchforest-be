package com.searchforest.site.dto;

import com.searchforest.keyword.domain.LeafKeyword;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeafKeywordDto {
    private String text;
    private double weight;

    public static LeafKeywordDto from(LeafKeyword leaf) {
        return LeafKeywordDto.builder()
                .text(leaf.getText())
                .weight(leaf.getWeight())
                .build();
    }
}