package com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.mapper;

import com.alexander.cv_project.auth.domain.model.RefreshToken;
import com.alexander.cv_project.auth.domain.model.User;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.RefreshTokenEntity;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.UserEntity;

public class RefreshTokenPersistenceMapper {

    public static RefreshTokenEntity toEntity(RefreshToken domain) {
        return RefreshTokenEntity.builder()
                .id(domain.getId())
                .token(domain.getToken())
                .expiryDate(domain.getExpiryDate())
                .user(UserEntity.builder().id(domain.getUser().getId()).build())
                .build();
    }

    public static RefreshToken toDomain(RefreshTokenEntity entity) {
        User user = User.builder()
                .id(entity.getUser().getId())
                .email(entity.getUser().getEmail())
                .password(entity.getUser().getPassword())
                .build();

        return RefreshToken.builder()
                .id(entity.getId())
                .token(entity.getToken())
                .expiryDate(entity.getExpiryDate())
                .user(user)
                .build();
    }
}
