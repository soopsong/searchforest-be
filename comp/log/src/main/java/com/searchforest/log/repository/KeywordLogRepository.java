package com.searchforest.log.repository;

import com.searchforest.log.domain.UserKeywordLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface KeywordLogRepository extends JpaRepository<UserKeywordLog, Long> {


    UserKeywordLog findBySessionId(UUID sessionId);
}
