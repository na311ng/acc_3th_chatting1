package com.example.chatting1.domain.mapper;

import com.example.chatting1.domain.dto.res.MessageDto;
import com.example.chatting1.domain.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageDtoMapper {

    private final UserDtoMapper userDtoMapper;

    public MessageDto toDto(Message m) {
        return MessageDto.builder()
                .id(m.getId())
                .content(m.getContent())
                .createdAt(m.getCreatedAt())
                .seq(m.getSeq())
                .sender(userDtoMapper.toDto(m.getSender()))
                .build();
    }
}
