package com.alexander.cv_project.auth.service;

import com.alexander.cv_project.auth.dto.AuthTokenResponse;
import com.alexander.cv_project.auth.dto.LoginRequest;
import com.alexander.cv_project.auth.dto.RefreshTokenRequest;
import com.alexander.cv_project.auth.dto.UserProfileResponse;
import com.alexander.cv_project.auth.dto.UserRegistrationRequest;
import com.alexander.cv_project.auth.dto.UserResponse;

public interface AuthService {
    UserResponse register(UserRegistrationRequest request);

    AuthTokenResponse login(LoginRequest request);

    AuthTokenResponse refresh(RefreshTokenRequest request);

    UserProfileResponse me();
}
