package com.alexander.cv_project.auth.domain.model;

import java.util.HashSet;
import java.util.Set;

import com.alexander.cv_project.auth.domain.exception.ValidationException;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String email;
    private String password;
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    public User(Long id, String email, String password, Set<Role> roles) {
        validate(email, password);
        this.id = id;
        this.email = email;
        this.password = password;
        this.roles = roles == null ? new HashSet<>() : roles;
    }

    private void validate(String email, String password) {
        if (email == null || email.isBlank()) {
            throw new ValidationException("Email is required");
        }

        if (password == null || password.isBlank()) {
            throw new ValidationException("Password is required");
        }
    }
}
