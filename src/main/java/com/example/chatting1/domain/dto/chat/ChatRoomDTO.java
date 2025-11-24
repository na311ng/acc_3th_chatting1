package com.example.chatting1.domain.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDTO {

    private Long id;
    private String name;
    private ChatMessageDTO lastMessage;

    public ChatRoomDTO(Long id, String name, ChatMessageDTO lastMessage) {
        this.id = id;
        this.name = name;
        this.lastMessage = lastMessage;
    }
}


