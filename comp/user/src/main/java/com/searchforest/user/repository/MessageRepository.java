package com.searchforest.user.repository;

import com.searchforest.user.domain.Messages;
import com.searchforest.user.domain.Sessions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Messages, Long> {

    List<Messages> findBySessionIdOrderByTimestampAsc(UUID sessionId);

}
