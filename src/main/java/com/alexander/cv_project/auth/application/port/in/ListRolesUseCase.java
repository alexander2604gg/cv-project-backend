package com.alexander.cv_project.auth.application.port.in;

import java.util.List;

import com.alexander.cv_project.auth.domain.model.Role;

public interface ListRolesUseCase {
    List<Role> execute();
}
