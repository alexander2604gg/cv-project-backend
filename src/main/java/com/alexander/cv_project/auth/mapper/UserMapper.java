package com.alexander.cv_project.auth.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.alexander.cv_project.auth.dto.UserRegistrationRequest;
import com.alexander.cv_project.auth.dto.UserResponse;
import com.alexander.cv_project.auth.entity.UserEntity;

@Component
public class UserMapper {

    public UserEntity toEntity(UserRegistrationRequest request, String encodedPassword) {
        return UserEntity.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .build();
    }

    public UserResponse toResponse(UserEntity entity) {
        Set<String> roles = entity.getRoles().stream()
                .map(role -> role.getCode())
                .collect(Collectors.toSet());

        return UserResponse.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .roles(roles)
                .build();
    }
}
