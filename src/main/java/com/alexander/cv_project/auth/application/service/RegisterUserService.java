package com.alexander.cv_project.auth.application.service;

import com.alexander.cv_project.auth.application.port.in.RegisterUserUseCase;
import com.alexander.cv_project.auth.domain.exception.UserAlreadyExistsException;
import com.alexander.cv_project.auth.domain.model.User;
import com.alexander.cv_project.auth.domain.port.out.PasswordEncoderPort;
import com.alexander.cv_project.auth.domain.port.out.UserRepositoryPort;

public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;

    public RegisterUserService(UserRepositoryPort userRepository, PasswordEncoderPort passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User execute(User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(existingUser -> {
                    throw new UserAlreadyExistsException(user.getEmail());
                });

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
