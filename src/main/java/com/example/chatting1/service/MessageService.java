package com.example.chatting1.service;

import com.example.chatting1.domain.entity.Message;
import com.example.chatting1.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public Page<Message> getMessages(Long chatRoomId, Pageable pageable) {
        return messageRepository.findByChatRoomIdOrderBySeqDesc(chatRoomId, pageable);
    }

    public Message getLastMessageOrNull(Long chatRoomId) {
        return messageRepository.findTop1ByChatRoomIdOrderBySeqDesc(chatRoomId).orElse(null);
    }
}
