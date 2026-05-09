package com.alexander.cv_project.auth.domain.port.out;

import java.util.List;

import com.alexander.cv_project.auth.domain.model.Permission;

public interface PermissionRepositoryPort {
    Permission save(Permission permission);

    void deleteById(Long id);

    boolean existsById(Long id);

    List<Permission> findAll();
}
