package com.example.chatting1.domain.dto.res;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {
    private String id;        // 스웨거에서 id가 string으로 보이더라
    private String content;
    private Instant createdAt;
    private long seq;
    private UserDto sender;
}
