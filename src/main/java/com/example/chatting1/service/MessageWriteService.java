package com.example.chatting1.service;

import com.example.chatting1.domain.entity.*;
import com.example.chatting1.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageWriteService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Transactional
    public Message saveMessage(Long roomId, String username, String content) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalStateException("ChatRoom not found: " + roomId));

        User sender = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found: " + username));

        // seq는 "방별 증가"가 정석인데, 최소 구현으로는 timestamp 기반/전역 증가도 가능
        // 여기선 방별로 마지막 seq 읽고 +1
        long nextSeq = messageRepository.findTop1ByChatRoomIdOrderBySeqDesc(roomId)
                .map(m -> m.getSeq() + 1)
                .orElse(1L);

        Message message = Message.builder()
                .chatRoom(room)
                .sender(sender)
                .content(content)
                .seq(nextSeq)
                .build();

        return messageRepository.save(message);
    }
}
