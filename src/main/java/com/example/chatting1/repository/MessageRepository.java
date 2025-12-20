package com.example.chatting1.repository;

import com.example.chatting1.domain.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, String> {

    Page<Message> findByChatRoomIdOrderBySeqDesc(Long chatRoomId, Pageable pageable);

    Optional<Message> findTop1ByChatRoomIdOrderBySeqDesc(Long chatRoomId);
}
