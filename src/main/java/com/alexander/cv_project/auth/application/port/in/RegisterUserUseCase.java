package com.alexander.cv_project.auth.application.port.in;

import com.alexander.cv_project.auth.domain.model.User;

public interface RegisterUserUseCase {
    User execute(User user);
}
