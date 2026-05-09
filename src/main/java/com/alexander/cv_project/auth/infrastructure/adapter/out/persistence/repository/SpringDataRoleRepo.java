package com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.RoleEntity;

public interface SpringDataRoleRepo extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByCode(String code);
}
