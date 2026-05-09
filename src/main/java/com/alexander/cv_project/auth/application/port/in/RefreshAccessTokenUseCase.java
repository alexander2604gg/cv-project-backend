package com.alexander.cv_project.auth.application.port.in;

import com.alexander.cv_project.auth.domain.model.AuthToken;

public interface RefreshAccessTokenUseCase {
    AuthToken execute(String refreshToken);
}
