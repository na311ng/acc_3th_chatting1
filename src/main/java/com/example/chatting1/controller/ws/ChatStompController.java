package com.example.chatting1.controller.ws;

import com.example.chatting1.domain.dto.ws.CreateChatMessageDto;
import com.example.chatting1.domain.dto.ws.JoinChatRoomDto;
import com.example.chatting1.domain.dto.ws.LeaveChatRoomDto;
import com.example.chatting1.domain.dto.ws.MessageDto;
import com.example.chatting1.domain.entity.Message;
import com.example.chatting1.service.ChatRoomService;
import com.example.chatting1.service.MessageWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Controller
@RequiredArgsConstructor
public class ChatStompController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomService chatRoomService;
    private final MessageWriteService messageWriteService;

    // ✅ AsyncAPI: /pub/chat/join
    @MessageMapping("/chat/join")
    public void join(@Payload JoinChatRoomDto req, Principal principal) {
        String username = principal != null ? principal.getName() : "anonymous";
        Long roomId = req.getRoomId();

        // 방 존재 체크
        chatRoomService.getRoomOrThrow(roomId);

        MessageDto event = MessageDto.builder()
                .messageId("SYSTEM")
                .roomId(String.valueOf(roomId))     // AsyncAPI string
                .sender("SYSTEM")
                .timestamp(nowIso())
                .content(username + " joined.")
                .build();

        // ✅ 방 단위로 발행
        messagingTemplate.convertAndSend("/sub/chat/" + roomId, event);
    }

    // ✅ AsyncAPI: /pub/chat/leave
    @MessageMapping("/chat/leave")
    public void leave(@Payload LeaveChatRoomDto req, Principal principal) {
        String username = principal != null ? principal.getName() : "anonymous";
        Long roomId = req.getRoomId();

        chatRoomService.getRoomOrThrow(roomId);

        MessageDto event = MessageDto.builder()
                .messageId("SYSTEM")
                .roomId(String.valueOf(roomId))
                .sender("SYSTEM")
                .timestamp(nowIso())
                .content(username + " left.")
                .build();

        messagingTemplate.convertAndSend("/sub/chat/" + roomId, event);
    }

    // ✅ AsyncAPI: /pub/chat/send
    @MessageMapping("/chat/send")
    public void send(@Payload CreateChatMessageDto req, Principal principal) {
        String username = principal != null ? principal.getName() : "anonymous";
        Long roomId = req.getChatRoomId();

        chatRoomService.getRoomOrThrow(roomId);

        // DB 저장
        Message saved = messageWriteService.saveMessage(roomId, username, req.getContent());

        MessageDto event = MessageDto.builder()
                .messageId(String.valueOf(saved.getId()))
                .roomId(String.valueOf(roomId))
                .sender(username)
                .timestamp(toIso(saved.getCreatedAt()))
                .content(saved.getContent())
                .build();

        messagingTemplate.convertAndSend("/sub/chat/" + roomId, event);
    }

    private String nowIso() {
        return OffsetDateTime.now(ZoneId.systemDefault()).toString();
    }

    private String toIso(Object createdAt) {
        // createdAt 타입이 Instant/LocalDateTime/OffsetDateTime 등 프로젝트마다 다를 수 있어서 안전하게 문자열 처리
        // 너 엔티티 createdAt이 Instant면 saved.getCreatedAt().toString() 하면 ISO로 나옴.
        return createdAt != null ? createdAt.toString() : nowIso();
    }
}
