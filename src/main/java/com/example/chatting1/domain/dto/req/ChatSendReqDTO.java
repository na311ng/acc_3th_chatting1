package com.example.chatting1.domain.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatSendReqDTO {
    public Long chatRoomId;
    public String content;

    public Long getChatRoomId() {
        return chatRoomId;
    }

    public String getContent() {
        return content;
    }
}
