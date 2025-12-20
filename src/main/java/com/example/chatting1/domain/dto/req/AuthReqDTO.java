package com.example.chatting1.domain.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthReqDTO {

    @Getter
    @NoArgsConstructor
    public static class Register {
        @NotBlank
        @Size(min = 4, max = 50)
        private String username;

        @NotBlank
        @Size(min = 4, max = 100)
        private String password;

        @NotBlank
        @Email
        private String email;
    }

    @Getter
    @NoArgsConstructor
    public static class Login {
        @NotBlank
        private String username;

        @NotBlank
        private String password;

        @Email
        private String email;
    }
}
