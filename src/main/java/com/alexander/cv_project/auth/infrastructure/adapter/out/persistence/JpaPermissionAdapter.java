package com.alexander.cv_project.auth.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.alexander.cv_project.auth.domain.exception.PermissionAlreadyExistsException;
import com.alexander.cv_project.auth.domain.exception.PersistenceException;
import com.alexander.cv_project.auth.domain.model.Permission;
import com.alexander.cv_project.auth.domain.port.out.PermissionRepositoryPort;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.PermissionEntity;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.mapper.PermissionPersistenceMapper;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.repository.SpringDataPermissionRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JpaPermissionAdapter implements PermissionRepositoryPort {

    private final SpringDataPermissionRepo repository;

    @Override
    public Permission save(Permission permission) {
        try {
            PermissionEntity entity = PermissionPersistenceMapper.toEntity(permission);
            return PermissionPersistenceMapper.toDomain(repository.save(entity));
        } catch (DataIntegrityViolationException ex) {
            throw new PermissionAlreadyExistsException();
        } catch (DataAccessException ex) {
            throw new PersistenceException("Persistence error while saving permission");
        }
    }

    @Override
    public void delete(Permission permission) {
        PermissionEntity permissionEntity =  PermissionPersistenceMapper.toEntity(permission);
        repository.delete(permissionEntity);
    }


    @Override
    public boolean existsById(Long id) {
        try {
            return repository.existsById(id);
        } catch (DataAccessException ex) {
            throw new PersistenceException("Persistence error while checking permission");
        }
    }

    @Override
    public List<Permission> findAll() {
        try {
            return PermissionPersistenceMapper.toDomainList(repository.findAll());
        } catch (DataAccessException ex) {
            throw new PersistenceException("Persistence error while listing permissions");
        }
    }

    @Override
    public Optional<Permission> findById(Long id) {
        return repository.findById(id).map(PermissionPersistenceMapper::toDomain);
    }
}
