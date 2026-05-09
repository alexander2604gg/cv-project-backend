package com.alexander.cv_project.auth.domain.port.out;

import java.util.Optional;

import com.alexander.cv_project.auth.domain.model.User;

public interface UserRepositoryPort {
    User save(User user);

    Optional<User> findByEmail(String email);
}
