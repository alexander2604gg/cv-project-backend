package com.alexander.cv_project.auth.application.port.in;

import com.alexander.cv_project.auth.domain.model.Role;

public interface CreateRoleUseCase {
    Role execute(Role role);
}
