package com.example.chatting1.domain.mapper;

import com.example.chatting1.domain.dto.res.UserDto;
import com.example.chatting1.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
