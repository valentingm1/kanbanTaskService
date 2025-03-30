package com.example.task_service.exceptions;

import com.example.task_service.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SwimlaneAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // 409 Conflict
    @ResponseBody
    public ErrorResponse handleSwimlaneAlreadyExists(SwimlaneAlreadyExistsException ex) {
        return new ErrorResponse("Conflict", ex.getMessage());
    }
}