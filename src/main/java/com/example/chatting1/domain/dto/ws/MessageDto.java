package com.example.chatting1.domain.dto.ws;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {

    private String messageId;
    private String roomId;
    private String sender;

    // ✅ AsyncAPI 기준: string
    // ISO-8601 추천: "2025-12-20T16:23:16.502+09:00"
    private String timestamp;

    // (너 화면 출력용으로 이미 쓰고 있으니 유지)
    private String content;
}
