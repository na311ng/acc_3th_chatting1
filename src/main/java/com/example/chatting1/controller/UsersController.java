package com.example.chatting1.controller;

import com.example.chatting1.domain.dto.res.ChatRoomDto;
import com.example.chatting1.domain.dto.res.UserDto;
import com.example.chatting1.domain.entity.ChatRoom;
import com.example.chatting1.domain.entity.Message;
import com.example.chatting1.domain.entity.User;
import com.example.chatting1.domain.mapper.ChatRoomDtoMapper;
import com.example.chatting1.domain.mapper.UserDtoMapper;
import com.example.chatting1.service.ChatRoomService;
import com.example.chatting1.service.MessageService;
import com.example.chatting1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final ChatRoomService chatRoomService;
    private final UserService userService;
    private final MessageService messageService;

    private final ChatRoomDtoMapper chatRoomDtoMapper;
    private final UserDtoMapper userDtoMapper;

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(userDtoMapper.toDto(user));
    }

    @GetMapping("/chat-rooms")
    public ResponseEntity<List<ChatRoomDto>> getChatRooms() {
        Long userId = userService.getCurrentUserId();

        List<ChatRoom> rooms = chatRoomService.getJoinedChatRooms(userId);

        List<ChatRoomDto> dtos = rooms.stream().map(room -> {
            Message last = messageService.getLastMessageOrNull(room.getId());
            return chatRoomDtoMapper.toDto(room, last);
        }).toList();

        return ResponseEntity.ok(dtos);
    }
}
