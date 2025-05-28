package com.searchforest.site.dto;

import com.searchforest.keyword.domain.SubKeyword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SubKeywordDto {
    private String text;
    private double weight;
    private List<LeafKeywordDto> sublist;

    public static SubKeywordDto from(SubKeyword subKeyword) {
        return new SubKeywordDto(
                subKeyword.getText(),
                subKeyword.getWeight(),
                subKeyword.getSublist().stream()
                        .map(LeafKeywordDto::from)
                        .toList()
        );
    }

}

