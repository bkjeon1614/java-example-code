package com.example.bkjeon.base.service.security;

import com.example.bkjeon.base.entity.security.jwt.Authority;
import com.example.bkjeon.base.entity.security.jwt.User;
import com.example.bkjeon.base.repository.security.jwt.UserRepository;
import com.example.bkjeon.base.controller.security.jwt.dto.UserDTO;
import com.example.bkjeon.base.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public User signUp(UserDTO userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return userRepository.save(user);
    }

    // username을 매개변수를 받아서 유저정보와 권한정보를 가져옴
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    // 현재 SecurityContext에 저장된 유저이름에 해당되는 유저정보와 권한정보를 가져옴
    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return AuthUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }

}
