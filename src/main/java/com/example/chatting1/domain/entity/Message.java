package com.example.chatting1.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "message")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @Column(length = 36)
    private String id; // UUID 문자열

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(nullable = false, length = 2000)
    private String content;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private long seq;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID().toString();
        if (createdAt == null) createdAt = Instant.now();
    }
}
