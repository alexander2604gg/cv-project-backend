package com.alexander.cv_project.auth.service.impl;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexander.cv_project.auth.exception.RoleAlreadyExistsException;
import com.alexander.cv_project.auth.exception.RoleNotFoundException;
import com.alexander.cv_project.auth.exception.RolePermissionAlreadyExistsException;
import com.alexander.cv_project.auth.dto.RoleRequest;
import com.alexander.cv_project.auth.dto.RoleResponse;
import com.alexander.cv_project.auth.mapper.RoleMapper;
import com.alexander.cv_project.auth.repository.RolePermissionRepository;
import com.alexander.cv_project.auth.repository.RoleRepository;
import com.alexander.cv_project.auth.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(
            RoleRepository roleRepository,
            RolePermissionRepository rolePermissionRepository,
            RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleResponse create(RoleRequest request) {
        try {
            return roleMapper.toResponse(roleRepository.save(roleMapper.toEntity(request)));
        } catch (DataIntegrityViolationException ex) {
            throw new RoleAlreadyExistsException();
        }
    }

    @Override
    public List<RoleResponse> list() {
        return roleMapper.toResponseList(roleRepository.findAll());
    }

    @Override
    public void delete(Long roleId) {
        boolean hasRolePermissions = !rolePermissionRepository.findByRoleId(roleId).isEmpty();
        if (hasRolePermissions) {
            throw new RolePermissionAlreadyExistsException();
        }

        if (!roleRepository.existsById(roleId)) {
            throw new RoleNotFoundException(roleId);
        }

        roleRepository.deleteById(roleId);
    }
}
