package com.example.chatting1.repository;

import com.example.chatting1.domain.entity.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {
    List<ChatUser> findByUserId(Long userId);
    Optional<ChatUser> findByChatRoomIdAndUserId(Long chatRoomId, Long userId);
}
