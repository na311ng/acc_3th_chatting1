package com.example.chatting1.controller;

import com.example.chatting1.domain.dto.chat.ChatMessageDTO;
import com.example.chatting1.domain.dto.chat.ChatRoomDTO;
import com.example.chatting1.domain.dto.chat.CreateChatRoomDTO;
import com.example.chatting1.domain.entity.ChatMessage;
import com.example.chatting1.domain.entity.ChatRoom;
import com.example.chatting1.domain.mapper.ChatMapper;
import com.example.chatting1.service.ChatService;
import com.example.chatting1.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChatMapper chatMapper; // DTO 변환
    private final UsersService userService;

    public ChatController(ChatService chatService, ChatMapper chatMapper, UsersService userService) {
        this.chatService = chatService;
        this.chatMapper = chatMapper;
        this.userService = userService;
    }

    @PostMapping
    public ChatRoomDTO createChatRoom(
            @RequestBody CreateChatRoomDTO dto,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User user
    ) {
        Long userId = userService.findIdByUsername(user.getUsername());
        ChatRoom room = chatService.createChatRoom(dto, userId);
        return chatMapper.toChatRoomDTO(room);
    }

    @GetMapping
    public List<ChatRoomDTO> getChatRooms(@AuthenticationPrincipal String userId) {
        Long uid = Long.valueOf(userId);

        return chatService.listChatRooms(uid)
                .stream()
                .map(chatMapper::toChatRoomDTO)
                .toList();
    }

    @GetMapping("/{chatRoomId}")
    public ChatRoomDTO getChatRoom(
            @PathVariable Long chatRoomId
    ) {
        return chatMapper.toChatRoomDTO(chatService.getRoom(chatRoomId));
    }

    @GetMapping("/{chatRoomId}/messages")
    public List<ChatMessageDTO> getMessages(
            @PathVariable Long chatRoomId
    ) {
        return chatService.getMessages(chatRoomId)
                .stream()
                .map(chatMapper::toChatMessageDTO)
                .toList();
    }

    @GetMapping("/{chatRoomId}/last-message")
    public ChatMessageDTO getLastMessage(
            @PathVariable Long chatRoomId
    ) {
        ChatMessage msg = chatService.getLastMessage(chatRoomId);
        return msg == null ? null : chatMapper.toChatMessageDTO(msg);
    }
}
