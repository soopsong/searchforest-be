package com.searchforest.user.service;


import com.searchforest.user.domain.PaperHistory;
import com.searchforest.user.domain.TextHistory;
import com.searchforest.user.repository.PaperHistoryRepository;
import com.searchforest.user.repository.TextHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaperHistoryService {

    private final PaperHistoryRepository paperHistoryRepository;


    // session 에 있는 message 들을 update 시간에 따라 정렬해 list 로 return
    public List<PaperHistory> getPaperHistory(Long userId) {
        return paperHistoryRepository.findAllByUserIdOrderBySearchedAtDesc(userId);
    }

    public void save(PaperHistory paperHistory) {
        paperHistoryRepository.save(paperHistory);
    }
}
