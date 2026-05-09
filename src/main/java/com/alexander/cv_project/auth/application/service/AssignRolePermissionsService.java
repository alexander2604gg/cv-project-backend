package com.alexander.cv_project.auth.application.service;

import java.util.List;

import com.alexander.cv_project.auth.application.port.in.AssignRolePermissionsUseCase;
import com.alexander.cv_project.auth.domain.exception.PermissionNotFoundException;
import com.alexander.cv_project.auth.domain.exception.RoleNotFoundException;
import com.alexander.cv_project.auth.domain.exception.ValidationException;
import com.alexander.cv_project.auth.domain.model.RolePermission;
import com.alexander.cv_project.auth.domain.port.out.PermissionRepositoryPort;
import com.alexander.cv_project.auth.domain.port.out.RolePermissionRepositoryPort;
import com.alexander.cv_project.auth.domain.port.out.RoleRepositoryPort;

public class AssignRolePermissionsService implements AssignRolePermissionsUseCase {

    private final RolePermissionRepositoryPort repository;
    private final RoleRepositoryPort roleRepository;
    private final PermissionRepositoryPort permissionRepository;

    public AssignRolePermissionsService(
            RolePermissionRepositoryPort repository,
            RoleRepositoryPort roleRepository,
            PermissionRepositoryPort permissionRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<RolePermission> execute(Long roleId, List<Long> permissionIds) {
        validateRequest(roleId, permissionIds);

        return permissionIds.stream()
                .distinct()
                .map(permissionId -> repository.save(new RolePermission(roleId, permissionId)))
                .toList();
    }

    private void validateRequest(Long roleId, List<Long> permissionIds) {
        if (roleId == null) {
            throw new ValidationException("RoleId is required");
        }

        if (permissionIds == null || permissionIds.isEmpty()) {
            throw new ValidationException("PermissionIds are required");
        }

        if (!roleRepository.existsById(roleId)) {
            throw new RoleNotFoundException(roleId);
        }

        permissionIds.stream()
                .filter(permissionId -> !permissionRepository.existsById(permissionId))
                .findFirst()
                .ifPresent(permissionId -> {
                    throw new PermissionNotFoundException(permissionId);
                });
    }
}
