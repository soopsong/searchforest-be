package com.searchforest.site.dto;


import com.searchforest.keyword.domain.SubKeyword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class KeywordResponse {

    private UUID sessionId;
    private String text;
    private int weight;
    private List<SubKeyword> sublist;
}
