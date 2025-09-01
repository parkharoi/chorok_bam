// UserApiController.java
package org.delivery.api.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.api.dto.LoginResponseDto;
import org.delivery.api.jwt.TokenProvider;
import org.delivery.user.domain.User;
import org.delivery.user.domain.dto.UserLoginDto;
import org.delivery.user.domain.dto.UserRegisterDto;
import org.delivery.user.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Validated UserRegisterDto userRegisterDto) {
        User registeredUser = userService.register(userRegisterDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Validated UserLoginDto userLoginDto) {
        User loggedinUser = userService.login(userLoginDto);
        String jwt = tokenProvider.createToken(loggedinUser.getEmail());

        return ResponseEntity.ok(
                LoginResponseDto.builder()
                        .token(jwt)
                        .email(loggedinUser.getEmail())
                        .build()
        );
    }
}