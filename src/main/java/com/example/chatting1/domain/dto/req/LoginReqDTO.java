package com.example.chatting1.domain.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqDTO {
    private String username;
    private String password;

    public LoginReqDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
