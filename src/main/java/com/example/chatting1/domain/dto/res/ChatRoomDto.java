package com.example.chatting1.domain.dto.res;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomDto {
    private Long id;
    private String name;
    private MessageDto lastMessage; // 없으면 null 가능
}
