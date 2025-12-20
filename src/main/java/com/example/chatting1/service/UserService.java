package com.example.chatting1.service;

import com.example.chatting1.domain.entity.User;
import com.example.chatting1.repository.UserRepository;
import com.example.chatting1.global.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // /api/users/me 에서 사용
    public User getCurrentUser() {
        String username = SecurityUtils.currentUsername();
        if (username == null) {
            throw new IllegalStateException("Unauthenticated");
        }
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found: " + username));
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
