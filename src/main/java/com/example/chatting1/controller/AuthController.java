package com.example.chatting1.controller;

import com.example.chatting1.config.JwtTokenProvider;
import com.example.chatting1.domain.dto.req.LoginReqDTO;
import com.example.chatting1.domain.dto.req.RegisterReqDTO;
import com.example.chatting1.domain.dto.res.AuthResDTO;
import com.example.chatting1.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResDTO> register(@RequestBody RegisterReqDTO registerReqDTO) {
        return ResponseEntity.ok(authService.register(registerReqDTO));
    }

    @GetMapping("/login")
    public ResponseEntity<AuthResDTO> login(@RequestBody LoginReqDTO loginReqDTO) {
        return ResponseEntity.ok(authService.login(loginReqDTO));
    }

}
