package com.alexander.cv_project.auth.infrastructure.adapter.out.persistence;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.alexander.cv_project.auth.domain.exception.PersistenceException;
import com.alexander.cv_project.auth.domain.exception.RolePermissionAlreadyExistsException;
import com.alexander.cv_project.auth.domain.model.RolePermission;
import com.alexander.cv_project.auth.domain.port.out.RolePermissionRepositoryPort;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.RolePermissionEntity;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.RolePermissionId;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.mapper.RolePermissionPersistenceMapper;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.repository.SpringDataRolePermissionRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JpaRolePermissionAdapter implements RolePermissionRepositoryPort {

    private final SpringDataRolePermissionRepo repository;

    @Override
    public RolePermission save(RolePermission rolePermission) {
        try {
            RolePermissionEntity entity = RolePermissionPersistenceMapper.toEntity(rolePermission);
            return RolePermissionPersistenceMapper.toDomain(repository.save(entity));
        } catch (DataIntegrityViolationException ex) {
            throw new RolePermissionAlreadyExistsException();
        } catch (DataAccessException ex) {
            throw new PersistenceException("Persistence error while saving role permission");
        }
    }

    @Override
    public void delete(RolePermission rolePermission) {
        try {
            repository.deleteById(new RolePermissionId(rolePermission.getRoleId(), rolePermission.getPermissionId()));
        } catch (DataAccessException ex) {
            throw new PersistenceException("Persistence error while deleting role permission");
        }
    }

    @Override
    public List<RolePermission> findAllByRoleId(Long roleId) {
        try {
            return RolePermissionPersistenceMapper.toDomainList(repository.findById_RoleId(roleId));
        } catch (DataAccessException ex) {
            throw new PersistenceException("Persistence error while listing role permissions by role");
        }
    }

    @Override
    public List<RolePermission> findByPermissionId(Long permissionId) {
        try {
            return RolePermissionPersistenceMapper.toDomainList(repository.findByIdPermissionId(permissionId));
        } catch (DataAccessException ex) {
            throw new PersistenceException("Persistence error while listing role permissions by permission");
        }
    }
}
