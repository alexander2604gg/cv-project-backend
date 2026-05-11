package com.alexander.cv_project.auth.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.alexander.cv_project.auth.domain.model.Permission;

public interface PermissionRepositoryPort {

    Permission save(Permission permission);

    void delete (Permission permission);

    boolean existsById(Long id);

    List<Permission> findAll();

    Optional<Permission> findById (Long id);


}
