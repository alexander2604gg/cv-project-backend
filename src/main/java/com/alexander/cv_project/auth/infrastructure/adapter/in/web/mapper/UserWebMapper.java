package com.alexander.cv_project.auth.infrastructure.adapter.in.web.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.alexander.cv_project.auth.domain.model.User;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.UserRegistrationRequest;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.UserResponse;

public class UserWebMapper {

    public static User toDomain(UserRegistrationRequest request) {
        return User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }

    public static UserResponse toResponse(User user) {
        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getCode())
                .collect(Collectors.toSet());

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .roles(roles)
                .build();
    }
}
