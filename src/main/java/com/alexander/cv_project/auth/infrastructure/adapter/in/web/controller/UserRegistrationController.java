package com.alexander.cv_project.auth.infrastructure.adapter.in.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexander.cv_project.auth.application.port.in.AuthenticateUserUseCase;
import com.alexander.cv_project.auth.application.port.in.RefreshAccessTokenUseCase;
import com.alexander.cv_project.auth.application.port.in.RegisterUserUseCase;
import com.alexander.cv_project.auth.domain.model.AuthToken;
import com.alexander.cv_project.auth.domain.model.User;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.LoginRequest;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.RefreshTokenRequest;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.UserRegistrationRequest;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.UserResponse;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.mapper.UserWebMapper;

@RestController
@RequestMapping("/api/v1/auth")
public class UserRegistrationController {

    private final RegisterUserUseCase useCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final RefreshAccessTokenUseCase refreshAccessTokenUseCase;

    public UserRegistrationController(
            RegisterUserUseCase useCase,
            AuthenticateUserUseCase authenticateUserUseCase,
            RefreshAccessTokenUseCase refreshAccessTokenUseCase) {
        this.useCase = useCase;
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.refreshAccessTokenUseCase = refreshAccessTokenUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegistrationRequest request) {
        User user = UserWebMapper.toDomain(request);
        User saved = useCase.execute(user);
        return ResponseEntity.ok(UserWebMapper.toResponse(saved));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthToken> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticateUserUseCase.execute(request.getEmail(), request.getPassword()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthToken> refresh(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(refreshAccessTokenUseCase.execute(request.getRefreshToken()));
    }
}
