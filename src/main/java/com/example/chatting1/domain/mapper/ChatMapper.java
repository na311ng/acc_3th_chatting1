package com.example.chatting1.domain.mapper;

import com.example.chatting1.domain.dto.chat.ChatMessageDTO;
import com.example.chatting1.domain.dto.chat.ChatRoomDTO;
import com.example.chatting1.domain.dto.user.UserDTO;
import com.example.chatting1.domain.entity.ChatMessage;
import com.example.chatting1.domain.entity.ChatRoom;
import com.example.chatting1.domain.entity.Users;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatMapper {

    public ChatRoomDTO toChatRoomDTO(ChatRoom room) {
        return new ChatRoomDTO(
                room.getId(),
                room.getName(),
                room.getMessages().isEmpty()
                        ? null
                        : toChatMessageDTO(
                        room.getMessages().get(room.getMessages().size() - 1)
                )
        );
    }

    public ChatMessageDTO toChatMessageDTO(ChatMessage message) {
        return new ChatMessageDTO(
                message.getId().toString(),
                message.getContent(),
                message.getCreatedAt(),
                message.getSeq(),
                toUserDTO(message.getSender())
        );
    }

    public UserDTO toUserDTO(Users user) {
        return new UserDTO(
                user.getId(),
                user.getUsername()
        );
    }

    public List<ChatMessageDTO> toChatMessageDTOList(List<ChatMessage> messages) {
        return messages.stream()
                .map(this::toChatMessageDTO)
                .toList();
    }
}

