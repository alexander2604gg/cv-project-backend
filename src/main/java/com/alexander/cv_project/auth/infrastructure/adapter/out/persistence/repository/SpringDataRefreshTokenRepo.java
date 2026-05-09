package com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.RefreshTokenEntity;

public interface SpringDataRefreshTokenRepo extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);

    void deleteByUser_Id(Long userId);
}
