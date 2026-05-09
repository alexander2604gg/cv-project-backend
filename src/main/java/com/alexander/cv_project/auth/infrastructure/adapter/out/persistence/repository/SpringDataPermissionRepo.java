package com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.PermissionEntity;

public interface SpringDataPermissionRepo extends JpaRepository<PermissionEntity, Long> {
    Optional<PermissionEntity> findByName(String name);
}
