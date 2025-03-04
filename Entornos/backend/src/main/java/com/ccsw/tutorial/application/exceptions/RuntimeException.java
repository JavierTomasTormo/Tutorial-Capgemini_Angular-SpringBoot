package com.ccsw.tutorial.application.exceptions;

/**
 * Base runtime exception for the Tutorial application.
 * This exception serves as the parent class for all custom exceptions
 * in the application, providing consistent error handling.
 */
public class TutorialException extends RuntimeException {
    
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructs a new exception with null as its detail message.
     */
    public TutorialException() {
        super();
    }
    
    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message
     */
    public TutorialException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new exception with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public TutorialException(Throwable cause) {
        super(cause);
    }
    
    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public TutorialException(String message, Throwable cause) {
        super(message, cause);
    }
}