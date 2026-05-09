package com.alexander.cv_project.auth.domain.model;

import com.alexander.cv_project.auth.domain.exception.ValidationException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Role {

    private Long id;
    private String code;
    private String name;
    private String description;

    public Role(Long id, String code, String name, String description) {
        validate(code, name);
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
    }

    private void validate(String code, String name) {
        if (code == null || code.isBlank()) {
            throw new ValidationException("Code is required");
        }
        if (name == null || name.isBlank()) {
            throw new ValidationException("Name is required");
        }
    }
}
