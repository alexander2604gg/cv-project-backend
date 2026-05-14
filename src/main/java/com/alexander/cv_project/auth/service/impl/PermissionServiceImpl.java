package com.alexander.cv_project.auth.service.impl;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexander.cv_project.auth.exception.PermissionAlreadyExistsException;
import com.alexander.cv_project.auth.exception.PermissionNotFoundException;
import com.alexander.cv_project.auth.exception.RolePermissionAlreadyExistsException;
import com.alexander.cv_project.auth.dto.PermissionRequest;
import com.alexander.cv_project.auth.dto.PermissionResponse;
import com.alexander.cv_project.auth.mapper.PermissionMapper;
import com.alexander.cv_project.auth.repository.PermissionRepository;
import com.alexander.cv_project.auth.repository.RolePermissionRepository;
import com.alexander.cv_project.auth.service.PermissionService;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionMapper permissionMapper;

    public PermissionServiceImpl(
            PermissionRepository permissionRepository,
            RolePermissionRepository rolePermissionRepository,
            PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public PermissionResponse create(PermissionRequest request) {
        try {
            return permissionMapper.toResponse(permissionRepository.save(permissionMapper.toEntity(request)));
        } catch (DataIntegrityViolationException ex) {
            throw new PermissionAlreadyExistsException();
        }
    }

    @Override
    public List<PermissionResponse> list() {
        return permissionMapper.toResponseList(permissionRepository.findAll());
    }

    @Override
    public void delete(Long permissionId) {
        boolean hasRolePermissions = !rolePermissionRepository.findByPermissionId(permissionId).isEmpty();
        if (hasRolePermissions) {
            throw new RolePermissionAlreadyExistsException();
        }

        if (!permissionRepository.existsById(permissionId)) {
            throw new PermissionNotFoundException(permissionId);
        }

        permissionRepository.deleteById(permissionId);
    }
}
