package com.searchforest.keyword.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.searchforest.keyword.domain.Keyword;
import com.searchforest.keyword.domain.LeafKeyword;
import com.searchforest.keyword.domain.SubKeyword;

import java.util.ArrayList;
import java.util.List;
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

    public static Keyword toKeyword(JsonNode node) {
        return Keyword.builder()
                .text(node.get("id").asText())
                .weight(node.get("value").asDouble())
                .sublist(
                        StreamSupport.stream(node.get("children").spliterator(), false)
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

    public static List<Keyword> fromGraphJsonToList(JsonNode keywordTree) {
        JsonNode children = keywordTree.get("children");

        if (children == null || !children.isArray()) {
            throw new IllegalArgumentException("keyword_tree 내부에 children 배열이 없음");
        }

        return StreamSupport.stream(children.spliterator(), false)
                .map(GraphResponseMapper::toKeyword)
                .toList();
    }

    public static List<Keyword> splitRootIntoKeywords(JsonNode rootNode) {
        String rootText = rootNode.get("id").asText();
        double rootWeight = rootNode.get("value").asDouble();

        JsonNode children = rootNode.get("children");
        if (children == null || !children.isArray()) {
            throw new IllegalArgumentException("children이 배열이 아닙니다.");
        }

        int batchSize = 5;
        int total = children.size();
        int numberOfBatches = (int) Math.ceil((double) total / batchSize);

        List<Keyword> keywords = new ArrayList<>();

        for (int i = 0; i < numberOfBatches; i++) {
            int from = i * batchSize;
            int to = Math.min(from + batchSize, total);

            List<SubKeyword> subKeywords = new ArrayList<>();

            for (int j = from; j < to; j++) {
                JsonNode subNode = children.get(j);
                SubKeyword subKeyword = toSubKeyword(subNode);
                subKeywords.add(subKeyword);
            }

            Keyword keyword = Keyword.builder()
                    .text(rootText)   // 동일한 루트 text 사용
                    .weight(rootWeight)
                    .sublist(subKeywords)
                    .build();

            // 연관관계 설정
            for (SubKeyword sub : subKeywords) {
                sub.setKeyword(keyword);
                for (LeafKeyword leaf : sub.getSublist()) {
                    leaf.setSubKeyword(sub);
                }
            }

            keywords.add(keyword);
        }

        return keywords;
    }

}