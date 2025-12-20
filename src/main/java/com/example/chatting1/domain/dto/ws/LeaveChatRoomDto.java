package com.example.chatting1.domain.dto.ws;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveChatRoomDto {

    @NotNull
    private Long roomId;
}
