package com.searchforest.user.service;


import com.searchforest.user.domain.TextHistory;
import com.searchforest.user.repository.TextHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TextHistoryService {

    private final TextHistoryRepository textHistoryRepository;


    // session 에 있는 message 들을 update 시간에 따라 정렬해 list 로 return
    public List<String> getTextHistory(UUID sessionId) {
        TextHistory textHistory = textHistoryRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new NoSuchElementException("해당 세션의 메시지를 찾을 수 없습니다: " + sessionId));

        List<String> result = new ArrayList<>();
        result.add(textHistory.getRootContent());  // rootContent는 0번 index

        if (textHistory.getSubContent() != null) {
            result.addAll(textHistory.getSubContent());  // subContent는 뒤에 붙임
        }

        return result;
    }

    public void save(TextHistory textHistory) {
        textHistoryRepository.save(textHistory);
    }
}
