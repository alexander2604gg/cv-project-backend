package com.alexander.cv_project.auth.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexander.cv_project.auth.exception.PermissionNotFoundException;
import com.alexander.cv_project.auth.exception.RoleNotFoundException;
import com.alexander.cv_project.auth.exception.RolePermissionAlreadyExistsException;
import com.alexander.cv_project.auth.dto.AssignRolePermissionsRequest;
import com.alexander.cv_project.auth.dto.RolePermissionResponse;
import com.alexander.cv_project.auth.entity.PermissionEntity;
import com.alexander.cv_project.auth.entity.RoleEntity;
import com.alexander.cv_project.auth.entity.RolePermissionEntity;
import com.alexander.cv_project.auth.entity.RolePermissionId;
import com.alexander.cv_project.auth.mapper.RolePermissionMapper;
import com.alexander.cv_project.auth.repository.PermissionRepository;
import com.alexander.cv_project.auth.repository.RolePermissionRepository;
import com.alexander.cv_project.auth.repository.RoleRepository;
import com.alexander.cv_project.auth.service.RolePermissionService;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionMapper rolePermissionMapper;

    public RolePermissionServiceImpl(
            RolePermissionRepository rolePermissionRepository,
            RoleRepository roleRepository,
            PermissionRepository permissionRepository,
            RolePermissionMapper rolePermissionMapper) {
        this.rolePermissionRepository = rolePermissionRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionMapper = rolePermissionMapper;
    }

    @Override
    @Transactional
    public List<RolePermissionResponse> assignPermissions(AssignRolePermissionsRequest request) {
        Long roleId = request.getRoleId();
        List<Long> permissionIds = request.getPermissionIds();

        if (!roleRepository.existsById(roleId)) {
            throw new RoleNotFoundException(roleId);
        }

        permissionIds.stream()
                .filter(permissionId -> !permissionRepository.existsById(permissionId))
                .findFirst()
                .ifPresent(permissionId -> {
                    throw new PermissionNotFoundException(permissionId);
                });

        List<RolePermissionEntity> created = permissionIds.stream()
                .distinct()
                .map(permissionId -> buildEntity(roleId, permissionId))
                .map(entity -> {
                    try {
                        return rolePermissionRepository.save(entity);
                    } catch (DataIntegrityViolationException ex) {
                        throw new RolePermissionAlreadyExistsException();
                    }
                })
                .toList();

        return rolePermissionMapper.toResponseList(created);
    }

    private RolePermissionEntity buildEntity(Long roleId, Long permissionId) {
        return RolePermissionEntity.builder()
                .id(new RolePermissionId(roleId, permissionId))
                .role(RoleEntity.builder().id(roleId).build())
                .permission(PermissionEntity.builder().id(permissionId).build())
                .assignedAt(LocalDateTime.now())
                .active(true)
                .build();
    }
}
