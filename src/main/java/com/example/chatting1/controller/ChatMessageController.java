package com.example.chatting1.controller;

import com.example.chatting1.domain.dto.chat.ChatMessageDTO;
import com.example.chatting1.domain.dto.req.ChatSendReqDTO;
import com.example.chatting1.domain.entity.ChatMessage;
import com.example.chatting1.service.ChatService;
import com.example.chatting1.domain.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatService chatService;
    private final ChatMapper chatMapper; // DTO 변환
    private final SimpMessageSendingOperations messagingTemplate;

    public ChatMessageController(ChatService chatService, ChatMapper chatMapper, SimpMessageSendingOperations messagingTemplate) {
        this.chatService = chatService;
        this.chatMapper = chatMapper;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatSendReqDTO message,
                            @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {

        ChatMessage saved = chatService.createMessage(
                message.getChatRoomId(),
                user.getId(),
                message.getContent()
        );

        ChatMessageDTO dto = chatMapper.toChatMessageDTO(saved);


        messagingTemplate.convertAndSend(
                "/topic/chat-room/" + message.chatRoomId(),
                dto
        );
    }
}

