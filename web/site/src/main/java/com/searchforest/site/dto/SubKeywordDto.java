package com.searchforest.site.dto;

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
}

