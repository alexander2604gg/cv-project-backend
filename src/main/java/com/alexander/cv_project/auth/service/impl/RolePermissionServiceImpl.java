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
@Transactional
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
    public List<RolePermissionResponse> assignPermissions(AssignRolePermissionsRequest request) {
        Long roleId = request.getRoleId();

        // actuales en BD
        List<RolePermissionEntity> actuales = rolePermissionRepository.findByRoleId(roleId);

        List<Long> actualesIds = actuales.stream()
                .map(rp -> rp.getPermission().getId())
                .toList();

        // nuevos (quitamos duplicados por si el front manda repetidos)
        List<Long> nuevosIds = request.getPermissionIds()
                .stream()
                .distinct()
                .toList();

        // 🔥 calcular diferencias
        List<Long> aEliminar = actualesIds.stream()
                .filter(id -> !nuevosIds.contains(id))
                .toList();

        List<Long> aAgregar = nuevosIds.stream()
                .filter(id -> !actualesIds.contains(id))
                .toList();

        // 🧹 eliminar solo lo necesario
        if (!aEliminar.isEmpty()) {
            rolePermissionRepository.deleteByRoleIdAndPermissionIds(roleId, aEliminar);
        }

        // ➕ agregar solo lo nuevo
        List<RolePermissionEntity> nuevos = aAgregar.stream()
                .map(permissionId -> buildEntity(roleId, permissionId))
                .toList();

        List<RolePermissionEntity> created = rolePermissionRepository.saveAll(nuevos);

        // devolver estado final (opcional: podrías volver a consultar)
        return rolePermissionMapper.toResponseList(created);
    }

    @Override
    public List<RolePermissionResponse> findByRoleId(Long roleId) {
        List<RolePermissionEntity> rolePermission = rolePermissionRepository.findByRoleId(roleId);
        return rolePermissionMapper.toResponseList(rolePermission);
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
