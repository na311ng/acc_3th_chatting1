package com.example.chatting1.service;

import com.example.chatting1.domain.entity.ChatRoom;
import com.example.chatting1.domain.entity.ChatUser;
import com.example.chatting1.domain.entity.User;
import com.example.chatting1.repository.ChatRoomRepository;
import com.example.chatting1.repository.ChatUserRepository;
import com.example.chatting1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatUserRepository chatUserRepository;
    private final UserRepository userRepository;

    public List<ChatRoom> getJoinedChatRooms(Long userId) {
        return chatUserRepository.findByUserId(userId)
                .stream()
                .map(ChatUser::getChatRoom)
                .toList();
    }

    @Transactional
    public ChatRoom createRoomAndJoin(Long userId, String name) {
        ChatRoom room = chatRoomRepository.save(ChatRoom.builder().name(name).build());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found: " + userId));

        chatUserRepository.save(ChatUser.builder()
                .chatRoom(room)
                .user(user)
                .build());

        return room;
    }

    public ChatRoom getRoomOrThrow(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalStateException("ChatRoom not found: " + chatRoomId));
    }

    public void assertJoined(Long chatRoomId, Long userId) {
        chatUserRepository.findByChatRoomIdAndUserId(chatRoomId, userId)
                .orElseThrow(() -> new IllegalStateException("Not joined chatRoomId=" + chatRoomId));
    }
}
