package org.delivery.user.domain.service;

import lombok.RequiredArgsConstructor;
import org.delivery.user.domain.User;
import org.delivery.user.domain.dto.UserLoginDto;
import org.delivery.user.domain.dto.UserRegisterDto;
import org.delivery.user.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(UserRegisterDto userRegisterDto) {
        if (userRepository.existsByEmail(userRegisterDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(userRegisterDto.getPassword());

        User newUser = User.builder()
                .email(userRegisterDto.getEmail())
                .password(encodedPassword)
                .name(userRegisterDto.getName())
                .build();

        return userRepository.save(newUser);
    }

    @Transactional
    public User login(UserLoginDto userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }
}
