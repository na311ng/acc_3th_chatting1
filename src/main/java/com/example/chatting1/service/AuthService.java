package com.example.chatting1.service;

import com.example.chatting1.config.JwtTokenProvider;
import com.example.chatting1.domain.dto.req.LoginReqDTO;
import com.example.chatting1.domain.dto.req.RegisterReqDTO;
import com.example.chatting1.domain.dto.res.AuthResDTO;
import com.example.chatting1.domain.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersService usersService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthResDTO register(RegisterReqDTO registerReqDTO) {
        Users users = usersService.register(registerReqDTO.getUsername(), registerReqDTO.getPassword(), registerReqDTO.getEmail());
        String token = jwtTokenProvider.generateToken(users.getUsername());

        return new AuthResDTO(token);
    }
    public AuthResDTO login(LoginReqDTO loginReqDTO) {
        Users users = usersService.login(loginReqDTO.getUsername(), loginReqDTO.getPassword());
        String token = jwtTokenProvider.generateToken(users.getUsername());

        return new AuthResDTO(token);
    }
}
