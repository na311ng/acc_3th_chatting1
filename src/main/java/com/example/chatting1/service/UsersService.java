package com.example.chatting1.service;

import com.example.chatting1.domain.entity.Users;
import com.example.chatting1.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public Users register(String username, String password, String email) {
        if(usersRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다.");
        }
        if(usersRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        Users users = Users.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
        return usersRepository.save(users);
    }

    public Users login(String username, String password) {
        Users users = usersRepository.findByUsername(username)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 사용자입니다. "));

        if(!passwordEncoder.matches(password, users.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. ");
        }
        return users;
    }
}
