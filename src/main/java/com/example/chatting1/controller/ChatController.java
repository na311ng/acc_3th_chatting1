package com.example.chatting1.controller;

import com.example.chatting1.domain.dto.req.CreateChatRoomReq;
import com.example.chatting1.domain.dto.res.*;
import com.example.chatting1.domain.entity.ChatRoom;
import com.example.chatting1.domain.entity.Message;
import com.example.chatting1.domain.mapper.ChatRoomDtoMapper;
import com.example.chatting1.domain.mapper.MessageDtoMapper;
import com.example.chatting1.service.ChatRoomService;
import com.example.chatting1.service.MessageService;
import com.example.chatting1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChatController {

    private final UserService userService;
    private final ChatRoomService chatRoomService;
    private final MessageService messageService;

    private final ChatRoomDtoMapper chatRoomDtoMapper;
    private final MessageDtoMapper messageDtoMapper;

    // GET /api/chats?page=&size=
    @GetMapping
    public ResponseEntity<PagedResponse<ChatRoomDto>> getChats(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long userId = userService.getCurrentUserId();

        // 유저가 참여한 방 목록을 pageable 형태로 흉내(간단버전)
        List<ChatRoom> all = chatRoomService.getJoinedChatRooms(userId);

        int from = Math.min(page * size, all.size());
        int to = Math.min(from + size, all.size());
        List<ChatRoom> sliced = all.subList(from, to);

        List<ChatRoomDto> content = sliced.stream().map(room -> {
            Message last = messageService.getLastMessageOrNull(room.getId());
            return chatRoomDtoMapper.toDto(room, last);
        }).toList();

        PageDto pageDto = PageDto.builder()
                .size(size)
                .number(page)
                .totalElements(all.size())
                .totalPages((int) Math.ceil((double) all.size() / size))
                .build();

        return ResponseEntity.ok(PagedResponse.<ChatRoomDto>builder()
                .content(content)
                .page(pageDto)
                .build());
    }

    // POST /api/chats  { "name": "string" }
    @PostMapping
    public ResponseEntity<ChatRoomDto> createChatRoom(@RequestBody CreateChatRoomReq req) {
        Long userId = userService.getCurrentUserId();
        ChatRoom room = chatRoomService.createRoomAndJoin(userId, req.getName());

        Message last = messageService.getLastMessageOrNull(room.getId());
        return ResponseEntity.ok(chatRoomDtoMapper.toDto(room, last));
    }

    // GET /api/chats/{chatRoomId}
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<ChatRoomDto> getChatRoom(@PathVariable Long chatRoomId) {
        Long userId = userService.getCurrentUserId();
        chatRoomService.assertJoined(chatRoomId, userId);

        ChatRoom room = chatRoomService.getRoomOrThrow(chatRoomId);
        Message last = messageService.getLastMessageOrNull(chatRoomId);
        return ResponseEntity.ok(chatRoomDtoMapper.toDto(room, last));
    }

    // GET /api/chats/{chatRoomId}/messages?page=&size=
    @GetMapping("/{chatRoomId}/messages")
    public ResponseEntity<PagedResponse<MessageDto>> getMessages(
            @PathVariable Long chatRoomId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long userId = userService.getCurrentUserId();
        chatRoomService.assertJoined(chatRoomId, userId);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "seq"));
        Page<Message> result = messageService.getMessages(chatRoomId, pageable);

        List<MessageDto> content = result.getContent().stream().map(messageDtoMapper::toDto).toList();

        PageDto pageDto = PageDto.builder()
                .size(result.getSize())
                .number(result.getNumber())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .build();

        return ResponseEntity.ok(PagedResponse.<MessageDto>builder()
                .content(content)
                .page(pageDto)
                .build());
    }

    // GET /api/chats/{chatRoomId}/last-message
    @GetMapping("/{chatRoomId}/last-message")
    public ResponseEntity<MessageDto> getLastMessage(@PathVariable Long chatRoomId) {
        Long userId = userService.getCurrentUserId();
        chatRoomService.assertJoined(chatRoomId, userId);

        Message last = messageService.getLastMessageOrNull(chatRoomId);
        if (last == null) {
            // 스펙에 204가 없어서 그냥 빈 메시지 대신 200 + null은 애매 → 여기선 200 + empty body 대신 404 처리
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(messageDtoMapper.toDto(last));
    }
}
