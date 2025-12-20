package com.example.chatting1.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat_room")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatRoom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;
}
