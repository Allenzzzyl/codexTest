package com.myblog.user.controller;

import com.myblog.common.domain.R;
import com.myblog.user.dto.UserLoginDTO;
import com.myblog.user.dto.UserRegisterDTO;
import com.myblog.user.exception.BizException;
import com.myblog.user.security.JwtTokenService;
import com.myblog.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private static final String BEARER_PREFIX = "Bearer ";

    private final UserService userService;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/register")
    public R<Void> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        userService.register(userRegisterDTO);
        return R.success();
    }

    @PostMapping("/login")
    public R<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        String token = userService.login(userLoginDTO);
        return R.success(token);
    }

    @GetMapping("/token/validate")
    public R<Boolean> validateToken(@RequestHeader("Authorization") String authorization) {
        if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
            throw new BizException("Invalid authorization header");
        }
        String token = authorization.substring(BEARER_PREFIX.length());
        return R.success(jwtTokenService.validateToken(token));
    }
}
