package com.alexander.cv_project.auth.domain.port.out;

import com.alexander.cv_project.auth.domain.model.RefreshToken;
import com.alexander.cv_project.auth.domain.model.User;

public interface RefreshTokenServicePort {
    RefreshToken create(User user);

    RefreshToken validate(String token);

    void deleteByUserId(Long userId);
}
