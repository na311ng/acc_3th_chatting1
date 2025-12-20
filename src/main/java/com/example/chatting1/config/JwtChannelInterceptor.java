package com.example.chatting1.config;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null || accessor.getCommand() == null) {
            return message;
        }

        StompCommand command = accessor.getCommand();

        // CONNECT / SEND / SUBSCRIBE 시점에 토큰 검사
        if (command == StompCommand.CONNECT
                || command == StompCommand.SEND
                || command == StompCommand.SUBSCRIBE) {

            String authHeader = accessor.getFirstNativeHeader("Authorization");

            // 🔹 토큰 없으면 anonymous 허용 (MVP 단계)
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return message;
            }

            String token = authHeader.substring(7);

            if (jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.getUsername(token);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                Collections.emptyList()
                        );

                // Spring Security Context + STOMP User 세팅
                SecurityContextHolder.getContext().setAuthentication(authentication);
                accessor.setUser(authentication);
            }
        }

        return message;
    }
}
