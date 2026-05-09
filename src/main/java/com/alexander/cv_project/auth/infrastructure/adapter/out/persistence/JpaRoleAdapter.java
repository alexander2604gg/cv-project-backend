package com.alexander.cv_project.auth.infrastructure.adapter.out.persistence;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.alexander.cv_project.auth.domain.exception.PersistenceException;
import com.alexander.cv_project.auth.domain.exception.RoleAlreadyExistsException;
import com.alexander.cv_project.auth.domain.model.Role;
import com.alexander.cv_project.auth.domain.port.out.RoleRepositoryPort;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.RoleEntity;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.mapper.RolePersistenceMapper;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.repository.SpringDataRoleRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JpaRoleAdapter implements RoleRepositoryPort {

    private final SpringDataRoleRepo repository;

    @Override
    public Role save(Role role) {
        try {
            RoleEntity entity = RolePersistenceMapper.toEntity(role);
            return RolePersistenceMapper.toDomain(repository.save(entity));
        } catch (DataIntegrityViolationException ex) {
            throw new RoleAlreadyExistsException();
        } catch (DataAccessException ex) {
            throw new PersistenceException("Persistence error while saving role");
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (DataAccessException ex) {
            throw new PersistenceException("Persistence error while deleting role");
        }
    }

    @Override
    public boolean existsById(Long id) {
        try {
            return repository.existsById(id);
        } catch (DataAccessException ex) {
            throw new PersistenceException("Persistence error while checking role");
        }
    }

    @Override
    public List<Role> findAll() {
        try {
            return RolePersistenceMapper.toDomainList(repository.findAll());
        } catch (DataAccessException ex) {
            throw new PersistenceException("Persistence error while listing roles");
        }
    }
}
