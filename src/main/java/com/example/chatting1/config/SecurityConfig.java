package com.example.chatting1.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // JwtAuthenticationFilter는 "나중에" 붙일 거면 주입은 둬도 됨.
    // 지금 당장 연결 문제 해결이 목표라면 필터 체인에 안 붙이는 게 안전함.
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // ✅ CORS 적용 (corsConfigurationSource()를 실제로 사용하게 함)
                .cors(cors -> {})

                // ✅ 개발 단계에서 CSRF 끄기
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // ✅ preflight 허용 (이거 없으면 프론트/WS 다 터질 수 있음)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // ✅ WebSocket + SockJS
                        .requestMatchers("/ws-chat/**").permitAll()

                        // ✅ 인증 API는 열어둬야 로그인/회원가입 가능
                        .requestMatchers("/api/auth/**").permitAll()

                        // ✅ Swagger 열기 (사용 시)
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // ✅ 에러 엔드포인트
                        .requestMatchers("/error").permitAll()

                        // ✅ 일단은 나머지도 모두 허용 (연결/기능 확인용)
                        .anyRequest().permitAll()
                )

                // ✅ httpBasic 끄기 (브라우저 인증 팝업/흐름 꼬임 방지)
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable());

        // ❗ JWT 필터는 "토큰 전송까지 준비된 후" 붙이자.
        // 지금은 붙이면 연결이 또 막힐 확률 큼.
        // http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ⭐ CORS 설정 (필요하면 allowedOrigins 추가)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:8080"
                // 배포 프론트 도메인 있으면 여기에 추가
                // "https://your-frontend-domain.com"
        ));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
