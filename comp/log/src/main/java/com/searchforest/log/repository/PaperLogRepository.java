package com.searchforest.log.repository;

import com.searchforest.log.domain.UserPaperLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaperLogRepository extends JpaRepository<UserPaperLog, Long> {

    UserPaperLog findByUserId(Long id);
}
