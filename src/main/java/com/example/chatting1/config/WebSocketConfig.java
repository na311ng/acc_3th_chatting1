package com.example.chatting1.config;

import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat")   // 클라이언트가 연결할 엔드포인트
                .setAllowedOriginPatterns("*") // 필요에 따라 origin 제한
                .withSockJS();                // SockJS fallback
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // 구독 prefix
        registry.setApplicationDestinationPrefixes("/app"); // @MessageMapping prefix
    }
}
