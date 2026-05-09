package com.alexander.cv_project.auth.infrastructure.adapter.in.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexander.cv_project.auth.application.dto.UserProfileResponse;
import com.alexander.cv_project.auth.application.port.in.GetAuthenticatedUserProfileUseCase;

@RestController
@RequestMapping("/api/v1/auth")
public class UserProfileController {

    private final GetAuthenticatedUserProfileUseCase useCase;

    public UserProfileController(GetAuthenticatedUserProfileUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> me() {
        return ResponseEntity.ok(useCase.execute());
    }
}
