package com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.RolePermissionEntity;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.RolePermissionId;

public interface SpringDataRolePermissionRepo extends JpaRepository<RolePermissionEntity, RolePermissionId> {
    List<RolePermissionEntity> findByIdRoleId(Long roleId);

    List<RolePermissionEntity> findByIdPermissionId(Long permissionId);
}
