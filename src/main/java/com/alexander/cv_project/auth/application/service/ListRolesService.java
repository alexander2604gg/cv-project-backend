package com.alexander.cv_project.auth.application.service;

import java.util.List;

import com.alexander.cv_project.auth.application.port.in.ListRolesUseCase;
import com.alexander.cv_project.auth.domain.model.Role;
import com.alexander.cv_project.auth.domain.port.out.RoleRepositoryPort;

public class ListRolesService implements ListRolesUseCase {

    private final RoleRepositoryPort repository;

    public ListRolesService(RoleRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public List<Role> execute() {
        return repository.findAll();
    }
}
