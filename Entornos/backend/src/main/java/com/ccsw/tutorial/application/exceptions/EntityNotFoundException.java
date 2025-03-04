package com.ccsw.tutorial.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends TutorialException {
    
    private static final long serialVersionUID = 1L;
    
    public EntityNotFoundException(String entityName, Long id) {
        super(entityName + " with id " + id + " not found");
    }
    
    public EntityNotFoundException(String message) {
        super(message);
    }
}