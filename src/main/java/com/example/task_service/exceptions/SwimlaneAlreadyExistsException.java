package com.example.task_service.exceptions;

public class SwimlaneAlreadyExistsException extends RuntimeException {
    public SwimlaneAlreadyExistsException(String message) {
        super(message);
    }
}
