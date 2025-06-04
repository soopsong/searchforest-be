package com.searchforest.log.service;


import com.searchforest.log.domain.UserKeywordLog;
import com.searchforest.log.domain.UserPaperLog;
import com.searchforest.log.repository.KeywordLogRepository;
import com.searchforest.log.repository.PaperLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LogService {

    private final KeywordLogRepository keywordLogRepository;
    private final PaperLogRepository paperLogRepository;

    public void save(UserKeywordLog userLog) {
        keywordLogRepository.save(userLog);
    }

    public void save(UserPaperLog userLog) {
        paperLogRepository.save(userLog);
    }

    public UserKeywordLog findBySessionId(UUID sessionId){
        return keywordLogRepository.findBySessionId(sessionId);
    }

    public UserPaperLog findUserPaperByUserId(Long id) {

        return paperLogRepository.findByUserId(id);
    }
}
