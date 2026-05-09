package com.alexander.cv_project.auth.infrastructure.adapter.out.persistence;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.alexander.cv_project.auth.domain.exception.PersistenceException;
import com.alexander.cv_project.auth.domain.exception.ValidationException;
import com.alexander.cv_project.auth.domain.model.RefreshToken;
import com.alexander.cv_project.auth.domain.model.User;
import com.alexander.cv_project.auth.domain.port.out.RefreshTokenServicePort;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.RefreshTokenEntity;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.mapper.RefreshTokenPersistenceMapper;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.repository.SpringDataRefreshTokenRepo;

public class RefreshTokenPersistenceAdapter implements RefreshTokenServicePort {

    private final SpringDataRefreshTokenRepo repository;
    private final long refreshTokenExpirationMs;

    public RefreshTokenPersistenceAdapter(
            SpringDataRefreshTokenRepo repository,
            long refreshTokenExpirationMs) {
        this.repository = repository;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    @Override
    @Transactional
    public RefreshToken create(User user) {
        try {
            repository.deleteByUser_Id(user.getId());

            RefreshToken refreshToken = RefreshToken.builder()
                    .token(UUID.randomUUID().toString())
                    .expiryDate(LocalDateTime.now().plus(Duration.ofMillis(refreshTokenExpirationMs)))
                    .user(user)
                    .build();

            RefreshTokenEntity saved = repository.save(RefreshTokenPersistenceMapper.toEntity(refreshToken));
            return RefreshToken.builder()
                    .id(saved.getId())
                    .token(saved.getToken())
                    .expiryDate(saved.getExpiryDate())
                    .user(user)
                    .build();
        } catch (DataAccessException ex) {
            throw new PersistenceException("Persistence error while creating refresh token");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public RefreshToken validate(String token) {
        try {
            RefreshToken refreshToken = repository.findByToken(token)
                    .map(RefreshTokenPersistenceMapper::toDomain)
                    .orElseThrow(() -> new ValidationException("Refresh token not found"));

            if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
                throw new ValidationException("Refresh token expired");
            }

            return refreshToken;
        } catch (DataAccessException ex) {
            throw new PersistenceException("Persistence error while validating refresh token");
        }
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        try {
            repository.deleteByUser_Id(userId);
        } catch (DataAccessException ex) {
            throw new PersistenceException("Persistence error while deleting refresh token");
        }
    }
}
