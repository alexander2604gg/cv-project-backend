package com.alexander.cv_project.auth.application.service;

import com.alexander.cv_project.auth.application.port.in.CreatePermissionUseCase;
import com.alexander.cv_project.auth.domain.model.Permission;
import com.alexander.cv_project.auth.domain.port.out.PermissionRepositoryPort;

public class CreatePermissionService implements CreatePermissionUseCase {

    private final PermissionRepositoryPort repository;

    public CreatePermissionService(PermissionRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Permission execute(Permission permission) {
        return repository.save(permission);
    }
}
