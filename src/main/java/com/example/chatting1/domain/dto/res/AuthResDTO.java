package com.example.chatting1.domain.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class AuthResDTO {
    @Getter
    @Builder
    public static class Register {
        private String accessToken;
    }

    @Getter
    @Builder
    public static class Login {
        private String accessToken;
    }
}
