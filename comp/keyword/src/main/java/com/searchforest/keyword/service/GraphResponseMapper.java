package com.searchforest.keyword.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.searchforest.keyword.domain.Keyword;
import com.searchforest.keyword.domain.LeafKeyword;
import com.searchforest.keyword.domain.SubKeyword;

import java.util.stream.StreamSupport;

public class GraphResponseMapper {

    public static Keyword fromGraphJson(JsonNode keywordTree) {
        return Keyword.builder()
                .text(keywordTree.get("id").asText())
                .weight(keywordTree.get("value").asDouble())
                .sublist(
                        StreamSupport.stream(keywordTree.get("children").spliterator(), false)
                                .map(GraphResponseMapper::toSubKeyword)
                                .toList()
                )
                .build();
    }

    private static SubKeyword toSubKeyword(JsonNode node) {
        return SubKeyword.builder()
                .text(node.get("id").asText())
                .weight(node.get("value").asDouble())
                .sublist(
                        StreamSupport.stream(node.get("children").spliterator(), false)
                                .map(GraphResponseMapper::toLeafKeyword)
                                .toList()
                )
                .build();
    }

    private static LeafKeyword toLeafKeyword(JsonNode node) {
        return LeafKeyword.of(
                node.get("id").asText(),
                node.get("value").asDouble()
        );
    }
}