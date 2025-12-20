package com.example.chatting1.service;

import com.example.chatting1.config.JwtTokenProvider;
import com.example.chatting1.domain.dto.req.AuthReqDTO;
import com.example.chatting1.domain.dto.res.AuthResDTO;
import com.example.chatting1.domain.entity.User;
import com.example.chatting1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthResDTO.Register register(AuthReqDTO.Register req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 username 입니다.");
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 email 입니다.");
        }

        User user = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .email(req.getEmail())
                .build();

        User saved = userRepository.save(user);

        // Swagger 예시: { accessToken: "string" }
        String token = jwtTokenProvider.generateToken(saved.getUsername());

        return AuthResDTO.Register.builder()
                .accessToken(token)
                .build();
    }

    @Transactional(readOnly = true)
    public AuthResDTO.Login login(AuthReqDTO.Login req) {
        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.generateToken(user.getUsername());

        return AuthResDTO.Login.builder()
                .accessToken(token)
                .build();
    }
}
