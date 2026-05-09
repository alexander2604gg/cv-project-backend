package com.alexander.cv_project.auth.domain.exception;

public class PersistenceException extends DomainException {
    public PersistenceException(String message) {
        super("PERSISTENCE_ERROR", message);
    }
}
