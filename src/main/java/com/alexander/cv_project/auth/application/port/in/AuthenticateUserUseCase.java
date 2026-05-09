package com.alexander.cv_project.auth.application.port.in;

import com.alexander.cv_project.auth.domain.model.AuthToken;

public interface AuthenticateUserUseCase {
    AuthToken execute(String email, String password);
}
