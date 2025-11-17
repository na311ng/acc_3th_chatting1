package com.example.chatting1.domain.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterReqDTO {
    private String username;
    private String password;
    private String email;
}
