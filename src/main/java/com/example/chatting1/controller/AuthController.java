package com.example.chatting1.controller;

import com.example.chatting1.domain.dto.req.AuthReqDTO;
import com.example.chatting1.domain.dto.res.AuthResDTO;
import com.example.chatting1.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResDTO.Register> register(@RequestBody @Valid AuthReqDTO.Register req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResDTO.Login> login(@RequestBody @Valid AuthReqDTO.Login req) {
        return ResponseEntity.ok(authService.login(req));
    }

}
