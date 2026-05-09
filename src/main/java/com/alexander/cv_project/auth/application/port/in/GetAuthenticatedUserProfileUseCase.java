package com.alexander.cv_project.auth.application.port.in;

import com.alexander.cv_project.auth.application.dto.UserProfileResponse;

public interface GetAuthenticatedUserProfileUseCase {
    UserProfileResponse execute();
}
