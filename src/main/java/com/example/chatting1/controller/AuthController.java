package com.example.chatting1.controller;

import com.example.chatting1.config.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;


    /**
     * 임시 로그인 API (토큰만 생성)
     * POST /api/auth/test-login
     */
    @PostMapping("/test-login")
    public ResponseEntity<?> testLogin(@RequestParam String username) {
        String token = jwtTokenProvider.generateToken(username);
        return ResponseEntity.ok("JWT Token: " + token);
    }

    /**
     * 토큰 검증 테스트용
     * GET /api/auth/verify?token=...
     */
    @GetMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestParam String token) {
        boolean valid = jwtTokenProvider.validateToken(token);
        if (valid) {
            return ResponseEntity.ok("✅ Token is valid. username=" + jwtTokenProvider.getUsername(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Invalid token");
        }
    }

}
