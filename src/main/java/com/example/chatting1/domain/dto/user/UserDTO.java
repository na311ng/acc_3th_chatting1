package com.example.chatting1.domain.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    public UserDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    private Long id;
    private String username;
}
