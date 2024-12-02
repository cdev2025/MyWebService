package com.example.mywebservice.user.service;

import com.example.mywebservice.user.controller.dto.AddUserDto;
import com.example.mywebservice.user.db.UserEntity;
import com.example.mywebservice.user.db.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServie {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public Long save(AddUserDto dto){
        UserEntity entity = UserEntity.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword())) // 패스워드 암호화
                .build();
        return userRepository.save(entity).getId();
    }
}
