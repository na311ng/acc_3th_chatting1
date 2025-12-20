package com.example.chatting1.domain.mapper;

import com.example.chatting1.domain.dto.res.ChatRoomDto;
import com.example.chatting1.domain.entity.ChatRoom;
import com.example.chatting1.domain.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatRoomDtoMapper {

    private final MessageDtoMapper messageDtoMapper;

    public ChatRoomDto toDto(ChatRoom room, Message lastMessageOrNull) {
        return ChatRoomDto.builder()
                .id(room.getId())
                .name(room.getName())
                .lastMessage(lastMessageOrNull == null ? null : messageDtoMapper.toDto(lastMessageOrNull))
                .build();
    }
}
