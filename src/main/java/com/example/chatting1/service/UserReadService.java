package com.example.chatting1.service;

import com.example.chatting1.domain.entity.User;
import com.example.chatting1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserReadService {
    private final UserRepository userRepository;

    public User getOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
    }
}
