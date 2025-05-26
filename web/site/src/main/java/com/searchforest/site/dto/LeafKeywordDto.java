package com.searchforest.site.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LeafKeywordDto {
    private String text;
    private double weight;
}