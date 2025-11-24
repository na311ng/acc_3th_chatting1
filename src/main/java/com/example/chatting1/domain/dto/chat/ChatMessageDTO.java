package com.example.chatting1.domain.dto.chat;

import com.example.chatting1.domain.dto.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageDTO {
    private String id;
    private String content;
    private LocalDateTime createdAt;
    private Long seq;
    private UserDTO sender;

    public ChatMessageDTO(String id, String content, LocalDateTime createdAt, Long seq, UserDTO sender) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.seq = seq;
        this.sender = sender;
    }
}
