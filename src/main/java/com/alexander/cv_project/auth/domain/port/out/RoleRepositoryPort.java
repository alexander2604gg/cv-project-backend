package com.alexander.cv_project.auth.domain.port.out;

import java.util.List;

import com.alexander.cv_project.auth.domain.model.Role;

public interface RoleRepositoryPort {
    Role save(Role role);

    void deleteById(Long id);

    boolean existsById(Long id);

    List<Role> findAll();
}
