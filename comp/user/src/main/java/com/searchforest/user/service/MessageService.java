package com.searchforest.user.service;


import com.searchforest.user.domain.Messages;
import com.searchforest.user.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;


    // session 에 있는 message 들을 update 시간에 따라 정렬해 list 로 return
    public List<String> getMessages(UUID sessionId){
        return messageRepository.findBySessionIdOrderByTimestampAsc(sessionId).stream()
                .map(Messages::getContent)
                .toList();
    }

    public void save(Messages messages){
        messageRepository.save(messages);
    }
}
