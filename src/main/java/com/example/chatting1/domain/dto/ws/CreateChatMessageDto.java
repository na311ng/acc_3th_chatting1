package com.example.chatting1.domain.dto.ws;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateChatMessageDto {

    @NotNull
    private Long chatRoomId;

    @NotBlank
    private String content;
}
