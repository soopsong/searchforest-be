package com.searchforest.site.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.searchforest.keyword.domain.SubKeyword;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "text", "weight", "totalCitation", "sublist" })
public class SubKeywordDto {
    private String text;
    private double weight;
    private List<LeafKeywordDto> sublist;
    private Integer totalCitation;

    public static SubKeywordDto from(SubKeyword sub) {
        return SubKeywordDto.builder()
                .text(sub.getText())
                .weight(sub.getWeight())
                .sublist(sub.getSublist().stream()
                        .map(LeafKeywordDto::from)
                        .toList())
                .totalCitation(sub.getTotalCitation())
                .build();
    }
}

