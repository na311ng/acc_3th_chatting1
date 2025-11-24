package com.example.chatting1.service;

import com.example.chatting1.domain.dto.chat.CreateChatRoomDTO;
import com.example.chatting1.domain.entity.ChatMessage;
import com.example.chatting1.domain.entity.ChatRoom;
import com.example.chatting1.domain.entity.Users;
import com.example.chatting1.repository.ChatMessageRepository;
import com.example.chatting1.repository.ChatRoomRepository;
import com.example.chatting1.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository messageRepository;
    private final UsersRepository userRepository;

    public ChatService(ChatRoomRepository chatRoomRepository, ChatMessageRepository messageRepository, UsersRepository userRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public ChatRoom createChatRoom(CreateChatRoomDTO dto, Long requesterId) {
        ChatRoom room = new ChatRoom();
        room.setName(dto.getName());

        Users creator = userRepository.findById(requesterId).orElseThrow();
        room.getMembers().add(creator);

        return chatRoomRepository.save(room);
    }

    public List<ChatRoom> listChatRooms(Long userId) {
        Users user = userRepository.findById(userId).orElseThrow();
        return user.getChatRooms(); // ManyToMany
    }

    public ChatRoom getRoom(Long id) {
        return chatRoomRepository.findById(id).orElseThrow();
    }

    public List<ChatMessage> getMessages(Long roomId) {
        return messageRepository.findByChatRoomIdOrderBySeq(roomId);
    }

    public ChatMessage getLastMessage(Long roomId) {
        return messageRepository.findTopByChatRoomIdOrderBySeqDesc(roomId)
                .orElse(null);
    }

    // 메시지 생성 (STOMP 메시지 처리용)
    public ChatMessage createMessage(Long roomId, Long senderId, String content) {
        ChatRoom room = chatRoomRepository.findById(roomId).orElseThrow();
        Users sender = userRepository.findById(senderId).orElseThrow();

        long nextSeq = messageRepository
                .findTopByChatRoomIdOrderBySeqDesc(roomId)
                .map(m -> m.getSeq() + 1)
                .orElse(1L);

        ChatMessage msg = new ChatMessage();
        msg.setChatRoom(room);
        msg.setSender(sender);
        msg.setContent(content);
        msg.setCreatedAt(LocalDateTime.now());
        msg.setSeq(nextSeq);

        return messageRepository.save(msg);
    }
}

