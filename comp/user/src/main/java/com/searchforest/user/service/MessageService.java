package com.searchforest.user.service;


import com.searchforest.user.domain.Messages;
import com.searchforest.user.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;


    // session 에 있는 message 들을 update 시간에 따라 정렬해 list 로 return
    public List<String> getMessages(UUID sessionId){
        Messages message = messageRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new NoSuchElementException("해당 세션의 메시지를 찾을 수 없습니다: " + sessionId));

        List<String> result = new ArrayList<>();
        result.add(message.getRootContent());  // rootContent는 0번 index

        if (message.getSubContent() != null) {
            result.addAll(message.getSubContent());  // subContent는 뒤에 붙임
        }

        return result;
    }

    public void save(Messages messages){
        messageRepository.save(messages);
    }
}
