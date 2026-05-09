package com.alexander.cv_project.auth.application.service;

import java.util.List;

import com.alexander.cv_project.auth.application.port.in.ListPermissionsUseCase;
import com.alexander.cv_project.auth.domain.model.Permission;
import com.alexander.cv_project.auth.domain.port.out.PermissionRepositoryPort;

public class ListPermissionsService implements ListPermissionsUseCase {

    private final PermissionRepositoryPort repository;

    public ListPermissionsService(PermissionRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Permission> execute() {
        return repository.findAll();
    }
}
