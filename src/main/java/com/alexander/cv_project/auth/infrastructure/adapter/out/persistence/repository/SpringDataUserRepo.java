package com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.UserEntity;

public interface SpringDataUserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
