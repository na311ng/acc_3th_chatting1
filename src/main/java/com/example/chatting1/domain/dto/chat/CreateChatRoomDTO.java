package com.example.chatting1.domain.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateChatRoomDTO {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
