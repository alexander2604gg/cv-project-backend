package com.alexander.cv_project.auth.application.service;

import java.util.List;

import com.alexander.cv_project.auth.application.port.in.AssignRolePermissionsUseCase;
import com.alexander.cv_project.auth.domain.exception.PermissionNotFoundException;
import com.alexander.cv_project.auth.domain.exception.RoleNotFoundException;
import com.alexander.cv_project.auth.domain.exception.ValidationException;
import com.alexander.cv_project.auth.domain.model.Permission;
import com.alexander.cv_project.auth.domain.model.RolePermission;
import com.alexander.cv_project.auth.domain.port.out.PermissionRepositoryPort;
import com.alexander.cv_project.auth.domain.port.out.RolePermissionRepositoryPort;
import com.alexander.cv_project.auth.domain.port.out.RoleRepositoryPort;

public class AssignRolePermissionsService implements AssignRolePermissionsUseCase {

    private final RolePermissionRepositoryPort rolePermissionRepository;
    private final RoleRepositoryPort roleRepository;
    private final PermissionRepositoryPort permissionRepository;

    public AssignRolePermissionsService(
            RolePermissionRepositoryPort rolePermissionRepository,
            RoleRepositoryPort roleRepository,
            PermissionRepositoryPort permissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<RolePermission> execute(Long roleId, List<Long> permissionIds) {

        validateRequest(roleId, permissionIds);

        //obtento todos los permisos que tiene ese role
        List<RolePermission> existingPermissions = rolePermissionRepository.findAllByRoleId(roleId);
        //obtengo los nuevos permisos que se quieren agregar
        List<RolePermission> newPermissions = rolePermissionRepository.findAllByPermissionIdIn(permissionIds);

        rolePermissionRepository.deleteAllInBatch(existingPermissions);
        return rolePermissionRepository.saveAll(newPermissions);
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
