package com.alexander.cv_project.auth.application.service;

import com.alexander.cv_project.auth.application.port.in.CreateRoleUseCase;
import com.alexander.cv_project.auth.domain.model.Role;
import com.alexander.cv_project.auth.domain.port.out.RoleRepositoryPort;

public class CreateRoleService implements CreateRoleUseCase {

    private final RoleRepositoryPort repository;

    public CreateRoleService(RoleRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Role execute(Role role) {
        return repository.save(role);
    }
}
