package com.ccsw.tutorial.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an invalid argument is provided to a method.
 * This is different from Java's built-in IllegalArgumentException as it
 * extends our base TutorialException and includes Spring's ResponseStatus.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidArgumentException extends TutorialException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidArgumentException(String message) {
        super(message);
    }
    
    /**
     * Construye una nueva excepción con un mensaje formateado para valores de campo no válidos.
     *
     * @param nombreCampo el nombre del campo que contiene un valor no válido
     * @param valor el valor no válido
     */
    public InvalidArgumentException(String nombreCampo, Object valor) {
        super(String.format("Valor no válido '%s' para el campo '%s'", valor, nombreCampo));
    }
    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}