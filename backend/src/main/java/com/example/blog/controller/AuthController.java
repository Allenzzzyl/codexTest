package com.example.blog.controller;

import com.example.blog.domain.User;
import com.example.blog.service.AuthService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest request) {
        String token = authService.login(request.username(), request.password());
        return new TokenResponse(token);
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody LoginRequest request) {
        User user = authService.register(request.username(), request.password());
        return new UserResponse(user.getId(), user.getUsername());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleAuthError(IllegalArgumentException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    public record LoginRequest(@NotBlank String username, @NotBlank String password) {
    }

    public record TokenResponse(String token) {
    }

    public record UserResponse(Long id, String username) {
    }

    public record ErrorResponse(String message) {
    }
}
