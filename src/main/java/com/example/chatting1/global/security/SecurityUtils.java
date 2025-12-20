package com.example.chatting1.global.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    private SecurityUtils() {}

    public static String currentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return null;

        Object principal = auth.getPrincipal();
        // JwtAuthenticationFilter가 principal에 username을 넣는다고 가정
        if (principal instanceof String s) return s;

        // 혹시 UserDetails면 username 꺼내기
        try {
            return (String) principal.getClass().getMethod("getUsername").invoke(principal);
        } catch (Exception e) {
            return null;
        }
    }
}
