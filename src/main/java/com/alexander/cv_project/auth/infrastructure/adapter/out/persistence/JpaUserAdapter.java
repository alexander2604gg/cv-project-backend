package com.alexander.cv_project.auth.infrastructure.adapter.out.persistence;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alexander.cv_project.auth.domain.exception.PersistenceException;
import com.alexander.cv_project.auth.domain.exception.UserAlreadyExistsException;
import com.alexander.cv_project.auth.domain.model.User;
import com.alexander.cv_project.auth.domain.port.out.UserRepositoryPort;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.mapper.UserPersistenceMapper;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.repository.SpringDataUserRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JpaUserAdapter implements UserRepositoryPort {

    private final SpringDataUserRepo repository;

    @Override
    public User save(User user) {
        try {
            UserEntity entity = UserPersistenceMapper.toEntity(user);
            return UserPersistenceMapper.toDomain(repository.save(entity));
        } catch (DataIntegrityViolationException ex) {
            throw new UserAlreadyExistsException(user.getEmail());
        } catch (DataAccessException ex) {
            throw new PersistenceException("Persistence error while saving user");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        try {
            return repository.findByEmail(email).map(UserPersistenceMapper::toDomain);
        } catch (DataAccessException ex) {
            throw new PersistenceException("Persistence error while searching user by email");
        }
    }
}
