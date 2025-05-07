package com.searchforest.user.service;

import com.searchforest.user.domain.Sessions;
import com.searchforest.user.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    //userId 로 session 을 검색해 해당 user 가 가지고 있는 상위 5개의 session 을 return;
    public List<Sessions> getSessions(Long userId){
        return sessionRepository.findAllByUserIdOrderByUpdatedAtDesc(userId, PageRequest.of(0,5));
    }

    public Sessions createSession(Long userId) {
        Sessions session = Sessions.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return sessionRepository.save(session);
    }

    public void save(Sessions session){
        sessionRepository.save(session);
    }

}
